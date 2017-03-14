package com.vijayyogapp.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.vijayyogapp.R;
import com.vijayyogapp.adapters.ReportSurnameListAdapter;
import com.vijayyogapp.database.DBHelper;
import com.vijayyogapp.models.SurnameCountModel;
import com.vijayyogapp.utils.SimpleDividerItemDecoration;
import com.vijayyogapp.utils.Utils;

import java.util.ArrayList;

/**
 * Created by SUHAS on 14/03/2017.
 */

public class SurnameWiseReportFragment extends Fragment {

    private RecyclerView rvSurname;
    private ArrayList<SurnameCountModel> surnameCountModelArrayList ;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.surname_wise_list_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        surnameCountModelArrayList = new ArrayList<>();

        rvSurname = (RecyclerView) view.findViewById(R.id.rv_surname);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvSurname.setHasFixedSize(true);
        rvSurname.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        rvSurname.setLayoutManager(llm);

        ReportSurnameListAdapter adapter = new ReportSurnameListAdapter(mContext,surnameCountModelArrayList);
        rvSurname.setAdapter(adapter);

        Utils.getInstance().showProgressDialog(getActivity());
        ArrayList<SurnameCountModel> list = DBHelper.getInstance(mContext).getSurnameWiseCount();
        Utils.getInstance().hideProgressDialog();
        if(list.size()>0){
            surnameCountModelArrayList = list;
            adapter.refreshAdapter(surnameCountModelArrayList);
        }

    }

}
