package com.mastercoding.gp.parkingworker.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParkingWorkerActiveStatus {

    @SerializedName("message")
    @Expose
    private String message;

    public ParkingWorkerActiveStatus() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
