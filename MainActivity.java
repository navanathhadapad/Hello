package com.tagcor.tagcorproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.tagcor.tagcorproject.Models.ImageModul;
import com.tagcor.tagcorproject.Models.ImageModul1;
import com.tagcor.tagcorproject.Models.LoginModel;
import com.tagcor.tagcorproject.Servers.ServiceOperations;
import com.tagcor.tagcorproject.net.RetrofitAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Context mContext;
    private SessionManager mSessionManager;

    EditText umail, upass;
    Button login;

    GridView listView;
    // private View parentView;
    List<ImageModul1> contactList;

    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide title bar code here---
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().hide();

        contactList = new ArrayList<>();

        listView = (GridView) findViewById(R.id.subcatlist);

        umail = findViewById(R.id.activity_main_username_et);
        upass = findViewById(R.id.activity_main_password_et);

        login = findViewById(R.id.activity_main_submit_bt);

        mContext = this;
        mSessionManager = new SessionManager(mContext);
        checkUserSessionExistsAndLogin();
        // helperService = new RetrofitHelperService();
        login.setOnClickListener(this);

        getUserDetails();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void checkUserSessionExistsAndLogin() {
        if (mSessionManager.isUserExists() != null)
            naviageProfilePage(Integer.parseInt(mSessionManager.isUserExists()));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_submit_bt:
                onSubmitButtonClick();
                break;
        }
    }

    private void onSubmitButtonClick() {
        String email = umail.getText().toString();
        String password = upass.getText().toString();

        ServiceOperations serviceOperations = RetrofitAdapter.getBaseClassService();
        Call<LoginModel> loginModelCall = serviceOperations.login(email, password);
        loginModelCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response != null && response.body() != null && response.body().getStatus()) {
                    naviageProfilePage(Integer.parseInt(response.body().getSid()));
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Unknown issue found ", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "onFailure: " + t.getMessage());
            }
        });
    }

    private void naviageProfilePage(int sid) {
        Intent profileIntent = new Intent(mContext, WelcomeActivity.class);
        profileIntent.putExtra("sid", sid);
        mSessionManager.createUserSession(String.valueOf(sid));
        startActivity(profileIntent);
        finish();
    }


    private void getUserDetails() {

        final ProgressDialog dialog;

        dialog = new ProgressDialog(MainActivity.this);
        //dialog.setTitle(getString(R.string.string_getting_json_title));
        //dialog.setMessage(getString(R.string.string_getting_json_message));
        dialog.setTitle("Navanath");
        dialog.setMessage("Please wait Loading...");
        dialog.show();

        //Creating an object of our api interface
        ServiceOperations serviceOperations = RetrofitAdapter.getBaseClassService();

        Call<ImageModul> call = serviceOperations.getImageData();

        /**
         * Enqueue Callback will be call when get response...
         */
        call.enqueue(new Callback<ImageModul>() {
            @Override
            public void onResponse(Call<ImageModul> call, Response<ImageModul> response) {

                dialog.dismiss();

                if (response.isSuccessful()) {

                    contactList = response.body().getUsers();

                    ImageAdapter adapter = new ImageAdapter(MainActivity.this, contactList);
                    listView.setAdapter(adapter);

                } else {
                    //Snackbar.make(parentView, R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ImageModul> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }
}
