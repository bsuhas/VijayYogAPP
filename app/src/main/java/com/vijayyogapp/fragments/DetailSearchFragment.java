package com.vijayyogapp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.vijayyogapp.R;
import com.vijayyogapp.adapters.VoterListAdapter;
import com.vijayyogapp.database.DBHelper;
import com.vijayyogapp.models.VoterDetailModel;
import com.vijayyogapp.utils.Constants;
import com.vijayyogapp.utils.SimpleDividerItemDecoration;
import com.vijayyogapp.utils.Utils;

import java.util.ArrayList;

/**
 * Created by SUHAS on 05/03/2017.
 */

public class DetailSearchFragment extends Fragment implements View.OnClickListener {
    private Context mContext;
    private String searchFor;
    private int searchType;
    private ArrayList<VoterDetailModel> mVoterDataList;
    private EditText edtSearch;
    private VoterListAdapter adapter;
    private int searchActualType;
    private EditText edtMinAge;
    private EditText edtMaxAge;
    private TextView txtNoResult;
    private RecyclerView voterList;
    private ProgressDialog pd;
    private LinearLayout llKeyboard;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            searchFor = bundle.getString(Constants.SEARCH_FOR_TEXT);
            searchType = bundle.getInt(Constants.SEARCH_FOR_TYPE, 1);
            searchActualType = bundle.getInt(Constants.SEARCH_FOR_ACTUAL_TYPE, 0);
            mVoterDataList = new ArrayList<>();
            mVoterDataList = (ArrayList<VoterDetailModel>) bundle.getSerializable(Constants.VOTER_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_search_fragment, container, false);
        this.mContext = getActivity();
        init(view);
        return view;
    }

    private void init(View view) {
        Button btnSearch = (Button) view.findViewById(R.id.btn_search);
        edtSearch = (EditText) view.findViewById(R.id.edt_search_by);
        edtMinAge = (EditText) view.findViewById(R.id.edt_min_age);
        edtMaxAge = (EditText) view.findViewById(R.id.edt_max_age);
        txtNoResult = (TextView) view.findViewById(R.id.txt_no_result);
        llKeyboard = (LinearLayout) view.findViewById(R.id.ll_keyboard);
        TextView txtSearchType = (TextView) view.findViewById(R.id.search_type);

        LinearLayout llAgeSearch = (LinearLayout) view.findViewById(R.id.ll_age_search);
        voterList = (RecyclerView) view.findViewById(R.id.cardList);

        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        voterList.setHasFixedSize(true);
        voterList.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        voterList.setLayoutManager(llm);

        txtSearchType.setText(searchFor);

        if (searchType == 2) {
            llAgeSearch.setVisibility(View.VISIBLE);
            edtSearch.setVisibility(View.GONE);
        } else {
            llAgeSearch.setVisibility(View.GONE);
            edtSearch.setVisibility(View.VISIBLE);
        }
        btnSearch.setOnClickListener(this);
        adapter = new VoterListAdapter(mContext, mVoterDataList);
        voterList.setAdapter(adapter);

        if (searchActualType == Constants.BY_FNAME || searchActualType == Constants.BY_LNAME) {
            llKeyboard.setVisibility(View.VISIBLE);
        } else {
            llKeyboard.setVisibility(View.GONE);
        }

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rg_keyboard);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_marathi:
                        Constants.KEYBOARD_TYPE = 1;
                        break;
                    case R.id.radio_english:
                        Constants.KEYBOARD_TYPE = 2;
                        break;
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                Utils.getInstance().hidekeyboard(getActivity());
                if (searchType == 1) {
                    String searchText = edtSearch.getText().toString().trim();
                    if (TextUtils.isEmpty(searchText)) {
                        Utils.getInstance().showToast(getActivity(), "Please enter to search");
                        return;
                    }
                    mVoterDataList.clear();
                    showProgressDialog(searchText);
                } else {
                    String minAge = edtMinAge.getText().toString().trim();
                    String maxAge = edtMaxAge.getText().toString().trim();
                    if (TextUtils.isEmpty(minAge)) {
                        Utils.getInstance().showToast(getActivity(), "Please enter to minimum age");
                        return;
                    }
                    if (TextUtils.isEmpty(maxAge)) {
                        Utils.getInstance().showToast(getActivity(), "Please enter to maximum age");
                        return;
                    }
                    mVoterDataList.clear();
                    showProgressDialogForAge(minAge, maxAge);
                }
                break;

        }
    }

    private void showProgressDialog(final String searchText) {
        pd = ProgressDialog.show(getActivity(), "", "Please wait....");
        new Thread() {
            public void run() {
                try {
                    mVoterDataList = DBHelper.getInstance(getActivity()).getSearchVotersByName(searchText, searchActualType);
                    handler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void showProgressDialogForAge(final String minAge, final String maxAge) {
        pd = ProgressDialog.show(getActivity(), "", "Please wait....");
        new Thread() {
            public void run() {
                try {
                    mVoterDataList = DBHelper.getInstance(getActivity()).getSearchVotersByAge(minAge, maxAge, searchActualType);
                    handler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            pd.dismiss();
            if (mVoterDataList.size() > 0) {
                Utils.getInstance().hideProgressDialog();
                voterList.setVisibility(View.VISIBLE);
                txtNoResult.setVisibility(View.GONE);
                adapter.refreshAdapter(mVoterDataList);
            } else {
                voterList.setVisibility(View.GONE);
                txtNoResult.setVisibility(View.VISIBLE);
            }
        }
    };
}


