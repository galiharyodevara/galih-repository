package com.example.splashscreen.model;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.splashscreen.R;

public class Book extends AppCompatActivity {

    private int id ;
    private String judul;
    private String penerbit ;
    private String penulis;
    private int harga;
    private int userid;
    private String thumb;

    public Book() {
    }
    public Book(String judul, String penulis, String penerbit, String thumb) {
        this.judul = judul;
        this.penerbit = penerbit;
        this.penulis = penulis;
        this.thumb = thumb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
    }
}