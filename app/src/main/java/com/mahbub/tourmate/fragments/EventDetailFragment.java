package com.mahbub.tourmate.fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mahbub.tourmate.R;
import com.mahbub.tourmate.activities.HomeActivity;
import com.mahbub.tourmate.adapter.ExpandableListAdapter;
import com.mahbub.tourmate.database.DataSource;
import com.mahbub.tourmate.model.EventExpense;
import com.mahbub.tourmate.model.TourEvent;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailFragment extends Fragment implements ExpandableListView.OnGroupClickListener,
        ExpandableListView.OnGroupExpandListener,
        ExpandableListView.OnGroupCollapseListener,
        ExpandableListView.OnChildClickListener

{

    private static final String TAG = "event_detail";
    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    private Context mContext;
    private int eventID = -1;
    private TourEvent mEvent = null;

    private TextView eventNamtTv,budgetStatusTv,progressStatusTv;
    private ProgressBar budgetProgressBar;

    private TextView eventDetailTv;
    private boolean cannotExpense = false;
    private double expense = 0;
    private ArrayList<EventExpense> allExpense = new ArrayList<>();
    private ArrayAdapter<String> expenseAdapter;
    private  DecimalFormat df = new DecimalFormat("#.#");

    private OnEventEditClickListener editListener;
    private OnCameraClickListener camreListener;
    private OnMomentsViewListener momentViewListenr;
    private OnDeleteEventClickedListenr eventDeleteListenr;
    private boolean isLongPressed = false;

    private DatabaseReference mReference;
    private DatabaseReference mEventRef;
    private DatabaseReference mExpenseRef;

    private ProgressDialog mProgressDialog;

    public EventDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Please wait...");

        mEventRef = FirebaseDatabase.getInstance().getReference().child(HomeActivity.mUserID).child("Event");
        mExpenseRef = FirebaseDatabase.getInstance().getReference().child(HomeActivity.mUserID).child("Event");
        mReference = FirebaseDatabase.getInstance().getReference().child(HomeActivity.mUserID).child("Event");

        eventNamtTv = view.findViewById(R.id.event_title_tv);
        budgetStatusTv = view.findViewById(R.id.budget_status_tv);
        progressStatusTv = view.findViewById(R.id.progress_status_tv);
        eventDetailTv = view.findViewById(R.id.event_detail_tv);

        budgetProgressBar = view.findViewById(R.id.progressBar);

        expListView = view.findViewById(R.id.event_expendble_list);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(mContext, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnGroupClickListener(this);
        expListView.setOnGroupExpandListener(this);
        expListView.setOnGroupCollapseListener(this);
        expListView.setOnChildClickListener(this);


        if (mEvent != null) {
            setEventDetails();
        }

        return view;
    }

    private void setEventDetails() {
        cannotExpense = false;
        expense = 0;
        allExpense = new ArrayList<>();
        mProgressDialog.show();
        mExpenseRef = mReference.child(mEvent.getEventID()).child("Expense");
        mExpenseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                expense = 0;
                allExpense.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    EventExpense e = d.getValue(EventExpense.class);
                    if (e != null) {
                        allExpense.add(e);
                        expense += e.getExpenseCost();
                        Log.d(TAG, "onDataChange: "+expense +"\n");
                    }
                }
                if (mEvent != null) {
                    Log.d(TAG, "onCreateView: "+( mEvent==null));
                    setDetails();
                }
                mProgressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        if (allExpense != null) {
//            for (EventExpense ex: allExpense) {
//                expense += ex.getExpenseCost();
//                Log.d(TAG, "setEventDetails: "+expense+"\n");
//            }
//        }


    }

    private void setDetails() {
        double expenseStatus =( expense / mEvent.getEstimateBudget() ) * 100;
        if (expenseStatus >= 100) {
            expenseStatus = 100;
            cannotExpense = true;
        }else{
            cannotExpense = false;
        }
        Log.d(TAG, "expense Status: "+expenseStatus);

        df = new DecimalFormat("#.#");
        double onDigitsD = Double.valueOf(df.format(expenseStatus));

        eventNamtTv.setText(mEvent.getEventName());
        budgetStatusTv.setText("Budget Status("+  expense +"/"+ mEvent.getEstimateBudget()+")");
        progressStatusTv.setText(onDigitsD +"%");

        if (expenseStatus >= 70.0) {
            budgetProgressBar.setProgress(0);
            budgetProgressBar.setSecondaryProgress((int) onDigitsD);
        }else{
            budgetProgressBar.setProgress((int)onDigitsD);
            budgetProgressBar.setSecondaryProgress(0);
        }
        if (mEvent.getNumOfPeople() < 1 ){
            mEvent.setNumOfPeople(1);
        }

        String details;
        if (mEvent.getNumOfPeople() == 1) {
            details = "Total: "+mEvent.getNumOfPeople() + " person" +
                    "\nStart date: "+mEvent.getStartingDate() +
                    "\nToday's date: "+getCurrentDate();
        }else {
            details = "Total: " + mEvent.getNumOfPeople() +" peoples" +
                    "\nPer person cost: " + Double.valueOf(df.format((mEvent.getEstimateBudget() / mEvent.getNumOfPeople()))) +
                    "\nStart date: " + mEvent.getStartingDate() +
                    "\nToday's date: " + getCurrentDate();
        }
        eventDetailTv.setText(details);
        Log.d(TAG, "setEventDetails: "+expense +" "+expenseStatus);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Expenditure");
        listDataHeader.add("Moments");
        listDataHeader.add("More on Event");

        // Adding child data
        List<String> expenditureMenu = new ArrayList<String>();
        expenditureMenu.add("Add New Expense");
        expenditureMenu.add("View All Expense");
        expenditureMenu.add("Add More Budget");
        expenditureMenu.add("Add or Remove Member");

        List<String> momentsMenu = new ArrayList<String>();
        momentsMenu.add("Take a Photo");
        //momentsMenu.add("View Gallery");
        momentsMenu.add("View All Moments");

        List<String> moreOnMenu = new ArrayList<String>();
        moreOnMenu.add("Edit Event");
        moreOnMenu.add("Delete Event");

        // Header, Child data
        listDataChild.put(listDataHeader.get(0), expenditureMenu);
        listDataChild.put(listDataHeader.get(1), momentsMenu);
        listDataChild.put(listDataHeader.get(2), moreOnMenu);
    }
    public void setEvetID(TourEvent event){
      mEvent = event;
        Log.d(TAG, "setEvetID: "+(mEvent == null));
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        return false;
    }
    @Override
    public void onGroupExpand(int groupPosition) {
//        Toast.makeText(mContext,
//                listDataHeader.get(groupPosition) + " Expanded",
//                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onGroupCollapse(int groupPosition) {
//        Toast.makeText(mContext,
//                listDataHeader.get(groupPosition) + " Collapsed",
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Toast.makeText(
                mContext,
                listDataHeader.get(groupPosition)
                        + " : "
                        + listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition), Toast.LENGTH_SHORT)
                .show();
        showDialogOnChildClick(listDataChild.get(
                listDataHeader.get(groupPosition)).get(
                childPosition));
        return false;
    }


    private void showDialogOnChildClick(String child){
       if (child.equals("Add New Expense")){
           showAddExpenseDialog(null);
       }else if (child.equals("Add New Expense")){
           if (!cannotExpense) {
               showAddMoreBudgetDialog();
           }else {
               Toast.makeText(mContext, "Please add money for expense.",Toast.LENGTH_SHORT).show();
           }
       }else if (child.equals("View All Expense")){
           showExpenseListDialog();
       }else if (child.equals("Add More Budget")){
           showAddMoreBudgetDialog();
       }else if (child.equals("Add or Remove Member")){
           showAddOrRemoveMemberDialog();

       }else if (child.equals("Take a Photo")){
           //TODO: photo
           Log.d(TAG, "showDialogOnChildClick");
           if (camreListener != null) {
               camreListener.onCameraClicked(mEvent.getEventID());
           }else{
               Log.d(TAG, "showDialogOnChildClick: null");
           }

       }else if (child.equals("View All Moments")){
           //TODO: gallery
           if (momentViewListenr != null) {
               momentViewListenr.onGalleryViewClicked(mEvent.getEventID());
           }

       }else if (child.equals("Edit Event")){
           if (editListener != null){
               editListener.onEditClicked(mEvent);
           }

       }else if (child.equals("Delete Event")){
           showEventDeleteDialog();

       }
    }

    private void showEventDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog
                .Builder(mContext)
                .setTitle("Delete Alert!")
                .setMessage("Are you sure? want to delete this event.")
                .setIcon(android.R.drawable.ic_delete)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String eid = mEvent.getEventID();
                            mEventRef.child(eid).removeValue();
                        if (eventDeleteListenr != null) {
                                eventDeleteListenr.onEventDelete();
                            Toast.makeText(mContext, "Event Deleted",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(mContext, "Event could not delete.Pleas try again.",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showAddOrRemoveMemberDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.add_new_member_layout);
        dialog.setCancelable(true);
        final EditText edit = dialog.findViewById(R.id.add_member_edit);
        Button save = dialog.findViewById(R.id.save_member_btn);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edit.getText().toString())) {

                    int people = mEvent.getNumOfPeople() + Integer.parseInt(edit.getText().toString());
                    if (people < 1) {
                        Toast.makeText(mContext, "Event Can not have empty people", Toast.LENGTH_SHORT).show();
                    }else {
                        mEvent.setNumOfPeople(people);
                        updateEvent("Event members updated", "Event members update failed");

                    }
                    dialog.dismiss();

                }
            }
        });

        dialog.show();
        return;
    }

    private void showExpenseListDialog() {
        if (allExpense == null || allExpense.size() == 0) {
            return;
        }
        isLongPressed = false;
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.all_event_expense_list);
        dialog.setCancelable(true);
        //dialog.setTitle("Title...");

        final ListView lv = dialog.findViewById(R.id.expense_lv);
        TextView tv = dialog.findViewById(R.id.total_expense);
        tv.setText("Total cost: "+expense);
        final String[] listOfExpense = new String[allExpense.size()];
        int i = 0;
        int people = mEvent.getNumOfPeople();
        for (EventExpense ex : allExpense) {
            String s;
 //           if (people == 1) {
                s = (i + 1) + "). " + getFormattedString(ex.getExpenseName() + ": ") + ex.getExpenseCost() + "/-\n\t" + ex.getExpenseDate() + "\n";
//            }else{
//                s = (i + 1) + ")." + getFormattedString(ex.getExpenseName() + ": ") + ex.getExpenseCost() + "/-\n\t" + getFormattedString("Per person cost:  ") +
//                        Double.valueOf(df.format((ex.getExpenseCost() / mEvent.getNumOfPeople()))) + "/-\n\t" + ex.getExpenseDate() + "\n";
//            }
            listOfExpense[i++] = s;
            Log.d(TAG, "showExpenseListDialog: "+s);
        }

        expenseAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,listOfExpense);
        lv.setAdapter(expenseAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(mContext, listOfExpense[position] , Toast.LENGTH_SHORT).show();
                if (!isLongPressed) {
                    showAddExpenseDialog(allExpense.get(position));
                    dialog.dismiss();
                }
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                isLongPressed  =true;
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                        .setTitle("Delete Expense Alert!")
                        .setMessage("Are you sure? want to delete this expense")
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               String key = allExpense.get(position).getExpenseID();
                                    mExpenseRef = mReference.child(mEvent.getEventID()).child("Expense");
                                    mExpenseRef.child(key).removeValue();
                                    setEventDetails();
                                    Toast.makeText(mContext, "Expense has been deleted", Toast.LENGTH_SHORT).show();
                               // dialog.dismiss();

                            }
                        })
                        .setNegativeButton("NO",null );
                builder.show();

                return false;
            }
        });

        dialog.show();
        return;
    }
    private String getFormattedString(String s) {
        int len = s.length();
        for(int i = len; i <= 30; i++ ){
            s += " ";
        }
        return s;
    }

    private void showAddMoreBudgetDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.more_budget_add_layout);
        dialog.setCancelable(true);
        //dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        final EditText costEdit =  dialog.findViewById(R.id.extra_cost_edit);

        Button saveMoreBudget = dialog.findViewById(R.id.save_more_budget_btn);
        saveMoreBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String costString = costEdit.getText().toString();

                if (!TextUtils.isEmpty(costString)) {
                    double cost = Double.parseDouble(costString);
                    updateEventBudget(cost);
                    dialog.dismiss();
                }else {
                    Toast.makeText(mContext, "add amount", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
        return;
    }

   private void updateEvent(final String succMgs, final String failMsg) {
        if (mEvent == null) {
            return;
        }

       mEventRef.child(mEvent.getEventID()).child("eventName").setValue(mEvent.getEventName());
       mEventRef.child(mEvent.getEventID()).child("satrtLocationName").setValue(mEvent.getSatrtLocationName());
       mEventRef.child(mEvent.getEventID()).child("destLocationName").setValue(mEvent.getDestLocationName());
       mEventRef.child(mEvent.getEventID()).child("startingDate").setValue(mEvent.getStartingDate());
       mEventRef.child(mEvent.getEventID()).child("creatingDate").setValue(mEvent.getCreatingDate());
       mEventRef.child(mEvent.getEventID()).child("numOfPeople").setValue(mEvent.getNumOfPeople());
       mEventRef.child(mEvent.getEventID()).child("estimateBudget").setValue(mEvent.getEstimateBudget())
               .addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
               if (task.isSuccessful()){
                   setEventDetails();
                   Toast.makeText(mContext, succMgs , Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(mContext, failMsg, Toast.LENGTH_SHORT).show();
               }
           }
       });

    }
    private void updateEventBudget(double cost) {
        mEvent.setEstimateBudget(mEvent.getEstimateBudget() + cost);
        updateEvent("Budget added successfully.", "Budget could not added.Pleas try again.");

    }

    private void showAddExpenseDialog(final EventExpense ex ) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.add_expance_layout);
        dialog.setCancelable(false);
        //dialog.setTitle("Title...");

        // set the custom dialog components - text, image and button
        final EditText costEdit =  dialog.findViewById(R.id.expance_cost_edit);
        final EditText detailEdit =  dialog.findViewById(R.id.expanse_detail_edit);
        Button expenseSaveButton = dialog.findViewById(R.id.save_expense_btn);
        final Button expenseCancelButton = dialog.findViewById(R.id.cancel_expense_btn);
        double costPrev = 0;
        if (ex != null) {
            costEdit.setText(ex.getExpenseCost()+"");
            detailEdit.setText(ex.getExpenseName());
        }

        expenseSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String costString = costEdit.getText().toString();
                String detailString = detailEdit.getText().toString();
                if (ex != null) {
                   // expense -= ex.getExpenseCost();
                }

               if (!TextUtils.isEmpty(costString) && !TextUtils.isEmpty(detailString)) {
                   double cost = Double.parseDouble(costString);
                   if (ex != null) {
                       ex.setEventExpenseID(mEvent.getEventID());
                       ex.setExpenseCost(cost);
                       ex.setExpenseName(detailString);
                       ex.setExpenseDate(getCurrentDate());
                       updateEventExpense(ex);
                   }else{
                       updateEventExpense(detailString, cost);
                   }

                   dialog.dismiss();
               }else {
                   Toast.makeText(mContext, "All fields are required.", Toast.LENGTH_SHORT).show();
               }
            }
        });
        expenseCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(mContext, "Expense didn't saved", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
        return;
    }

    private void updateEventExpense(String detailString, double cost) {
        if (expense + cost > mEvent.getEstimateBudget() ){
            Snackbar.make(getView(), "Cannot add this expense it's exceed the current budget.", Snackbar.LENGTH_LONG).show();
            return;
        }
        mExpenseRef = mReference.child(mEvent.getEventID()).child("Expense");
        String key = mExpenseRef.push().getKey();
        mExpenseRef.child(key).setValue(new EventExpense(key, mEvent.getEventID(), detailString, cost, getCurrentDate())).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if( task.isSuccessful()){
                    setEventDetails();
                    Toast.makeText(mContext, "Expense saved", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext, "Expense save failed. Please try again!", Toast.LENGTH_LONG).show();
                }
            }
        }) ;


    }

    private void updateEventExpense(EventExpense exp) {

        if (mEvent != null && exp != null) {
            if (expense+exp.getExpenseCost() > mEvent.getEstimateBudget() ){
                Snackbar.make(getView(), "Cannot add this expense it's exceed the current budget.", Snackbar.LENGTH_LONG).show();
                return;
            }
            String key;
            if (exp != null) {
                key = exp.getExpenseID();
            }else{
                key = mExpenseRef.push().getKey();
                exp.setExpenseID(key);
            }
            mExpenseRef = mReference.child(mEvent.getEventID()).child("Expense");
            mExpenseRef.child(key).setValue(exp).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        setEventDetails();
                        Toast.makeText(mContext, "Expense saved", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mContext, "Expense save failed. Please try again!", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM/yyyy hh:mm a");
        return sdf.format(calendar.getTime());
    }
    public void setOnEventEditClickListener(OnEventEditClickListener listener) {
        editListener = listener;
    }
    public void setOnCameraClickListener(OnCameraClickListener listener) {
        camreListener = listener;
    }
    public void setOnMomentsViewListener(OnMomentsViewListener listener) {
        momentViewListenr = listener;
    }

    public void setOnDeleteEventClickedListenr(OnDeleteEventClickedListenr listener) {
        eventDeleteListenr = listener;
    }

    @Override
    public void onStart() {
        super.onStart();
       // setEventDetails();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        mEvent = null;
    }

    public interface OnEventEditClickListener{
            void onEditClicked(TourEvent event);
    }
    public interface OnCameraClickListener{
        void onCameraClicked(String eid);
    }
    public interface OnMomentsViewListener{
        void onGalleryViewClicked(String eid);
    }
    public interface OnDeleteEventClickedListenr{
        void onEventDelete();
    }

}
