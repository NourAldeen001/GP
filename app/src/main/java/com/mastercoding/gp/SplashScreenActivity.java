package com.mastercoding.gp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.mastercoding.gp.auth.login.ui.LoginViewModel;
import com.mastercoding.gp.cleaningworker.CleaningWorkerActivity;
import com.mastercoding.gp.customer.CustomerActivity;
import com.mastercoding.gp.databinding.ActivitySplashScreenBinding;
import com.mastercoding.gp.maintenanceworker.MaintenanceWorkerActivity;
import com.mastercoding.gp.parkingworker.ParkingWorkerActivity;

public class SplashScreenActivity extends AppCompatActivity {

    public static int SPLASH_TIMER = 2000;

    ActivitySplashScreenBinding binding;

    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToCorrectActivity();
            }
        }, SPLASH_TIMER);


    }

    private void navigateToCorrectActivity() {
        loginViewModel.getIsLoggedIn().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean check) {
                if(check){
                    SessionSharedPreferences sessionSharedPreferences = new SessionSharedPreferences(getApplicationContext());
                    if(sessionSharedPreferences.getRole().equals("CUSTOMER")){
                        startActivity(new Intent(getApplicationContext(), CustomerActivity.class));
                        finish();
                    }
                    else if(sessionSharedPreferences.getRole().equals("PARKING_WORKER")) {
                        startActivity(new Intent(getApplicationContext(), ParkingWorkerActivity.class));
                        finish();
                    }
                    else if(sessionSharedPreferences.getRole().equals("CLEANING_WORKER")) {
                        startActivity(new Intent(getApplicationContext(), CleaningWorkerActivity.class));
                        finish();
                    }
                    else if(sessionSharedPreferences.getRole().equals("MAINTENANCE_WORKER")) {
                        startActivity(new Intent(getApplicationContext(), MaintenanceWorkerActivity.class));
                        finish();
                    }
                }
                else {
                    startActivity(new Intent(getApplicationContext(), OnBoardingActivity.class));
                    finish();
                }
            }
        });
    }
}