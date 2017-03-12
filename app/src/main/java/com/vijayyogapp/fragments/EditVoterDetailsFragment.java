package com.vijayyogapp.fragments;

/**
 * Created by SUHAS on 09/03/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.vijayyogapp.R;
import com.vijayyogapp.database.DBHelper;
import com.vijayyogapp.models.VoterDetailModel;
import com.vijayyogapp.models.VoterSurveyDetailModel;
import com.vijayyogapp.utils.Constants;
import com.vijayyogapp.utils.Utils;


public class EditVoterDetailsFragment extends Fragment implements View.OnClickListener {

    private TextView txtName;
    private TextView txtGenderAge;
    private TextView txtVoterId;
    private TextView txtBoothId;
    private TextView txtAddress;
    private TextView txtListNo;
    private TextView txtSrNo;
    private VoterDetailModel mVoterDetailModel;
    private VoterSurveyDetailModel mVoterSurveyDetailModel;

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
        mVoterSurveyDetailModel = DBHelper.getInstance(getActivity()).getSurveyData(mVoterDetailModel.getPrimaryKey());
        if(mVoterSurveyDetailModel.isValid()){
            getEdtAadharId().setText(mVoterSurveyDetailModel.getAadharNo());
            getEdtMobileNumber().setText(mVoterSurveyDetailModel.getMobileNo());
        }

        //Action done
        getEdtMobileNumber().setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ((actionId == EditorInfo.IME_ACTION_DONE)) {
                    validateAndSaveRecord();
                }
                return false;
            }
        });

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
                validateAndSaveRecord();
                break;
        }
    }

    private void validateAndSaveRecord() {
        String aadharNumber = getEdtAadharId().getText().toString();
        String mobileNumber = getEdtMobileNumber().getText().toString();

        //Mobile number validation
        int aadharNumberLength = aadharNumber.length();

        if(aadharNumberLength!=0) {
            if (aadharNumberLength > 12 || aadharNumberLength < 12) {
                Utils.getInstance().showToast(getActivity(), getString(R.string.msg_enter_valid_aadhar_number));
                return;
            }
        }

        //Mobile number validation
        int mobileLength = mobileNumber.length();

        if(mobileLength!=0) {
            if (mobileLength > 10 || mobileLength < 10) {
                Utils.getInstance().showToast(getActivity(), getString(R.string.msg_enter_valid_mobile_number));
                return;
            }
        }

        boolean isUpdate;
        if(mVoterSurveyDetailModel.isValid()){
            isUpdate = false;
        }else{
            isUpdate = true;
        }
        VoterSurveyDetailModel voterSurveyDetailModel = new VoterSurveyDetailModel();
        voterSurveyDetailModel.setUniquekey(mVoterDetailModel.getPrimaryKey());
        voterSurveyDetailModel.setAadharNo(aadharNumber);
        voterSurveyDetailModel.setMobileNo(mobileNumber);

        if(mVoterSurveyDetailModel.isValid()){
            voterSurveyDetailModel.setStatus(mVoterSurveyDetailModel.getStatus());
        }else{
            voterSurveyDetailModel.setStatus(getString(R.string.neutral_vote));
        }

        boolean result = DBHelper.getInstance(getActivity()).insertOrUpdateSurveyDetails(voterSurveyDetailModel,isUpdate);
        if(result){
            getFragmentManager().popBackStackImmediate();
        }
    }
}

