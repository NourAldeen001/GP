package com.mastercoding.gp.customer.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceOnOrderList {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("serviceName")
    @Expose
    private String serviceName;

    @SerializedName("servicePrice")
    @Expose
    private String servicePrice;

    @SerializedName("requiredTime")
    @Expose
    private String requiredTime;

    @SerializedName("progressStatus")
    @Expose
    private String progressStatus;

    @SerializedName("availableInBranch")
    @Expose
    private Boolean availableInBranch;

    @SerializedName("image")
    @Expose
    private String image;

    public ServiceOnOrderList() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getRequiredTime() {
        return requiredTime;
    }

    public void setRequiredTime(String requiredTime) {
        this.requiredTime = requiredTime;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public Boolean getAvailableInBranch() {
        return availableInBranch;
    }

    public void setAvailableInBranch(Boolean availableInBranch) {
        this.availableInBranch = availableInBranch;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
