package com.mastercoding.gp.parkingworker.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParkingWorkerGetCountVisitationResponse {

    @SerializedName("visitationCount")
    @Expose
    private String visitationCount;

    public ParkingWorkerGetCountVisitationResponse() {

    }

    public String getVisitationCount() {
        return visitationCount;
    }

    public void setVisitationCount(String visitationCount) {
        this.visitationCount = visitationCount;
    }
}
