package com.example.yuliia.diploma.views;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.Recyclers.AdapterRecyclerItems;
import com.example.yuliia.diploma.models.ListItem;
import com.example.yuliia.diploma.models.URLs;

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

public class ItemsLayout extends AppCompatActivity {
    private RecyclerView ail_rv;
    private List<ListItem> itemList;
    private Intent intent;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_layout);
        ail_rv = (RecyclerView)findViewById(R.id.ail_recycler);
        TextView list_name = (TextView) findViewById(R.id.ail_list_name);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ail_rv.setLayoutManager(layoutManager);
        ail_rv.setHasFixedSize(true);

        itemList = new ArrayList<>();
        final AdapterRecyclerItems adapter = new AdapterRecyclerItems(itemList, this);
        ail_rv.setAdapter(adapter);

        intent = getIntent();
        final String lid = intent.getStringExtra("com.example.yuliia.diploma.list_id");
        list_name.setText(intent.getStringExtra("com.example.yuliia.diploma.list_name"));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemsLayout.this, AddingNewItem.class);
                intent.putExtra("com.example.yuliia.diploma.list_id_itls",lid);
                startActivity(intent);
            }
        });
        adapter.notifyDataSetChanged();
        loadItemsRV();
    }

    public void loadItemsRV() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.selectItemsFromList+intent.getStringExtra("com.example.yuliia.diploma.list_id"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray lists = new JSONArray(response);
                            for(int i = 0; i < lists.length(); i++){
                                JSONObject obj = lists.getJSONObject(i);
                                DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

                                int item_id = obj.getInt("item_id");
                                String item_name = obj.getString("item_name");
                                int item_bought = obj.getInt("item_bought");
                                int item_checked = obj.getInt("item_checked");
                                Date date_added = format.parse(obj.getString("date_added"));
                                String item_note = obj.getString("item_note");

                                ListItem listItem = new ListItem(item_id, item_name, item_bought, item_checked, item_note, date_added);
                                itemList.add(listItem);
                            }

                            AdapterRecyclerItems adapter = new AdapterRecyclerItems(itemList, ItemsLayout.this);
                            ail_rv.setAdapter(adapter);

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
