package com.mahbub.tourmate.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.mahbub.tourmate.R;
import com.mahbub.tourmate.model.PlaceResponse;
import com.mahbub.tourmate.model.Task;
import com.mahbub.tourmate.model.TourEvent;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by Mobile App Develop on 12/11/2017.
 */
public class GenericItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "adapter";
    private Context mContext;
    private List<Object> mItemList;
    public static final int PLACE_TYPE = 1;
    public static final int EVENT_TYPE = 2;
    public static final int TASK_TYPE = 3;
    private ClickListener mClickListener;
    private OnLongClickListener mLongClickListener;
    private OnTaskClickListener mTaskClickListener;
    private GeoDataClient geoDataClient;
    private Map<String, Bitmap> placeImages;
    private String Photo_URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=100&photoreference=";
    private String API_KEY = "";

    public GenericItemAdapter(Context context, List<Object> itemList) {
        this.mContext = context;
        this.mItemList = itemList;
//        mClickListener = (ClickListener) context;
        placeImages = new HashMap<>();
        geoDataClient = Places.getGeoDataClient(context, null);
        API_KEY = mContext.getResources().getString(R.string.place_api_key);
    }

    public void setRCVOnClickListener(ClickListener listener) {
        mClickListener = listener;
    }
    public void setOnLongClickListener(OnLongClickListener listener ) {
        mLongClickListener = listener;
    }
    public void setOnTaskClickListener(OnTaskClickListener listener ) {
        mTaskClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        Log.d(TAG, "onCreateViewHolder: "+mItemList.size());
        View view = null;
        switch (viewType) {
            case PLACE_TYPE:
                view = LayoutInflater.from(mContext).inflate(R.layout.places_list_layout, parent, false);
                holder = new PlaceViewHolder(view);
                break;
            case EVENT_TYPE :
                view = LayoutInflater.from(mContext).inflate(R.layout.event_list_layout, parent, false);
                holder = new EventHolder(view);
                break;
            case TASK_TYPE :
                view = LayoutInflater.from(mContext).inflate(R.layout.task_row, parent, false);
                holder = new TodoHolder(view);
                break;

        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        switch (holder.getItemViewType()) {
            case PLACE_TYPE:
                Log.d(TAG, "onBindViewHolder: Place "+position);
                PlaceResponse.Result place = (PlaceResponse.Result) mItemList.get(position);
                PlaceViewHolder pv = (PlaceViewHolder) holder;
                pv.nameTv.setText(place.getName());
                pv.ratingTv.setText("rating: "+place.getRating());
                pv.addrTv.setText(place.getVicinity());

                Uri uri;
                List<PlaceResponse.Photo> photo = place.getPhotos();
                Log.d(TAG, "onBindViewHolder: photo "+place.getPhotos());
                if (photo != null ) {
                    uri = Uri.parse(Photo_URL + photo.get(0).getPhotoReference() + "&key="+API_KEY);
                    Log.d(TAG, "onBindViewHolder: photo "+uri.toString());
                }else{
                    uri = Uri.parse(place.getIcon());
                }

                Picasso.with(mContext).load(uri).into(pv.imageIV);
                Log.d(TAG, "onBindViewHolder: "+uri.toString());



                pv.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //passing id to our listener
                        if (mClickListener != null) {
                            mClickListener.ClickOnItemListener(position);
                        }
                    }
                });

                break;
            case EVENT_TYPE:
 /*               EventHolder eventHolder = (EventHolder) holder;
                final TourEvent event = (TourEvent) mItemList.get(position);
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
                        if (mClickListener != null) {
                            //TODO:
                            mClickListener.ClickOnItemListener(event.getEventID());
                        }
                    }
                });
                eventHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        //TODO:
                        return false;
                    }
                });*/
                break;
            case TASK_TYPE:
                TodoHolder todoHolder = (TodoHolder) holder;
                final Task task = (Task) mItemList.get(position);
                Log.d(TAG, "onBindViewHolder: "+task.toString());
                todoHolder.taskNameTv.setText(task.getName());
             /*   todoHolder.taskTimeTv.setText(task.getTime());
                todoHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (mLongClickListener != null) {
                            mLongClickListener.onLongClicked(task.getTask_id());
                        }
                        return false;
                    }
                });

                todoHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTaskClickListener != null) {
                            mTaskClickListener.OnTaskClicked(task);
                        }
                    }
                });*/

                break;

        }

    }


    @Override
    public int getItemCount() {
        return mItemList.size();
    }


    public void setDataset(List<Object> list) {
        mItemList = list;
        this.notifyDataSetChanged();
    }

    //TODO: overwrite  getItemViewType
    @Override
    public int getItemViewType(int position) {

        if (mItemList.get(position) instanceof PlaceResponse.Result) {
            return PLACE_TYPE;
        }else if (mItemList.get(position) instanceof TourEvent){
            return EVENT_TYPE;
        }else if (mItemList.get(position) instanceof Task){
            return TASK_TYPE;
        }
        return 0;
    }

    // TODO: create all holders

    private class PlaceViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        TextView addrTv;
        TextView ratingTv;
        ImageView imageIV;
        View mView;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            nameTv = itemView.findViewById(R.id.place_name_tv);
            ratingTv = itemView.findViewById(R.id.place_rating_tv);
            addrTv = itemView.findViewById(R.id.place_addr_tv);
            imageIV = itemView.findViewById(R.id.place_image_iv);

        }
    }
    private class EventHolder extends RecyclerView.ViewHolder {
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
    private class TodoHolder extends RecyclerView.ViewHolder {
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

    //TODO: implement a onClick listener
    public interface ClickListener {
        void ClickOnItemListener(long id);
    }

    public interface OnLongClickListener{
        void onLongClicked(int id);
    }
    public interface OnTaskClickListener{
        void OnTaskClicked(Task task);
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
        } catch (ParseException e) {
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
}
