package com.mastercoding.gp.parkingworker.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mastercoding.gp.customer.data.OrderList;

public class ParkingWorkerCheckoutResponse {

    @SerializedName("carPlateNumber")
    @Expose
    private String carPlateNumber;

    @SerializedName("customerName")
    @Expose
    private String customerName;

    @SerializedName("parkingPeriod")
    @Expose
    private String parkingPeriod;

    @SerializedName("parkingPrice")
    @Expose
    private String parkingPrice;

    @SerializedName("order")
    @Expose
    private OrderList order;

    @SerializedName("totalCost")
    @Expose
    private String totalCost;

    public ParkingWorkerCheckoutResponse() {

    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getParkingPeriod() {
        return parkingPeriod;
    }

    public void setParkingPeriod(String parkingPeriod) {
        this.parkingPeriod = parkingPeriod;
    }

    public String getParkingPrice() {
        return parkingPrice;
    }

    public void setParkingPrice(String parkingPrice) {
        this.parkingPrice = parkingPrice;
    }

    public OrderList getOrder() {
        return order;
    }

    public void setOrder(OrderList order) {
        this.order = order;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }
}
