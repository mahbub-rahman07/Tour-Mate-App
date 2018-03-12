package com.mahbub.tourmate.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahbub.tourmate.R;


/**
 * Created by Mahbuburrahman on 1/5/18.
 */

public class WeatherListHolder extends RecyclerView.ViewHolder {


    public ImageView weaTherIcon;
    public TextView dayTimeTv;
    public TextView maxTempTv;
    public TextView minTempTv;
    public TextView windTv;
    public TextView cloudPressureTV;
    public TextView humidityTv;
    public TextView monTV;
    public TextView dayTV;
    public TextView eveTV;
    public TextView nightTV;
    public TextView rainTV;
    public TextView snowTV;
    public View view;

    public WeatherListHolder(View itemView) {
        super(itemView);
        view = itemView;

        dayTimeTv = view.findViewById(R.id.time_day_tv);
        maxTempTv = view.findViewById(R.id.max_weather_tv);
        minTempTv = view.findViewById(R.id.min_weather_tv);
        cloudPressureTV = view.findViewById(R.id.cloud_prerssure_tv);
        windTv = view.findViewById(R.id.wind_tv);
        humidityTv = view.findViewById(R.id.heumidity_tv);
        weaTherIcon = view.findViewById(R.id.weather_icon);

        monTV = view.findViewById(R.id.monTv);
        dayTV = view.findViewById(R.id.dayTv);
        eveTV = view.findViewById(R.id.eveTv);
        nightTV = view.findViewById(R.id.nightTv);
        rainTV = view.findViewById(R.id.rainTv);
        snowTV = view.findViewById(R.id.snowTv);

    }
}
