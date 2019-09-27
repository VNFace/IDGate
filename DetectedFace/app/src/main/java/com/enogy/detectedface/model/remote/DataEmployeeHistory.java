package com.enogy.detectedface.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataEmployeeHistory {
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<EmployeeHistory> list;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<EmployeeHistory> getList() {
        return list;
    }

    public void setList(List<EmployeeHistory> list) {
        this.list = list;
    }

    public DataEmployeeHistory(String message, List<EmployeeHistory> list) {
        this.message = message;
        this.list = list;
    }
}
