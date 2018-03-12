package com.mahbub.tourmate.activities;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.GridView;
import android.widget.Toast;

import com.mahbub.tourmate.R;
import com.mahbub.tourmate.adapter.GridViewImageAdapter;
import com.mahbub.tourmate.database.DataSource;
import com.mahbub.tourmate.model.Moment;
import com.mahbub.tourmate.utils.ImageUtils;

import java.util.ArrayList;

public class GridViewActivity extends AppCompatActivity {

    private static final String TAG = "gridActivity";
    private ImageUtils utils;
    private ArrayList<String> imagePaths;
    private GridViewImageAdapter adapter;
    private GridView gridView;
    private int columnWidth;
    private String eventId;
    private DataSource dataSource;
    private ArrayList<Moment> moments = new ArrayList<>();

    // Number of columns of Grid View
    public static final int NUM_OF_COLUMNS = 3;
    // Gridview image padding
    public static final int GRID_PADDING = 8; // in dp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);


        dataSource = new DataSource(this);
        imagePaths = new ArrayList<>();



        eventId = getIntent().getStringExtra("EVENT_ID");
        if (eventId != null) {
            Log.d(TAG, "onCreate: "+eventId);
            moments = dataSource.getMomentByEventId(eventId);
//            if (moments != null) {
//                for (Moment moment : moments) {
//                    imagePaths.add(moment.getMomentImagePath());
//                }
//            }
        }else{
            Log.d(TAG, "onCreate: "+eventId);
        }


        gridView =  findViewById(R.id.grid_view);

        utils = new ImageUtils(this);

        // Initilizing Grid View
        InitilizeGridLayout();

        // Gridview adapter
        adapter = new GridViewImageAdapter(GridViewActivity.this, moments,
                columnWidth,eventId);

        // setting grid view adapter
        gridView.setAdapter(adapter);


        adapter.setOnImageLongClickListener(new GridViewImageAdapter.OnImageLongClickListener() {
            @Override
            public boolean onLongClick(final int id) {

                new AlertDialog.Builder(GridViewActivity.this)
                        .setTitle("Delete Image Alert")
                        .setMessage("Are you sure? want to delete this image.")
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (dataSource.deleteMomentById(id)) {
                                    moments = dataSource.getMomentByEventId(eventId);
                                    adapter.refreshData(moments);
                                    Toast.makeText(GridViewActivity.this, "Image has been delete", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .show();



                return false;
            }
        });
    }

    private void InitilizeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                GRID_PADDING, r.getDisplayMetrics());

        columnWidth = (int) ((utils.getScreenWidth() - ((NUM_OF_COLUMNS + 1) * padding)) / NUM_OF_COLUMNS);

        gridView.setNumColumns(NUM_OF_COLUMNS);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding,
                (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }
}
