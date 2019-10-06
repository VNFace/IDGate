package com.enogy.detectedface.uis.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enogy.detectedface.R;
import com.enogy.detectedface.model.remote.Account;
import com.enogy.detectedface.model.remote.DataAccount;
import com.enogy.detectedface.model.remote.DataEmployee;
import com.enogy.detectedface.model.remote.DataEmployeeHistory;
import com.enogy.detectedface.model.remote.Employee;
import com.enogy.detectedface.model.retrofit.RetrofitCreated;
import com.enogy.detectedface.uis.MainActivity;
import com.enogy.detectedface.utils.Config;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

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
    private List<Account> accountList;
    private SharedPreferences sharedPreferences;
    private int stateLogin;
    private String ipAddress;
    private String ports;

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


        sharedPreferences = getSharedPreferences(Config.NAME, Context.MODE_PRIVATE);
        edPort.setText(sharedPreferences.getString(Config.PORT, ""));
        edIpAdress.setText(sharedPreferences.getString(Config.IP_ADDRESS, ""));
        stateLogin = sharedPreferences.getInt(Config.STATE_LOGIN, 0);
        Log.e("TAG", " stateLogin " + stateLogin);
        if (stateLogin == Config.STATE_LOGIN_TRUE) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
            accountList = new ArrayList<>();
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userName = edUserName.getText().toString().trim();
                    pass = edPassword.getText().toString().trim();
                    ipAdress = edIpAdress.getText().toString().trim();
                    port = edPort.getText().toString().trim();
                    if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pass)) {
                        Toast.makeText(LoginActivity.this, "Please fill infomation", Toast.LENGTH_SHORT).show();
                    } else {
                        String baseApi = "http://" + edIpAdress.getText().toString().trim() + ":" + edPort.getText().toString().trim() + "/";
                        sharedPreferences.edit().putString(Config.BASE_API, baseApi).commit();
                        sharedPreferences.edit().putString(Config.IP_ADDRESS, ipAdress).commit();
                        sharedPreferences.edit().putString(Config.PORT, port).commit();
                        Call<DataAccount> call = RetrofitCreated.createApi(baseApi).getAllAccount();
                        call.enqueue(new Callback<DataAccount>() {
                            @Override
                            public void onResponse(Call<DataAccount> call, Response<DataAccount> response) {
                                for (Account a : response.body().getList()) {
                                    if (a.getUserName().equals(userName) && a.getPass().equals(pass)) {
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        sharedPreferences.edit().putInt(Config.STATE_LOGIN, Config.STATE_LOGIN_TRUE).commit();
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Information incorrect", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<DataAccount> call, Throwable t) {

                            }
                        });

                    }
                }
            });


        }

    }
}
