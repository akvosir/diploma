package com.example.yuliia.diploma.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.models.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ItemLayout extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_layout);

        final Intent intent = getIntent();

        Button editBtn = (Button) findViewById(R.id.ail_edit);
        final RequestQueue queue = Volley.newRequestQueue(this);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddingNewItem.class);
                startActivity(intent);
            }
        });

        Button deleteBtn = (Button) findViewById(R.id.ail_delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest dr = new StringRequest(Request.Method.DELETE, URLs.deleteItem + intent.getStringExtra("com.example.yuliia.diploma.item_id"),
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Toast.makeText(ItemLayout.this, "deleted", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error.

                            }
                        }
                );
                queue.add(dr);
            }
        });

        final TextView product_name = (TextView) findViewById(R.id.ail_productName);
        final TextView product_date = (TextView) findViewById(R.id.ail_productAdded);
        //final TextView product_link = (TextView) findViewById(R.id.ail_productLink);
        final TextView product_note = (TextView) findViewById(R.id.ail_productNote);

        //loadItem()
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.selectItem + intent.getStringExtra("com.example.yuliia.diploma.item_id"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray lists = new JSONArray(response);
                            for(int i = 0; i < lists.length(); i++){
                                JSONObject obj = lists.getJSONObject(i);
                                DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

                                product_name.setText(obj.getString("item_name"));
                                product_date.setText(obj.getString("date_added"));
                                //get item picture
                                //product_link.setText(obj.getString("item_name"));
                                product_note.setText(obj.getString("item_note"));

                            }

                        }catch (JSONException e){
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
