package com.servond.reaper.servond.Model;

/**
 * Created by Reaper on 8/12/2017.
 */

public class Order {
    private int id_order;
    private String name;
    private int id_mechanic;
    private int id_customer;
    private String category;
    private String longitude;
    private String latitude;
    private String description;
    private String location;
    private String need_at;
    private String motor;
    private String year;

    public Order(int id_order, String name, String category,String description,String location, String need_at){
        this.id_order = id_order;
        this.name = name;
        this.category = category;
        this.description = description;
        this.location = location;
        this.need_at = need_at;
    }

    public Order(int id_order, String name, String category, String description, String latitude, String longitude, String need_at, int id_mechanic) {
        this.id_order = id_order;
        this.name = name;
        this.category = category;
        this.description = description;
        this.longitude = longitude;
        this.latitude = latitude;
        this.need_at = need_at;
        this.id_mechanic = id_mechanic;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getId_mechanic() {
        return id_mechanic;
    }

    public void setId_mechanic(int id_mechanic) {
        this.id_mechanic = id_mechanic;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeed_at() {
        return need_at;
    }

    public void setNeed_at(String need_at) {
        this.need_at = need_at;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
