package com.example.yuliia.diploma.views.lists;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddingNewItem extends AppCompatActivity {
    TextInputEditText itemName;
    TextInputEditText productLink;
    TextInputEditText productNote;
    TextView productDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_new_item);
        Intent intent = getIntent();
        final String list_id = intent.getStringExtra("com.example.yuliia.diploma.list_id_itls");
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final Date date = new Date();

        itemName = (TextInputEditText) findViewById(R.id.aani_productName);
        productLink = (TextInputEditText) findViewById(R.id.aani_productLink);
        productNote = (TextInputEditText) findViewById(R.id.aani_productNote);
        productDate = findViewById(R.id.aani_productAdded);

        //TODO UPLOAD IMAGE

        productDate.setText(dateFormat.format(date));

        Button btnSave = (Button) findViewById(R.id.aani_saveBtn);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItem(list_id, itemName.getText().toString(), dateFormat.format(date), productNote.getText().toString());
            }
        });

        Button btnCancel = (Button) findViewById(R.id.aani_cancelBtn);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();            }
        });

    }

    public void AddItem(final String list_id, final String item_name, final String date, final String item_note){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("list_id", list_id));
                nameValuePairs.add(new BasicNameValuePair("item_name", item_name));
                nameValuePairs.add(new BasicNameValuePair("date_added", date));
                nameValuePairs.add(new BasicNameValuePair("item_note", item_note));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(URLs.insertItem);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Submit Successfully";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Toast.makeText(AddingNewItem.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(item_name);
    }
}
