package com.mastercoding.gp.shareddata.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteNotificationResponse {

    @SerializedName("message")
    @Expose
    private String message;

    public DeleteNotificationResponse() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
