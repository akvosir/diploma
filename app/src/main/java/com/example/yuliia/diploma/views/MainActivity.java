package com.example.yuliia.diploma.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.views.products.bottom_sheet;
import com.example.yuliia.diploma.views.user.LoginActivity;


public class MainActivity extends AppCompatActivity implements bottom_sheet.BottomSheetListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }
    @Override
    public void onButtonClicked(String text) {

    }
}
