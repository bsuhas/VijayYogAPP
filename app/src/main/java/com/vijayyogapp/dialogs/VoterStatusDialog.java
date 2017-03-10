package com.vijayyogapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.TextView;

import com.vijayyogapp.R;

/**
 * Created by SUHAS on 10/03/2017.
 */

public class VoterStatusDialog extends Dialog {
    private RadioButton radioNeutral;
    private RadioButton radioTentative;
    private RadioButton radioConfirm;
    private RadioButton radioNever;
    private TextView txtCancel;

    public VoterStatusDialog(Context context) {
        super(context, R.style.MyDialogTheme);
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        this.setContentView(R.layout.status_dialog_layout);
        makeDialogLayoutSetting();

        radioNeutral = (RadioButton) findViewById(R.id.radio_neutral);
        radioTentative = (RadioButton) findViewById(R.id.radio_tentative);
        radioConfirm = (RadioButton) findViewById(R.id.radio_confirm);
        radioNever = (RadioButton) findViewById(R.id.radio_never);
        txtCancel = (TextView) findViewById(R.id.txt_cancel);
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void makeDialogLayoutSetting() {
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;

        wlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wlp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;

        window.setAttributes(wlp);
    }

}
