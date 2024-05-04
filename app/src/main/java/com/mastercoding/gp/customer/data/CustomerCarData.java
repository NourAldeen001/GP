package com.mastercoding.gp.customer.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerCarData {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("carPlateNumber")
    @Expose
    private String carPlateNumber;

    @SerializedName("carType")
    @Expose
    private String carType;

    @SerializedName("model")
    @Expose
    private String model;

    @SerializedName("customerId")
    @Expose
    private int customerId;

    public CustomerCarData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
