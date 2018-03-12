package com.mahbub.tourmate.fragments;


import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mahbub.tourmate.R;
import com.mahbub.tourmate.adapter.NearbyGridAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private GridView nearbyGrid;
    private Context mContext;
    private SeekBar radiusSeekbar;
    private TextView rediusTv;
    private int radiusRate = 100;
    private Location mLocation;

    private OnSearchPlaceChoiceListener mChoiceListener;

    public NearbyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mChoiceListener = (OnSearchPlaceChoiceListener) mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby, container, false);

        nearbyGrid = view.findViewById(R.id.nearby_grid);
        radiusSeekbar = view.findViewById(R.id.search_radius_seekbar);
        rediusTv = view.findViewById(R.id.redius_tv);
        radiusSeekbar.setOnSeekBarChangeListener(this);

        NearbyGridAdapter adapter = new NearbyGridAdapter(mContext);
        nearbyGrid.setAdapter(adapter);
        adapter.setOnLocationChoiceListener(new NearbyGridAdapter.OnLocationChoiceListener() {
            @Override
            public void onLocationChoiceClicked(String s) {
                Log.d("nearby", "Places Type: "+s+" Radius: "+radiusRate);
                //TODO: show location list
                if (mChoiceListener != null) {
                    mChoiceListener.onPlaceChoiceClicked(s,radiusRate);
                }

            }
        });

        return view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (progress < 100) {
            progress = 100;
            seekBar.setProgress(100);
        }
        radiusRate = progress;
        rediusTv.setText(progress+" meters");

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }



    public interface OnSearchPlaceChoiceListener{
            void onPlaceChoiceClicked(String placeType, int radius);
    }

}
