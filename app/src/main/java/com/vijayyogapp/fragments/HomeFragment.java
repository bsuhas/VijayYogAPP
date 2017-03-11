package com.vijayyogapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vijayyogapp.activity.HomeActivity;
import com.vijayyogapp.R;
import com.vijayyogapp.dialogs.PickMemoryDialog;
import com.vijayyogapp.models.UserData;
import com.vijayyogapp.utils.CircularImageView;
import com.vijayyogapp.utils.Constants;
import com.vijayyogapp.utils.UserPreferences;
import com.vijayyogapp.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by SUHAS on 05/03/2017.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    private Context mContext;
    private CircularImageView profileImage;
    private TextView txtProfileName;
    private TextView txtMobileNumber;
    private TextView txtLokId;
    private TextView txtVidanId;
    private TextView txtWardId;
    private PickMemoryDialog mPickMemoryDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        this.mContext = getActivity();
        init(view);
        return view;
    }

    private void init(View view) {
        profileImage = (CircularImageView) view.findViewById(R.id.profile_image);
        txtProfileName = (TextView) view.findViewById(R.id.txt_profile_name);
        txtMobileNumber = (TextView) view.findViewById(R.id.txt_mobile_number);
        txtLokId = (TextView) view.findViewById(R.id.txt_lok_id);
        txtVidanId = (TextView) view.findViewById(R.id.txt_vidan_id);
        txtWardId = (TextView) view.findViewById(R.id.txt_ward_id);

        setDataToView();

        Button btnSearch = (Button) view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);
        profileImage.setOnClickListener(this);
    }

    private void setDataToView() {
        UserData userData = ((HomeActivity) getActivity()).getUserData();
        if (userData != null) {
            txtProfileName.setText(userData.getUsername());
            txtMobileNumber.setText(userData.getMobileNumber());
            txtLokId.setText(userData.getLoksabhaId());
            txtVidanId.setText(userData.getVidhansabhaId());
            txtWardId.setText(userData.getWardNumber());
        }
        String profileImg = UserPreferences.getInstance(mContext).getProfileImage();
        if (profileImg == null) {
            profileImage.setImageResource(R.drawable.user);
        } else {
            setImageToView(profileImg);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_search:
                Bundle bundle = new Bundle();
                ((HomeActivity) getActivity()).setFragment(new SearchFragment(), bundle);
                break;
            case R.id.profile_image:
                mPickMemoryDialog = new PickMemoryDialog(getActivity(), HomeFragment.this);
                mPickMemoryDialog.show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Image from Camera
        if (requestCode == Constants.CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            setImageToView(Constants.mCurrentPhotoPath);
            UserPreferences.getInstance(mContext).saveProfileImage(Constants.mCurrentPhotoPath);
            mPickMemoryDialog.dismiss();

        }
        //Image from Gallery
        else if (requestCode == Constants.GALLERY_INTENT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            try {
                String path = getFilePath(data);

                File src = new File(path);
                File dest = getImageUri();
                copyFile(src, dest);

                UserPreferences.getInstance(mContext).saveProfileImage(dest.getAbsolutePath());
                setImageToView(path);
                mPickMemoryDialog.dismiss();
//                setImageToBlogDescription(getFilePath(data));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setImageToView(String path) {
        File file = new File(path);
        if (file.exists()){
            Uri uri = Uri.fromFile(file);
            Picasso.with(getActivity()).load(uri).into(profileImage);
            ((HomeActivity) getActivity()).setDrawerProfileImage();
        }
        else {
            profileImage.setImageResource(R.drawable.user);
        }
    }

    private String getFilePath(Intent data) {
        String imagePath;
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        imagePath = cursor.getString(columnIndex);
        cursor.close();

        return imagePath;

    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }

    public File getImageUri() {

        File file = new File(Environment.getExternalStorageDirectory().getPath(), Constants.STORED_IMAGE_PATH + "/Images/camera");
        if (!file.exists()) {
            file.mkdirs();
        }
        String currentPhotoPath = (file.getAbsolutePath() + "/" + "IMG_" + "profile.jpg");
        return new File(currentPhotoPath);
    }
}

