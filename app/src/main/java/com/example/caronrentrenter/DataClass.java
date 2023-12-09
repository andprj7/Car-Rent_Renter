package com.example.caronrentrenter;

import java.io.Serializable;

public class DataClass implements Serializable {
    private String modelName;

    private String imageURL;
    private String modelDescription;
    // private String price;


    private String rentPerDay;
    private String maximumSpeed;
    private String Fuel;
    private String carRating;
    private String numberPassengers;
    private String gearMode;
    private String CarCompany;


    public DataClass() {
    }

    public DataClass(String modelName, String imageURL, String modelDescription, String rentPerDay, String maximumSpeed, String fuel, String carRating ,String numberPassengers,String gearMode,String CarCompany) {
        this.modelName = modelName;
        this.imageURL = imageURL;
        this.modelDescription = modelDescription;
        this.rentPerDay = rentPerDay;
        this.maximumSpeed = maximumSpeed;
        this.Fuel = fuel;
        this.carRating = carRating;
        this.numberPassengers = numberPassengers;
        this.gearMode = gearMode;
        this.CarCompany = CarCompany;

    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public String getRentPerDay() {
        return rentPerDay;
    }

    public void setRentPerDay(String rentPerDay) {
        this.rentPerDay = rentPerDay;
    }

    public String getMaximumSpeed() {
        return maximumSpeed;
    }

    public void setMaximumSpeed(String maximumSpeed) {
        this.maximumSpeed = maximumSpeed;
    }

    public String getFuel() {
        return Fuel;
    }

    public void setFuel(String fuel) {
        Fuel = fuel;
    }

    public String getNumberPassengers() {
        return numberPassengers;
    }

    public void setNumberPassengers(String numberPassengers) {
        this.numberPassengers = numberPassengers;
    }

    public String getGearMode() {
        return gearMode;
    }

    public void setGearMode(String gearMode) {
        this.gearMode = gearMode;
    }

    public String getCarRating() {
        return carRating;
    }

    public void setCarRating(String carRating) {
        this.carRating = carRating;
    }
//    public DataClass(String title, String imageURL, String caption, String price, int time, int energy, String score) {
//        this.title = title;
//        this.imageURL = imageURL;
//        this.caption = caption;
//        this.price = price;
//        this.time = time;
//        this.energy = energy;
//        this.score = score;
//        //this.numberInCart = numberInCart;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getImageURL() {
//        return imageURL;
//    }
//
//    public void setImageURL(String imageURL) {
//        this.imageURL = imageURL;
//    }
//
//    public String getCaption() {
//        return caption;
//    }
//
//    public void setCaption(String caption) {
//        this.caption = caption;
//    }
//
//    public String getPrice() {
//        return price;
//    }
//
//    public void setPrice(String price) {
//        this.price = price;
//    }
//
//    public int getTime() {
//        return time;
//    }
//
//    public void setTime(int time) {
//        this.time = time;
//    }
//
//    public int getEnergy() {
//        return energy;
//    }
//
//    public void setEnergy(int energy) {
//        this.energy = energy;
//    }
//
//    public String getScore() {
//        return score;
//    }
//
//    public void setScore(String score) {
//        this.score = score;
//    }
//
//    public int getNumberInCart() {
//        return numberInCart;
//    }
//
//    public void setNumberInCart(int numberInCart) {
//        this.numberInCart = numberInCart;
//    }
}
