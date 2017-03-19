package com.vijayyogapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.vijayyogapp.MyApplication;
import com.vijayyogapp.R;

import java.util.Locale;

public class DialogUtils {

    public static void showAlertDialog(Context context, String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        if (title != null)
            alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.create().show();
    }

    public static void showYesNoLogoutConfirmDialogAndFinishActivity(final Activity mActivity, String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        if (title != null)
            alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                mActivity.finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.create().show();
    }
    public static void showLanguageSelectionDialog(final Context context, final Activity activity) {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        final View view = LayoutInflater.from(context).inflate(R.layout.language_selection_dialog, null, false);

        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btnSubmit = (Button) view.findViewById(R.id.btn_submit);
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        setSelectedLangRadioBtn(context, view);
        alertDialogBuilder.setView(view).setCancelable(false);
        Dialog dialog = null;
        dialog = alertDialogBuilder.show();

        final Dialog finalDialog = dialog;
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();

//                // find the radiobutton by returned id
//                RadioButton radioSexButton = (RadioButton) view.findViewById(selectedId);
//                Toast.makeText(context, radioSexButton.getText(), Toast.LENGTH_SHORT).show();

                setLanguage(activity, selectedId);

                finalDialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalDialog.dismiss();
            }
        });
    }
    private static void setSelectedLangRadioBtn(Context context, View view) {
        String defaultLang = UserPreferences.getInstance(context).getUserDefaultLanguage();
        switch (defaultLang) {

            case Constants.LANG_ENGLISH:
                RadioButton radioButtonEng = (RadioButton) view.findViewById(R.id.rb_english);
                radioButtonEng.setChecked(true);
                break;
            case Constants.LANG_MARATHI:
                RadioButton radioButtonMarathi = (RadioButton) view.findViewById(R.id.rb_marathi);
                radioButtonMarathi.setChecked(true);
                break;

        }
    }
    private static void setLanguage(Activity activity, int selectedId) {
        switch (selectedId) {

            case R.id.rb_english:
                setDefaultAPPLang(activity, Constants.LANG_ENGLISH);
                break;
            case R.id.rb_marathi:
                setDefaultAPPLang(activity, Constants.LANG_MARATHI);
                break;
        }

    }
    public static void setDefaultAPPLang(Activity activity, String lang) {
        //Save in shared pref
        UserPreferences.getInstance(activity).setUserDefaultLanguage(lang);

        Resources res = activity.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(lang);
        res.updateConfiguration(conf, dm);
        activity.recreate();
    }

    public static void updateConfig(Application app,String lang) {
        Resources res = app.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();

        if( Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //Wrapping the configuration to avoid Activity endless loop
//           loop Configuration config = new Configuration(res.getConfiguration());
            conf.locale = new Locale(lang);
            res.updateConfiguration(conf, res.getDisplayMetrics());
        }
    }

}
