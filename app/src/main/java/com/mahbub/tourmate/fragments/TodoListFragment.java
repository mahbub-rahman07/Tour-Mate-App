package com.mahbub.tourmate.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahbub.tourmate.R;
import com.mahbub.tourmate.activities.HomeActivity;
import com.mahbub.tourmate.adapter.TaskViewAdapter;
import com.mahbub.tourmate.database.DataSource;
import com.mahbub.tourmate.model.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodoListFragment extends Fragment {

    private static final String TAG = "todo";
    private RecyclerView list_task;
    private Context mContext;
    private TextView dayValue,dateValue;
    private FloatingActionButton addFloatBtn;
    private FloatingActionButton refreshFloatBtn;


    private ArrayList<Task> mTasks = new ArrayList<>();

    private TaskViewAdapter mAdapter;
    private DatabaseReference mReference;
    private ValueEventListener mTaskListener;

    private OnaddTodoClickListener mAddClickedListener;
    private OnTaskEditListener mEditListener;
    private OnTaskEditListener mTaskEditListener;
    private OnTaskClickedListener mTaskClickedListener;


    public TodoListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateList();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_list, container, false);
        dayValue =  view.findViewById(R.id.bannaerDay);
        dateValue =  view.findViewById(R.id.bannerDate);
        addFloatBtn =  view.findViewById(R.id.add_float_btn);

      //  refreshFloatBtn = view.findViewById(R.id.refresh_float_btn);
        // taskEmptyTv = view.findViewById(R.id.empty_task_tv);

        addFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAddClickedListener != null) {
                    mAddClickedListener.onAddTodoClicked();
                }
            }
        });

        list_task = view.findViewById(R.id.task_list);
        list_task.setLayoutManager(new LinearLayoutManager(mContext));


//        refreshFloatBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateList();
//            }
//        });

        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        long date = System.currentTimeMillis();

        sdf = new SimpleDateFormat("dd MMM yyy");
        String dateOftheday = sdf.format(date);

        dayValue.setText(dayOfTheWeek);
        dateValue.setText(dateOftheday);


        mReference = FirebaseDatabase.getInstance().getReference().child(HomeActivity.mUserID).child("Task");
        return view;

    }



    public void updateList() {
        ValueEventListener taskListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: in frag "+dataSnapshot.getKey());
                Task task = dataSnapshot.getValue(Task.class);
                mTasks.add(task);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        list_task.setHasFixedSize(true);
        mReference.addValueEventListener(taskListener);
        mTaskListener = taskListener;
        mAdapter = new TaskViewAdapter(mContext, mReference);
        list_task.setAdapter(mAdapter);
        mAdapter.setOnItemLongClicked(new TaskViewAdapter.OnItemLongClicked() {
            @Override
            public void onItemLongPressed(final String id) {
                new AlertDialog.Builder(mContext)
                        .setTitle("Delete Expense Alert!")
                        .setMessage("Are you sure ? want to delete this task")
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                mReference.child(id).removeValue();
                                mAdapter = new TaskViewAdapter(mContext, mReference);
                                updateList();
                                Toast.makeText(mContext, "Task has been deleted", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("NO",null )
                        .show();

            }
        });
        mAdapter.setOnItemClicked(new TaskViewAdapter.OnItemClicked() {
            @Override
            public void onItemPressed(Task task) {
                if (mTaskClickedListener != null) {
                    mTaskClickedListener.onTaskClicked(task);
                }
            }
        });
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // Remove post value event listener
        if (mTaskListener != null) {
            mReference.removeEventListener(mTaskListener);
        }

        // Clean up listener
        mAdapter.cleanupListener();
    }


    public void setOnaddTodoClickListener(OnaddTodoClickListener listener) {
        mAddClickedListener = listener;
    }
    public void setOnTaskEditListener(OnTaskEditListener listener) {
        mTaskEditListener = listener;
    }

    public void setOnTaskClickedListener(OnTaskClickedListener listener) {
        mTaskClickedListener = listener;
    }


    public interface OnTaskClickedListener{
        void onTaskClicked(Task task);
    }
    public interface OnaddTodoClickListener{
            void onAddTodoClicked();
    }
    public interface OnTaskEditListener{
        void onTaskEdit(Task task);
    }



}
