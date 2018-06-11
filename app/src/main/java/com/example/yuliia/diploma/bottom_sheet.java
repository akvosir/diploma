package com.example.yuliia.diploma;

import android.animation.LayoutTransition;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yuliia.diploma.Recyclers.AdapterLists;
import com.example.yuliia.diploma.models.URLs;
import com.example.yuliia.diploma.models.WishList;
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

public class bottom_sheet extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    private List<WishList> wishListList;
    private RecyclerView abs_rv;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_bottom_sheet, container, false);
        mAuth = FirebaseAuth.getInstance();

        TextView textView = v.findViewById(R.id.abs_text);
        abs_rv = v.findViewById(R.id.abs_rv);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        abs_rv.setLayoutManager(layoutManager);
        abs_rv.setHasFixedSize(true);

        wishListList = new ArrayList<>();

        AdapterLists adapter = new AdapterLists(wishListList, getActivity());
        abs_rv.setAdapter(adapter);

        loadListsRV();

        return v;
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

    //mAuth.getCurrentUser().getUid()
    public void loadListsRV() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.selectUserList + "uQrqb7r4n3hmJl7W1lCPMw4ILk82",
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

                            AdapterLists adapter = new AdapterLists(wishListList, getActivity());
                            abs_rv.setAdapter(adapter);

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
