package com.mahbub.tourmate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.mahbub.tourmate.R;
import com.mahbub.tourmate.model.Task;
import com.mahbub.tourmate.model.TourEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mahbuburrahman on 2/16/18.
 */

public class EventViewAdapter extends RecyclerView.Adapter<EventViewAdapter.EventHolder> {
    private Context mContext;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    public static final String TAG = "adapter";

    private List<TourEvent> mTourEvents = new ArrayList<>();


    private OnItemLongClicked mItemLongClicked;
    private OnItemClicked mItemClicked;

    public EventViewAdapter(Context context, DatabaseReference databaseReference) {
        mContext = context;
        mDatabaseReference = databaseReference;

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded: "+dataSnapshot.getKey());

                TourEvent event = dataSnapshot.getValue(TourEvent.class);
                if (event != null) {
                    mTourEvents.add(event);
                    notifyDataSetChanged();
                }
             //   notifyItemInserted(mTourEvents.size() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged: "+dataSnapshot.getKey());


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved: "+dataSnapshot.getKey());

                notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildMoved: "+dataSnapshot.getKey());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: "+databaseError.toException());

            }
        };


        mDatabaseReference.addChildEventListener(childEventListener);

        mChildEventListener = childEventListener;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(mContext).inflate(R.layout.event_list_layout, parent, false);
        EventHolder holder = new EventHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        EventHolder eventHolder =  holder;
        final TourEvent event = mTourEvents.get(position);
        eventHolder.eventNameTv.setText(event.getEventName());
        eventHolder.creatingDateTv.setText("Created on: "+event.getCreatingDate());
        eventHolder.startingTimeTv.setText("Start date: "+event.getStartingDate());

        long DaysLeft = getDaysLeft(event.getStartingDate());
        if (DaysLeft > 0) {
            eventHolder.daysLeftTv.setVisibility(View.VISIBLE);
            if (DaysLeft > 1) {
                eventHolder.daysLeftTv.setText(DaysLeft+" days left.");
            }else {
                eventHolder.daysLeftTv.setText(DaysLeft+" days left.");
            }
        }else if(DaysLeft == 0){
            eventHolder.daysLeftTv.setText("Today.");
        }
        else{
            eventHolder.daysLeftTv.setVisibility(View.INVISIBLE);
        }

        eventHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClicked != null) {
                    //TODO:
                    mItemClicked.onItemPressed(event);
                }
            }
        });
        eventHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //TODO:
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mTourEvents != null) {
            return mTourEvents.size();
        }
        return 0;
    }

    private long getDaysLeft(String startingDate) {
        //TODO: calculate days left
        String DATE_FORMAT = "EEE dd/MM/yyyy";

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        String current = dateFormat.format(calendar.getTime());

        Date startDate, endDate;
        long numberOfDays = 0;
        try {
            startDate = dateFormat.parse(startingDate);
            endDate = dateFormat.parse(current);
            numberOfDays = getUnitBetweenDates(startDate, endDate, TimeUnit.DAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "getDaysLeft: " +numberOfDays +" "+ current);
        return numberOfDays;

    }
    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        long timeDiff = startDate.getTime() - endDate.getTime();
        Log.d(TAG, "getUnitBetweenDates: "+timeDiff);
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }
    public static class EventHolder extends RecyclerView.ViewHolder {
        TextView eventNameTv;
        TextView creatingDateTv;
        TextView startingTimeTv;
        TextView daysLeftTv;
        View mView;
        public EventHolder(View itemView) {
            super(itemView);
            mView = itemView;
            eventNameTv = itemView.findViewById(R.id.event_name_tv);
            creatingDateTv = itemView.findViewById(R.id.creating_time_tv);
            startingTimeTv = itemView.findViewById(R.id.staring_time_tv);
            daysLeftTv = itemView.findViewById(R.id.days_left_tv);
        }
    }
    public void cleanupListener() {
        if (mChildEventListener != null) {
            mDatabaseReference.removeEventListener(mChildEventListener);
        }
    }

    public void setOnItemClicked(OnItemClicked listener){
        mItemClicked = listener;
    }


    public interface OnItemClicked{
        void onItemPressed(TourEvent event);
    }

    public void setOnItemLongClicked(OnItemLongClicked listener){
        mItemLongClicked = listener;
    }

    public interface OnItemLongClicked{
        void onItemLongPressed(String id);
    }

}
