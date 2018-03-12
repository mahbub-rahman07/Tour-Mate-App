package com.mahbub.tourmate.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahbub.tourmate.R;
import com.mahbub.tourmate.activities.HomeActivity;
import com.mahbub.tourmate.database.DataSource;
import com.mahbub.tourmate.model.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTodoFragment extends Fragment {
    private EditText editTask;
    private Button addTodoBtn, dateBtn,timeBtn;
    private Context mContext;
    private int year, month, day, hour, minute;
    private Calendar calendar;
    private Task mTask;
    private boolean updateFlag = false;
    private String dateString, timeString;

    private DatabaseReference mReference;

    public AddTodoFragment() {
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
        View view = inflater.inflate(R.layout.fragment_add_todo, container, false);

        mReference = FirebaseDatabase.getInstance().getReference().child(HomeActivity.mUserID).child("Task");
        mReference.keepSynced(true);

        editTask = view.findViewById(R.id.editTast);
        addTodoBtn = view.findViewById(R.id.add_todo_btn);
        dateBtn = view.findViewById(R.id.date_pick_btn);
        timeBtn = view.findViewById(R.id.time_pick_btn);
        clearFields();

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        if (mTask != null) {
            editTask.setText(mTask.getName());
            addTodoBtn.setText("UPDATE");
            updateFlag = true;
        }


        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        mContext,dateListener,year,month,day
                );
                datePickerDialog.show();
            }
        });

        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        mContext,timeListener,hour,minute,true
                );
                timePickerDialog.show();
            }
        });

 //final String uid = FirebaseDatabase.getInstance().getReference().child("" +HomeActivity.mUser.getUserId()+ HomeActivity.mUser.getUserId()).child("task").push().getKey();

        addTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editTask.getText().toString()) &&
                        !TextUtils.isEmpty(timeString) &&
                        !TextUtils.isEmpty(dateString) ){

                    if (updateFlag) {
                        mTask.setName(editTask.getText().toString());
                        mTask.setTime(timeString+" on "+ dateString);
                    }else{
                        String key = mReference.push().getKey();
                        mTask = new Task(key, editTask.getText().toString(), timeString+" on "+ dateString, HomeActivity.mUserID);
                    }
                    mReference.child(mTask.getTask_id()).setValue(mTask);
                    clearFields();

                    Log.d("todo", "onClick: "+timeString+" "+dateString);
                    Toast.makeText(mContext, "Data saved Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mContext, "All fields are required", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }


    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int y, int m, int d) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd, MMM, yyyy");
            if (year <= y && month <= m && day <= d) {
                calendar.set(y, m, d);
                dateString = sdf.format(calendar.getTime());
                dateBtn.setText(dateString);

            }else{
                Toast.makeText(mContext, "Date not valid" ,Toast.LENGTH_SHORT).show();
            }
        }
    };


    TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            calendar.set(0,0,0,hourOfDay,minute);
            timeString = sdf.format(calendar.getTime());
            timeBtn.setText(timeString);
        }
    };

    public void clearFields() {
        editTask.setText("");
        timeBtn.setText("Tap to chose the time");
        dateBtn.setText("Tap to chose the date");
        addTodoBtn.setText("SAVE");
    }
    public void setUpdateTodo(Task task) {
        mTask = task;
    }

}
