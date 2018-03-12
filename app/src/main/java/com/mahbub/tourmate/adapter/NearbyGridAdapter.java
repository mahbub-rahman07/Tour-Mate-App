package com.mahbub.tourmate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mahbub.tourmate.R;

/**
 * Created by Mahbuburrahman on 2/3/18.
 */

public class NearbyGridAdapter extends BaseAdapter{

    int[] placesImage = {
            R.drawable.mosque,R.drawable.airport,
            R.drawable.bank,R.drawable.atm,
            R.drawable.shopping,R.drawable.hospital,
            R.drawable.restaurant,R.drawable.cafe,R.drawable.bus_station,
            R.drawable.police_station, R.drawable.train_station,
            R.drawable.move_theater, R.drawable.library
    };
    String[] placesNames = {
            "Mosque","Airport","Bank","ATM",
            "Shopping Mall","Hospital","Restaurant","Cafe",
            "Bus Station","Police Station",
            "Train Station",
            "Movie Theater","Library"
    };
    String[] placesQueryNames = {
            "mosque","airport","bank","atm",
            "shopping_mall","hospital","restaurant","cafe",
            "bus_station","police",
            "train_station",
            "movie_theater","library"
    };
    Context mContext;
    OnLocationChoiceListener mLocationChoiceListener;
    public NearbyGridAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return placesImage.length;
    }

    @Override
    public Object getItem(int position) {
        return placesImage[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout .nearby_places_grid_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.placeName.setText(placesNames[position]);
        viewHolder.placeImage.setImageResource(placesImage[position]);
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "You Choice "+placesNames[position], Toast.LENGTH_SHORT).show();
                if (mLocationChoiceListener != null) {
                    mLocationChoiceListener.onLocationChoiceClicked(placesQueryNames[position]);
                }

            }
        });
        return convertView;
    }
    private class ViewHolder {
        TextView placeName;
        ImageView placeImage;
        View mView;
        public ViewHolder(View view) {
            mView = view;
            placeName = view.findViewById(R.id.name_place_tv);
            placeImage = view.findViewById(R.id.image_place_iv);
        }
    }

    public void setOnLocationChoiceListener(OnLocationChoiceListener listener){
        this.mLocationChoiceListener = listener;
    }

    public interface OnLocationChoiceListener{
        void onLocationChoiceClicked(String s);
    }
}
