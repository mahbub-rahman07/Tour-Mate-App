package com.mahbub.tourmate.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Mahbuburrahman on 1/31/18.
 */

public class MarkerItems implements ClusterItem{
    private LatLng mPosition;
    private String mTitle;
    private String mSnippet;

    public MarkerItems(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    public MarkerItems(double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }
}
