package com.mastercoding.gp.customer.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderList {

    @SerializedName("services")
    @Expose
    private List<ServiceOnOrderList> services;

    @SerializedName("packages")
    @Expose
    private List<PackageOnOrderList> packages;

    @SerializedName("totalRequiredTime")
    @Expose
    private String totalRequiredTime;

    @SerializedName("orderTotalCost")
    @Expose
    private String orderTotalCost;

    public OrderList() {

    }

    public List<ServiceOnOrderList> getServices() {
        return services;
    }

    public List<PackageOnOrderList> getPackages() {
        return packages;
    }

    public String getTotalRequiredTime() {
        return totalRequiredTime;
    }

    public String getOrderTotalCost() {
        return orderTotalCost;
    }
}
