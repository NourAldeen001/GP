package com.mastercoding.gp.parkingworker.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParkingWorkerGetCapacityResponse {

    @SerializedName("capacity")
    @Expose
    private String capacity;

    public ParkingWorkerGetCapacityResponse() {

    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }
}
