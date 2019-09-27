package com.enogy.detectedface.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataEmployee {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("employee")
    @Expose
    private List<Employee> list;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Employee> getList() {
        return list;
    }

    public void setList(List<Employee> list) {
        this.list = list;
    }

    public DataEmployee(String message, List<Employee> list) {
        this.message = message;
        this.list = list;
    }
}
