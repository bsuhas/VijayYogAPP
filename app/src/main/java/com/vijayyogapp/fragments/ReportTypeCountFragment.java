package com.vijayyogapp.fragments;

/**
 * Created by SUHAS on 12/03/2017.
 */

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.TextView;

import com.vijayyogapp.R;
import com.vijayyogapp.database.DBHelper;

public class ReportTypeCountFragment extends Fragment {

    private TextView txtNeutralVote;
    private TextView txtTentativeCount;
    private TextView txtConfirmVoteCount;
    private TextView txtNeverGoingToVote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_type_count_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtNeutralVote = (TextView) view.findViewById(R.id.txt_neutral_vote);
        txtTentativeCount = (TextView) view.findViewById(R.id.txt_tentative_count);
        txtConfirmVoteCount = (TextView) view.findViewById(R.id.txt_confirm_vote_count);
        txtNeverGoingToVote = (TextView) view.findViewById(R.id.txt_never_going_to_vote);

        setDataToView();
    }

    private void setDataToView() {
        int neutralCount = DBHelper.getInstance(getActivity()).getStatusTypeCount(getString(R.string.neutral_vote));
        txtNeutralVote.setText(neutralCount);

        int tentativeCount = DBHelper.getInstance(getActivity()).getStatusTypeCount(getString(R.string.tentative_vote));
        txtTentativeCount.setText(tentativeCount);

        int confirmCount = DBHelper.getInstance(getActivity()).getStatusTypeCount(getString(R.string.confirm_vote));
        txtConfirmVoteCount.setText(confirmCount);

        int neverCount = DBHelper.getInstance(getActivity()).getStatusTypeCount(getString(R.string.never_going_to_vote));
        txtNeverGoingToVote.setText(neverCount);


    }

}
