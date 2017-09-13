package com.servond.reaper.servond.Model;

import java.util.Date;

/**
 * Created by Reaper on 9/3/2017.
 */

public class Customer {
    private int id_customer;
    private int id_user;
    private String motor;
    private int year;
    private String name;

    public Customer(int id_customer, int id_user, String motor, String name, int year){
        this.id_customer = id_customer;
        this.id_user = id_user;
        this.motor = motor;
        this.year = year;
        this.name = name;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public int getYear() {
        return year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
