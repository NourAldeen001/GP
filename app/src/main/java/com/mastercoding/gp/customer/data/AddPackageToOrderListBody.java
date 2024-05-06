package com.mastercoding.gp.customer.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddPackageToOrderListBody {

    @SerializedName("customerId")
    @Expose
    private String customerId;

    @SerializedName("packageId")
    @Expose
    private String packageId;

    public AddPackageToOrderListBody(String customerId, String packageId) {
        this.customerId = customerId;
        this.packageId = packageId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
}
