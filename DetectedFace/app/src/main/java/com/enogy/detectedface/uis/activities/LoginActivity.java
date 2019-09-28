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
    //    private EditText edIpAdress;
//    private EditText edPort;
    private TextView btnSignIn;
    private ImageView imgLogin;
    private String userName;
    private String pass;
    private String ipAdress;
    private String port;
    private List<Account> accountList;
    private SharedPreferences sharedPreferences;
    private int stateLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUserName = findViewById(R.id.edUserName);
        edPassword = findViewById(R.id.edPass);
//        edIpAdress = findViewById(R.id.edIPAdress);
//        edPort = findViewById(R.id.edPort);
        btnSignIn = findViewById(R.id.btnSignIn);
        imgLogin = findViewById(R.id.imageViewLogin);


        sharedPreferences = getSharedPreferences(Config.LOGIN, Context.MODE_PRIVATE);
        stateLogin = sharedPreferences.getInt(Config.STATE_LOGIN, 0);
        if (stateLogin == Config.STATE_LOGIN_TRUE) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
            accountList = new ArrayList<>();
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userName = edUserName.getText().toString().trim();
                    pass = edPassword.getText().toString().trim();
//                ipAdress = edIpAdress.getText().toString().trim();
//                port = edPort.getText().toString().trim();
                    if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pass)) {
                        Toast.makeText(LoginActivity.this, "Please fill infomation", Toast.LENGTH_SHORT).show();
                    } else {
                        for (Account a : accountList) {
                            if (a.getUserName().equals(userName) && a.getPass().equals(pass)) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "Information incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });


            Call<DataAccount> call = RetrofitCreated.createApi().getAllAccount();
            call.enqueue(new Callback<DataAccount>() {
                @Override
                public void onResponse(Call<DataAccount> call, Response<DataAccount> response) {
                    accountList = response.body().getList();
                }

                @Override
                public void onFailure(Call<DataAccount> call, Throwable t) {

                }
            });

            Call<DataEmployee> call1 = RetrofitCreated.createApi().getEmployeeByDate("2019-09-22");
            call1.enqueue(new Callback<DataEmployee>() {
                @Override
                public void onResponse(Call<DataEmployee> call, Response<DataEmployee> response) {
                    Log.e("TAG", " r " + response.body().getList().size());
                    for (Employee e : response.body().getList()) {
                        Log.e("TAG", "name " + e.getFullName());
                    }
                }

                @Override
                public void onFailure(Call<DataEmployee> call, Throwable t) {

                }
            });
        }

    }
}
