package com.mahbub.tourmate.model;

/**
 * Created by Mahbuburrahman on 1/31/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceResponse {

        @SerializedName("html_attributions")
        @Expose
        private List<Object> htmlAttributions = null;
        @SerializedName("results")
        @Expose
        private List<Result> results = null;
        @SerializedName("status")
        @Expose
        private String status;

        /**
         * No args constructor for use in serialization
         *
         */
        public PlaceResponse() {
        }

        /**
         *
         * @param results
         * @param status
         * @param htmlAttributions
         */
        public PlaceResponse(List<Object> htmlAttributions, List<Result> results, String status) {
            super();
            this.htmlAttributions = htmlAttributions;
            this.results = results;
            this.status = status;
        }

        public List<Object> getHtmlAttributions() {
            return htmlAttributions;
        }

        public void setHtmlAttributions(List<Object> htmlAttributions) {
            this.htmlAttributions = htmlAttributions;
        }

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }



    public static class Geometry {

        @SerializedName("location")
        @Expose
        private Location location;
        @SerializedName("viewport")
        @Expose
        private Viewport viewport;

        /**
         * No args constructor for use in serialization
         *
         */
        public Geometry() {
        }

        /**
         *
         * @param viewport
         * @param location
         */
        public Geometry(Location location, Viewport viewport) {
            super();
            this.location = location;
            this.viewport = viewport;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Viewport getViewport() {
            return viewport;
        }

        public void setViewport(Viewport viewport) {
            this.viewport = viewport;
        }

    }


    public static class Location {

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
        public Location() {
        }

        /**
         *
         * @param lng
         * @param lat
         */
        public Location(double lat, double lng) {
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


    public static class OpeningHours {

        @SerializedName("open_now")
        @Expose
        private boolean openNow;
        @SerializedName("weekday_text")
        @Expose
        private List<Object> weekdayText = null;

        /**
         * No args constructor for use in serialization
         *
         */
        public OpeningHours() {
        }

        /**
         *
         * @param weekdayText
         * @param openNow
         */
        public OpeningHours(boolean openNow, List<Object> weekdayText) {
            super();
            this.openNow = openNow;
            this.weekdayText = weekdayText;
        }

        public boolean isOpenNow() {
            return openNow;
        }

        public void setOpenNow(boolean openNow) {
            this.openNow = openNow;
        }

        public List<Object> getWeekdayText() {
            return weekdayText;
        }

        public void setWeekdayText(List<Object> weekdayText) {
            this.weekdayText = weekdayText;
        }

    }


    public static class Photo {

        @SerializedName("height")
        @Expose
        private int height;
        @SerializedName("html_attributions")
        @Expose
        private List<String> htmlAttributions = null;
        @SerializedName("photo_reference")
        @Expose
        private String photoReference;
        @SerializedName("width")
        @Expose
        private int width;

        /**
         * No args constructor for use in serialization
         *
         */
        public Photo() {
        }

        /**
         *
         * @param height
         * @param width
         * @param htmlAttributions
         * @param photoReference
         */
        public Photo(int height, List<String> htmlAttributions, String photoReference, int width) {
            super();
            this.height = height;
            this.htmlAttributions = htmlAttributions;
            this.photoReference = photoReference;
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public List<String> getHtmlAttributions() {
            return htmlAttributions;
        }

        public void setHtmlAttributions(List<String> htmlAttributions) {
            this.htmlAttributions = htmlAttributions;
        }

        public String getPhotoReference() {
            return photoReference;
        }

        public void setPhotoReference(String photoReference) {
            this.photoReference = photoReference;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        @Override
        public String toString() {
            return "Photo{" +
                    "height=" + height +
                    ", htmlAttributions=" + htmlAttributions +
                    ", photoReference='" + photoReference + '\'' +
                    ", width=" + width +
                    '}';
        }
    }


    public static class Result {

        @SerializedName("geometry")
        @Expose
        private Geometry geometry;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("opening_hours")
        @Expose
        private OpeningHours openingHours;
        @SerializedName("photos")
        @Expose
        private List<Photo> photos = null;
        @SerializedName("place_id")
        @Expose
        private String placeId;
        @SerializedName("rating")
        @Expose
        private double rating;
        @SerializedName("reference")
        @Expose
        private String reference;
        @SerializedName("scope")
        @Expose
        private String scope;
        @SerializedName("types")
        @Expose
        private List<String> types = null;
        @SerializedName("vicinity")
        @Expose
        private String vicinity;

        /**
         * No args constructor for use in serialization
         *
         */
        public Result() {
        }

        /**
         *
         * @param photos
         * @param id
         * @param icon
         * @param vicinity
         * @param scope
         * @param placeId
         * @param openingHours
         * @param name
         * @param rating
         * @param types
         * @param reference
         * @param geometry
         */
        public Result(Geometry geometry, String icon, String id, String name, OpeningHours openingHours, List<Photo> photos, String placeId, double rating, String reference, String scope, List<String> types, String vicinity) {
            super();
            this.geometry = geometry;
            this.icon = icon;
            this.id = id;
            this.name = name;
            this.openingHours = openingHours;
            this.photos = photos;
            this.placeId = placeId;
            this.rating = rating;
            this.reference = reference;
            this.scope = scope;
            this.types = types;
            this.vicinity = vicinity;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public OpeningHours getOpeningHours() {
            return openingHours;
        }

        public void setOpeningHours(OpeningHours openingHours) {
            this.openingHours = openingHours;
        }

        public List<Photo> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photo> photos) {
            this.photos = photos;
        }

        public String getPlaceId() {
            return placeId;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public String getVicinity() {
            return vicinity;
        }

        public void setVicinity(String vicinity) {
            this.vicinity = vicinity;
        }

    }


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

    public static class Viewport {

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
        public Viewport() {
        }

        /**
         *
         * @param southwest
         * @param northeast
         */
        public Viewport(Northeast northeast, Southwest southwest) {
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

}


