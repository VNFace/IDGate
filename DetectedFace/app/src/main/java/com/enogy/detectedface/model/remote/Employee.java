package com.enogy.detectedface.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employee {
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Finger")
    @Expose
    private String finger;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("ID_Emp")
    @Expose
    private String idEmp;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("trainedCount")
    @Expose
    private String trainedCount;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFinger() {
        return finger;
    }

    public void setFinger(String finger) {
        this.finger = finger;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(String idEmp) {
        this.idEmp = idEmp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTrainedCount() {
        return trainedCount;
    }

    public void setTrainedCount(String trainedCount) {
        this.trainedCount = trainedCount;
    }

    public Employee(String address, String finger, String fullName, String idEmp, String email, String phone, String position, String trainedCount) {
        this.address = address;
        this.finger = finger;
        this.fullName = fullName;
        this.idEmp = idEmp;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.trainedCount = trainedCount;
    }
}
