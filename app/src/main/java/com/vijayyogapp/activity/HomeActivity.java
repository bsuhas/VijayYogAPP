package com.vijayyogapp.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import com.vijayyogapp.fragments.VoterDetailsFragment;
import com.vijayyogapp.interfaces.RESTClientResponse;
import com.vijayyogapp.models.BoothDataResponse;
import com.vijayyogapp.models.RequestModel;
import com.vijayyogapp.models.StatusModel;
import com.vijayyogapp.models.UserData;
import com.vijayyogapp.models.VoterDataResponseModel;
import com.vijayyogapp.utils.CircularImageView;
import com.vijayyogapp.utils.DialogUtils;
import com.vijayyogapp.utils.UserPreferences;
import com.vijayyogapp.utils.Utils;
import com.vijayyogapp.webservices.BoothDataService;
import com.vijayyogapp.webservices.VoterDataService;

import java.io.File;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private UserData mUserData;
    private SystemBarTintManager mTintManager;
    private CircularImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mUserData = UserPreferences.getInstance(this).getUserNameInfo();
        initToolBar();
        Bundle bundle = new Bundle();
        setFragment(new HomeFragment(), bundle);

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
                Uri uri = Uri.fromFile(file);
                Picasso.with(HomeActivity.this).load(uri).into(profileImage);
//                profileImage.setImageBitmap(Utils.getInstance().getOriententionBitmap(profileImg));
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
            getBoothData();
        } else if (id == R.id.nav_voter_sync) {
            getVoterData();
        } else if (id == R.id.nav_survey_sync) {

        } else if (id == R.id.nav_lang) {
            setFragment(new SettingsFragment(), bundle);
        } else if (id == R.id.nav_exit) {
            exitFromAPP();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void exitFromAPP() {
        DialogUtils.showYesNoLogoutConfirmDialogAndFinishActivity(this, "", getString(R.string.close_the_app));
    }

    //Get Voter List
    private void getVoterData() {
        Utils.getInstance().showProgressDialog(this);
        RequestModel requestModel = getRequestModel();

        new VoterDataService().getVoterData(this, requestModel, new RESTClientResponse() {
            @Override
            public void onSuccess(Object response, int statusCode) {
                if (statusCode == 200) {
                    Utils.getInstance().hideProgressDialog();
                    VoterDataResponseModel model = (VoterDataResponseModel) response;
                    Log.e("TAG", "VoterData Response:" + model.toString());
                    StatusModel statusModel = model.getStatus().get(0);
                    if (statusModel.getErrorcode() == 1) {
                        boolean result = DBHelper.getInstance(HomeActivity.this).insertVoterData(model.getVoterData());
                        if (result)
                        Utils.getInstance().showToast(HomeActivity.this, "Data Saved Successfully");
                        else
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

}
