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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahbuburrahman on 2/16/18.
 */

public class TaskViewAdapter extends RecyclerView.Adapter<TaskViewAdapter.TodoHolder> {
    private Context mContext;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    public static final String TAG = "adapter";

    private List<Task> mTaskLists = new ArrayList<>();


    private OnItemLongClicked mItemLongClicked;
    private OnItemClicked mItemClicked;

    public TaskViewAdapter(Context context, DatabaseReference databaseReference) {
        mContext = context;
        mDatabaseReference = databaseReference;

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded: "+dataSnapshot.getKey());

                Task task = dataSnapshot.getValue(Task.class);
                mTaskLists.add(task);
                notifyDataSetChanged();
             //   notifyItemInserted(mTaskLists.size() - 1);
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
    public TodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(mContext).inflate(R.layout.task_row, parent, false);
        TodoHolder holder = new TodoHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TodoHolder holder, int position) {
        TodoHolder todoHolder =  holder;
        final Task task = mTaskLists.get(position);
        Log.d(TAG, "onBindViewHolder: "+task.toString());
        todoHolder.taskNameTv.setText(task.getName());
        todoHolder.taskTimeTv.setText(task.getTime());


        todoHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (mItemLongClicked != null) {
                    mItemLongClicked.onItemLongPressed(task.getTask_id());
                }
                return true;
            }
        });

        todoHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClicked != null ) {
                    mItemClicked.onItemPressed(task);
                }else{
                    Log.d(TAG, "onClick: null");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mTaskLists != null) {
            return mTaskLists.size();
        }
        return 0;
    }

    public static class TodoHolder extends RecyclerView.ViewHolder {
        TextView taskNameTv;
        TextView taskTimeTv;

        View mView;
        public TodoHolder(View itemView) {
            super(itemView);
            mView = itemView;
            taskNameTv = itemView.findViewById(R.id.task_name);
            taskTimeTv = itemView.findViewById(R.id.task_time);


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
        void onItemPressed(Task task);
    }

    public void setOnItemLongClicked(OnItemLongClicked listener){
        mItemLongClicked = listener;
    }

    public interface OnItemLongClicked{
        void onItemLongPressed(String id);
    }

}
