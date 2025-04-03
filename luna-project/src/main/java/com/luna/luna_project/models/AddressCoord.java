package com.luna.luna_project.models;

public class AddressCoord {

        private double lat;
        private double lng;

    public AddressCoord(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }

}
