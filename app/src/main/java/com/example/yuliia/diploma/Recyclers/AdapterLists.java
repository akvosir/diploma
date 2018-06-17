package com.example.yuliia.diploma.recyclers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yuliia.diploma.models.URLs;
import com.example.yuliia.diploma.models.WishList;
import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.navigation.FriendsLists;
import com.example.yuliia.diploma.views.lists.EditList;
import com.example.yuliia.diploma.views.lists.ItemLayout;
import com.example.yuliia.diploma.views.lists.ItemsLayout;

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
        return new AdapterLists.ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, final int position) {
        final WishList wl = wlist.get(position);
        holder.mListName.setText(wl.getListName());
        String items = String.valueOf(wl.getItemCount()) + " items";

        holder.mItemCount.setText(items);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(mctx, ItemsLayout.class);
               intent.putExtra("com.example.yuliia.diploma.list_id",String.valueOf(wl.getListId()));
               intent.putExtra("com.example.yuliia.diploma.list_name", wl.getListName());
               mctx.startActivity(intent);
            }
        });

        if(this.mctx instanceof FriendsLists){
            holder.mImg.setVisibility(View.GONE);
        }else{
            holder.mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu pm = new PopupMenu(mctx, holder.mImg);
                    pm.inflate(R.menu.list_options);
                    pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.opt_1:
                                    Intent intent = new Intent(mctx, EditList.class);
                                    intent.putExtra("com.example.yuliia.diploma.list_id_edit",String.valueOf(wl.getListId()));
                                    mctx.startActivity(intent);
                                    break;
                                case R.id.opt_2:
                                    final RequestQueue queue = Volley.newRequestQueue(mctx);
                                    StringRequest dr = new StringRequest(Request.Method.DELETE, URLs.deleteList + String.valueOf(wl.getListId()),
                                            new Response.Listener<String>()
                                            {
                                                @Override
                                                public void onResponse(String response) {
                                                    notifyDataSetChanged();
                                                    Toast.makeText(mctx, "deleted", Toast.LENGTH_LONG).show();
                                                }
                                            },
                                            new Response.ErrorListener()
                                            {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    // error.
                                                }
                                            }
                                    );
                                    queue.add(dr);
                                    break;
                                default:
                                    break;
                            }
                            return false;
                        }
                    });
                    pm.show();
                }
            });
        }
    }

    //TODO delete, edit

    @Override
    public int getItemCount() {
        return wlist.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mListName;
        public TextView mItemCount;
        public CardView mCardView;
        public ImageView mImg;


        public ListViewHolder(View itemView){
            super(itemView);
            mListName = (TextView) itemView.findViewById(R.id.list_name);
            mItemCount = (TextView) itemView.findViewById(R.id.alls_itemCount);
            mCardView = (CardView) itemView.findViewById(R.id.alls_cardview);
            mImg = itemView.findViewById(R.id.alls_img);
        }


        public void bindView(int position){
        }

        public void onClick (View view){}

    }
}
