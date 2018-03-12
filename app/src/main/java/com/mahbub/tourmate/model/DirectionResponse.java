package com.mahbub.tourmate.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mahbuburrahman on 1/22/18.
 */



public class DirectionResponse {

    @SerializedName("geocoded_waypoints")
    @Expose
    private List<GeocodedWaypoint> geocodedWaypoints = null;
    @SerializedName("routes")
    @Expose
    private List<Route> routes = null;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * No args constructor for use in serialization
     *
     */
    public DirectionResponse() {
    }

    /**
     *
     * @param status
     * @param routes
     * @param geocodedWaypoints
     */
    public DirectionResponse(List<GeocodedWaypoint> geocodedWaypoints, List<Route> routes, String status) {
        super();
        this.geocodedWaypoints = geocodedWaypoints;
        this.routes = routes;
        this.status = status;
    }

    public List<GeocodedWaypoint> getGeocodedWaypoints() {
        return geocodedWaypoints;
    }

    public void setGeocodedWaypoints(List<GeocodedWaypoint> geocodedWaypoints) {
        this.geocodedWaypoints = geocodedWaypoints;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Bounds {

        @SerializedName("northeast")
        @Expose
        private Northeast northeast;
        @SerializedName("southwest")
        @Expose
        private Southwest southwest;

        /**
         * No args constructor for use in serialization
         *
         */
        public Bounds() {
        }

        /**
         *
         * @param southwest
         * @param northeast
         */
        public Bounds(Northeast northeast, Southwest southwest) {
            super();
            this.northeast = northeast;
            this.southwest = southwest;
        }

        public Northeast getNortheast() {
            return northeast;
        }

        public void setNortheast(Northeast northeast) {
            this.northeast = northeast;
        }

        public Southwest getSouthwest() {
            return southwest;
        }

        public void setSouthwest(Southwest southwest) {
            this.southwest = southwest;
        }

    }
//-----------------------------------com.mahbuburrahman.weatherapp.Distance.java-----------------------------------


    public static class Distance {

        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("value")
        @Expose
        private int value;

        /**
         * No args constructor for use in serialization
         *
         */
        public Distance() {
        }

        /**
         *
         * @param text
         * @param value
         */
        public Distance(String text, int value) {
            super();
            this.text = text;
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

    }
//-----------------------------------com.mahbuburrahman.weatherapp.Distance_.java-----------------------------------


    public static class Distance_ {

        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("value")
        @Expose
        private int value;

        /**
         * No args constructor for use in serialization
         *
         */
        public Distance_() {
        }

        /**
         *
         * @param text
         * @param value
         */
        public Distance_(String text, int value) {
            super();
            this.text = text;
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

    }
//-----------------------------------com.mahbuburrahman.weatherapp.Duration.java-----------------------------------


    public static class Duration {

        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("value")
        @Expose
        private int value;

        /**
         * No args constructor for use in serialization
         *
         */
        public Duration() {
        }

        /**
         *
         * @param text
         * @param value
         */
        public Duration(String text, int value) {
            super();
            this.text = text;
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

    }
//-----------------------------------com.mahbuburrahman.weatherapp.Duration_.java-----------------------------------


    public static class Duration_ {

        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("value")
        @Expose
        private int value;

        /**
         * No args constructor for use in serialization
         *
         */
        public Duration_() {
        }

        /**
         *
         * @param text
         * @param value
         */
        public Duration_(String text, int value) {
            super();
            this.text = text;
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

    }
//-----------------------------------com.mahbuburrahman.weatherapp.EndLocation.java-----------------------------------


    public static class EndLocation {

        @SerializedName("lat")
        @Expose
        private double lat;
        @SerializedName("lng")
        @Expose
        private double lng;

        /**
         * No args constructor for use in serialization
         *
         */
        public EndLocation() {
        }

        /**
         *
         * @param lng
         * @param lat
         */
        public EndLocation(double lat, double lng) {
            super();
            this.lat = lat;
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

    }
//-----------------------------------com.mahbuburrahman.weatherapp.EndLocation_.java-----------------------------------


    public static class EndLocation_ {

        @SerializedName("lat")
        @Expose
        private double lat;
        @SerializedName("lng")
        @Expose
        private double lng;

        /**
         * No args constructor for use in serialization
         *
         */
        public EndLocation_() {
        }

        /**
         *
         * @param lng
         * @param lat
         */
        public EndLocation_(double lat, double lng) {
            super();
            this.lat = lat;
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

    }
//-----------------------------------com.mahbuburrahman.weatherapp.GeocodedWaypoint.java-----------------------------------


    public static class GeocodedWaypoint {

        @SerializedName("geocoder_status")
        @Expose
        private String geocoderStatus;
        @SerializedName("place_id")
        @Expose
        private String placeId;
        @SerializedName("types")
        @Expose
        private List<String> types = null;

        /**
         * No args constructor for use in serialization
         *
         */
        public GeocodedWaypoint() {
        }

        /**
         *
         * @param geocoderStatus
         * @param placeId
         * @param types
         */
        public GeocodedWaypoint(String geocoderStatus, String placeId, List<String> types) {
            super();
            this.geocoderStatus = geocoderStatus;
            this.placeId = placeId;
            this.types = types;
        }

        public String getGeocoderStatus() {
            return geocoderStatus;
        }

        public void setGeocoderStatus(String geocoderStatus) {
            this.geocoderStatus = geocoderStatus;
        }

        public String getPlaceId() {
            return placeId;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

    }
//-----------------------------------com.mahbuburrahman.weatherapp.Leg.java-----------------------------------


    public static class Leg {

        @SerializedName("distance")
        @Expose
        private Distance distance;
        @SerializedName("duration")
        @Expose
        private Duration duration;
        @SerializedName("end_address")
        @Expose
        private String endAddress;
        @SerializedName("end_location")
        @Expose
        private EndLocation endLocation;
        @SerializedName("start_address")
        @Expose
        private String startAddress;
        @SerializedName("start_location")
        @Expose
        private StartLocation startLocation;
        @SerializedName("steps")
        @Expose
        private List<Step> steps = null;
        @SerializedName("traffic_speed_entry")
        @Expose
        private List<Object> trafficSpeedEntry = null;
        @SerializedName("via_waypoint")
        @Expose
        private List<Object> viaWaypoint = null;

        /**
         * No args constructor for use in serialization
         *
         */
        public Leg() {
        }

        /**
         *
         * @param startAddress
         * @param duration
         * @param distance
         * @param trafficSpeedEntry
         * @param endLocation
         * @param startLocation
         * @param steps
         * @param endAddress
         * @param viaWaypoint
         */
        public Leg(Distance distance, Duration duration, String endAddress, EndLocation endLocation, String startAddress, StartLocation startLocation, List<Step> steps, List<Object> trafficSpeedEntry, List<Object> viaWaypoint) {
            super();
            this.distance = distance;
            this.duration = duration;
            this.endAddress = endAddress;
            this.endLocation = endLocation;
            this.startAddress = startAddress;
            this.startLocation = startLocation;
            this.steps = steps;
            this.trafficSpeedEntry = trafficSpeedEntry;
            this.viaWaypoint = viaWaypoint;
        }

        public Distance getDistance() {
            return distance;
        }

        public void setDistance(Distance distance) {
            this.distance = distance;
        }

        public Duration getDuration() {
            return duration;
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }

        public String getEndAddress() {
            return endAddress;
        }

        public void setEndAddress(String endAddress) {
            this.endAddress = endAddress;
        }

        public EndLocation getEndLocation() {
            return endLocation;
        }

        public void setEndLocation(EndLocation endLocation) {
            this.endLocation = endLocation;
        }

        public String getStartAddress() {
            return startAddress;
        }

        public void setStartAddress(String startAddress) {
            this.startAddress = startAddress;
        }

        public StartLocation getStartLocation() {
            return startLocation;
        }

        public void setStartLocation(StartLocation startLocation) {
            this.startLocation = startLocation;
        }

        public List<Step> getSteps() {
            return steps;
        }

        public void setSteps(List<Step> steps) {
            this.steps = steps;
        }

        public List<Object> getTrafficSpeedEntry() {
            return trafficSpeedEntry;
        }

        public void setTrafficSpeedEntry(List<Object> trafficSpeedEntry) {
            this.trafficSpeedEntry = trafficSpeedEntry;
        }

        public List<Object> getViaWaypoint() {
            return viaWaypoint;
        }

        public void setViaWaypoint(List<Object> viaWaypoint) {
            this.viaWaypoint = viaWaypoint;
        }

    }
//-----------------------------------com.mahbuburrahman.weatherapp.Northeast.java-----------------------------------


    public static class Northeast {

        @SerializedName("lat")
        @Expose
        private double lat;
        @SerializedName("lng")
        @Expose
        private double lng;

        /**
         * No args constructor for use in serialization
         *
         */
        public Northeast() {
        }

        /**
         *
         * @param lng
         * @param lat
         */
        public Northeast(double lat, double lng) {
            super();
            this.lat = lat;
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

    }

    public static class OverviewPolyline {

        @SerializedName("points")
        @Expose
        private String points;

        /**
         * No args constructor for use in serialization
         *
         */
        public OverviewPolyline() {
        }

        /**
         *
         * @param points
         */
        public OverviewPolyline(String points) {
            super();
            this.points = points;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

    }
//-----------------------------------com.mahbuburrahman.weatherapp.Polyline.java-----------------------------------


    public static class Polyline {

        @SerializedName("points")
        @Expose
        private String points;

        /**
         * No args constructor for use in serialization
         *
         */
        public Polyline() {
        }

        /**
         *
         * @param points
         */
        public Polyline(String points) {
            super();
            this.points = points;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

    }
//-----------------------------------com.mahbuburrahman.weatherapp.Route.java-----------------------------------


    public static class Route {

        @SerializedName("bounds")
        @Expose
        private Bounds bounds;
        @SerializedName("copyrights")
        @Expose
        private String copyrights;
        @SerializedName("legs")
        @Expose
        private List<Leg> legs = null;
        @SerializedName("overview_polyline")
        @Expose
        private OverviewPolyline overviewPolyline;
        @SerializedName("summary")
        @Expose
        private String summary;
        @SerializedName("warnings")
        @Expose
        private List<Object> warnings = null;
        @SerializedName("waypoint_order")
        @Expose
        private List<Object> waypointOrder = null;

        /**
         * No args constructor for use in serialization
         *
         */
        public Route() {
        }

        /**
         *
         * @param waypointOrder
         * @param summary
         * @param bounds
         * @param copyrights
         * @param legs
         * @param warnings
         * @param overviewPolyline
         */
        public Route(Bounds bounds, String copyrights, List<Leg> legs, OverviewPolyline overviewPolyline, String summary, List<Object> warnings, List<Object> waypointOrder) {
            super();
            this.bounds = bounds;
            this.copyrights = copyrights;
            this.legs = legs;
            this.overviewPolyline = overviewPolyline;
            this.summary = summary;
            this.warnings = warnings;
            this.waypointOrder = waypointOrder;
        }

        public Bounds getBounds() {
            return bounds;
        }

        public void setBounds(Bounds bounds) {
            this.bounds = bounds;
        }

        public String getCopyrights() {
            return copyrights;
        }

        public void setCopyrights(String copyrights) {
            this.copyrights = copyrights;
        }

        public List<Leg> getLegs() {
            return legs;
        }

        public void setLegs(List<Leg> legs) {
            this.legs = legs;
        }

        public OverviewPolyline getOverviewPolyline() {
            return overviewPolyline;
        }

        public void setOverviewPolyline(OverviewPolyline overviewPolyline) {
            this.overviewPolyline = overviewPolyline;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public List<Object> getWarnings() {
            return warnings;
        }

        public void setWarnings(List<Object> warnings) {
            this.warnings = warnings;
        }

        public List<Object> getWaypointOrder() {
            return waypointOrder;
        }

        public void setWaypointOrder(List<Object> waypointOrder) {
            this.waypointOrder = waypointOrder;
        }

    }
//-----------------------------------com.mahbuburrahman.weatherapp.Southwest.java-----------------------------------


    public static class Southwest {

        @SerializedName("lat")
        @Expose
        private double lat;
        @SerializedName("lng")
        @Expose
        private double lng;

        /**
         * No args constructor for use in serialization
         *
         */
        public Southwest() {
        }

        /**
         *
         * @param lng
         * @param lat
         */
        public Southwest(double lat, double lng) {
            super();
            this.lat = lat;
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

    }
//-----------------------------------com.mahbuburrahman.weatherapp.StartLocation.java-----------------------------------


    public static class StartLocation {

        @SerializedName("lat")
        @Expose
        private double lat;
        @SerializedName("lng")
        @Expose
        private double lng;

        /**
         * No args constructor for use in serialization
         *
         */
        public StartLocation() {
        }

        /**
         *
         * @param lng
         * @param lat
         */
        public StartLocation(double lat, double lng) {
            super();
            this.lat = lat;
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

    }
//-----------------------------------com.mahbuburrahman.weatherapp.StartLocation_.java-----------------------------------


    public static class StartLocation_ {

        @SerializedName("lat")
        @Expose
        private double lat;
        @SerializedName("lng")
        @Expose
        private double lng;

        /**
         * No args constructor for use in serialization
         *
         */
        public StartLocation_() {
        }

        /**
         *
         * @param lng
         * @param lat
         */
        public StartLocation_(double lat, double lng) {
            super();
            this.lat = lat;
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

    }
//-----------------------------------com.mahbuburrahman.weatherapp.Step.java-----------------------------------

    public static class Step {

        @SerializedName("distance")
        @Expose
        private Distance_ distance;
        @SerializedName("duration")
        @Expose
        private Duration_ duration;
        @SerializedName("end_location")
        @Expose
        private EndLocation_ endLocation;
        @SerializedName("html_instructions")
        @Expose
        private String htmlInstructions;
        @SerializedName("polyline")
        @Expose
        private Polyline polyline;
        @SerializedName("start_location")
        @Expose
        private StartLocation_ startLocation;
        @SerializedName("travel_mode")
        @Expose
        private String travelMode;
        @SerializedName("maneuver")
        @Expose
        private String maneuver;

        /**
         * No args constructor for use in serialization
         *
         */
        public Step() {
        }

        /**
         *
         * @param duration
         * @param distance
         * @param polyline
         * @param endLocation
         * @param htmlInstructions
         * @param startLocation
         * @param maneuver
         * @param travelMode
         */
        public Step(Distance_ distance, Duration_ duration, EndLocation_ endLocation, String htmlInstructions, Polyline polyline, StartLocation_ startLocation, String travelMode, String maneuver) {
            super();
            this.distance = distance;
            this.duration = duration;
            this.endLocation = endLocation;
            this.htmlInstructions = htmlInstructions;
            this.polyline = polyline;
            this.startLocation = startLocation;
            this.travelMode = travelMode;
            this.maneuver = maneuver;
        }

        public Distance_ getDistance() {
            return distance;
        }

        public void setDistance(Distance_ distance) {
            this.distance = distance;
        }

        public Duration_ getDuration() {
            return duration;
        }

        public void setDuration(Duration_ duration) {
            this.duration = duration;
        }

        public EndLocation_ getEndLocation() {
            return endLocation;
        }

        public void setEndLocation(EndLocation_ endLocation) {
            this.endLocation = endLocation;
        }

        public String getHtmlInstructions() {
            return htmlInstructions;
        }

        public void setHtmlInstructions(String htmlInstructions) {
            this.htmlInstructions = htmlInstructions;
        }

        public Polyline getPolyline() {
            return polyline;
        }

        public void setPolyline(Polyline polyline) {
            this.polyline = polyline;
        }

        public StartLocation_ getStartLocation() {
            return startLocation;
        }

        public void setStartLocation(StartLocation_ startLocation) {
            this.startLocation = startLocation;
        }

        public String getTravelMode() {
            return travelMode;
        }

        public void setTravelMode(String travelMode) {
            this.travelMode = travelMode;
        }

        public String getManeuver() {
            return maneuver;
        }

        public void setManeuver(String maneuver) {
            this.maneuver = maneuver;
        }

    }

}