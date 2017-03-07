package com.vijayyogapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.vijayyogapp.HomeActivity;
import com.vijayyogapp.R;
import com.vijayyogapp.models.UserData;
import com.vijayyogapp.utils.UserPreferences;


/**
 * Created by SUHAS on 04/03/2017.
 */
public class RegisterScreenActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private LinearLayout llWardNumber;
    private EditText edtName, edtMobile, edtLoksabha, edtVidhansabha, edtWardNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        mContext = this;
        init();
    }

    private void init() {
        llWardNumber = (LinearLayout) findViewById(R.id.ll_ward_number);
        Button btnRegister = (Button) findViewById(R.id.btn_register);

        edtName = (EditText) findViewById(R.id.edt_name);
        edtMobile = (EditText) findViewById(R.id.edt_mobile);
        edtLoksabha = (EditText) findViewById(R.id.edt_loksabha);
        edtVidhansabha = (EditText) findViewById(R.id.edt_vidhansabha);
        edtWardNumber = (EditText) findViewById(R.id.edt_ward_number);
        btnRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                if (validate()) {
                    UserData userData =  new UserData();
                    userData.setName(edtName.getText().toString().trim());
                    userData.setMobileNumber(edtMobile.getText().toString().trim());
                    userData.setLoaksabha(edtLoksabha.getText().toString().trim());
                    userData.setVidhansabha(edtVidhansabha.getText().toString().trim());
                    userData.setWardNumber(edtWardNumber.getText().toString().trim());

                    //TODO API call
                    UserPreferences.getInstance(this).saveUserInfo(userData,true);
                    Intent intent = new Intent(RegisterScreenActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(edtName.getText().toString().trim())) {
            showToast(this, getString(R.string.error_msg_enter_name));
            return false;
        }
        if (TextUtils.isEmpty(edtMobile.getText().toString().trim())) {
            showToast(this, getString(R.string.error_msg_enter_mobile_number));
            return false;
        }
        if (edtMobile.getText().toString().trim().length() < 10) {
            showToast(this, getString(R.string.error_msg_enter_mobile_number));
            return false;
        }
        if (TextUtils.isEmpty(edtLoksabha.getText().toString().trim())) {
            showToast(this, getString(R.string.error_msg_enter_loksabha));
            return false;
        }
        if (TextUtils.isEmpty(edtVidhansabha.getText().toString().trim())) {
            showToast(this, getString(R.string.error_msg_enter_vidhan_sabha));
            return false;
        }
        if (TextUtils.isEmpty(edtWardNumber.getText().toString().trim())) {
            showToast(this, getString(R.string.error_msg_enter_ward_number));
            return false;
        }
        return true;
    }

    private void showToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }
}

