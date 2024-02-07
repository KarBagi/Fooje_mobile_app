package com.example.foojeserver.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "restaurant")
public class Restauration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESTAURANT_ID")
    private long restaurantId;

    private String restaurantName;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private String streetAddress;
    private String city;
    private String webPage;
    private int foodType;
    private Boolean animalInfo;
    private Boolean toiletInfo;
    private Boolean carInfo;
    private String hoursMonInfo;
    private String hoursTueInfo;
    private String hoursWenInfo;
    private String hoursThuInfo;
    private String hoursFriInfo;
    private String hoursSatInfo;
    private String hoursSunInfo;
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public int getFoodType() {
        return foodType;
    }

    public void setFoodType(int foodType) {
        this.foodType = foodType;
    }

    public Boolean getAnimalInfo() {
        return animalInfo;
    }

    public void setAnimalInfo(Boolean animalInfo) {
        this.animalInfo = animalInfo;
    }

    public Boolean getToiletInfo() {
        return toiletInfo;
    }

    public void setToiletInfo(Boolean toiletInfo) {
        this.toiletInfo = toiletInfo;
    }

    public Boolean getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(Boolean carInfo) {
        this.carInfo = carInfo;
    }

    public String getHoursMonInfo() {
        return hoursMonInfo;
    }

    public void setHoursMonInfo(String hoursMonInfo) {
        this.hoursMonInfo = hoursMonInfo;
    }

    public String getHoursTueInfo() {
        return hoursTueInfo;
    }

    public void setHoursTueInfo(String hoursTueInfo) {
        this.hoursTueInfo = hoursTueInfo;
    }

    public String getHoursWenInfo() {
        return hoursWenInfo;
    }

    public void setHoursWenInfo(String hoursWenInfo) {
        this.hoursWenInfo = hoursWenInfo;
    }

    public String getHoursThuInfo() {
        return hoursThuInfo;
    }

    public void setHoursThuInfo(String hoursThuInfo) {
        this.hoursThuInfo = hoursThuInfo;
    }

    public String getHoursFriInfo() {
        return hoursFriInfo;
    }

    public void setHoursFriInfo(String hoursFriInfo) {
        this.hoursFriInfo = hoursFriInfo;
    }

    public String getHoursSatInfo() {
        return hoursSatInfo;
    }

    public void setHoursSatInfo(String hoursSatInfo) {
        this.hoursSatInfo = hoursSatInfo;
    }

    public String getHoursSunInfo() {
        return hoursSunInfo;
    }

    public void setHoursSunInfo(String hoursSunInfo) {
        this.hoursSunInfo = hoursSunInfo;
    }
}
