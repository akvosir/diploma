package com.example.yuliia.diploma.recyclers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.models.Product;
import com.example.yuliia.diploma.views.products.ProductView;

import java.util.List;

public class AdapterRecyclerProducts extends RecyclerView.Adapter<AdapterRecyclerProducts.ListViewHolder> {
    private List<Product> productList;
    private Context mctx;


    public AdapterRecyclerProducts(List<Product> productList, Context mctx) {
        this.productList = productList;
        this.mctx = mctx;
    }

    @Override
    public AdapterRecyclerProducts.ListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_product_item, viewGroup, false);
        return new AdapterRecyclerProducts.ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterRecyclerProducts.ListViewHolder holder, int position) {
        final Product product = productList.get(position);
        holder.productName.setText(product.getProductName());
        holder.productDesc.setText(product.getProductDescription());
        holder.productPrice.setText(String.valueOf(product.getProductPriceLow())+ " - " + String.valueOf(product.getProductPriceHigh()) + "грн");
        Glide.with(mctx)
                .load(product.getProductImg())
                .into(holder.productImage);

        holder.prodRV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mctx, ProductView.class);
                intent.putExtra("com.example.yuliia.diploma.product_id", String.valueOf(product.getProductId()));
                mctx.startActivity(intent);
            }
        });

        /*holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mctx, FriendsLists.class);
                intent.putExtra("com.example.yuliia.diploma.product_id", product.getProductId());
                mctx.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView productName;
        private TextView productDesc;
        private TextView productPrice;
        private ImageView productImage;
        private RelativeLayout prodRV;


        public ListViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.api_productName);
            productPrice = (TextView) itemView.findViewById(R.id.api_price);
            productDesc = (TextView) itemView.findViewById(R.id. api_description);
            productImage = (ImageView) itemView.findViewById(R.id.api_productImage);
            prodRV = (RelativeLayout) itemView.findViewById(R.id.api_relative);

            itemView.setOnClickListener(this);
        }

        public void bindView(int position) {
        }

        public void onClick(View view) {
        }

    }
}
