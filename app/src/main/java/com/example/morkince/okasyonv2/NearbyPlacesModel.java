package com.example.morkince.okasyonv2;

public class NearbyPlacesModel {
String nameOfStore;
String vicinityOfStore;
String imageUrlOfStore;
double starRatingOfStore;
String openingHoursOfStore;
String totalNumberOfRatings;


    public NearbyPlacesModel(){}

    public NearbyPlacesModel(String nameOfStore, String vicinityOfStore, String imageUrlOfStore, double starRatingOfStore, String openingHoursOfStore, String totalNumberOfRatings) {
        this.nameOfStore = nameOfStore;
        this.vicinityOfStore = vicinityOfStore;
        this.imageUrlOfStore = imageUrlOfStore;
        this.starRatingOfStore = starRatingOfStore;
        this.openingHoursOfStore = openingHoursOfStore;
        this.totalNumberOfRatings = totalNumberOfRatings;
    }

    public String getNameOfStore() {
        return nameOfStore;
    }

    public void setNameOfStore(String nameOfStore) {
        this.nameOfStore = nameOfStore;
    }

    public String getVicinityOfStore() {
        return vicinityOfStore;
    }

    public void setVicinityOfStore(String vicinityOfStore) {
        this.vicinityOfStore = vicinityOfStore;
    }

    public String getImageUrlOfStore() {
        return imageUrlOfStore;
    }

    public void setImageUrlOfStore(String imageUrlOfStore) {
        this.imageUrlOfStore = imageUrlOfStore;
    }

    public double getStarRatingOfStore() {
        return starRatingOfStore;
    }

    public void setStarRatingOfStore(double starRatingOfStore) {
        this.starRatingOfStore = starRatingOfStore;
    }

    public String getOpeningHoursOfStore() {
        return openingHoursOfStore;
    }

    public void setOpeningHoursOfStore(String openingHoursOfStore) {
        this.openingHoursOfStore = openingHoursOfStore;
    }

    public String getTotalNumberOfRatings() {
        return totalNumberOfRatings;
    }

    public void setTotalNumberOfRatings(String totalNumberOfRatings) {
        this.totalNumberOfRatings = totalNumberOfRatings;
    }
}
