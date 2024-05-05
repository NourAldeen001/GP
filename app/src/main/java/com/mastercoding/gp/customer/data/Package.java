package com.mastercoding.gp.customer.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Package {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("originalTotalRequiredTime")
    @Expose
    private String originalTotalRequiredTime;

    @SerializedName("currentPackagePrice")
    @Expose
    private String currentPackagePrice;

    @SerializedName("availableInBranch")
    @Expose
    private String availableInBranch;

    public Package() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOriginalTotalRequiredTime() {
        return originalTotalRequiredTime;
    }

    public void setOriginalTotalRequiredTime(String originalTotalRequiredTime) {
        this.originalTotalRequiredTime = originalTotalRequiredTime;
    }

    public String getCurrentPackagePrice() {
        return currentPackagePrice;
    }

    public void setCurrentPackagePrice(String currentPackagePrice) {
        this.currentPackagePrice = currentPackagePrice;
    }

    public String getAvailableInBranch() {
        return availableInBranch;
    }

    public void setAvailableInBranch(String availableInBranch) {
        this.availableInBranch = availableInBranch;
    }
}
