package com.vijayyogapp.fragments;

/**
 * Created by SUHAS on 09/03/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.vijayyogapp.R;
import com.vijayyogapp.models.VoterDetailModel;
import com.vijayyogapp.utils.Constants;


public class EditVoterDetailsFragment extends Fragment implements View.OnClickListener {

    private TextView txtName;
    private TextView txtGenderAge;
    private TextView txtVoterId;
    private TextView txtBoothId;
    private TextView txtAddress;
    private TextView txtListNo;
    private TextView txtSrNo;
    private VoterDetailModel mVoterDetailModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mVoterDetailModel = (VoterDetailModel) bundle.getSerializable(Constants.VOTER_MODEL);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_voter_details_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtListNo= (TextView) view.findViewById(R.id.txt_list_no);
        txtSrNo= (TextView) view.findViewById(R.id.txt_srno);
        txtName = (TextView) view.findViewById(R.id.txt_name);
        txtGenderAge = (TextView) view.findViewById(R.id.txt_gender_age);
        txtVoterId = (TextView) view.findViewById(R.id.txt_voter_id);
        txtBoothId = (TextView) view.findViewById(R.id.txt_booth_id);
        txtAddress = (TextView) view.findViewById(R.id.txt_address);
        view.findViewById(R.id.btn_edit).setOnClickListener(this);
        setDataToView();
    }
    private void setDataToView() {
        txtListNo.setText(""+mVoterDetailModel.getListNo());
        txtSrNo.setText(""+mVoterDetailModel.getSRNO());
        txtName.setText(mVoterDetailModel.getMFULLNAME());
        txtGenderAge.setText(mVoterDetailModel.getSEX()+"/"+mVoterDetailModel.getAge());
        txtVoterId.setText(mVoterDetailModel.getVoterID());
        txtAddress.setText(mVoterDetailModel.getAddress());
    }

    private EditText getEdtAadharId() {
        return (EditText) getView().findViewById(R.id.edt_aadhar_id);
    }

    private EditText getEdtMobileNumber() {
        return (EditText) getView().findViewById(R.id.edt_mobile_number);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_edit:
                //TODO implement
                break;
        }
    }
}

