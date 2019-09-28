package com.enogy.detectedface.uis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enogy.detectedface.R;
import com.enogy.detectedface.model.remote.Employee;
import com.enogy.detectedface.model.retrofit.RetrofitCreated;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewEmployeeActivity extends AppCompatActivity {
    private TextView txtAddNewEmployee;
    private EditText edFullName;
    private EditText edIDEmployee;
    private EditText edAddress;
    private EditText edEmail;
    private EditText edPhone;
    private EditText edPosition;
    private ImageView imgNewEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_employee);
        txtAddNewEmployee = findViewById(R.id.txtAddNewEmployee);
        edFullName = findViewById(R.id.edFullNameNewEmployee);
        edIDEmployee = findViewById(R.id.edIdNewEmployee);
        edAddress = findViewById(R.id.edAddressNewEmployee);
        edPhone = findViewById(R.id.edPhoneNewEmployee);
        edEmail = findViewById(R.id.edEmailNewEmployee);
        edPosition = findViewById(R.id.edPositionNewEmployee);
        imgNewEmployee = findViewById(R.id.imgViewImageNewEmployee);

        txtAddNewEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idEmp = edIDEmployee.getText().toString().trim();
                String fullName = edFullName.getText().toString().trim();
                String address = edAddress.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
                String phone = edPhone.getText().toString().trim();
                String position = edPosition.getText().toString().trim();
                if (TextUtils.isEmpty(idEmp) || TextUtils.isEmpty(fullName)) {
                    Toast.makeText(AddNewEmployeeActivity.this, "ID or Name must fill", Toast.LENGTH_SHORT).show();
                } else {
                    Call<Employee> call = RetrofitCreated.createApi().addNewEmployee(
                            idEmp, fullName, address, email, phone, position
                    );
                    call.enqueue(new Callback<Employee>() {
                        @Override
                        public void onResponse(Call<Employee> call, Response<Employee> response) {
                            Toast.makeText(AddNewEmployeeActivity.this, "Employee added", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Employee> call, Throwable t) {

                        }
                    });

                }
            }
        });
    }
}
