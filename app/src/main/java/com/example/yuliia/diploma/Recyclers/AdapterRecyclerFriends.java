package com.example.yuliia.diploma.Recyclers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yuliia.diploma.navigation.FriendsLists;
import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.models.User;

import java.util.List;

public class AdapterRecyclerFriends extends RecyclerView.Adapter<AdapterRecyclerFriends.ListViewHolder>{
    private List<User> userList;
    private Context mctx;
    TextView mListName;
    TextView mItemCount;

    public AdapterRecyclerFriends(List<User> userList, Context mctx) {
        this.userList = userList;
        this.mctx = mctx;
    }

    @Override
    public AdapterRecyclerFriends.ListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_friends_layout_single, viewGroup, false);
        return new AdapterRecyclerFriends.ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterRecyclerFriends.ListViewHolder holder, int position) {
        final User user = userList.get(position);
        holder.mItemTextView.setText(user.getPersonName());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mctx, FriendsLists.class);
                intent.putExtra("com.example.yuliia.diploma.person_id", String.valueOf(user.getPersonId()));
                intent.putExtra("com.example.yuliia.diploma.person_name",user.getPersonName());
                mctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mItemTextView;
        private ImageView mItemImageView;
        private CardView cv;

        public ListViewHolder(View itemView){
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.afls_cv);
            mItemTextView = (TextView) itemView.findViewById(R.id.person_name);
            itemView.setOnClickListener(this);
        }

        public void bindView(int position){
        }

        public void onClick (View view){}

    }
}
