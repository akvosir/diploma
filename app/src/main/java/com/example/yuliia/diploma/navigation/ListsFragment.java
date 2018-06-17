package com.example.yuliia.diploma.navigation;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.models.URLs;
import com.example.yuliia.diploma.models.WishList;
import com.example.yuliia.diploma.recyclers.AdapterLists;
import com.google.firebase.auth.FirebaseAuth;

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

public class ListsFragment extends Fragment {
    private  List<WishList> wishListList;
    private RecyclerView ll_rv;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.lists_layout, container, false);
        ll_rv = (RecyclerView)myView.findViewById(R.id.ll_rv);
        mAuth = FirebaseAuth.getInstance();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ll_rv.setLayoutManager(layoutManager);
        ll_rv.setHasFixedSize(true);

        wishListList = new ArrayList<>();
        loadListsRV();
        return myView;
    }

    public void loadListsRV() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.selectUserList + mAuth.getCurrentUser().getUid(),
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
                                int itemCount = obj.getInt("count");

                                WishList wl = new WishList(list_id, user_id, list_name, date_created, is_public, itemCount);
                                wishListList.add(wl);
                            }

                            AdapterLists adapter = new AdapterLists(wishListList, getActivity());
                            ll_rv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

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
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
}
