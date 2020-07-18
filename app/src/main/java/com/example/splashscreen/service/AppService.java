package com.example.splashscreen.service;

public class AppService {
    private static String token;
    private static int idBook;

    public static int getIdBook() {
        return idBook;
    }

    public static void setIdBook(int idBook) {
        AppService.idBook = idBook;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        AppService.token = token;
    }
}

