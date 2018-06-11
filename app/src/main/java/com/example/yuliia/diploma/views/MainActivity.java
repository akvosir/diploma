package com.example.yuliia.diploma.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.bottom_sheet;

public class MainActivity extends AppCompatActivity implements bottom_sheet.BottomSheetListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bottom_sheet bottomSheet = new bottom_sheet();
        //bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    @Override
    public void onButtonClicked(String text) {

    }
}
