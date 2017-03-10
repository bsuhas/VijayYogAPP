package com.vijayyogapp.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import com.vijayyogapp.R;
import com.vijayyogapp.interfaces.RetrofitRestClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SUHAS on 10/03/2017.
 */

public class Utils {
    private static Utils instance;
    private RetrofitRestClient fbRestClient;
    private ProgressDialog dialog;

    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    public boolean isOnline(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        } else {
            return false;
        }
    }

    public void showProgressDialog(Activity activity) {
        dialog = ProgressDialog.show(activity, null, "", true);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loaders_progress_dialog);
        dialog.show();
    }

    public void hideProgressDialog() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    public RetrofitRestClient getRestClient() {
        if (fbRestClient == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            OkHttpClient client = httpClient
                    .readTimeout(1, TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            fbRestClient = retrofit.create(RetrofitRestClient.class);
        }
        return fbRestClient;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public Bitmap getOriententionBitmap(String filePath){
        Bitmap myBitmap = null;
        try
        {
            File f = new File(filePath);
            ExifInterface exif = new ExifInterface(f.getPath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            int angle = 0;

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                angle = 90;
            }
            else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                angle = 180;
            }
            else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                angle = 270;
            }

            Matrix mat = new Matrix();
            mat.postRotate(angle);

            Bitmap bmp1 = BitmapFactory.decodeStream(new FileInputStream(f), null, null);
            myBitmap = Bitmap.createBitmap(bmp1, 0, 0, bmp1.getWidth(), bmp1.getHeight(), mat, true);

            if(myBitmap.getByteCount()>31961088){
                myBitmap = getResizedBitmap(myBitmap, myBitmap.getWidth() / 2, myBitmap.getHeight() / 2);
            }

//            LocalLog.d("myBitmap_size:", "" + myBitmap.getByteCount());//
//            OutputStream imagefile = new FileOutputStream(filePath);
//            // Write 'bitmap' to file using JPEG and 80% quality hint for JPEG:
//            myBitmap.compress(Bitmap.CompressFormat.JPEG, 80, imagefile);
//            LocalLog.d("myBitmap_size:",""+myBitmap.getByteCount());
        }
        catch (IOException e) {
            Log.w("TAG", "-- Error in setting image");
        }
        catch(OutOfMemoryError oom) {
            Log.w("TAG", "-- OOM Error in setting image");
        }
        return myBitmap;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
      /*  if (resizedBitmap.getByteCount() > 31961088)
            getResizedBitmap(resizedBitmap,THUMBNAIL_SIZE, THUMBNAIL_SIZE);*/
        return resizedBitmap;
    }
}
