package com.vijayyogapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vijayyogapp.R;
import com.vijayyogapp.utils.Constants;
import com.vijayyogapp.utils.DialogUtils;
import com.vijayyogapp.utils.UserPreferences;


/**
 * Created by maheshwarligade on 20/5/16.
 */
public class SettingsFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.seetings_layout, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnSelectLang =(Button) view.findViewById(R.id.btn_select_lang);
        TextView txtSelectedLang =(TextView) view.findViewById(R.id.txt_selected_lang);

        String defaultLang = UserPreferences.getInstance(getActivity()).getUserDefaultLanguage();
        switch (defaultLang){
            case Constants.LANG_ENGLISH :
                txtSelectedLang.setText(R.string.lang_english);
                break;
            case Constants.LANG_MARATHI :
                txtSelectedLang.setText(R.string.lang_marathi);
                break;
        }


        btnSelectLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showLanguageSelectionDialog(getActivity(),getActivity());
            }
        });
    }

}
