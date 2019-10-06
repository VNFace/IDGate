package com.enogy.detectedface.model.retrofit;

import com.enogy.detectedface.model.remote.DataAccount;
import com.enogy.detectedface.model.remote.DataEmployee;
import com.enogy.detectedface.model.remote.DataEmployeeHistory;
import com.enogy.detectedface.model.remote.Employee;
import com.google.gson.JsonElement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Api {

    @GET("people")
    Call<DataEmployeeHistory> getListEmployeeHistory();

    @GET("people")
    Call<JsonElement> testApi();

    @GET("employee")
    Call<DataEmployee> getDataEmployee();

    @GET("idmax")
    Call<DataEmployee> getIDmax();

    //get history of employee by date time
    @GET("history")
    Call<DataEmployee> getEmployeeByDate(@Query("arg1") String time);

    //method get information employee
    @GET("information")
    Call<DataEmployee> getEmployeeByID(@Query("arg1") String idEmp);

    //method for login activity
    @GET("login")
    Call<DataAccount> getAllAccount();

    //method add new employee
    //solution 1 : correct
    @POST("insert")
    Call<Employee> addNewEmployee(@Query("idEmp") String idEmp,
                                  @Query("fullName") String fullName,
                                  @Query("address") String address,
                                  @Query("email") String email,
                                  @Query("phone") String phone,
                                  @Query("position") String position);

    @PUT("update")
    Call<Employee> editEmployee(@Query("idEmp") String idEmp,
                                @Query("fullName") String fullName,
                                @Query("address") String address,
                                @Query("email") String email,
                                @Query("phone") String phone,
                                @Query("position") String position);

    @DELETE("delete")
    Call<Employee> deleteEmployee(@Query("idEmp") String idEmp);


}
