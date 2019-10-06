package com.enogy.detectedface.model.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeHistory {
    @SerializedName("FullName")
    @Expose
    private String fullName;

    @SerializedName("ID_Emp_His")
    @Expose
    private int idEmpHis;

    @SerializedName("confident")
    @Expose
    private String confident;

    @SerializedName("etime ")
    @Expose
    private String eTime;

    @SerializedName("id_emp")
    @Expose
    private String idEmp;

    @SerializedName("image")
    @Expose
    private String image;

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return image;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getIdEmpHis() {
        return idEmpHis;
    }

    public void setIdEmpHis(int idEmpHis) {
        this.idEmpHis = idEmpHis;
    }

    public String getConfident() {
        return confident;
    }

    public void setConfident(String confident) {
        this.confident = confident;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    public String getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(String idEmp) {
        this.idEmp = idEmp;
    }

    public EmployeeHistory(String fullName, int idEmpHis,
                           String confident, String eTime,
                           String idEmp, String image) {
        this.fullName = fullName;
        this.idEmpHis = idEmpHis;
        this.confident = confident;
        this.eTime = eTime;
        this.idEmp = idEmp;
        this.image = image;
    }
}
