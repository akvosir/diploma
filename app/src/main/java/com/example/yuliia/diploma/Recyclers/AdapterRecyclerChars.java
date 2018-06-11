package com.example.yuliia.diploma.Recyclers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.models.Characteristics;

import java.util.List;

public class AdapterRecyclerChars extends RecyclerView.Adapter<AdapterRecyclerChars.ListViewHolder> {
        private List<Characteristics> characteristicsList;
        private Context mctx;


    public AdapterRecyclerChars(List<Characteristics> characteristicsList, Context mctx) {
            this.characteristicsList = characteristicsList;
            this.mctx = mctx;
        }

        @Override
        public AdapterRecyclerChars.ListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_char_list, viewGroup, false);
            return new AdapterRecyclerChars.ListViewHolder(v);
        }

        @Override
        public void onBindViewHolder(AdapterRecyclerChars.ListViewHolder holder, int position) {
            final Characteristics characteristics = characteristicsList.get(position);
            holder.charName.setText(characteristics.getName());
            holder.charValue.setText(characteristics.getValue());
        }

        @Override
        public int getItemCount() {
            return characteristicsList.size();
        }

        class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView charName;
            private TextView charValue;


            public ListViewHolder(View itemView) {
                super(itemView);
                charName = (TextView) itemView.findViewById(R.id.acl_name);
                charValue = (TextView) itemView.findViewById(R.id.acl_value);

                itemView.setOnClickListener(this);
            }

            public void bindView(int position) {
            }

            public void onClick(View view) {
            }

        }
}
