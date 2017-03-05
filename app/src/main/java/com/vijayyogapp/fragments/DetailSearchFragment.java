package com.vijayyogapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vijayyogapp.R;
import com.vijayyogapp.utils.Constants;

/**
 * Created by SUHAS on 05/03/2017.
 */

public class DetailSearchFragment extends Fragment implements View.OnClickListener {
    private Context mContext;
    private String searchFor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
             searchFor  = bundle.getString(Constants.SEARCH_FOR_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_search_fragment, container, false);
        this.mContext = getActivity();
        init(view);
        return view;
    }

    private void init(View view) {
        Button btnSearch = (Button) view.findViewById(R.id.btn_search);
        EditText edtSearch = (EditText) view.findViewById(R.id.edt_search_by);
        TextView txtSearchType = (TextView) view.findViewById(R.id.search_type);
        txtSearchType.setText(searchFor);
        btnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                break;

        }
    }
}

