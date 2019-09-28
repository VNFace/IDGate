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

        preferences = getSharedPreferences(Config.EMPLOYEE, Context.MODE_PRIVATE);
        idEmployee = preferences.getString(Config.ID_EMPLOYEE, "");

        Call<DataEmployee> call = RetrofitCreated.createApi().getEmployeeByID(idEmployee);
        Log.e("TAG", "idemployee " + idEmployee);
        call.enqueue(new Callback<DataEmployee>() {
            @Override
            public void onResponse(Call<DataEmployee> call, Response<DataEmployee> response) {
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
        switch (view.getId()){
            case R.id.txtActionOkInformation : {

            } break;
            case R.id.imgViewDeleteEmployee : {

            } break;
        }
    }
}
