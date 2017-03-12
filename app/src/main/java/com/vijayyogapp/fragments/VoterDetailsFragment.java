package com.vijayyogapp.fragments;

/**
 * Created by SUHAS on 10/03/2017.
 */

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.RelativeLayout;

import com.vijayyogapp.activity.HomeActivity;
import com.vijayyogapp.R;
import com.vijayyogapp.database.DBHelper;
import com.vijayyogapp.dialogs.VoterStatusDialog;
import com.vijayyogapp.interfaces.IDialogVoterStatus;
import com.vijayyogapp.models.VoterDetailModel;
import com.vijayyogapp.models.VoterSurveyDetailModel;
import com.vijayyogapp.utils.Constants;
import com.vijayyogapp.utils.Utils;

public class VoterDetailsFragment extends Fragment implements View.OnClickListener, IDialogVoterStatus {

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
    private VoterDetailModel mVoterDetailModel;
    private TextView txtVoterStatus;
    private VoterSurveyDetailModel mVoterSurveyDetailModel;
    private IDialogVoterStatus mIDialogVoterStatus;

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
        txtVoterStatus = (TextView) view.findViewById(R.id.txt_voter_status);

        rlEditVoter = (RelativeLayout) view.findViewById(R.id.rl_edit_voter);
        rlVoterStatus = (RelativeLayout) view.findViewById(R.id.rl_voter_status);

        setDataToView();
        rlEditVoter.setOnClickListener(this);
        rlVoterStatus.setOnClickListener(this);
        mIDialogVoterStatus = VoterDetailsFragment.this;

    }

    private void setDataToView() {
        txtListNo.setText("" + mVoterDetailModel.getListNo());
        txtSrNo.setText("" + mVoterDetailModel.getSRNO());
        txtName.setText(mVoterDetailModel.getMFULLNAME());
        txtGenderAge.setText(mVoterDetailModel.getSEX() + "/" + mVoterDetailModel.getAge());
        txtVoterId.setText(mVoterDetailModel.getVoterID());
        txtAddress.setText(mVoterDetailModel.getAddress());

        String boothAddress = DBHelper.getInstance(getActivity()).getBoothAddress("" + mVoterDetailModel.getBID());
        if (boothAddress != null)
            txtBoothId.setText(boothAddress);
        else
            txtBoothId.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();
        mVoterSurveyDetailModel = DBHelper.getInstance(getActivity()).getSurveyData(mVoterDetailModel.getPrimaryKey());
        if (mVoterSurveyDetailModel.isValid()) {
            txtAadharId.setText(mVoterSurveyDetailModel.getAadharNo());
            txtMobileNumber.setText(mVoterSurveyDetailModel.getMobileNo());

            String voterStatus = mVoterSurveyDetailModel.getStatus();
            txtVoterStatus.setText(voterStatus);
            setTextColorToView(voterStatus);
        } else {
            txtAadharId.setText("");
            txtMobileNumber.setText("");

            String voterStatus = getString(R.string.neutral_vote);
            txtVoterStatus.setText(voterStatus);
            setTextColorToView(voterStatus);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_edit_voter:
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.VOTER_MODEL, mVoterDetailModel);
                ((HomeActivity) getActivity()).setFragment(new EditVoterDetailsFragment(), bundle);
                break;
            case R.id.rl_voter_status:
                VoterStatusDialog dialog = new VoterStatusDialog(getActivity(), mIDialogVoterStatus);
                dialog.show();
                break;
        }
    }

    private void setTextColorToView(String voterStatus) {
        if (voterStatus.equalsIgnoreCase(getString(R.string.neutral_vote))) {
            txtVoterStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.md_indigo_700));
        } else if (voterStatus.equalsIgnoreCase(getString(R.string.tentative_vote))) {
            txtVoterStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.md_yellow_700));
        } else if (voterStatus.equalsIgnoreCase(getString(R.string.confirm_vote))) {
            txtVoterStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.md_green_700));
        } else {
            txtVoterStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.md_red_700));
        }
    }

    @Override
    public void selectedVoterStatus(String status) {
        txtVoterStatus.setText(status);
        setTextColorToView(status);

        boolean isUpdate;
        VoterSurveyDetailModel voterSurveyDetailModel = new VoterSurveyDetailModel();
        if (mVoterSurveyDetailModel.isValid()) {
            isUpdate = true;
            voterSurveyDetailModel.setUniquekey(mVoterSurveyDetailModel.getUniquekey());
            voterSurveyDetailModel.setAadharNo(mVoterSurveyDetailModel.getAadharNo());
            voterSurveyDetailModel.setMobileNo(mVoterSurveyDetailModel.getMobileNo());
            voterSurveyDetailModel.setStatus(status);
        }else {
            isUpdate = true;
            voterSurveyDetailModel.setUniquekey(mVoterDetailModel.getPrimaryKey());
            voterSurveyDetailModel.setAadharNo(" ");
            voterSurveyDetailModel.setMobileNo(" ");
            voterSurveyDetailModel.setStatus(status);
        }
        boolean result = DBHelper.getInstance(getActivity()).insertOrUpdateSurveyDetails(voterSurveyDetailModel,isUpdate);
        if(result){
            Utils.getInstance().showToast(getContext(),"Saved Successfully.");
        }

    }
}
