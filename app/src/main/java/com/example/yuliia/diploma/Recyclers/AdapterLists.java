package com.example.yuliia.diploma.Recyclers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yuliia.diploma.views.ItemsLayout;
import com.example.yuliia.diploma.models.WishList;
import com.example.yuliia.diploma.R;

import java.util.List;

public class AdapterLists extends RecyclerView.Adapter<AdapterLists.ListViewHolder>{
    private List<WishList> wlist;
    private Context mctx;

    public AdapterLists(List<WishList> wlist, Context mctx) {
        this.wlist = wlist;
        this.mctx = mctx;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_list_layout_single, viewGroup, false);

        /*LayoutInflater inflater = (LayoutInflater) mctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;

        switch (i){
            case -1:
                view = inflater.inflate(R.layout.activity_list_layout_single, viewGroup, false);
                return cc;
        }*/
        return new AdapterLists.ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        final WishList wl = wlist.get(position);
        holder.mListName.setText(wl.getListName());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(mctx, ItemsLayout.class);
               intent.putExtra("com.example.yuliia.diploma.list_id",String.valueOf(wl.getListId()));
               intent.putExtra("com.example.yuliia.diploma.list_name", wl.getListName());
               mctx.startActivity(intent);
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
            mListName = (TextView) itemView.findViewById(R.id.list_name);
            mItemCount = (TextView) itemView.findViewById(R.id.alls_itemCount);
            mCardView = (CardView) itemView.findViewById(R.id.alls_cardview);

            itemView.setOnClickListener(this);
        }

        public void bindView(int position){
        }

        public void onClick (View view){}

    }
}
