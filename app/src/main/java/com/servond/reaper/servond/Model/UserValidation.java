package com.servond.reaper.servond.Model;

/**
 * Created by Reaper on 8/13/2017.
 */

public class UserValidation {
    private boolean status;
    private String msg;
    private int id;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
