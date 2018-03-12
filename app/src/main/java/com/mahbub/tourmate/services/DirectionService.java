package com.mahbub.tourmate.services;

import com.mahbub.tourmate.model.DirectionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Mahbuburrahman on 1/22/18.
 */

public interface DirectionService {

    @GET
    Call<DirectionResponse> getDirections(@Url String urlString);
}

