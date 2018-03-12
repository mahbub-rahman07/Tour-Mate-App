package com.mahbub.tourmate.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.mahbub.tourmate.R;
import com.mahbub.tourmate.activities.WeatherActivity;
import com.mahbub.tourmate.adapter.WeatherAdapter;
import com.mahbub.tourmate.database.SharedPrefsData;
import com.mahbub.tourmate.model.ForecastMainClass;
import com.mahbub.tourmate.services.WeatherServices;
import com.mahbub.tourmate.utils.NetworkHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForeCastFragment extends Fragment {
    //API URLS
    private String API_KEY = null;
    private WeatherServices mServices;
    private Retrofit retrofit;
    private Context mContext;
    private RecyclerView rcv;
    private String foreCastUrl = null;
    private int forecastDays = 7;
    private List<ForecastMainClass.WeatherList> mWeatherLists;
    public ForeCastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        API_KEY = mContext.getResources().getString(R.string.wather_api_key);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fore_cast, container, false);
        rcv = view.findViewById(R.id.forecast_rcv);
        rcv.setLayoutManager(new LinearLayoutManager(mContext));
        getData(foreCastUrl);

        Log.d("forecast", "onCreateView: called"+foreCastUrl);

        return view;
    }

    public void checkForecastDays() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
        boolean is14 = settings.getBoolean(mContext.getString(R.string.forecast_key), false);

        if (is14){
            forecastDays = 14;
        }else{
            forecastDays = 7;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        checkForecastDays();
        if (WeatherActivity.latitude != 0 && WeatherActivity.longitude != 0) {
            String url = String.format("forecast/daily?lat=%f&lon=%f&cnt=%d&appid=", WeatherActivity.latitude,
                    WeatherActivity.longitude, forecastDays);
            getData(url);
            Log.d("forecast", "onResume: "+ WeatherActivity.longitude);
        }
    }

    public void setUrl(String url) {
        foreCastUrl = url;
    }
    public void setForecast(double lat, double lon) {
        checkForecastDays();
        String url = String.format("forecast/daily?lat=%f&lon=%f&cnt=%d&appid=",lat,
                lon, forecastDays);
        getData(url);
    }
    private void getData(final String url) {
        Log.d("forecast", "onFragment getData: called");

        boolean hasNetwork = NetworkHelper.hasNetwork(mContext);
        if (!hasNetwork || url == null) {
            if (WeatherActivity.defaultDataStore == null) {
                WeatherActivity.defaultDataStore = new SharedPrefsData(mContext);
            }

            List<ForecastMainClass.WeatherList> forecasts = WeatherActivity.defaultDataStore.getForecast();
            if (forecasts != null) {
                WeatherAdapter adapter = new WeatherAdapter(mContext, forecasts);
                if (rcv != null) {
                    rcv.setAdapter(adapter);
                }
            }
            return;
        }
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(WeatherActivity.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        mServices = retrofit.create(WeatherServices.class);
        String urlString = String.format(WeatherActivity.BASE_URL+url+API_KEY);

        Log.d("forecast", "url: "+urlString);

        Call<ForecastMainClass> call = mServices.getForecastWeather(urlString);
        call.enqueue(new Callback<ForecastMainClass>() {
            @Override
            public void onResponse(Call<ForecastMainClass> call, Response<ForecastMainClass> response) {
                if (response.code() == 200){
                    mWeatherLists = response.body().getList();
                    WeatherAdapter adapter = new WeatherAdapter(mContext, mWeatherLists);
                    if (rcv != null) {
                        rcv.setAdapter(adapter);
                    }

                    Log.d("forecast", "onResponse: "+response.body().getList().size());
                }

                Log.d("forecast", "onResponse: "+response.code());

            }

            @Override
            public void onFailure(Call<ForecastMainClass> call, Throwable t) {
                Log.d("forecast", "onResponse failed: "+t);
            }
        });
        Log.d("forecast", "onResponse: finished");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mWeatherLists != null && mWeatherLists.size() > 0 ) {
            WeatherActivity.defaultDataStore.saveForecast(mWeatherLists);
        }
        Log.d("forecast", "onDestroyView: called"+(mWeatherLists == null));
    }

}
