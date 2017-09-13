package com.servond.reaper.servond.Model;

/**
 * Created by Reaper on 8/12/2017.
 */

public class Book {
    private int id;
    private int id_users;
    private int id_montir;
    private String time;
    private int interval_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_users() {
        return id_users;
    }

    public void setId_users(int id_users) {
        this.id_users = id_users;
    }

    public int getId_montir() {
        return id_montir;
    }

    public void setId_montir(int id_montir) {
        this.id_montir = id_montir;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getInterval_time() {
        return interval_time;
    }

    public void setInterval_time(int interval_time) {
        this.interval_time = interval_time;
    }
}
