package com.mahbub.tourmate.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.mahbub.tourmate.R;
import com.mahbub.tourmate.model.ForecastMainClass;
import com.mahbub.tourmate.model.WeatherResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahbuburrahman on 12/16/17.
 */

public class SharedPrefsData {
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    private static final String DATA_KEY = "data_key";
    private static final String SUGGESTION_KEY = "suggestion_key";
    private static final String FORECAST_DATA_KEY = "forecast_key";
    private static final String LAST_LOC_LAT = "lastLat_key";
    private static final String LAST_LOC_LNG = "lastLng_key";
    private static final String CELCIUS = "celcius_key";
    private static final String SEVENDAYS = "seven_key";


    public SharedPrefsData(Context context) {
        mContext = context;
        mPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public void deleteData() {
        mEditor.clear();
        mEditor.commit();
    }
    public void saveLastLocation(float lat, float lng) {

        mEditor.putFloat(LAST_LOC_LAT, lat);
        mEditor.putFloat(LAST_LOC_LNG, lng);
        mEditor.commit();
        Log.d("shared", "saveData: location");

    }
    public float getLastLocationLatitude() {
        float lat = mPreferences.getFloat(LAST_LOC_LAT, 23.709366f);
        Log.d("shared", "latitude: "+lat);
        return lat;

    }
    public float getLastLocationLongitude() {
        float lng = mPreferences.getFloat(LAST_LOC_LNG, 90.431928f);
        Log.d("shared", "latitude: "+lng);
        return lng;
    }
    public void saveData(WeatherResponse response) {
        Gson gson = new Gson();
        String gsonString =  gson.toJson(response);

        mEditor.putString(DATA_KEY, gsonString);
        mEditor.commit();
        Log.d("shared", "saveData: ");

    }
    public WeatherResponse getData() {
        WeatherResponse response = null;
        Gson gson = new Gson();
        String gsonString = mPreferences.getString(DATA_KEY, null);

        if (gsonString != null) {
            response = gson.fromJson(gsonString, WeatherResponse.class);
            Log.d("shared", "getData: "+(response.getMain().getTemp()));
        }

        return response;
    }
    public void saveForecast(List<ForecastMainClass.WeatherList> forecast) {
        ForecastAll forecastAll = new ForecastAll();
        forecastAll.setResponseList(forecast);

        Gson gson = new Gson();
        String gsonString =  gson.toJson(forecastAll);
        mEditor.putString(FORECAST_DATA_KEY, gsonString);
        mEditor.commit();
        Log.d("shared", "saveData: ");
    }
    public List<ForecastMainClass.WeatherList> getForecast() {

        ForecastAll forecast = null;
        Gson gson = new Gson();
        String gsonString = mPreferences.getString(FORECAST_DATA_KEY, null);

        if (gsonString != null) {
            forecast = gson.fromJson(gsonString, ForecastAll.class);
            Log.d("shared", "getData: "+(forecast.getResponseList().size()));
            return forecast.getResponseList();
        }
        return null;

    }
    public void saveSuggetion(String query) {
        Suggestion suggestion = new Suggestion();

        ArrayList<String> stringList = getSuggestions();
        if (stringList == null) {
            stringList=new ArrayList<>();
        }
        stringList.add(query);
        suggestion.setSuggetionList(stringList);

        Gson gson = new Gson();
        String gsonString =  gson.toJson(suggestion);
        mEditor.putString(SUGGESTION_KEY, gsonString);
        mEditor.commit();
        Log.d("shared", "saveSuggetion: "+query);
    }
    public ArrayList<String> getSuggestions() {
        Suggestion suggestion = null;
        Gson gson = new Gson();
        String gsonString = mPreferences.getString(SUGGESTION_KEY, null);
        if (gsonString == null || gsonString.length() <= 0) {
            String[] cities = mContext.getResources().getStringArray(R.array.cities);
            ArrayList<String> citys = new ArrayList<>();
            for (String city : cities) {
                citys.add(city);
            }
            return citys;
        }

        else if (gsonString != null) {
            suggestion = gson.fromJson(gsonString, Suggestion.class);
            Log.d("shared", "getData: " + (suggestion == null) +" "+gsonString);
            if (suggestion != null && suggestion.getSuggetionList() != null) {
                Log.d("shared", "getData: " + (suggestion.getSuggetionList().size()));
                return suggestion.getSuggetionList();
            }
        }
        return null;
    }

    public void setTempFormat(boolean celcius) {
        mEditor.putBoolean(CELCIUS, celcius);
    }
    public boolean getTempFormat() {
        boolean celcius = mPreferences.getBoolean(CELCIUS, false);
        return celcius;
    }
    public void setForecastFormat(boolean seven) {
        mEditor.putBoolean(SEVENDAYS, seven);
    }
    public boolean getForecastFormat() {
        boolean days7 = mPreferences.getBoolean(SEVENDAYS, false);
        return days7;
    }

    private class Suggestion {
        ArrayList<String> suggetionList;
        public ArrayList<String> getSuggetionList() {
            return suggetionList;
        }
        public void setSuggetionList(ArrayList<String> suggetionList) {
            this.suggetionList = suggetionList;
        }
    }
    private class ForecastAll{
        List<ForecastMainClass.WeatherList> responseList;
        public List<ForecastMainClass.WeatherList> getResponseList() {
            return responseList;
        }
        public void setResponseList(List<ForecastMainClass.WeatherList> responseList) {
            this.responseList = responseList;
        }
    }


}
