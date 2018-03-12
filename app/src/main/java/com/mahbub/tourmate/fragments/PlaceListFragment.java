package com.mahbub.tourmate.fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mahbub.tourmate.R;
import com.mahbub.tourmate.adapter.GenericItemAdapter;
import com.mahbub.tourmate.model.PlaceResponse;
import com.mahbub.tourmate.services.PlaceService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceListFragment extends Fragment {

    private String NEARBY_BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    private PlaceService mPlaceService;
    private GeoDataClient geoDataClient;

    private GenericItemAdapter adapter;
    private RecyclerView mRecyclerView;

    private String placeType;
    private String radius;
    private TextView emptTV;

    private LatLng MyLoc = null;
    private ProgressDialog mProgressDialog;

    private OnPlaceDetailClickListener mListener;

    private Context mContext;
    public PlaceListFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_list, container, false);
        mRecyclerView = view.findViewById(R.id.nearby_place_rcv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        emptTV = view.findViewById(R.id.empty_list_tv);

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Please Wait....");
        searchPlaceByType();


        return view;
    }
    private void getPlacePhoto(String placeID) {
        geoDataClient.getPlacePhotos(placeID).addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                if (task.isComplete() && task.getResult() != null) {
                    PlacePhotoMetadataResponse response = task.getResult();
                    PlacePhotoMetadataBuffer buffer = response.getPhotoMetadata();
                    PlacePhotoMetadata metadata = buffer.get(0);
                    geoDataClient.getPhoto(metadata).addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                            if (task.isComplete() && task.getResult() != null) {
                                PlacePhotoResponse photo = task.getResult();
                                Bitmap bitmap = photo.getBitmap();
                                openPhotoDialog(bitmap);
                            }
                        }
                    });
                }
            }
        });
    }

    private void openPhotoDialog(Bitmap bitmap) {
        final ImageView imageView = new ImageView(mContext);
        imageView.setImageBitmap(bitmap);
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setView(imageView)
                .show();
    }

    public void searchPlaceByType() {

        if (placeType == null || radius == null || MyLoc == null){
            return;
        }
        mProgressDialog.show();

        String key = getString(R.string.place_api_key);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NEARBY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mPlaceService = retrofit.create(PlaceService.class);



        String lat = String.valueOf(MyLoc.latitude);
        String lng = String.valueOf(MyLoc.longitude);
        placeType = placeType.toLowerCase();
        String urlString = String.format("json?location=%s,%s&radius=%s&types=%s&key=%s",lat,lng,radius,placeType,key);

        Log.d("nearby", "searchPlaceByType: "+NEARBY_BASE_URL+urlString);

        Call<PlaceResponse> responseCall = mPlaceService.getPlacesByType(urlString);
        responseCall.enqueue(new Callback<PlaceResponse>() {
            @Override
             public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                    if (response.code() == 200) {
                        Toast.makeText(mContext, "Result size: "+response.body().getResults().size(),Toast.LENGTH_SHORT).show();
                        //TODO: show nearby places
                        mProgressDialog.dismiss();
                        setItemList(response.body().getResults());
                    }
                }

                @Override
                public void onFailure(Call<PlaceResponse> call, Throwable t) {
                    mProgressDialog.dismiss();
                }
            });


    }
    public void setItemList(final List<PlaceResponse.Result> response) {
        if (response.size() > 0) {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptTV.setVisibility(View.INVISIBLE);
        }else {
            mRecyclerView.setVisibility(View.INVISIBLE);
            emptTV.setVisibility(View.VISIBLE);
        }
        List<Object> result = new ArrayList<>();
        for (PlaceResponse.Result r : response) {
            result.add(r);
        }
        adapter = new GenericItemAdapter(mContext, result);
        mRecyclerView.setAdapter(adapter);
        adapter.setRCVOnClickListener(new GenericItemAdapter.ClickListener() {
            @Override
            public void ClickOnItemListener(long id) {
                if (mListener != null) {
                    mListener.onPlaceClicked(response.get((int) id));
                }
            }
        });
    }
    public void setSearchPlaceByType(String type,int rds, LatLng latLng){
        placeType = type;
        radius = String.valueOf(rds);
        MyLoc = latLng;
    }

    public void setOnPlaceDetailClickListener(OnPlaceDetailClickListener listener) {
        mListener = listener;
    }

    public interface OnPlaceDetailClickListener{
        void onPlaceClicked(PlaceResponse.Result result);
    }
}
