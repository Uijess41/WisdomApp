package com.maestros.wisomnursingclass.model;

public class CourseUnlockModel {


    private String id;
    private String title;
    private String lockStatus;
    private String paymentStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String status) {
        this.lockStatus = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
