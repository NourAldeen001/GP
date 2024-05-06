package com.mastercoding.gp.customer.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddServiceToOrderListResponse {

    @SerializedName("message")
    @Expose
    private String message;

    public AddServiceToOrderListResponse() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
