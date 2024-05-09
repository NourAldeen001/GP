package com.mastercoding.gp.customer.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProgressConfirmOrderResponse {

    @SerializedName("services")
    @Expose
    private List<ServiceOnOrderList> services;

    @SerializedName("status")
    @Expose
    private String status;

    public GetProgressConfirmOrderResponse() {

    }

    public List<ServiceOnOrderList> getServices() {
        return services;
    }

    public void setServices(List<ServiceOnOrderList> services) {
        this.services = services;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
