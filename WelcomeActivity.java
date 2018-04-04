package com.tagcor.tagcorproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tagcor.tagcorproject.B2B.B2bMainActivity;
import com.tagcor.tagcorproject.C2C.C2CMainActivity;
import com.tagcor.tagcorproject.Models.ProfileModel;
import com.tagcor.tagcorproject.Models.UserModel;
import com.tagcor.tagcorproject.Servers.ServiceOperations;
import com.tagcor.tagcorproject.TJobs.JobsActivity;
import com.tagcor.tagcorproject.net.RetrofitAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private Context mContext;
    private SessionManager mSessionManager;
    private int mSid;

   /* @BindView(R.id.profile_fn_tv)
    TextView mTVFirstName;
    @BindView(R.id.profile_ln_tv)
    TextView mTVLastName;

    @BindView(R.id.profile_logout_bt)
    Button mBTLogout;*/

    TextView mTVFirstName, mTVLastName, profession;
   // Button mBTLogout;
    ImageView user_img, b2bpro, c2cpro, jobs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTVFirstName = (TextView) findViewById(R.id.profile_fn_tv);
        mTVLastName = (TextView) findViewById(R.id.profile_ln_tv);
        profession = (TextView) findViewById(R.id.profession);
        //mBTLogout = findViewById(R.id.profile_logout_bt);
        user_img = (ImageView) findViewById(R.id.user_img);
        //ButterKnife.bind(this);
        mContext = this;
        // helperService = new RetrofitHelperService();
        mSessionManager = new SessionManager(mContext);
        getArgument();
        getUserDetails();
       // mBTLogout.setOnClickListener(this);

        b2bpro = (ImageView)findViewById(R.id.b2b);
        b2bpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeActivity.this, B2bMainActivity.class);
                startActivity(i);
            }
        });

        c2cpro = (ImageView)findViewById(R.id.c2c);
        c2cpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeActivity.this, C2CMainActivity.class);
                startActivity(i);
            }
        });

        jobs = (ImageView)findViewById(R.id.jobs);
        jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeActivity.this, JobsActivity.class);
                startActivity(i);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeaderView =  navigationView.getHeaderView(0);

       // View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_welcome);
        mTVFirstName = (TextView) navHeaderView.findViewById(R.id.profile_fn_tv);
        mTVLastName = (TextView) navHeaderView.findViewById(R.id.profile_ln_tv);
        //mBTLogout = findViewById(R.id.profile_logout_bt);
        user_img = (ImageView) navHeaderView.findViewById(R.id.user_img);
        profession = (TextView) navHeaderView.findViewById(R.id.profession);
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
        getMenuInflater().inflate(R.menu.welcome, menu);
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

            mSessionManager.createUserSession(null);
            Intent intent = new Intent(mContext, MainActivity.class );
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getUserDetails() {
        //ServiceOperations serviceOperations =  helperService.getBaseClassService();
        ServiceOperations serviceOperations =  RetrofitAdapter.getBaseClassService();
        Call<ProfileModel> profileModelCall = serviceOperations.getUserProfile(String.valueOf(mSid));
        profileModelCall.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                if(response.body() != null && response.body()!= null )
                    assignValuesToUI(response.body().getUser());
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Toast.makeText(mContext, "Unknown issue found ", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "onFailure: "+t.getMessage());
            }
        });
    }

    private void assignValuesToUI(UserModel user) {
        mTVFirstName.setText(user.getFname());
        mTVLastName.setText(user.getLname());
        profession.setText(user.getJobProfileTitle());

       // String urls = "http://192.168.0.100/Tagcor/product_images/Main_cat_images/";
        String url = user.getPersonalProfile();
        Glide.with(WelcomeActivity.this).load(url).centerCrop().into(user_img);
    }

    private void getArgument() {
        if (getIntent() != null) {
            mSid = getIntent().getIntExtra("sid",0);
        }

    }



  /*  @Override
    public void onClick(View v) {
        //click logout
        switch (v.getId()) {
            case R.id.profile_logout_bt:
                makeUserLogout();
                break;
        }
    }

    private void makeUserLogout() {
        mSessionManager.createUserSession(null);
        Intent intent = new Intent(mContext, MainActivity.class );
        startActivity(intent);
        finish();
    }*/

}
