package com.enogy.detectedface.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("ID")
    @Expose
    private int id;
    @SerializedName("ID_Account")
    @Expose
    private int idAccount;
    @SerializedName("password")
    @Expose
    private String pass;
    @SerializedName("user_name")
    @Expose
    private String userName;

    public Account(int id, int idAccount, String pass, String userName) {
        this.id = id;
        this.idAccount = idAccount;
        this.pass = pass;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
