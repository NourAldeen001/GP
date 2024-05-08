package com.mastercoding.gp.customer.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageOnOrderList {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("packageName")
    @Expose
    private String packageName;

    @SerializedName("packagePrice")
    @Expose
    private String packagePrice;

    @SerializedName("requiredTime")
    @Expose
    private String requiredTime;

    @SerializedName("availableInBranch")
    @Expose
    private Boolean availableInBranch;

    @SerializedName("image")
    @Expose
    private String image;

    public PackageOnOrderList() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice;
    }

    public String getRequiredTime() {
        return requiredTime;
    }

    public void setRequiredTime(String requiredTime) {
        this.requiredTime = requiredTime;
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
