package com.vijayyogapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.vijayyogapp.HomeActivity;
import com.vijayyogapp.R;


/**
 * Created by SUHAS on 04/03/2017.
 */
public class RegisterScreenActivity extends AppCompatActivity implements View.OnClickListener{
    private Context mContext;
    private LinearLayout llWardNumber;

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
        btnRegister.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case  R.id.btn_register:
            Intent intent = new Intent(RegisterScreenActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

