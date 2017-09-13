package com.servond.reaper.servond.Model;

import java.util.List;

/**
 * Created by Reaper on 8/12/2017.
 */

public class ListService{
    private String status;
    private List<Order> clients;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Order> getClients() {
        return clients;
    }

    public void setClients(List<Order> clients) {
        this.clients = clients;
    }
}
