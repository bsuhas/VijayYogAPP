package com.vijayyogapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vijayyogapp.R;
import com.vijayyogapp.HomeActivity;
import com.vijayyogapp.utils.Constants;

/**
 * Created by SUHAS on 05/03/2017.
 */

public class SearchFragment extends Fragment implements View.OnClickListener {
    private Context mContext;

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
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.cv_search_by_name:
                bundle.putString(Constants.SEARCH_FOR_TEXT, getString(R.string.by_name));
                ((HomeActivity) getActivity()).setFragment(new DetailSearchFragment(), bundle);
                break;
            case R.id.cv_search_by_surname:
                bundle.putString(Constants.SEARCH_FOR_TEXT, getString(R.string.by_surname));
                ((HomeActivity) getActivity()).setFragment(new DetailSearchFragment(), bundle);
                break;
            case R.id.cv_search_by_voting_card:
                bundle.putString(Constants.SEARCH_FOR_TEXT, getString(R.string.by_election_card));
                ((HomeActivity) getActivity()).setFragment(new DetailSearchFragment(), bundle);
                break;
            case R.id.cv_search_by_age:
                bundle.putString(Constants.SEARCH_FOR_TEXT, getString(R.string.by_age));
                ((HomeActivity) getActivity()).setFragment(new DetailSearchFragment(), bundle);
                break;
            case R.id.cv_search_by_address:
                bundle.putString(Constants.SEARCH_FOR_TEXT, getString(R.string.by_address));
                ((HomeActivity) getActivity()).setFragment(new DetailSearchFragment(), bundle);
                break;
            case R.id.cv_search_by_booth:
                bundle.putString(Constants.SEARCH_FOR_TEXT, getString(R.string.by_booth));
                ((HomeActivity) getActivity()).setFragment(new DetailSearchFragment(), bundle);
                break;

        }
    }
}

