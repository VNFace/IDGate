package com.enogy.detectedface.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataAccount {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("account")
    @Expose
    private List<Account> list;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Account> getList() {
        return list;
    }

    public void setList(List<Account> list) {
        this.list = list;
    }

    public DataAccount(String message, List<Account> list) {
        this.message = message;
        this.list = list;
    }
}
