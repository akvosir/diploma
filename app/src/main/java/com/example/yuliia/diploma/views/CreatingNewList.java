package com.example.yuliia.diploma.views;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.models.URLs;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreatingNewList extends AppCompatActivity {
    EditText edListName;
    RadioGroup rgPublic;
    private FirebaseAuth mAuth;
    int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_new_list);

        edListName = (EditText) findViewById(R.id.acnl_listName);
        rgPublic = (RadioGroup) findViewById(R.id.acnl_radioPublic);

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final Date date = new Date();

        rgPublic.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                value = 1;
                switch (checkedId) {
                    case R.id.acnl_radioButtonPublic:
                        value = 1;
                        break;
                    case R.id.acnl_radioButtonNotPublic:
                        value = 0;
                        break;
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.acnl_buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edListName.getText().toString())) {
                    edListName.setError("Please enter list name");
                    edListName.requestFocus();
                    return;
                }
                CreateNewList(edListName.getText().toString(), value, dateFormat.format(date));
                finish();
            }
        });

        findViewById(R.id.acnl_buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void CreateNewList(final String list_name, final int is_public, final String date){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("user_id", mAuth.getCurrentUser().getUid()));
                nameValuePairs.add(new BasicNameValuePair("list_name", list_name));
                nameValuePairs.add(new BasicNameValuePair("date_created", date));
                nameValuePairs.add(new BasicNameValuePair("public", String.valueOf(is_public)));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(URLs.insertList);
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

                Toast.makeText(CreatingNewList.this, "List added!", Toast.LENGTH_LONG).show();

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(list_name);
    }
}
