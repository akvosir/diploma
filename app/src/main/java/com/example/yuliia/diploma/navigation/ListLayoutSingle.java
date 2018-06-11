package com.example.yuliia.diploma.navigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.example.yuliia.diploma.R;

public class ListLayoutSingle extends AppCompatActivity {
    CardView listCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_layout_single);

        listCardView = (CardView) findViewById(R.id.alls_cardview);
        listCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "opened list", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
