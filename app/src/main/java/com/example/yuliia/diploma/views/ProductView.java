package com.example.yuliia.diploma.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.Recyclers.AdapterRecyclerChars;
import com.example.yuliia.diploma.models.Characteristics;
import com.example.yuliia.diploma.models.Product;
import com.example.yuliia.diploma.models.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductView extends AppCompatActivity {
    private List<Characteristics> characterList;
    private RecyclerView recyclerView;
    private TextView name;
    private TextView description;
    private TextView price;
    private ImageView imgProduct;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        intent = getIntent();

        name = (TextView) findViewById(R.id.apv_name);
        description = (TextView) findViewById(R.id.apv_desc);
        price = (TextView) findViewById(R.id.apv_price);
        imgProduct = (ImageView) findViewById(R.id.apv_productimage);

        recyclerView = (RecyclerView) findViewById(R.id.apv_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductView.this);
        recyclerView.setLayoutManager(layoutManager);

        characterList = new ArrayList<>();

        Button addBtn = (Button) findViewById(R.id.apv_addToListBtn);
        Button compareBtn = (Button) findViewById(R.id.apv_compareBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //choose list
                //insert to db prod as item
                //onclick open product item with id

            }
        });

        compareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        AdapterRecyclerChars adapter = new AdapterRecyclerChars (characterList, this);
        recyclerView.setAdapter(adapter);
        loadProduct();
        loadProductChar();
    }

    private void loadProduct() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.selectProduct + intent.getStringExtra("com.example.yuliia.diploma.product_id"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject product = array.getJSONObject(i);

                                Product pr = new Product(
                                        product.getInt("product_id"),
                                        product.getString("product_name"),
                                        product.getInt("product_price_low"),
                                        product.getInt("product_price_high"),
                                        product.getString("description"),
                                        product.getString("product_img_uri")
                                );

                                String priceS = String .valueOf(pr.getProductPriceLow()) + " - " + String.valueOf(pr.getProductPriceHigh());

                                name.setText(pr.getProductName());
                                description.setText(pr.getProductDescription());
                                price.setText(priceS);
                                Glide.with(ProductView.this)
                                        .load(pr.getProductImg())
                                        .into(imgProduct);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void loadProductChar() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.selectProductChar + intent.getStringExtra("com.example.yuliia.diploma.product_id"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject charact = array.getJSONObject(i);

                                characterList.add(new Characteristics(
                                        charact.getString("charact_name"),
                                        charact.getString("cp_value")
                                ));
                            }

                            AdapterRecyclerChars adapter = new AdapterRecyclerChars(characterList, ProductView.this);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
