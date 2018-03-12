package com.mahbub.tourmate.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mahbub.tourmate.R;
import com.mahbub.tourmate.activities.HomeActivity;
import com.mahbub.tourmate.model.TourEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEventFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "create";
    public static boolean UPDATE_FLAG = false;
    private EditText eventNameEdit;
    private EditText eventstartLocEdit;
    private EditText eventDesLocEdit;
    private EditText eventNumOfPeopleEdit;
    private EditText eventBudgetEdit;
    private Button saveEventButton;
    private Button datePickBitton;
    private TextView titleTv;

    private int year,month,day;
    private Calendar calendar;

    private Context mContext;

    private String departureTime;
    private String currentTimeDate;
    private TourEvent mEvent;


    private DatabaseReference mReference;

    public CreateEventFragment() {
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
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);

        mReference = FirebaseDatabase.getInstance().getReference().child(HomeActivity.mUserID).child("Event");

        eventNameEdit = view.findViewById(R.id.event_name_edit);
        eventstartLocEdit = view.findViewById(R.id.starting_loc_edit);
        eventDesLocEdit = view.findViewById(R.id.destination_loc_edit);
        eventNumOfPeopleEdit = view.findViewById(R.id.members_edit);
        eventBudgetEdit = view.findViewById(R.id.budget_edit);
        saveEventButton = view.findViewById(R.id.save_event_btn);
        datePickBitton = view.findViewById(R.id.departure_time_btn);
        titleTv = view.findViewById(R.id.title_create);


        //Calender
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year,month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM/yyyy");
        currentTimeDate = sdf.format(calendar.getTime());
        datePickBitton.setText(currentTimeDate);

        if (mEvent != null) {
            saveEventButton.setText("update");
            titleTv.setText("Update Event");
            updateUIforEventEupdate();
        }else{
            titleTv.setText("New Event");
            saveEventButton.setText("save");
        }
        saveEventButton.setOnClickListener(this);
        datePickBitton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(mContext, datePickerListeenr, year, month, day);
                dpd.show();
            }
        });

        return view;
    }

    private void updateUIforEventEupdate() {
        UPDATE_FLAG = true;
        eventNameEdit.setText(mEvent.getEventName());
        eventstartLocEdit.setText(mEvent.getSatrtLocationName());
        eventDesLocEdit.setText(mEvent.getDestLocationName());
        eventBudgetEdit.setText(mEvent.getEstimateBudget()+"");
        eventNumOfPeopleEdit.setText(mEvent.getNumOfPeople()+"");
        datePickBitton.setText(mEvent.getStartingDate());
    }

    DatePickerDialog.OnDateSetListener datePickerListeenr = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM/yyyy");
            if (year <= y && m >= month && d >= day) {
                calendar.set(y, m, d);
                departureTime = sdf.format(calendar.getTime());
                datePickBitton.setText(departureTime);
            }else{
                Toast.makeText(mContext, "Date not valid" ,Toast.LENGTH_SHORT).show();
            }


        }
    };

    @Override
    public void onClick(View v) {
        String name = eventNameEdit.getText().toString();
        String start = eventstartLocEdit.getText().toString();
        String dest = eventDesLocEdit.getText().toString();
        String people = eventNumOfPeopleEdit.getText().toString();
        String budget = eventBudgetEdit.getText().toString();

        if (!TextUtils.isEmpty(name)
                && !TextUtils.isEmpty(start)
                && !TextUtils.isEmpty(dest)
                && !TextUtils.isEmpty(people)
                && !TextUtils.isEmpty(budget)
                && !TextUtils.isEmpty(currentTimeDate)
                && !TextUtils.isEmpty(departureTime) ) {
            if (HomeActivity.mUserID != null) {
                double budgetMoney = Double.parseDouble(budget);
                int mem = Integer.parseInt(people);


                if (!UPDATE_FLAG ) {
                    String key = mReference.push().getKey();
                    mEvent = new TourEvent(key,HomeActivity.mUserID,name, start, dest, departureTime, currentTimeDate, mem, budgetMoney);
                    Toast.makeText(mContext, "Event saved successfully!", Toast.LENGTH_SHORT).show();
                    mReference.child(key).setValue(mEvent);
                    clearFields();
                } else if (UPDATE_FLAG) {
                    mEvent =  new TourEvent(mEvent.getEventID(), HomeActivity.mUserID,name, start, dest, departureTime, currentTimeDate, mem, budgetMoney);
                    updateEvent("Event updated successfully!", "Event update failed!" );
                    clearFields();
                } else {
                    Toast.makeText(mContext, "Event save Failed!", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(mContext, "User couldn't found", Toast.LENGTH_SHORT).show();
            }

        }else {
            Snackbar.make(v, "All fields are required.", Snackbar.LENGTH_SHORT).show();
        }

    }

    private void updateEvent(final String succMgs, final String failMsg) {
        if (mEvent == null) {
            return;
        }
        mReference = FirebaseDatabase.getInstance().getReference().child(HomeActivity.mUserID).child("Event");
        mReference.child(mEvent.getEventID()).child("eventName").setValue(mEvent.getEventName());
        mReference.child(mEvent.getEventID()).child("satrtLocationName").setValue(mEvent.getSatrtLocationName());
        mReference.child(mEvent.getEventID()).child("destLocationName").setValue(mEvent.getDestLocationName());
        mReference.child(mEvent.getEventID()).child("startingDate").setValue(mEvent.getStartingDate());
        mReference.child(mEvent.getEventID()).child("creatingDate").setValue(mEvent.getCreatingDate());
        mReference.child(mEvent.getEventID()).child("numOfPeople").setValue(mEvent.getNumOfPeople());
        mReference.child(mEvent.getEventID()).child("estimateBudget").setValue(mEvent.getEstimateBudget())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(mContext, succMgs , Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(mContext, failMsg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private String getTimeInMilis() {
        long date = System.currentTimeMillis();
        return String.valueOf(date);

    }
    public void setUpdateEvent(TourEvent event) {
        mEvent = event;
    }
    private void clearFields() {
        eventNameEdit.setText("");
        eventstartLocEdit.setText("");
        eventDesLocEdit.setText("");
        eventBudgetEdit.setText("");
        eventNumOfPeopleEdit.setText("");
        datePickBitton.setText(currentTimeDate);
        UPDATE_FLAG = false;

    }
}
