package com.servond.reaper.servond.Model;

/**
 * Created by Reaper on 8/12/2017.
 */

public class Mechanic {

    private int id_mechanic;
    private String name;
    private String address;
    private String longitude;
    private String latitude;
    private String phone;
    private String no_identity;
    private String email;
    private String username;
    private String password;
    private String status;
    private String ip_mechanic;

    public int getId_mechanic() {
        return id_mechanic;
    }

    public void setId_mechanic(int id_mechanic) {
        this.id_mechanic = id_mechanic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNo_identity() {
        return no_identity;
    }

    public void setNo_identity(String no_identity) {
        this.no_identity = no_identity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIp_mechanic() {
        return ip_mechanic;
    }

    public void setIp_mechanic(String ip_mechanic) {
        this.ip_mechanic = ip_mechanic;
    }
}
