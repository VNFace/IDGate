package com.enogy.detectedface.uis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enogy.detectedface.R;
import com.enogy.detectedface.model.remote.DataEmployeeHistory;
import com.enogy.detectedface.model.retrofit.RetrofitCreated;
import com.enogy.detectedface.uis.MainActivity;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText edUserName;
    private EditText edPassword;
    private EditText edIpAdress;
    private EditText edPort;
    private TextView btnSignIn;
    private ImageView imgLogin;
    private String userName;
    private String pass;
    private String ipAdress;
    private String port;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUserName = findViewById(R.id.edUserName);
        edPassword = findViewById(R.id.edPass);
        edIpAdress = findViewById(R.id.edIPAdress);
        edPort = findViewById(R.id.edPort);
        btnSignIn = findViewById(R.id.btnSignIn);
        imgLogin = findViewById(R.id.imageViewLogin);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = edUserName.getText().toString().trim();
                pass = edPassword.getText().toString().trim();
                ipAdress = edIpAdress.getText().toString().trim();
                port = edPort.getText().toString().trim();
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pass) ||
                TextUtils.isEmpty(ipAdress) || TextUtils.isEmpty(port)){
                    Toast.makeText(LoginActivity.this, "Please fill infomation", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        });

//        Call<DataEmployeeHistory> call = RetrofitCreated.createApi().getListEmployeeHistory();
//        call.enqueue(new Callback<DataEmployeeHistory>() {
//            @Override
//            public void onResponse(Call<DataEmployeeHistory> call, Response<DataEmployeeHistory> response) {
//                Log.e("TAG", " body " + response.body());
//            }
//
//            @Override
//            public void onFailure(Call<DataEmployeeHistory> call, Throwable t) {
//                Log.e("TAG", " error " + t.toString());
//
//            }
//        });

        Call<JsonElement> call = RetrofitCreated.createApi().testApi();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.e("TAG", " ok " + response.body());
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("TAG", "not ok " + t.toString());
            }
        });
//        Call<JsonElement> call = RetrofitCreated.createApi().getAllEmployee();
//
//        call.enqueue(new Callback<JsonElement>() {
//            @Override
//            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                Log.e("TAG", "body " + response.body());
//            }
//
//            @Override
//            public void onFailure(Call<JsonElement> call, Throwable t) {
//                Log.e("TAG", "error " + t.toString());
//            }
//        });
    }
}
