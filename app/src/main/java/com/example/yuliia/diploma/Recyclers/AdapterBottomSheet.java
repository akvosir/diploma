package com.example.yuliia.diploma.recyclers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
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
import com.bumptech.glide.Glide;
import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.models.Product;
import com.example.yuliia.diploma.models.URLs;
import com.example.yuliia.diploma.models.WishList;
import com.example.yuliia.diploma.views.products.ProductView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdapterBottomSheet extends RecyclerView.Adapter<AdapterBottomSheet.ListViewHolder>{
    private List<WishList> wlist;
    private Context mctx;

    public AdapterBottomSheet(List<WishList> wlist, Context mctx) {
        this.wlist = wlist;
        this.mctx = mctx;
    }

    @Override
    public AdapterBottomSheet.ListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_list_layout_single_small, viewGroup, false);
        return new AdapterBottomSheet.ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterBottomSheet.ListViewHolder holder, int position) {
        final WishList wl = wlist.get(position);
        holder.mListName.setText(wl.getListName());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO onclick добавить товар в список
                //select product
                //add product to list, converting product to an item

            }
        });
    }

    @Override
    public int getItemCount() {
        return wlist.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mListName;
        public TextView mItemCount;
        public CardView mCardView;

        public ListViewHolder(View itemView){
            super(itemView);
            mListName = (TextView) itemView.findViewById(R.id.allss_list_name);
            mCardView = (CardView) itemView.findViewById(R.id.allss_cardview);

            itemView.setOnClickListener(this);
        }

        public void bindView(int position){
        }

        public void onClick (View view){}

    }
}
