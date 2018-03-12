package com.mahbub.tourmate.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.mahbub.tourmate.activities.FullScreenViewActivity;
import com.mahbub.tourmate.database.DataSource;
import com.mahbub.tourmate.model.Moment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Mahbuburrahman on 2/10/18.
 */

public class GridViewImageAdapter extends BaseAdapter {

    private static final String TAG = "gridAdapter";
    private Activity _activity;
    private ArrayList<Moment>  mMoments = new ArrayList<>();
    private ArrayList<String> _filePaths = new ArrayList<String>();
    private int imageWidth;
    private String eventID;
    private OnImageLongClickListener mLongClickListener;

    public GridViewImageAdapter(Activity activity, ArrayList<Moment> moments,
                                int imageWidth,String eventid) {
        this._activity = activity;
        this.mMoments = moments;
        this.imageWidth = imageWidth;
        this.eventID = eventid;
    }

    @Override
    public int getCount() {
        return this.mMoments.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mMoments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(_activity);
        } else {
            imageView = (ImageView) convertView;
        }
        Moment moment = mMoments.get(position);

        // get screen dimensions
        Bitmap image = decodeFile(moment.getMomentImagePath(), imageWidth,
                imageWidth);

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(imageWidth,
                imageWidth));
        imageView.setImageBitmap(image);

        // image view click listener
        imageView.setOnClickListener(new OnImageClickListener(position));
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mLongClickListener != null) {
                    Moment moment = mMoments.get(position);
                    mLongClickListener.onLongClick(moment.getMomentID());
                }

                return false;
            }
        });

        return imageView;
    }

    class OnImageClickListener implements View.OnClickListener {

        int _postion;

        // constructor
        public OnImageClickListener(int position) {
            this._postion = position;
        }

        @Override
        public void onClick(View v) {
            // on selecting grid view image
            // launch full screen activity
            Log.d(TAG, "onClick: "+_postion);
            Intent i = new Intent(_activity, FullScreenViewActivity.class);
            i.putExtra("position", _postion);
            i.putExtra("EVENT_ID", eventID);
            _activity.startActivity(i);
        }

    }
    public void setOnImageLongClickListener(OnImageLongClickListener listener) {
        mLongClickListener = listener;

    }
    public interface OnImageLongClickListener {
        public boolean onLongClick(int id);

    }
    /*
     * Resizing image size
     */
    public static Bitmap decodeFile(String filePath, int WIDTH, int HIGHT) {
        try {

            File f = new File(filePath);

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            final int REQUIRED_WIDTH = WIDTH;
            final int REQUIRED_HIGHT = HIGHT;
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH
                    && o.outHeight / scale / 2 >= REQUIRED_HIGHT)
                scale *= 2;

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void refreshData(ArrayList<Moment> moments) {
            this.mMoments = moments;
            this.notifyDataSetChanged();
    }
}
