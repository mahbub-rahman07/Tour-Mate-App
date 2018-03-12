package com.mahbub.tourmate.services;

import com.mahbub.tourmate.model.PlaceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Mahbuburrahman on 1/31/18.
 */

public interface PlaceService {
    @GET
    Call<PlaceResponse> getPlacesByType(@Url String url);
}
