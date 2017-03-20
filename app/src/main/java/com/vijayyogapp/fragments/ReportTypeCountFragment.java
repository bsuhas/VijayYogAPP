package com.vijayyogapp.fragments;

/**
 * Created by SUHAS on 12/03/2017.
 */

import android.support.v4.app.Fragment;
import android.util.Log;
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
    private TextView txtVoteCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_type_count_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtVoteCount = (TextView) view.findViewById(R.id.txt_vote_count);
        txtNeutralVote = (TextView) view.findViewById(R.id.txt_neutral_vote);
        txtTentativeCount = (TextView) view.findViewById(R.id.txt_tentative_count);
        txtConfirmVoteCount = (TextView) view.findViewById(R.id.txt_confirm_vote_count);
        txtNeverGoingToVote = (TextView) view.findViewById(R.id.txt_never_going_to_vote);

        setDataToView();
    }

    private void setDataToView() {
        long totalVoterCount = DBHelper.getInstance(getActivity()).getTotalVoterCount();
        Log.e("voterCount","VoterCount:"+totalVoterCount);
        txtVoteCount.setText("" + totalVoterCount);

        long tentativeCount = DBHelper.getInstance(getActivity()).getStatusTypeCount(getString(R.string.tentative_vote));
        txtTentativeCount.setText("" + tentativeCount);

        long confirmCount = DBHelper.getInstance(getActivity()).getStatusTypeCount(getString(R.string.confirm_vote));
        txtConfirmVoteCount.setText("" + confirmCount);

        long neverCount = DBHelper.getInstance(getActivity()).getStatusTypeCount(getString(R.string.never_going_to_vote));
        txtNeverGoingToVote.setText("" + neverCount);

        long neutralCount = totalVoterCount - (tentativeCount + confirmCount + neverCount);
        txtNeutralVote.setText("" + neutralCount);


    }

}
