package com.example.yuliia.diploma.views.lists;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditList extends AppCompatActivity {
    private Intent intent;
    private EditText listName;
    private TextView dateText;
    private RadioGroup radioG;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

        listName = (EditText) findViewById(R.id.ael_listName);
        dateText = (TextView) findViewById(R.id.ael_date);
        radioG = (RadioGroup) findViewById(R.id.ael_radioPublic);

        intent = getIntent();
        loadList();

        radioG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                value = 1;
                switch (checkedId) {
                    case R.id.ael_radioButtonPublic:
                        value = 1;
                        break;
                    case R.id.ael_radioButtonNotPublic:
                        value = 0;
                        break;
                }
            }
        });

        findViewById(R.id.ael_buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(listName.getText().toString())) {
                    listName.setError("Please enter list name");
                    listName.requestFocus();
                    return;
                }
                updateList();
                finish();
            }
        });

        findViewById(R.id.ael_buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void loadList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.selectList + intent.getStringExtra("com.example.yuliia.diploma.list_id_edit"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray lists = new JSONArray(response);
                            for(int i = 0; i < lists.length(); i++){
                                JSONObject obj = lists.getJSONObject(i);
                                final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                                final Date date = new Date();

                                listName.setText(obj.getString("list_name"));
                                dateText.setText(obj.getString("date_created"));
                                int val = obj.getInt("public");
                                if (val == 0){
                                    radioG.check(R.id.ael_radioButtonNotPublic);
                                }else {
                                    radioG.check(R.id.ael_radioButtonPublic);
                                }
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

    public void updateList(){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("list_id", getIntent().getStringExtra("com.example.yuliia.diploma.list_id_edit")));
                nameValuePairs.add(new BasicNameValuePair("list_name", listName.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("public", String.valueOf(value)));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(URLs.updateList );
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "List saved!";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Toast.makeText(EditList.this, "List saved!", Toast.LENGTH_LONG).show();

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute();
    }
}
