package com.vijayyogapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.vijayyogapp.R;
import com.vijayyogapp.interfaces.IDialogVoterStatus;

/**
 * Created by SUHAS on 10/03/2017.
 */

public class VoterStatusDialog extends Dialog implements View.OnClickListener {
    private IDialogVoterStatus mIDialogVoterStatus;
    private Context mContext;

    public VoterStatusDialog(Context context, IDialogVoterStatus iDailogVoterStatus) {
        super(context, R.style.MyDialogTheme);
        mIDialogVoterStatus = iDailogVoterStatus;
        mContext = context;
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        this.setContentView(R.layout.status_dialog_layout);
        makeDialogLayoutSetting();
        TextView txtCancel = (TextView) findViewById(R.id.txt_cancel);
        txtCancel.setOnClickListener(this);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_voter_status);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_neutral:
                        mIDialogVoterStatus.selectedVoterStatus(mContext.getString(R.string.neutral_vote));
                        dismiss();
                        break;
                    case R.id.radio_tentative:
                        mIDialogVoterStatus.selectedVoterStatus(mContext.getString(R.string.tentative_vote));
                        dismiss();
                        break;
                    case R.id.radio_confirm:
                        mIDialogVoterStatus.selectedVoterStatus(mContext.getString(R.string.confirm_vote));
                        dismiss();
                        break;
                    case R.id.radio_never:
                        mIDialogVoterStatus.selectedVoterStatus(mContext.getString(R.string.never_going_to_vote));
                        dismiss();
                        break;
                }

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_cancel:
                dismiss();
                break;
        }
    }
}
