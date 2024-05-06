package com.mastercoding.gp.customer.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddServiceToOrderListBody {

    @SerializedName("customerId")
    @Expose
    private String customerId;

    @SerializedName("serviceId")
    @Expose
    private String serviceId;

    public AddServiceToOrderListBody(String customerId, String serviceId) {
        this.customerId = customerId;
        this.serviceId = serviceId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
