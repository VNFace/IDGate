package com.enogy.detectedface.uis.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.enogy.detectedface.R;
import com.enogy.detectedface.model.remote.DataEmployee;
import com.enogy.detectedface.model.remote.Employee;
import com.enogy.detectedface.model.retrofit.RetrofitCreated;
import com.enogy.detectedface.utils.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationEmployeeActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgImageEmployee;
    private EditText edFullName;
    private EditText edAddress;
    private EditText edEmail;
    private EditText edPhone;
    private EditText edPosition;
    private ImageView imgDelete;
    private TextView txtOk;
    private Toolbar toolbar;
    private String idEmployee;
    private SharedPreferences preferences;
    private String baseApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_employee);
        toolbar = findViewById(R.id.toolBarInformationEmployee);
        imgImageEmployee = findViewById(R.id.imgViewImageInformation);
        edFullName = findViewById(R.id.edFullNameEmployee);
        edAddress = findViewById(R.id.edAddressEmployee);
        edEmail = findViewById(R.id.edEmailEmployee);
        edPhone = findViewById(R.id.edPhoneEmployee);
        edPosition = findViewById(R.id.edPosition);
        imgDelete = findViewById(R.id.imgViewDeleteEmployee);
        txtOk = findViewById(R.id.txtActionOkInformation);

        preferences = getSharedPreferences(Config.NAME, Context.MODE_PRIVATE);
        idEmployee = preferences.getString(Config.ID_EMPLOYEE, "");
        baseApi = preferences.getString(Config.BASE_API, "");
        Call<DataEmployee> call = RetrofitCreated.createApi(baseApi).getEmployeeByID(idEmployee);
        Log.e("TAG", "idemployee " + idEmployee);
        call.enqueue(new Callback<DataEmployee>() {
            @Override
            public void onResponse(Call<DataEmployee> call, Response<DataEmployee> response) {
                for (Employee e : response.body().getList()) {
                    Log.e("TAG", " e " + e.getAddress());
                }
                Employee employee = response.body().getList().get(0);
                edFullName.setText(employee.getFullName());
                edAddress.setText(employee.getAddress());
                edEmail.setText(employee.getEmail());
                edPhone.setText(employee.getPhone());
                edPosition.setText(employee.getPosition());

            }

            @Override
            public void onFailure(Call<DataEmployee> call, Throwable t) {

            }
        });
        imgDelete.setOnClickListener(this);
        txtOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtActionOkInformation: {
                String fullNam = edFullName.getText().toString().trim();
                String address = edAddress.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
                String phone = edPhone.getText().toString().trim();
                String position = edPosition.getText().toString().trim();
                Call<Employee> call = RetrofitCreated.createApi(baseApi).editEmployee(idEmployee,
                        fullNam, address, email, phone, position);
                call.enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {

                    }
                });
            }
            break;
            case R.id.imgViewDeleteEmployee: {
                Call<Employee> call = RetrofitCreated.createApi(baseApi).deleteEmployee(idEmployee);
                call.enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {
                        finish();

                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {

                    }
                });
            } break;
        }
    }
}
