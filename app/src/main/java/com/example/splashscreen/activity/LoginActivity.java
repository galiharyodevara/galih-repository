package com.example.splashscreen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    Button buttonLogin, buttonSignUp;
    EditText username, password;
    private Retrofit retrofit;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Utility.askPermission(this);
        retrofit = RetrofitUtility.initializeRetrofit();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        addValidationToViews();

        buttonSignUp = findViewById(R.id.buttonSignUp);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                // action
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){

                if(username.getText().toString().length()==0){
                    //jika form Username belum di isi / masih kosong
                    username.setError("Username diperlukan!");
                }else if(password.getText().toString().length()==0){
                    //jika form Passwrod belum di isi / masih kosong
                    password.setError("Password diperlukan!");
                }else {
                    //jika form sudah terisi semua
                    Toast.makeText(getApplicationContext(), "Login Berhasil!", Toast.LENGTH_SHORT).show();
                    loginSubmit(username.getText().toString(), password.getText().toString());
                }
            }
        });

    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String text = parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
//    }
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//    }

    private void addValidationToViews() {
        awesomeValidation.addValidation(this, R.id.username, RegexTemplate.NOT_EMPTY, R.string.invalid_username);
        String regexPassword = ".{8,}";
        awesomeValidation.addValidation(this, R.id.password, regexPassword, R.string.invalid_password);
    }

    private void loginSubmit(String userName, String password) {

        LoginBody loginBody = new LoginBody(userName, password);

        UserApiService apiService = retrofit.create(UserApiService.class);

        Call<LoginResult> result = apiService.getResultInfo(loginBody);

        result.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                try {
                    if (response.body().isSuccess()) {
                        Log.e("TAG", "Login Success" + response.body().toString());
                        AppService.setToken("Bearer " + response.body().getToken());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.e("TAG", "Login Failed" + response.body().toString());
                        Toast.makeText(LoginActivity.this, "Error Login", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}