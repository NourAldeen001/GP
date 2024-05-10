package com.mastercoding.gp.parkingworker.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParkingWorkerFinishTaskBody {

    @SerializedName("carPlateNumber")
    @Expose
    private String carPlateNumber;

    @SerializedName("workerId")
    @Expose
    private String workerId;

    public ParkingWorkerFinishTaskBody(String carPlateNumber, String workerId) {
        this.carPlateNumber = carPlateNumber;
        this.workerId = workerId;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }
}
