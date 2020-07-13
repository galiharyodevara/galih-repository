package com.example.splashscreen.service;

import com.example.splashscreen.model.LoginBody;
import com.example.splashscreen.model.LoginResult;
import com.example.splashscreen.model.SignUpBody;
import com.example.splashscreen.model.SignUpResult;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {
    @POST("login")
    Call<LoginResult> getResultInfo(@Body LoginBody loginBody);

    @POST("api/user")
    Call<SignUpResult> signUpUser(@Body SignUpBody signUpBody);
}
