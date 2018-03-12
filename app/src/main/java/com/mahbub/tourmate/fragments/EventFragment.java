package com.mahbub.tourmate.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mahbub.tourmate.R;
import com.mahbub.tourmate.activities.HomeActivity;
import com.mahbub.tourmate.adapter.EventViewAdapter;
import com.mahbub.tourmate.database.FireDB;
import com.mahbub.tourmate.model.TourEvent;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {

    public static final String TAG = "event";

    private Context mContext;
    private TextView emptyEventTV;
    private FloatingActionButton createEventFloatButton;
    private RecyclerView eventRecyclerView;
    private boolean isEmptyEventList = true;

    private OnCreateButtonClickListener mCreateButtonClickListener;
    private OnEventClickListener mEventClickListener;

    private EventViewAdapter mAdapter;
    private DatabaseReference mReference;
    private ValueEventListener mEventListener;

    private ProgressDialog mProgressDialog;

    ArrayList<TourEvent> mTourEvents = new ArrayList<>();

    public EventFragment() {
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
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Please wait...");

        mReference = FireDB.getInstance().getReference().child(HomeActivity.mUserID).child("Event");

        emptyEventTV = view.findViewById(R.id.empt_evt_tv);
        createEventFloatButton = view.findViewById(R.id.create_float_btn);
        eventRecyclerView = view.findViewById(R.id.event_rcv);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));


        createEventFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCreateButtonClickListener != null) {
                    mCreateButtonClickListener.onCreateButtonClicked();
                }
            }
        });


        return view;
    }




    public void updateList() {
        mProgressDialog.show();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: in frag "+dataSnapshot.getKey());
                TourEvent event = dataSnapshot.getValue(TourEvent.class);
                if (event != null) {
                    mTourEvents.add(event);
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        eventRecyclerView.setHasFixedSize(true);
        mReference.addValueEventListener(eventListener);
        mEventListener = eventListener;
        mAdapter = new EventViewAdapter(mContext, mReference);
        eventRecyclerView.setAdapter(mAdapter);
        if (mTourEvents != null ) {
            emptyEventTV.setVisibility(View.INVISIBLE);
        }else{
            emptyEventTV.setVisibility(View.VISIBLE);
        }


        mAdapter.setOnItemClicked(new EventViewAdapter.OnItemClicked() {
            @Override
            public void onItemPressed(TourEvent event) {
                if (mEventClickListener != null) {
                    mEventClickListener.onEventClicked(event);
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
        if (mEventListener != null) {
            mReference.removeEventListener(mEventListener);
        }

        // Clean up listener
        mAdapter.cleanupListener();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setOnCreateButtonClickListener(OnCreateButtonClickListener listener) {
        this.mCreateButtonClickListener = listener;
    }
    public void setOnEventClickListener(OnEventClickListener listener) {
        this.mEventClickListener = listener;
    }


    public interface OnCreateButtonClickListener{
        void onCreateButtonClicked();
    }
    public interface OnEventClickListener{
        void onEventClicked(TourEvent event);
    }

}
