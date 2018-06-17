package com.example.yuliia.diploma.views.lists;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.models.URLs;

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

public class ItemLayoutEdit extends AppCompatActivity {
    private TextView prodName;
    private TextView prodAdded;
    private ImageView prodImg;
    private TextView prodLink;
    private TextView prodNote;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_layout_edit);

        prodName = findViewById(R.id.aile_productName);
        prodAdded = findViewById(R.id.aile_productAdded);
        prodImg = findViewById(R.id.aile_productImg);
        prodLink = findViewById(R.id.aile_productLink);
        prodNote = findViewById(R.id.aile_productNote);

        Button saveBtn = (Button) findViewById(R.id.aile_saveBtn);
        Button cancelBtn = (Button) findViewById(R.id.aile_cancelBtn);

        loadItem();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO save
                updateItem();
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadItem(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.selectItem + getIntent().getStringExtra("com.example.yuliia.diploma.item_id_edit"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray lists = new JSONArray(response);
                            for(int i = 0; i < lists.length(); i++){
                                JSONObject obj = lists.getJSONObject(i);
                                prodName.setText(obj.getString("item_name"));
                                prodAdded.setText(obj.getString("date_added"));
                                //TODO ссылка на товар версус ссылка на айтем
                                //TODO друзья убрать контрол кнопки, добавить отмеченные и купленные
                                prodLink.setText(obj.getString("product_link"));
                                prodNote.setText(obj.getString("item_note"));

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

    public void updateItem(){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                //TODO image upload

                nameValuePairs.add(new BasicNameValuePair("item_id", getIntent().getStringExtra("com.example.yuliia.diploma.item_id_edit")));
                nameValuePairs.add(new BasicNameValuePair("item_name", String.valueOf(prodName.getText())));
                nameValuePairs.add(new BasicNameValuePair("item_link", String.valueOf(prodLink.getText())));
                nameValuePairs.add(new BasicNameValuePair("item_note", String.valueOf(prodNote.getText())));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(URLs.updateItem );
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Item saved!";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Toast.makeText(ItemLayoutEdit.this, "Item saved!", Toast.LENGTH_LONG).show();

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }
}
