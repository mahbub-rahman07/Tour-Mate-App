package com.mahbub.tourmate.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mahbub.tourmate.R;
import com.mahbub.tourmate.holder.WeatherListHolder;
import com.mahbub.tourmate.model.ForecastMainClass;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mahbuburrahman on 1/5/18.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherListHolder> {

    private List<ForecastMainClass.WeatherList> mResponseList;
    private Context mContext;

    public WeatherAdapter(Context context, List<ForecastMainClass.WeatherList> responseList) {
        mContext = context;
        mResponseList = responseList;
    }

    @Override
    public WeatherListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.forecast_layout, parent, false);

        return new WeatherListHolder(v);
    }

    @Override
    public void onBindViewHolder(WeatherListHolder holder, int position) {
        ForecastMainClass.WeatherList weatherResponse = mResponseList.get(position);
        setData(holder, weatherResponse, position);
        Log.d("forecast", "onBind: "+(weatherResponse == null));
    }

    @Override
    public int getItemCount() {
        return mResponseList.size();
    }

    public void setData(WeatherListHolder holder,ForecastMainClass.WeatherList currentWeather, int pos){

        if (currentWeather != null) {
            Log.d("forecast", "setData: "+currentWeather.getSpeed());
            List<ForecastMainClass.Weather> weatherList = currentWeather.getWeather();
            DecimalFormat df = new DecimalFormat("#.#");

            double maxTmp = currentWeather.getTemp().getMax() - 273;
            double minTmp = currentWeather.getTemp().getMin() - 273;

            double monTmp = currentWeather.getTemp().getMorn() - 273;
            double dayTmp = currentWeather.getTemp().getDay() - 273;
            double eveTmp = currentWeather.getTemp().getEve() - 273;
            double nightTmp = currentWeather.getTemp().getNight() - 273;


            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
            boolean celsius = settings.getBoolean(mContext.getString(R.string.celsious_key), false);
            if (!celsius) {
                maxTmp = ((9 / 5.0) * (maxTmp)) + 32;
                minTmp = ((9 / 5.0) * (minTmp)) + 32;
                monTmp = ((9 / 5.0) * (monTmp)) + 32;
                dayTmp = ((9 / 5.0) * (dayTmp)) + 32;
                eveTmp = ((9 / 5.0) * (eveTmp)) + 32;
                nightTmp = ((9 / 5.0) * (nightTmp)) + 32;
            }

            float onDigitsFMax = Float.valueOf(df.format(maxTmp));
            float onDigitsFMin = Float.valueOf(df.format(minTmp));
            float onDigitsFmon = Float.valueOf(df.format(monTmp));
            float onDigitsFday = Float.valueOf(df.format(dayTmp));
            float onDigitsFeve = Float.valueOf(df.format(eveTmp));
            float onDigitsFnight = Float.valueOf(df.format(nightTmp));

            String dayTime = null;
            if (pos == 0) {
                dayTime = convertTimeDay(currentWeather.getDt()) + "\n\tToday";
            }else if(pos == 1) {
                dayTime = convertTimeDay(currentWeather.getDt()) + "\n  Tomorrow";
            }else {
                dayTime = convertTimeDay(currentWeather.getDt());
            }


            String maxTem = "Max " + onDigitsFMax + "°" + (celsius ? "C" : "F");
            String minTem = "Min " + onDigitsFMin + "°" + (celsius ? "C" : "F");
            String monTem = onDigitsFmon + "°" + (celsius ? "C" : "F");
            String dayTem = onDigitsFday + "°" + (celsius ? "C" : "F");
            String eveTem = onDigitsFeve + "°" + (celsius ? "C" : "F");
            String nightTem = onDigitsFnight + "°" + (celsius ? "C" : "F");


            String rain = "Rain: "+currentWeather.getRain()+"%";
            String snow = "Snow: "+currentWeather.getSnow()+"%";

            String wind = "Wind " + currentWeather.getSpeed()+ "m/s, " + currentWeather.getDeg() + "°";
            String cloudPressuer = "Cloud " + currentWeather.getClouds() + "%,Pressure " + currentWeather.getPressure() + "hpa";
            String humidity = "Humidity " + currentWeather.getHumidity() + "%";
            String img = weatherList.get(0).getIcon() + ".png";

            holder.dayTimeTv.setText(dayTime);
            holder.maxTempTv.setText(maxTem);
            holder.minTempTv.setText(minTem);
            holder.cloudPressureTV.setText(cloudPressuer);
            holder.windTv.setText(wind);
            holder.humidityTv.setText(humidity);


            holder.monTV.setText(monTem);
            holder.dayTV.setText(dayTem);
            holder.eveTV.setText(eveTem);
            holder.nightTV.setText(nightTem);

            holder.rainTV.setText(rain);
            holder.snowTV.setText(snow);

            Uri uri = Uri.parse("http://api.openweathermap.org/img/w/" + img);
            Picasso.with(mContext).load(uri).into(holder.weaTherIcon);
        }else{
            Log.d("forecast", "setData: "+(currentWeather == null));
        }
    }


    public String convertTimeDay(long times) {
        Date date = new Date(times * 1000);
        //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM", Locale.getDefault());
        String time = sdf.format(date);

        Log.d("TIME", "convertTime: "+time);
        return time;

    }
}
