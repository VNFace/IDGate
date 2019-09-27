package com.enogy.detectedface.model.retrofit;

import com.enogy.detectedface.model.remote.DataEmployee;
import com.enogy.detectedface.model.remote.DataEmployeeHistory;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("people")
    Call<DataEmployeeHistory> getListEmployeeHistory();

    @GET("people")
    Call<JsonElement> testApi();

    @GET("employee")
    Call<DataEmployee> getDataEmployee();
}
