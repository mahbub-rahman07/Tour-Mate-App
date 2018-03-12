package com.mahbub.tourmate.model;

/**
 * Created by Mahbuburrahman on 1/5/18.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherForecast {
    @SerializedName("dt")
    @Expose
    private int dt;
    @SerializedName("temp")
    @Expose
    private Temp temp;
    @SerializedName("pressure")
    @Expose
    private double pressure;
    @SerializedName("humidity")
    @Expose
    private int humidity;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;
    @SerializedName("speed")
    @Expose
    private double speed;
    @SerializedName("deg")
    @Expose
    private int deg;
    @SerializedName("clouds")
    @Expose
    private int clouds;
    @SerializedName("rain")
    @Expose
    private double rain;
    @SerializedName("snow")
    @Expose
    private double snow;

    /**
     * No args constructor for use in serialization
     *
     */
    public WeatherForecast() {
    }

    /**
     *
     * @param clouds
     * @param dt
     * @param humidity
     * @param pressure
     * @param speed
     * @param snow
     * @param deg
     * @param weather
     * @param temp
     * @param rain
     */
    public WeatherForecast(int dt, Temp temp, double pressure, int humidity, List<Weather> weather, double speed, int deg, int clouds, double rain, double snow) {
        super();
        this.dt = dt;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.weather = weather;
        this.speed = speed;
        this.deg = deg;
        this.clouds = clouds;
        this.rain = rain;
        this.snow = snow;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getSnow() {
        return snow;
    }

    public void setSnow(double snow) {
        this.snow = snow;
    }
//-----------------------------------com.mahbuburrahman.weatherapp.Weather.java-----------------------------------

    public static class Weather {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("main")
        @Expose
        private String main;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("icon")
        @Expose
        private String icon;

        /**
         * No args constructor for use in serialization
         *
         */
        public Weather() {
        }

        /**
         *
         * @param id
         * @param icon
         * @param description
         * @param main
         */
        public Weather(int id, String main, String description, String icon) {
            super();
            this.id = id;
            this.main = main;
            this.description = description;
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

    }

//-----------------------------------com.mahbuburrahman.weatherapp.Temp.java-----------------------------------

    public static class Temp {

        @SerializedName("day")
        @Expose
        private double day;
        @SerializedName("min")
        @Expose
        private double min;
        @SerializedName("max")
        @Expose
        private double max;
        @SerializedName("night")
        @Expose
        private double night;
        @SerializedName("eve")
        @Expose
        private double eve;
        @SerializedName("morn")
        @Expose
        private double morn;

        /**
         * No args constructor for use in serialization
         *
         */
        public Temp() {
        }

        /**
         *
         * @param min
         * @param eve
         * @param max
         * @param morn
         * @param night
         * @param day
         */
        public Temp(double day, double min, double max, double night, double eve, double morn) {
            super();
            this.day = day;
            this.min = min;
            this.max = max;
            this.night = night;
            this.eve = eve;
            this.morn = morn;
        }

        public double getDay() {
            return day;
        }

        public void setDay(double day) {
            this.day = day;
        }

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public double getNight() {
            return night;
        }

        public void setNight(double night) {
            this.night = night;
        }

        public double getEve() {
            return eve;
        }

        public void setEve(double eve) {
            this.eve = eve;
        }

        public double getMorn() {
            return morn;
        }

        public void setMorn(double morn) {
            this.morn = morn;
        }

    }
}
