package com.example.yuliia.diploma.navigation;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yuliia.diploma.Recyclers.AdapterRecyclerFriends;
import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.models.URLs;
import com.example.yuliia.diploma.models.User;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.yuliia.diploma.models.URLs.*;

public class FriendsFragment extends Fragment {
    private List<User> userList;
    private RecyclerView fl_rv;
    private FirebaseAuth mAuth;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.friends_layout, container, false);
        fl_rv = (RecyclerView)myView.findViewById(R.id.fl_rv);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        fl_rv.setLayoutManager(layoutManager);

        mAuth = FirebaseAuth.getInstance();
        userList = new ArrayList<>();

        AdapterRecyclerFriends adapter = new AdapterRecyclerFriends(userList, getActivity());
        fl_rv.setAdapter(adapter);
        loadFrinedsRV();

        searchView = (SearchView) myView.findViewById(R.id.fl_search);
//change icon color
        ImageView searchIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        //searchIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.search));
        searchIcon.setColorFilter(Color.WHITE);


        return myView;
    }

    public void loadFrinedsRV() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, selectFriends + mAuth.getUid(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray lists = new JSONArray(response);
                            for(int i = 0; i < lists.length(); i++){
                                JSONObject obj = lists.getJSONObject(i);
                                String user_id = obj.getString("user_id");
                                String user_name = obj.getString("user_name");
                                String user_email = obj.getString("email");

                                User user = new User();
                                user.setPersonId(user_id);
                                user.setPersonName(user_name);
                                user.setPersonEmail(user_email);

                                userList.add(user);
                            }

                            AdapterRecyclerFriends adapter = new AdapterRecyclerFriends(userList, getActivity());
                            fl_rv.setAdapter(adapter);

                        }catch (JSONException e){
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
