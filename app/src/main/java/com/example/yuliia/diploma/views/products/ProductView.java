package com.example.yuliia.diploma.views.products;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.models.ActivityUser;
import com.example.yuliia.diploma.models.Characteristics;
import com.example.yuliia.diploma.models.Product;
import com.example.yuliia.diploma.models.URLs;
import com.example.yuliia.diploma.recyclers.AdapterRecyclerChars;
import com.google.firebase.auth.FirebaseAuth;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductView extends AppCompatActivity implements bottom_sheet.BottomSheetListener {
    private List<Characteristics> characterList;
    private RecyclerView recyclerView;
    private TextView name;
    private TextView description;
    private TextView price;
    private ImageView imgProduct;
    private RatingBar rated;
    private ImageView imgLike;
    private FirebaseAuth mAuth;
    private int liked;
    private int rating;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        mAuth = FirebaseAuth.getInstance();
        intent = getIntent();
        name = (TextView) findViewById(R.id.apv_name);
        description = (TextView) findViewById(R.id.apv_desc);
        price = (TextView) findViewById(R.id.apv_price);
        imgProduct = (ImageView) findViewById(R.id.apv_productimage);
        rated = (RatingBar) findViewById(R.id.apv_ratingbar);
        imgLike = findViewById(R.id.apv_like);
        liked = 0;

        loadAct();

        imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(liked == 0){
                    liked = 1;
                    imgLike.setImageResource(R.drawable.heartred);
                    like();
                }else{
                    liked = 0;
                    imgLike.setImageResource(R.drawable.heart);
                    like();
                }
            }
        });

        rated.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.apv_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProductView.this);
        recyclerView.setLayoutManager(layoutManager);

        characterList = new ArrayList<>();

        Button addBtn = (Button) findViewById(R.id.apv_addToListBtn);
        Button compareBtn = (Button) findViewById(R.id.apv_compareBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottom_sheet bottomSheet = new bottom_sheet();
                bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
            }
        });


        compareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO обрабочик на сравнение товаров
            }
        });

        AdapterRecyclerChars adapter = new AdapterRecyclerChars(characterList, this);
        recyclerView.setAdapter(adapter);
        loadProduct();
        loadProductChar();
    }

    @Override
    public void onButtonClicked(String text) {

    }

    public void like(){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("user_id", mAuth.getCurrentUser().getUid()));
                nameValuePairs.add(new BasicNameValuePair("product_id", intent.getStringExtra("com.example.yuliia.diploma.product_id")));
                nameValuePairs.add(new BasicNameValuePair("liked", String.valueOf(liked)));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(URLs.insertUserActivityLike);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "List added!";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }

    public void rate(){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("user_id", mAuth.getCurrentUser().getUid()));
                nameValuePairs.add(new BasicNameValuePair("product_id", intent.getStringExtra("com.example.yuliia.diploma.product_id")));
                nameValuePairs.add(new BasicNameValuePair("liked", String.valueOf(liked)));
                nameValuePairs.add(new BasicNameValuePair("rating_user", String.valueOf(rated.getRating())));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(URLs.insertUserActivityRate);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "List added!";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Toast.makeText(ProductView.this, "product rated", Toast.LENGTH_LONG).show();

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
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

    private void loadAct() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.getActivityUser + intent.getStringExtra("com.example.yuliia.diploma.product_id")
                + "&user_id=" + mAuth.getUid(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject act = array.getJSONObject(i);

                                ActivityUser au = new ActivityUser(
                                        act.getInt("liked"),
                                        act.getInt("rating_user")
                                );

                                liked = au.getLiked();
                                if(liked == 0){
                                    imgLike.setImageResource(R.drawable.heart);
                                }else
                                {
                                    imgLike.setImageResource(R.drawable.heartred);
                                }
                                rated.setRating((float)au.getRating());


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
