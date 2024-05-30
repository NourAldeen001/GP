package com.mastercoding.gp.shareddata.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("notificationTitle")
    @Expose
    private String notificationTitle;

    @SerializedName("notificationContent")
    @Expose
    private String notificationContent;

    @SerializedName("status")
    @Expose
    private String status;

    public Notification() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
