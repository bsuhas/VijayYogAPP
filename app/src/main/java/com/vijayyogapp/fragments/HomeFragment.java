package com.vijayyogapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vijayyogapp.HomeActivity;
import com.vijayyogapp.R;

/**
 * Created by SUHAS on 05/03/2017.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        this.mContext = getActivity();
        init(view);
        return view;
    }

    private void init(View view) {
        Button btnSearch = (Button) view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                Bundle bundle = new Bundle();
                ((HomeActivity) getActivity()).setFragment(new SearchFragment(), bundle);
                break;
        }
    }
}

