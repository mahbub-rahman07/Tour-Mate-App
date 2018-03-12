package com.mahbub.tourmate.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.ClusterManager;
import com.mahbub.tourmate.R;
import com.mahbub.tourmate.model.DirectionResponse;
import com.mahbub.tourmate.model.MarkerItems;
import com.mahbub.tourmate.services.DirectionService;
import com.mahbub.tourmate.services.PendingIntentService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    private static final String TAG = "mapfrag";
    private Context mContext;
    private GoogleMap mMap;
    private GoogleMapOptions mOptions;
    private List<MarkerItems> clusterItems;
    private ClusterManager<MarkerItems> mClusterManager;
    private final int PLACE_PICKER_REQUEST = 1;
    private final int AUTOCOMPLTE_REQUEST = 2;

    public static final String DIRECTION_BASE_URL = "https://maps.googleapis.com/maps/api/directions/";

    private DirectionService mDirectionService;

    private ImageButton searchButton;
    private ImageButton nextRouteButton;
    private ImageButton directionButton;
    private ImageButton walkingButton;
    private ImageButton drivingButton;
    private Button mapViewButton;

    private LatLng origin;
    private LatLng destination;

    private int markerCount = 0;
    private int CAMERA_ZOOM = 12;

    private int route = 1;
    private int index = 0;
    String mode = "driving";
    private DirectionResponse mDirectionResponse;

    private MarkerOptions ExtraMarker;
    private ProgressDialog mProgressDialog;
    private LatLng mLatLng;


    private GeofencingClient mGeofencingClient;
    private PendingIntent mPendingIntent;
    private ArrayList<Geofence> mGeofence = null;

    public MapFragment() {
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
        View view = inflater.inflate(R.layout.fragment_map, null, false);

        mOptions = new GoogleMapOptions();
        mOptions.zoomControlsEnabled(true)
                .compassEnabled(true)
                .mapType(GoogleMap.MAP_TYPE_TERRAIN);
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage("Please Wait....");


        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.mapContainer);
        mapFragment.getMapAsync(this);

        //      clusterItems = new ArrayList<>();
        //       mClusterManager = new ClusterManager<MarkerItems>(mContext,mMap);

        searchButton = view.findViewById(R.id.search_button_map);
        nextRouteButton = view.findViewById(R.id.next_route_ib);
        directionButton = view.findViewById(R.id.direction_show_ib);
        walkingButton = view.findViewById(R.id.walking_ib);
        drivingButton = view.findViewById(R.id.driving_ib);
        mapViewButton = view.findViewById(R.id.map_view_btn);

        searchButton.setOnClickListener(this);
        nextRouteButton.setEnabled(false);
        directionButton.setOnClickListener(this);
        nextRouteButton.setOnClickListener(this);
        walkingButton.setOnClickListener(this);
        drivingButton.setOnClickListener(this);
        mapViewButton.setOnClickListener(this);


        return view;
    }

    public void setMarkerOnMap(double lat, double lng, String title, String snippet) {
        LatLng latLng = new LatLng(lat, lng);
        ExtraMarker = new MarkerOptions().position(latLng).title(title).snippet(snippet);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        if (mLatLng != null) {
            origin = mLatLng;
            Log.d(TAG, "onMapReady: null");
        } else {
            origin = new LatLng(23.7503816, 90.3930701);
        }

        //mMap.addMarker(new MarkerOptions().position(origin).title("Marker in Dhaka"));


        if (ExtraMarker != null) {
            mMap.addMarker(ExtraMarker);
            destination = ExtraMarker.getPosition();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, CAMERA_ZOOM));
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, CAMERA_ZOOM));
        }


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                //clusterItems.add(new MarkerItems(latLng.latitude, latLng.longitude));
                //mClusterManager.addItems(clusterItems);
                // mClusterManager.cluster();
                mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Destination"));
                destination = latLng;
            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                showGeofenceDialog(latLng);
            }
        });
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                if (mMap.getMyLocation() != null) {
                    mLatLng = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
                }
                if (mLatLng != null) {
                    origin = mLatLng;
                    //mMap.clear();
                    mMap.addMarker(new MarkerOptions().title("Your location").position(origin));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, CAMERA_ZOOM));
                }
                Log.d(TAG, "onMyLocationButtonClick: clicked");
                return false;
            }
        });

        try {
            mMap.setMyLocationEnabled(true);

        } catch (SecurityException e) {
            e.printStackTrace();
        }


    }

    private void showGeofenceDialog(final LatLng latLng) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.geofence_layout);
        final EditText areaEdit = dialog.findViewById(R.id.area_range_edit);
        final EditText timeEdit = dialog.findViewById(R.id.expire_time_edit);
        final EditText nameEdit = dialog.findViewById(R.id.name_geo_edit);
        Button savebtn = dialog.findViewById(R.id.geofence_save_btn);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String areaString = areaEdit.getText().toString();
                String timeString = timeEdit.getText().toString();
                String nameString = nameEdit.getText().toString();

                if (!TextUtils.isEmpty(areaString) && !TextUtils.isEmpty(timeString) && !TextUtils.isEmpty(nameString)) {
                    int area = 0;
                    int time = 0;
                    try {
                        area = Integer.parseInt(areaString);
                        time = Integer.parseInt(timeString);
                    } catch (NumberFormatException e) {

                        e.printStackTrace();
                    }
                    mMap.addMarker(new MarkerOptions().position(latLng).title(nameString).snippet("latitude: "+latLng.latitude+" , longitude: "+latLng.longitude));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM));

                    saveAsGeofence(area, time, nameString, latLng);
                    dialog.dismiss();
                }else{
                    Toast.makeText(mContext, "Please give all required information",Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();

    }

    private void saveAsGeofence(int area, int time, String name, LatLng latLng) {
        //TODO: geofence

        mGeofence = new ArrayList<>();
        mGeofencingClient = LocationServices.getGeofencingClient(mContext);
        mPendingIntent = null;

        Geofence geofence = new Geofence.Builder()
                .setRequestId(name)
                .setCircularRegion(latLng.latitude, latLng.longitude, area)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .setExpirationDuration(time * 60 * 60 * 1000)
                .build();

        mGeofence.add(geofence);
        registerGeofence();

    }

    private void registerGeofence() {
       try {
           mGeofencingClient.addGeofences(getGeofencingRequest(), getPendingIntent()).addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                   if (task.isSuccessful()) {
                       Toast.makeText(mContext, "Geofence Added", Toast.LENGTH_SHORT).show();
                   }
               }
           });
           Log.d(TAG, "Geofence: registerd");
       }catch (SecurityException e) {
           Log.d(TAG, "Geofence: not register");
       }


    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofence);
        Log.d(TAG, "Geofence: requested");
        return builder.build();
    }
    private PendingIntent getPendingIntent() {
        if (mPendingIntent != null) {
            return mPendingIntent;
        }
        Intent intent = new Intent(getActivity(), PendingIntentService.class);
        mPendingIntent = PendingIntent.getService(mContext, 999, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return mPendingIntent;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.search_button_map:
                showAutocompleteWidget();
                break;
            case R.id.direction_show_ib:
                getDirections();
                break;
            case R.id.next_route_ib:
                showDirection();
                break;
            case R.id.walking_ib:
                mode = "walking";
                index=0;
                getDirections();
                break;
            case R.id.driving_ib:
                mode = "driving";
                index=0;
                getDirections();
                break;
            case R.id.map_view_btn:
                showMapViewDialog();
                break;
        }

    }

    private void showMapViewDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.map_view_layout);


        // set the custom dialog components - text, image and button
        ImageView defaultMap = dialog.findViewById(R.id.default_view_iv);

        ImageView satelliteMap =  dialog.findViewById(R.id.satellite_iv);
        ImageView normalMap =  dialog.findViewById(R.id.normal_iv);

        satelliteMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        defaultMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                dialog.dismiss();
            }
        });
        satelliteMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                dialog.dismiss();
            }
        });
        normalMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                dialog.dismiss();
            }
        });
        dialog.show();
        return;
    }

    private void showAutocompleteWidget() {
        try {
            AutocompleteFilter filter = new AutocompleteFilter.Builder().build();
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).setFilter(filter).build((Activity) mContext);
            startActivityForResult(intent, AUTOCOMPLTE_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case AUTOCOMPLTE_REQUEST:
                if (resultCode == RESULT_OK) {
                    Place place = PlaceAutocomplete.getPlace(mContext, data);
                    LatLng latLng = place.getLatLng();
                    mMap.addMarker(new MarkerOptions().title(place.getName().toString()).position(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM));
                    destination = latLng;
                    Toast.makeText(mContext, ""+place.getName().toString(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public void setMyLocation(LatLng latLng) {
        mLatLng = latLng;
        Log.d(TAG, "setMyLocation: "+(mLatLng == null));
    }


    //---------------------Direction-------------------//
    private void getDirections() {

        if (destination == null) {
            return;
        }
        mProgressDialog.show();

        String key = getString(R.string.direction_api);
        String org = origin.latitude + "," + origin.longitude;
        String dest = destination.latitude + "," + destination.longitude;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DIRECTION_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mDirectionService = retrofit.create(DirectionService.class);


        String urlString
                = String.format("json?origin=%s&destination=%s&alternatives=true&mode=%s&key=%s",
                org,dest,mode,key);
        Log.d("direction", "getDirections: url :"+DIRECTION_BASE_URL + urlString);

        Call<DirectionResponse> directionResponseCall = mDirectionService.getDirections(urlString);
        directionResponseCall.enqueue(new Callback<DirectionResponse>() {
            @Override
            public void onResponse(Call<DirectionResponse> call, Response<DirectionResponse> response) {
                if(response.isSuccessful()){
                    mProgressDialog.dismiss();
                    DirectionResponse directionResponse = response.body();
                    route = directionResponse.getRoutes().size();
                    Log.d("distance", "onResponse: Route "+route);
                    if (route > 0) {
                        mDirectionResponse = directionResponse;
                        showDirection();
                    }
                }
            }

            @Override
            public void onFailure(Call<DirectionResponse> call, Throwable t) {
                mProgressDialog.dismiss();
            }
        });
    }

    private void showDirection() {

        if (mDirectionResponse == null) {
            directionButton.setEnabled(false);
            nextRouteButton.setEnabled(false);
            return;
        }

        index = index % route;
        List<DirectionResponse.Step> steps =
                mDirectionResponse.getRoutes().get(index)
                        .getLegs().get(0).getSteps();
        getDestinationDistanceAndDuration(mDirectionResponse.getRoutes().get(index).getLegs());


        for(int i = 0; i < steps.size(); i++){
            double startLat = steps.get(i).getStartLocation().getLat();
            double startLng = steps.get(i).getStartLocation().getLng();
            LatLng startPoint = new LatLng(startLat,startLng);

            double endLat = steps.get(i).getEndLocation().getLat();
            double endLng = steps.get(i).getEndLocation().getLng();
            LatLng endPoint = new LatLng(endLat,endLng);

            Polyline polyline = mMap.addPolyline(new PolylineOptions()
                    .add(startPoint)
                    .add(endPoint));
            polyline.setColor(Color.GREEN);

        }
        directionButton.setEnabled(true);
        if (route > 1) {
            nextRouteButton.setEnabled(true);
        }
        index++;

    }

    private void getDestinationDistanceAndDuration(List<DirectionResponse.Leg> legs) {
        Log.d("distance", "getDestinationDistanceAndDuration: "+legs.get(0).getDistance().getText().toString() +" "+ legs.get(0).getDuration().getText().toString()+" Travel_mode: "+legs.get(0).getSteps().get(0).getTravelMode());
        String destAddr = legs.get(0).getEndAddress();
        mMap.clear();
        mMap.addMarker(new MarkerOptions().title(destAddr).snippet("Distance: "+legs.get(0).getDistance().getText().toString()+" Duration: "+legs.get(0).getDuration().getText().toString()).position(destination));
        mMap.addMarker(new MarkerOptions().title("Your Location").position(origin).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, CAMERA_ZOOM));


    }



}
