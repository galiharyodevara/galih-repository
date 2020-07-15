package com.example.splashscreen.service;

import com.example.splashscreen.model.Book;
import com.example.splashscreen.model.LoginResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface BookApiService {
//    @Headers({
//            "Accept-Encoding: gzip, deflate",
//            "Content-Encoding: gzip",
//    })

    @GET("api/buku")
    Call<List<Book>> getAllBuku(@Header("Authorization") String token);

    @POST("api/buku")
    Call<Book> insertNewBook(@Header("Authorization") String token, @Body Book body);

}

