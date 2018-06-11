package com.example.yuliia.diploma.navigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.Recyclers.AdapterLists;
import com.example.yuliia.diploma.models.URLs;
import com.example.yuliia.diploma.models.WishList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FriendsLists extends AppCompatActivity {
    private List<WishList> wishListList;
    private RecyclerView afl_rv;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_lists);
        intent = getIntent();

        TextView name = (TextView) findViewById(R.id.afl_username);
        name.setText(intent.getStringExtra("com.example.yuliia.diploma.person_name"));
        afl_rv = (RecyclerView)findViewById(R.id.afl_rv);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        afl_rv.setLayoutManager(layoutManager);
        afl_rv.setHasFixedSize(true);

        wishListList = new ArrayList<>();
        AdapterLists adapter = new AdapterLists(wishListList,this);
        afl_rv.setAdapter(adapter);

        loadListsRV();
    }

    public void loadListsRV() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.selectFriendsList + intent.getStringExtra("com.example.yuliia.diploma.person_id"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray lists = new JSONArray(response);
                            for(int i = 0; i < lists.length(); i++){
                                JSONObject obj = lists.getJSONObject(i);
                                DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

                                String user_id = obj.getString("user_id");
                                int list_id = obj.getInt("list_id");
                                String list_name = obj.getString("list_name");
                                int is_public = obj.getInt("public");
                                Date date_created = format.parse(obj.getString("date_created"));
                                WishList wl = new WishList(list_id, user_id, list_name, date_created, is_public);
                                wishListList.add(wl);
                            }

                            AdapterLists adapter = new AdapterLists(wishListList, FriendsLists.this);
                            afl_rv.setAdapter(adapter);

                        }catch (JSONException e){
                            e.printStackTrace();

                        } catch (ParseException e) {
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
