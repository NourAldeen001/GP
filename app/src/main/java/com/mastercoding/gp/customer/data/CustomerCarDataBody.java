package com.mastercoding.gp.customer.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerCarDataBody {

    @SerializedName("carPlateNumber")
    @Expose
    private String carPlateNumber;

    @SerializedName("customerId")
    @Expose
    private String customerId;

    @SerializedName("carType")
    @Expose
    private String carType;

    @SerializedName("model")
    @Expose
    private String model;

    public CustomerCarDataBody(String carPlateNumber, String customerId, String carType, String model) {
        this.carPlateNumber = carPlateNumber;
        this.customerId = customerId;
        this.carType = carType;
        this.model = model;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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
}
