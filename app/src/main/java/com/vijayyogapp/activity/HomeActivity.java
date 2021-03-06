package com.vijayyogapp.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.picasso.Picasso;
import com.vijayyogapp.R;
import com.vijayyogapp.database.DBHelper;
import com.vijayyogapp.fragments.HomeFragment;
import com.vijayyogapp.fragments.ReportScreenFragment;
import com.vijayyogapp.fragments.SearchFragment;
import com.vijayyogapp.fragments.SettingsFragment;
import com.vijayyogapp.interfaces.RESTClientResponse;
import com.vijayyogapp.models.BoothDataResponse;
import com.vijayyogapp.models.RequestModel;
import com.vijayyogapp.models.StatusModel;
import com.vijayyogapp.models.SurveyDataRequestModel;
import com.vijayyogapp.models.SurveyDataResponse;
import com.vijayyogapp.models.SurveyStatusModel;
import com.vijayyogapp.models.UserData;
import com.vijayyogapp.models.VoterDataRequestModel;
import com.vijayyogapp.models.VoterDataResponseModel;
import com.vijayyogapp.models.VoterSurveyDetailModel;
import com.vijayyogapp.utils.CircularImageView;
import com.vijayyogapp.utils.DialogUtils;
import com.vijayyogapp.utils.UserPreferences;
import com.vijayyogapp.utils.Utils;
import com.vijayyogapp.webservices.BoothDataService;
import com.vijayyogapp.webservices.SurveyDataService;
import com.vijayyogapp.webservices.VoterDataService;

import java.io.File;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private UserData mUserData;
    private SystemBarTintManager mTintManager;
    private CircularImageView profileImage;
    private int startID = 0;
    private int endId = 0;
    private boolean showProgress = true;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 201;
    private int surveyDataCount = 0;
    private ArrayList<SurveyDataRequestModel> surveyReqDataModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mUserData = UserPreferences.getInstance(this).getUserNameInfo();
        initToolBar();
        Bundle bundle = new Bundle();
        setFragment(new HomeFragment(), bundle);
        checkPermissionForWrite();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int colorCode = ContextCompat.getColor(this, R.color.colorPrimary);
        applyKitKatTranslucency(Color.parseColor("#" + Integer.toHexString(colorCode)));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        TextView txtProfileName = (TextView) view.findViewById(R.id.txt_profile_name);
        TextView txtMobileNumber = (TextView) view.findViewById(R.id.txt_mobile_number);
        profileImage = (CircularImageView) view.findViewById(R.id.profile_image);

        if (mUserData != null) {
            txtProfileName.setText(mUserData.getUsername());
            txtMobileNumber.setText(mUserData.getMobileNumber());
        }
        setDrawerProfileImage();
    }

    public void setDrawerProfileImage() {
        String profileImg = UserPreferences.getInstance(this).getProfileImage();
        if (profileImg == null) {
            profileImage.setImageResource(R.drawable.user);
        } else {
            File file = new File(profileImg);
            if (file.exists()) {
                Picasso.with(HomeActivity.this).load("file:///" + file.getAbsolutePath()).placeholder(R.drawable.user).into(profileImage);
            } else
                profileImage.setImageResource(R.drawable.user);
        }
    }

    public void applyKitKatTranslucency(int color) {
        if (Utils.hasKitKat()) {
            if (mTintManager == null)
                mTintManager = new SystemBarTintManager(this);
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setStatusBarTintColor(color);

        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                DialogUtils.showYesNoLogoutConfirmDialogAndFinishActivity(this, "", "Do you want to close the app?");
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Bundle bundle = new Bundle();
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setFragment(new HomeFragment(), bundle);
        } else if (id == R.id.nav_search) {
            setFragment(new SearchFragment(), bundle);
        } else if (id == R.id.nav_list) {

        } else if (id == R.id.nav_report) {
            setFragment(new ReportScreenFragment(), bundle);
        } else if (id == R.id.nav_booth_sync) {
            DBHelper.getInstance(this).deleteBoothData();
            getBoothData();
        } else if (id == R.id.nav_voter_sync) {
            showYesNoDialog(HomeActivity.this, "Alert", " Data Sync required 10 -15 minute time so do not kill App");
        } else if (id == R.id.nav_survey_sync) {
            UserData userData = UserPreferences.getInstance(this).getUserNameInfo();
            surveyReqDataModelList = DBHelper.getInstance(this).getSurveyDataList(userData);
            if (surveyReqDataModelList.size() > 0) {
                surveyDataCount = 0;
                showProgress = true;
                sendSurveyData(surveyReqDataModelList.get(surveyDataCount));
            } else {
                Utils.getInstance().showToast(HomeActivity.this, "No survey data available to sync");
            }

        } else if (id == R.id.nav_lang) {
            setFragment(new SettingsFragment(), bundle);
        } else if (id == R.id.nav_exit) {
            exitFromAPP();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sendSurveyData(SurveyDataRequestModel reqModel) {
        if (showProgress)
            Utils.getInstance().showProgressDialog(this);
        Log.e("SurveyReqModel", reqModel.toString());
        new SurveyDataService().getSurveyData(this, reqModel, new RESTClientResponse() {
            @Override
            public void onSuccess(Object response, int statusCode) {
                if (statusCode == 200) {
                    SurveyDataResponse model = (SurveyDataResponse) response;
                    Log.e("TAG", "VoterData Response:" + model.toString());
                    SurveyStatusModel statusModel = model.getStatus().get(0);
                    if (statusModel.getSTATUS().equalsIgnoreCase("SUCCESS")) {
                        int count = surveyDataCount;
                        count = count + 1;
                        Log.e("SurveyReqModel", "Listsize="+surveyReqDataModelList.size()+"\t Count:" + count);
                        if (surveyReqDataModelList.size() == count ) {
                            showProgress = true;
                            Utils.getInstance().hideProgressDialog();
                            Utils.getInstance().showToast(HomeActivity.this, "Voter Survey Data Saved Successfully.");
                            surveyDataCount = 0;
                        } else {
                            showProgress = false;
                            surveyDataCount = surveyDataCount + 1;
                            sendSurveyData(surveyReqDataModelList.get(surveyDataCount));
                        }
                    } else {
                        Utils.getInstance().showToast(HomeActivity.this, "Please try again later.");
                    }
                }
            }

            @Override
            public void onFailure(Object errorResponse) {
                Utils.getInstance().showToast(HomeActivity.this, "Please try again later.");
            }
        });

    }

    private void exitFromAPP() {
        DialogUtils.showYesNoLogoutConfirmDialogAndFinishActivity(this, "", getString(R.string.close_the_app));
    }

    //Get Voter List
    private void getVoterData() {
        if (showProgress)
            Utils.getInstance().showProgressDialog(this);

        VoterDataRequestModel requestModel = getVoterDataRequestModel(startID, endId);

        new VoterDataService().getVoterData(this, requestModel, new RESTClientResponse() {
            @Override
            public void onSuccess(Object response, int statusCode) {
                if (statusCode == 200) {

                    VoterDataResponseModel model = (VoterDataResponseModel) response;
                    Log.e("TAG", "VoterData Response:" + model.toString());
                    StatusModel statusModel = model.getStatus().get(0);
                    if (statusModel.getErrorcode() == 1) {
                        boolean result = DBHelper.getInstance(HomeActivity.this).insertVoterData(model.getVoterData());
                        if (result) {
//
                            int listSize = model.getVoterData().size();
                            if (listSize < 500) {
                                showProgress = true;
                                Utils.getInstance().hideProgressDialog();
//                                Utils.getInstance().showToast(HomeActivity.this, "Total "+ (startID + listSize)+" voter data Saved Successfully");
                                long totalCount = DBHelper.getInstance(HomeActivity.this).getTotalVoterCount();
                                DialogUtils.showAlertDialog(HomeActivity.this, "Alert", "Total " + totalCount + " voter data Saved Successfully");
                            } else {
                                showProgress = false;
                                if (startID == 0) {//StartID = 0 + 500 + 1 = 501 (First Time)
                                    startID = DBHelper.getInstance(HomeActivity.this).getLastInsertedVoterID();
                                    startID = startID + 1; //501
                                } else // 501+499 = 1000(from 2 time)
                                    startID = startID + listSize;

                                endId = startID + 499; //1000

                                getVoterData();
                            }
                        } else
                            Utils.getInstance().showToast(HomeActivity.this, "Something went wrong, please try again.");
                    } else {
                        Utils.getInstance().showToast(HomeActivity.this, "Please try again later.");
                    }

                }
            }

            @Override
            public void onFailure(Object errorResponse) {

            }
        });

    }

    //Get Booth List
    private void getBoothData() {
        Utils.getInstance().showProgressDialog(this);
        RequestModel requestModel = getRequestModel();

        new BoothDataService().getBoothData(this, requestModel, new RESTClientResponse() {
            @Override
            public void onSuccess(Object response, int statusCode) {
                if (statusCode == 200) {
                    Utils.getInstance().hideProgressDialog();
                    BoothDataResponse model = (BoothDataResponse) response;
                    Log.e("TAG", "BoothData Response:" + model.toString());
                    StatusModel statusModel = model.getStatus().get(0);
                    if (statusModel.getErrorcode() == 1) {
                        //TODO SaveData To Booth
                        boolean result = DBHelper.getInstance(HomeActivity.this).insertBoothData(model.getBoothModelList());
                        if (result)
                            Utils.getInstance().showToast(HomeActivity.this, "Data Saved Successfully");
                        else
                            Utils.getInstance().showToast(HomeActivity.this, "Something went wrong, please try again.");
                    } else {
                        Utils.getInstance().showToast(HomeActivity.this, "Please try again.");
                    }

                }
            }

            @Override
            public void onFailure(Object errorResponse) {

            }
        });

    }

    private RequestModel getRequestModel() {
        RequestModel model = new RequestModel();
        model.setVidhansabhaId(mUserData.getVidhansabhaId());
        model.setLoksabhaId(mUserData.getLoksabhaId());
        model.setWardNumber(mUserData.getWardNumber());
        model.setUserID(mUserData.getUserId());
        Log.e("TAG", "RequestModel:" + model.toString());
        return model;
    }

    private VoterDataRequestModel getVoterDataRequestModel(int start, int end) {
        VoterDataRequestModel model = new VoterDataRequestModel();
        model.setVidhansabhaId(mUserData.getVidhansabhaId());
        model.setLoksabhaId(mUserData.getLoksabhaId());
        model.setWardNumber(mUserData.getWardNumber());
        model.setUserID(mUserData.getUserId());
        model.setStartID("" + start);
        model.setEndID("" + end);
        Log.e("TAG", "RequestModel:" + model.toString());
        return model;
    }

    public void setFragment(Fragment fragment, Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.replace(R.id.fl_container, fragment);
        fragment.setArguments(bundle);
        fragmentTransaction.commit();
    }

    public UserData getUserData() {
        return mUserData;
    }

    public void showYesNoDialog(final Activity mActivity, String title, String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mActivity);
        if (title != null)
            alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                callWebServiceFromMenu(mActivity);
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

    private void callWebServiceFromMenu(Activity mActivity) {
        int result = DBHelper.getInstance(mActivity).deleteVoterData();
        Log.e("result", "Result: " + result);
        getVoterData();
    }

    private void checkPermissionForWrite() {
        // Here, mContext is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

}
