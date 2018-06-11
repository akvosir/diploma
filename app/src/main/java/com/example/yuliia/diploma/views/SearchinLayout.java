package com.example.yuliia.diploma.views;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.example.yuliia.diploma.Recyclers.AdapterRecyclerProducts;
import com.example.yuliia.diploma.models.Product;
import com.example.yuliia.diploma.models.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchinLayout extends Fragment {
    private List<Product> productList;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.activity_searchin_layout, container, false);

        recyclerView = myView.findViewById(R.id.asl_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //initializing the productlist
        productList = new ArrayList<>();
        loadProducts();
        return myView;
    }


    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.selectProducts,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new Product(
                                        product.getInt("product_id"),
                                        product.getString("product_name"),
                                        product.getInt("product_price_low"),
                                        product.getInt("product_price_high"),
                                        product.getString("description"),
                                        product.getString("product_img_uri")
                                ));
                            }
                            //creating adapter object and setting it to recyclerview
                            AdapterRecyclerProducts adapter = new AdapterRecyclerProducts(productList, getActivity());
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
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
}
