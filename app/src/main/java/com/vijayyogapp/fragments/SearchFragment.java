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
import com.vijayyogapp.utils.Constants;

/**
 * Created by SUHAS on 05/03/2017.
 */

public class SearchFragment extends Fragment implements View.OnClickListener {
    private Context mContext;
    private Button btnSearchByName;
    private Button btnSearchBySurname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        this.mContext = getActivity();
        init(view);
        return view;
    }

    private void init(View view) {
        btnSearchByName = (Button) view.findViewById(R.id.btn_search_by_name);
        btnSearchBySurname = (Button) view.findViewById(R.id.btn_search_by_surname);
        btnSearchByName.setOnClickListener(this);
        btnSearchBySurname.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.btn_search_by_name:
                bundle.putString(Constants.SEARCH_FOR_TEXT, btnSearchByName.getText().toString());
                ((HomeActivity) getActivity()).setFragment(new DetailSearchFragment(), bundle);
                break;
            case R.id.btn_search_by_surname:
                bundle.putString(Constants.SEARCH_FOR_TEXT, btnSearchBySurname.getText().toString());
                ((HomeActivity) getActivity()).setFragment(new DetailSearchFragment(), bundle);
                break;
        }
    }
}

