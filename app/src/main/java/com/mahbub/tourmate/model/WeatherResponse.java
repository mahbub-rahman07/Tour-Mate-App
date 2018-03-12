package com.mahbub.tourmate.model;

/**
 * Created by Mahbuburrahman on 1/3/18.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {

    @SerializedName("coord")
    @Expose
    private Coord coord;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("dt")
    @Expose
    private int dt;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cod")
    @Expose
    private int cod;

    /**
     * No args constructor for use in serialization
     *
     */
    public WeatherResponse() {
    }

    /**
     *
     * @param id
     * @param dt
     * @param clouds
     * @param coord
     * @param wind
     * @param cod
     * @param sys
     * @param name
     * @param base
     * @param weather
     * @param main
     */
    public WeatherResponse(Coord coord, List<Weather> weather, String base, Main main, Wind wind, Clouds clouds, int dt, Sys sys, int id, String name, int cod) {
        super();
        this.coord = coord;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.wind = wind;
        this.clouds = clouds;
        this.dt = dt;
        this.sys = sys;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

//-----------------------------------com.example.Wind.java-----------------------------------
    public static class Wind {

        @SerializedName("speed")
        @Expose
        private double speed;
        @SerializedName("deg")
        @Expose
        private double deg;

        /**
         * No args constructor for use in serialization
         *
         */
        public Wind() {
        }

        /**
         *
         * @param speed
         * @param deg
         */
        public Wind(double speed, double deg) {
            super();
            this.speed = speed;
            this.deg = deg;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public double getDeg() {
            return deg;
        }

        public void setDeg(double deg) {
            this.deg = deg;
        }

    }
//-----------------------------------com.example.Weather.java-----------------------------------
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

//-----------------------------------com.example.Sys.java-----------------------------------
    public static class Sys {

        @SerializedName("message")
        @Expose
        private double message;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("sunrise")
        @Expose
        private int sunrise;
        @SerializedName("sunset")
        @Expose
        private int sunset;

        /**
         * No args constructor for use in serialization
         *
         */
        public Sys() {
        }

        /**
         *
         * @param message
         * @param sunset
         * @param sunrise
         * @param country
         */
        public Sys(double message, String country, int sunrise, int sunset) {
            super();
            this.message = message;
            this.country = country;
            this.sunrise = sunrise;
            this.sunset = sunset;
        }

        public double getMessage() {
            return message;
        }

        public void setMessage(double message) {
            this.message = message;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getSunrise() {
            return sunrise;
        }

        public void setSunrise(int sunrise) {
            this.sunrise = sunrise;
        }

        public int getSunset() {
            return sunset;
        }

        public void setSunset(int sunset) {
            this.sunset = sunset;
        }

    }

//-----------------------------------com.example.Main.java-----------------------------------

    public static class Main {

        @SerializedName("temp")
        @Expose
        private double temp;
        @SerializedName("pressure")
        @Expose
        private double pressure;
        @SerializedName("humidity")
        @Expose
        private int humidity;
        @SerializedName("temp_min")
        @Expose
        private double tempMin;
        @SerializedName("temp_max")
        @Expose
        private double tempMax;
        @SerializedName("sea_level")
        @Expose
        private double seaLevel;
        @SerializedName("grnd_level")
        @Expose
        private double grndLevel;

        /**
         * No args constructor for use in serialization
         *
         */
        public Main() {
        }

        /**
         *
         * @param seaLevel
         * @param humidity
         * @param pressure
         * @param grndLevel
         * @param tempMax
         * @param temp
         * @param tempMin
         */
        public Main(double temp, double pressure, int humidity, double tempMin, double tempMax, double seaLevel, double grndLevel) {
            super();
            this.temp = temp;
            this.pressure = pressure;
            this.humidity = humidity;
            this.tempMin = tempMin;
            this.tempMax = tempMax;
            this.seaLevel = seaLevel;
            this.grndLevel = grndLevel;
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
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

        public double getTempMin() {
            return tempMin;
        }

        public void setTempMin(double tempMin) {
            this.tempMin = tempMin;
        }

        public double getTempMax() {
            return tempMax;
        }

        public void setTempMax(double tempMax) {
            this.tempMax = tempMax;
        }

        public double getSeaLevel() {
            return seaLevel;
        }

        public void setSeaLevel(double seaLevel) {
            this.seaLevel = seaLevel;
        }

        public double getGrndLevel() {
            return grndLevel;
        }

        public void setGrndLevel(double grndLevel) {
            this.grndLevel = grndLevel;
        }

        @Override
        public String toString() {
            return "Main{" +
                    "temp=" + temp +
                    ", pressure=" + pressure +
                    ", humidity=" + humidity +
                    ", tempMin=" + tempMin +
                    ", tempMax=" + tempMax +
                    ", seaLevel=" + seaLevel +
                    ", grndLevel=" + grndLevel +
                    '}';
        }
    }
//-----------------------------------com.example.Coord.java-----------------------------------
    public static class Coord {

        @SerializedName("lon")
        @Expose
        private double lon;
        @SerializedName("lat")
        @Expose
        private double lat;

        /**
         * No args constructor for use in serialization
         *
         */
        public Coord() {
        }

        /**
         *
         * @param lon
         * @param lat
         */
        public Coord(double lon, double lat) {
            super();
            this.lon = lon;
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

    }
//-----------------------------------com.example.Clouds.java-----------------------------------
    public static class Clouds {

        @SerializedName("all")
        @Expose
        private int all;

        /**
         * No args constructor for use in serialization
         *
         */
        public Clouds() {
        }

        /**
         *
         * @param all
         */
        public Clouds(int all) {
            super();
            this.all = all;
        }

        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }

    }

}