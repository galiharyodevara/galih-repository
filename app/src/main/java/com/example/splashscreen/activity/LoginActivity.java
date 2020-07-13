package com.example.splashscreen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.splashscreen.R;
import com.example.splashscreen.service.AppService;
import com.example.splashscreen.apiHelper.RetrofitUtility;
import com.example.splashscreen.service.UserApiService;
import com.example.splashscreen.apiHelper.Utility;
import com.example.splashscreen.model.LoginBody;
import com.example.splashscreen.model.LoginResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LoginActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private Button buttonLogin;
    private TextView daftar;
    private EditText tUsername, tPassword;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Utility.askPermission(this);
        retrofit = RetrofitUtility.initializeRetrofit();


        tUsername = findViewById(R.id.username);
        tPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.buttonLogin);
        daftar = findViewById(R.id.buttonSignUp);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tUsername.getText().toString().length() == 0) {
                    tUsername.setError("tidak boleh kosong");
                } else if (tPassword.getText().toString().length() == 0) {
                    tPassword.setError("tidak boleh kosong");
                } else {
                    loginSubmit(tUsername.getText().toString(), tPassword.getText().toString());
                }
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // action
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loginSubmit(String userName, String password) {
        LoginBody loginBody = new LoginBody(userName, password);
        Log.e("TAG", "loginSubmit: " + userName);
        Log.e("TAG", "loginSubmit: " + password);

        UserApiService apiService = retrofit.create(UserApiService.class);
        Call<LoginResult> result = apiService.getResultInfo(loginBody);

        result.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                try {
                    if (response.body().isSuccess()) {
                        Toast.makeText(LoginActivity.this, "Logiin berhasil", Toast.LENGTH_SHORT).show();
                        AppService.setToken("Bearer " + response.body().getToken());
                        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Logiin gagal", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {t.printStackTrace();
            }
        });

    }
}
