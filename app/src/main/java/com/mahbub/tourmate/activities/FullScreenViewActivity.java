package com.mahbub.tourmate.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mahbub.tourmate.R;
import com.mahbub.tourmate.adapter.FullScreenImageAdapter;
import com.mahbub.tourmate.database.DataSource;
import com.mahbub.tourmate.model.Moment;
import com.mahbub.tourmate.utils.ImageUtils;

import java.util.ArrayList;

public class FullScreenViewActivity extends AppCompatActivity {
    private static final String TAG = "FULL";
    private ImageUtils utils;
    private FullScreenImageAdapter adapter;
    private ViewPager viewPager;
    private ArrayList<String> imagePaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        viewPager = (ViewPager) findViewById(R.id.pager);

       // utils = new ImageUtils(getApplicationContext());

        Intent i = getIntent();
        String eventId = i.getStringExtra("EVENT_ID");
        int position = i.getIntExtra("position", 0);

        if (eventId != null) {
            Log.d(TAG, "onCreate: "+position);
            DataSource dataSource = new DataSource(this);
            ArrayList<Moment> moments = dataSource.getMomentByEventId(eventId);
            imagePaths  =new ArrayList<>();
            if (moments != null) {
                for (Moment moment : moments) {
                    imagePaths.add(moment.getMomentImagePath());
                }
            }
        }else{
            Log.d(TAG, "onCreate: "+eventId);
        }

        adapter = new FullScreenImageAdapter(FullScreenViewActivity.this,
                imagePaths);

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(position);
    }
}
