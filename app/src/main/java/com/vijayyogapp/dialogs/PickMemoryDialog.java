package com.vijayyogapp.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.vijayyogapp.R;
import com.vijayyogapp.fragments.HomeFragment;
import com.vijayyogapp.utils.Constants;

import java.io.File;


/**
 * Created by suhasbachewar
 */
public class PickMemoryDialog extends Dialog implements View.OnClickListener {


    public interface OnImagePickerDialogCancelled {
        void onImagePickerDismissed();
    }


    private Activity mActivity;
    private HomeFragment mHomeFragment;
    private String mCallFromFragment = "";


    public PickMemoryDialog(Context context, HomeFragment homeFragment) {
        super(context, R.style.PickImageDialogTheme);
        mHomeFragment = homeFragment;
        mCallFromFragment = "homeFragment";
        init();
    }
    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.pick_memory);
        makeDialogLayoutSetting();
        Button btnGallery = (Button) findViewById(R.id.btn_gallery);
        Button btnCamera = (Button) findViewById(R.id.btn_camera);
        btnGallery.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
        LinearLayout linearLayoutOuter = (LinearLayout) findViewById(R.id.ll_outer);
        linearLayoutOuter.setOnClickListener(this);
    }

    private void makeDialogLayoutSetting() {
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_gallery:
                pickImageFromGallery();
                break;
            case R.id.btn_camera:
                takePhotoWithCamera();
                break;
            case R.id.ll_outer:

                if (mActivity != null && mActivity instanceof OnImagePickerDialogCancelled) {
                    ((OnImagePickerDialogCancelled) mActivity).onImagePickerDismissed();
                }
                dismiss();
                break;

        }
    }

    void pickImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        if (mCallFromFragment.equalsIgnoreCase("homeFragment")) {
            mHomeFragment.startActivityForResult(photoPickerIntent, Constants.GALLERY_INTENT_REQUEST_CODE);
        }
        else {
            mActivity.startActivityForResult(photoPickerIntent, Constants.GALLERY_INTENT_REQUEST_CODE);
        }
    }

    void takePhotoWithCamera() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
        cameraIntent.putExtra("return-data", true);
        if (mCallFromFragment.equalsIgnoreCase("homeFragment")) {
            mHomeFragment.startActivityForResult(cameraIntent, Constants.CAMERA_REQUEST);
        }
        else {
            mActivity.startActivityForResult(cameraIntent, Constants.CAMERA_REQUEST);
        }
    }

    public Uri setImageUri() {

        File file = new File(Environment.getExternalStorageDirectory().getPath(), Constants.STORED_IMAGE_PATH + "/Images/camera");
        if (!file.exists()) {
            file.mkdirs();
        }
        Constants.mCurrentPhotoPath = (file.getAbsolutePath() + "/" + "IMG_" + System.currentTimeMillis() + ".jpg");
        return Uri.fromFile(new File(Constants.mCurrentPhotoPath));
    }

    @Override
    public void onBackPressed() {

        if (mActivity != null && mActivity instanceof OnImagePickerDialogCancelled) {
            ((OnImagePickerDialogCancelled) mActivity).onImagePickerDismissed();
        }
        else{
            dismiss();
        }
    }
}


