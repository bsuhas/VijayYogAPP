package com.vijayyogapp.fragments;

/**
 * Created by SUHAS on 10/03/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vijayyogapp.R;
import com.vijayyogapp.activity.HomeActivity;
import com.vijayyogapp.database.DBHelper;

public class ReportScreenFragment extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_screen, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_surname).setOnClickListener(this);
        view.findViewById(R.id.btn_netural_vote).setOnClickListener(this);
//        view.findViewById(R.id.btn_tentative_vote).setOnClickListener(this);
//        view.findViewById(R.id.btn_confirm_vote).setOnClickListener(this);
//        view.findViewById(R.id.btn_never_going_to_vote).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_surname:
                //TODO implement
                DBHelper.getInstance(getActivity()).getSurnameWiseCount();
                break;
            case R.id.btn_netural_vote:
                //TODO implement
                ((HomeActivity) getActivity()).setFragment(new ReportTypeCountFragment(), new Bundle());
                break;

        }
    }
}
