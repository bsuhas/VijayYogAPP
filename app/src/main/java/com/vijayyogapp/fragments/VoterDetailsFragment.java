package com.vijayyogapp.fragments;

/**
 * Created by SUHAS on 10/03/2017.
 */

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.RelativeLayout;

import com.vijayyogapp.activity.HomeActivity;
import com.vijayyogapp.R;
import com.vijayyogapp.dialogs.VoterStatusDialog;

public class VoterDetailsFragment extends Fragment implements View.OnClickListener {

    private TextView txtName;
    private TextView txtGenderAge;
    private TextView txtVoterId;
    private TextView txtAddress;
    private TextView txtBoothId;
    private TextView txtAadharId;
    private TextView txtMobileNumber;
    private RelativeLayout rlEditVoter;
    private RelativeLayout rlVoterStatus;
    private TextView txtListNo;
    private TextView txtSrNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.voter_details_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtListNo = (TextView) view.findViewById(R.id.txt_list_no);
        txtSrNo = (TextView) view.findViewById(R.id.txt_srno);
        txtName = (TextView) view.findViewById(R.id.txt_name);
        txtGenderAge = (TextView) view.findViewById(R.id.txt_gender_age);
        txtVoterId = (TextView) view.findViewById(R.id.txt_voter_id);
        txtAddress = (TextView) view.findViewById(R.id.txt_address);
        txtBoothId = (TextView) view.findViewById(R.id.txt_booth_id);
        txtAadharId = (TextView) view.findViewById(R.id.txt_aadhar_id);
        txtMobileNumber = (TextView) view.findViewById(R.id.txt_mobile_number);
        rlEditVoter = (RelativeLayout) view.findViewById(R.id.rl_edit_voter);
        rlVoterStatus = (RelativeLayout) view.findViewById(R.id.rl_voter_status);

        rlEditVoter.setOnClickListener(this);
        rlVoterStatus.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_edit_voter:
                Bundle bundle = new Bundle();
                ((HomeActivity) getActivity()).setFragment(new EditVoterDetailsFragment(), bundle);
                break;
            case R.id.rl_voter_status:
                VoterStatusDialog dialog = new VoterStatusDialog(getActivity());
                dialog.show();
                break;
        }
    }
}
