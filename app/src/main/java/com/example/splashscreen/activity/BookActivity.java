package com.example.splashscreen.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.splashscreen.R;

public class BookActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        Button btnHome;
        Button btnAdd;
        btnHome = findViewById(R.id.btnHome);
        btnAdd = findViewById(R.id.btnAdd);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openHomeFragment();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openSecondFragment();
            }
        });
    }


    private void openHomeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BookFragment strCode = new BookFragment();
        fragmentTransaction.replace(R.id.content, strCode,"home fragment");
        fragmentTransaction.commit();
    }

    private void openSecondFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SecondFragment strCode = new SecondFragment();
        fragmentTransaction.replace(R.id.content, strCode,"second fragment");
        fragmentTransaction.commit();
    }


}