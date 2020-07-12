package com.example.splashscreen.service;

public class AppService {
    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        AppService.token = token;
    }
}

