package com.example.yuliia.diploma.recyclers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.models.ListItem;
import com.example.yuliia.diploma.views.lists.ItemLayout;

import java.util.List;

public class AdapterRecyclerItems extends RecyclerView.Adapter<AdapterRecyclerItems.ListViewHolder> {
    private List<ListItem> itemList;
    private Context mctx;

    public AdapterRecyclerItems(List<ListItem> itemList, Context mctx) {
        this.itemList = itemList;
        this.mctx = mctx;
    }

    @Override
    public AdapterRecyclerItems.ListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_item_layout_single, viewGroup, false);
        return new AdapterRecyclerItems.ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterRecyclerItems.ListViewHolder holder, int position) {
        final ListItem listItem = itemList.get(position);
        holder.mItemName.setText(listItem.getItemName());
        String date = String.valueOf(listItem.getDateAdded());

        holder.mDateAdded.setText(date);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mctx, ItemLayout.class);
                intent.putExtra("com.example.yuliia.diploma.item_id", String.valueOf(listItem.getItemId()));
                mctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mItemName;
        private TextView mDateAdded;
        private CardView mCardView;

        public ListViewHolder(View itemView) {
            super(itemView);
            mItemName = (TextView) itemView.findViewById(R.id.ails_item);
            mDateAdded = (TextView) itemView.findViewById(R.id.ails_date);
            mCardView = (CardView) itemView.findViewById(R.id.ails_cardview);
            itemView.setOnClickListener(this);
        }

        public void bindView(int position) {
        }

        public void onClick(View view) {
        }

    }
}