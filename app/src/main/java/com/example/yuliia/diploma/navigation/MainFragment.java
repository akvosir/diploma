package com.example.yuliia.diploma.navigation;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yuliia.diploma.R;
import com.example.yuliia.diploma.views.SearchinLayout;

public class MainFragment extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.main_layout, container, false);

        final FragmentManager manager = getFragmentManager();
        LinearLayout l1 = (LinearLayout) myView.findViewById(R.id.category1);
        LinearLayout l2 = (LinearLayout) myView.findViewById(R.id.category2);
        LinearLayout l3 = (LinearLayout) myView.findViewById(R.id.category3);
        LinearLayout l4 = (LinearLayout) myView.findViewById(R.id.category4);

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction()
                        .replace(R.id.frame_nav, new SearchinLayout()).commit();
            }
        });

        return myView;
    }
}
