package com.mahbub.tourmate.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mahbub.tourmate.R;
import com.mahbub.tourmate.database.DataSource;
import com.mahbub.tourmate.model.Moment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment {

    private Context mContext;
    private ImageView mImageView;
    private Button saveBtn;
    private Bitmap mBitmap;
    private String mImageName;
    private String mImagePath;
    private String eventID;

    private OnsaveCompleteListener mListener;
    public CameraFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mListener = (OnsaveCompleteListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        mImageView = view.findViewById(R.id.camera_image_iv);
        saveBtn = view.findViewById(R.id.save_image_btn);
        if (mBitmap != null) {
            mImageView.setImageBitmap(mBitmap);
        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSource dataSource = new DataSource(mContext);
                if (dataSource.insertMoment(new Moment(eventID, mImageName, mImagePath))){
                    Toast.makeText(mContext, "Image saved", Toast.LENGTH_SHORT).show();
                    if (mListener != null) {
                        mListener.onSaveCompeleted();
                    }
                }else{
                    Toast.makeText(mContext, "Image failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    public void setImage(String eventId, Bitmap bitmap,String imageName, String imagePath) {
        mBitmap = bitmap;
        mImageName = imageName;
        mImagePath = imagePath;
        eventID = eventId;
        Log.d("CAMERA", "setImage: "+imagePath);
    }
    public interface OnsaveCompleteListener{
        void onSaveCompeleted();
    }

}
