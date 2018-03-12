package com.mahbub.tourmate.utils;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Mahbuburrahman on 1/4/18.
 */

public class LocationHelper {


    private static Location currentLocation = null;

    public static Location getLocation(Context context) {
        FusedLocationProviderClient client = null;
        LocationCallback callback = null;
        LocationRequest locationRequest = null;

        client = LocationServices.getFusedLocationProviderClient(context);

        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(300)
                .setFastestInterval(1000);

        callback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                for (Location location: locationResult.getLocations()) {
                    currentLocation = location;
                }

            }
        };
        try {
            client.requestLocationUpdates(locationRequest, callback, null);
        }catch (SecurityException e) {
            e.printStackTrace();
        }


        Log.d("location", "getLocation: "+(currentLocation == null));
       while(currentLocation == null) {
            currentLocation = getLocation(context);
        }

        if (client != null && callback != null) {
            client.removeLocationUpdates(callback);
        }
        return currentLocation;
    }

}
