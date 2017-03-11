package com.vijayyogapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vijayyogapp.R;
import com.vijayyogapp.activity.HomeActivity;
import com.vijayyogapp.database.DBHelper;
import com.vijayyogapp.models.VoterDetailModel;
import com.vijayyogapp.utils.Constants;

import java.util.ArrayList;

/**
 * Created by SUHAS on 05/03/2017.
 */

public class SearchFragment extends Fragment implements View.OnClickListener {
    private Context mContext;
    private ArrayList<VoterDetailModel> mVoterList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        this.mContext = getActivity();
        init(view);
        return view;
    }

    private void init(View view) {
        CardView cvSearchByName = (CardView) view.findViewById(R.id.cv_search_by_name);
        CardView cvSearchBySurname = (CardView) view.findViewById(R.id.cv_search_by_surname);
        CardView cvSearchByVotingCard = (CardView) view.findViewById(R.id.cv_search_by_voting_card);

        CardView cvSearchByAge = (CardView) view.findViewById(R.id.cv_search_by_age);
        CardView cvSearchByAddress = (CardView) view.findViewById(R.id.cv_search_by_address);
        CardView cvSearchByBoooth = (CardView) view.findViewById(R.id.cv_search_by_booth);

        cvSearchByName.setOnClickListener(this);
        cvSearchBySurname.setOnClickListener(this);
        cvSearchByVotingCard.setOnClickListener(this);
        cvSearchByAge.setOnClickListener(this);
        cvSearchByAddress.setOnClickListener(this);
        cvSearchByBoooth.setOnClickListener(this);
         mVoterList = DBHelper.getInstance(getActivity()).getAllVoters();
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.VOTER_LIST,mVoterList);
        switch (view.getId()) {
            case R.id.cv_search_by_name:
                bundle.putString(Constants.SEARCH_FOR_TEXT, getString(R.string.by_name));
                bundle.putInt(Constants.SEARCH_FOR_TYPE, 1);
                ((HomeActivity) getActivity()).setFragment(new DetailSearchFragment(), bundle);
                break;
            case R.id.cv_search_by_surname:
                bundle.putString(Constants.SEARCH_FOR_TEXT, getString(R.string.by_surname));
                bundle.putInt(Constants.SEARCH_FOR_TYPE, 1);
                ((HomeActivity) getActivity()).setFragment(new DetailSearchFragment(), bundle);
                break;
            case R.id.cv_search_by_voting_card:
                bundle.putString(Constants.SEARCH_FOR_TEXT, getString(R.string.by_election_card));
                bundle.putInt(Constants.SEARCH_FOR_TYPE, 1);
                ((HomeActivity) getActivity()).setFragment(new DetailSearchFragment(), bundle);
                break;
            case R.id.cv_search_by_age:
                bundle.putString(Constants.SEARCH_FOR_TEXT, getString(R.string.by_age));
                bundle.putInt(Constants.SEARCH_FOR_TYPE, 2);
                ((HomeActivity) getActivity()).setFragment(new DetailSearchFragment(), bundle);
                break;
            case R.id.cv_search_by_address:
                bundle.putString(Constants.SEARCH_FOR_TEXT, getString(R.string.by_address));
                bundle.putInt(Constants.SEARCH_FOR_TYPE, 1);
                ((HomeActivity) getActivity()).setFragment(new DetailSearchFragment(), bundle);
                break;
            case R.id.cv_search_by_booth:
                bundle.putString(Constants.SEARCH_FOR_TEXT, getString(R.string.by_booth));
                bundle.putInt(Constants.SEARCH_FOR_TYPE, 1);
                ((HomeActivity) getActivity()).setFragment(new DetailSearchFragment(), bundle);
                break;

        }
    }
}

