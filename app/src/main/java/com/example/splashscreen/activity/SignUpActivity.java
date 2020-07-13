package com.example.splashscreen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.splashscreen.R;
import com.example.splashscreen.apiHelper.RetrofitUtility;
import com.example.splashscreen.service.UserApiService;
import com.example.splashscreen.apiHelper.Utility;
import com.example.splashscreen.model.SignUpBody;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button buttonSignUp;
    RadioGroup radioGroup;
    RadioButton radioButton, radioButton2;
    Spinner roles;
    EditText email, username, password, name;
    private AwesomeValidation awesomeValidation;
    private Retrofit retrofit;
    private boolean rbAktif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Utility.askPermission(this);
        retrofit = RetrofitUtility.initializeRetrofit();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        roles = findViewById(R.id.roles);
        radioButton = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        buttonSignUp = findViewById(R.id.buttonSignUp);


        buttonSignUp = findViewById(R.id.buttonSignUp);

        awesomeValidation.addValidation(this, R.id.username, RegexTemplate.NOT_EMPTY, R.string.invalid_username);
        String regexPassword = ".{8,}";
        awesomeValidation.addValidation(this, R.id.password, regexPassword, R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.name, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.email, RegexTemplate.NOT_EMPTY, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.roles, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this, R.id.radioGroup, RegexTemplate.NOT_EMPTY, R.string.invalid_name);


        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate()) {

                    List<String> hasilRole = new ArrayList<>();
                    hasilRole.add(roles.getSelectedItem().toString());

                    signUpSubmit(username.getText().toString(), password.getText().toString(),
                            name.getText().toString(), email.getText().toString(), hasilRole, rbAktif);
                    Toast.makeText(getApplicationContext(), "Sign Up Berhasil!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Sign Up Gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Spinner spinner = findViewById(R.id.roles);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void signUpSubmit(String toString, String toString1, String toString2, String toString3, List<String> hasilRole, Boolean rbAktif) {
        SignUpBody signUpBody = new SignUpBody(username, password, name, email, roles, radioGroup);

            UserApiService apiService = retrofit.create(UserApiService.class);
            Call<ResponseBody> result = apiService.signUpUser(signUpBody);

                    result.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.isSuccessful()) {
                            Log.e("TAG", "Sign Up Success" + response.body().toString());
                            Intent intent = new Intent(SignUpActivity.this, BookActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e("TAG", "Sign Up Failed" + response.body().toString());
                            Toast.makeText(SignUpActivity.this, "Error Sign Up", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });

    }
}

//        private void signUpSubmit (String username, String password, String name, String email, List < String > roles, boolean active) {
//
//            SignUpBody signUpBody = new SignUpBody(username, password, name, email, roles, active);
//
//            UserApiService apiService = retrofit.create(UserApiService.class);
//
//            Call<ResponseBody> result = apiService.signUpUser(signUpBody);
//
//            result.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    try {
//                        if (response.body().isSuccess()) {
//                            Log.e("TAG", "Sign Up Success" + response.body().toString());
//                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            Log.e("TAG", "Sign Up Failed" + response.body().toString());
//                            Toast.makeText(SignUpActivity.this, "Error Sign Up", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    t.printStackTrace();
//                }
//            });
//        }
//    }
