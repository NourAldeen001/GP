package com.mastercoding.gp.cleaningworker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.auth.AuthActivity;
import com.mastercoding.gp.auth.login.ui.LoginViewModel;
import com.mastercoding.gp.databinding.ActivityCleaningWorkerBinding;
import com.mastercoding.gp.parkingworker.ParkingWorkerActivity;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerData;
import com.mastercoding.gp.parkingworker.ui.viewmodel.GetParkingWorkerByIdViewModel;
import com.mastercoding.gp.shareddata.viewmodel.GetCountUnOpenedByUserIdViewModel;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class CleaningWorkerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SessionSharedPreferences sessionSharedPreferences;

    LoginViewModel loginViewModel;

    GetParkingWorkerByIdViewModel parkingWorkerByIdViewModel;

    GetCountUnOpenedByUserIdViewModel getCountUnOpenedByUserIdViewModel;

    ActivityCleaningWorkerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cleaning_worker);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        parkingWorkerByIdViewModel = new ViewModelProvider(this).get(GetParkingWorkerByIdViewModel.class);
        getCountUnOpenedByUserIdViewModel = new ViewModelProvider(this).get(GetCountUnOpenedByUserIdViewModel.class);
        sessionSharedPreferences = new SessionSharedPreferences(getApplicationContext());

        String userName = sessionSharedPreferences.getUsername();
        String password = sessionSharedPreferences.getPass();

        String base = userName + ":" + password;

        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        setSupportActionBar(binding.cleaningWorkerToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        binding.cleaningWorkerToolbar.setTitle("");

        binding.navigationDrawer.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.cleaningWorkerDrawerLayout, binding.cleaningWorkerToolbar,
                R.string.open_nav, R.string.close_nav);
        binding.cleaningWorkerDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = binding.navigationDrawer.getHeaderView(0);
        ImageView headerProfileImage = headerView.findViewById(R.id.circleImageView);
        TextView headerText = headerView.findViewById(R.id.fullNameProfile);

        parkingWorkerByIdViewModel.getParkingWorkerById(sessionSharedPreferences.getID(), authHeader).observe(this, new Observer<ParkingWorkerData>() {
            @Override
            public void onChanged(ParkingWorkerData parkingWorkerData) {
                if(parkingWorkerData.getImage() != null){
                    Bitmap bitmapImg = convertBase64ToBitmap(parkingWorkerData.getImage());
                    headerProfileImage.setImageBitmap(bitmapImg);
                    Log.i("Drawer", "Success");
                }
                headerText.setText(String.format("%s %s", parkingWorkerData.getFirstName(), parkingWorkerData.getLastName()));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.worker_toolbar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.worker_toolbar_profile);
        View view = menuItem.getActionView();

        CircleImageView profileImage = view.findViewById(R.id.toolbar_profile_image);
        TextView profileName = view.findViewById(R.id.toolbar_profile_name);

        // Important Data to call Authorization APIs
        String userName = sessionSharedPreferences.getUsername();
        String password = sessionSharedPreferences.getPass();

        String base = userName + ":" + password;

        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);


        parkingWorkerByIdViewModel.getParkingWorkerById(sessionSharedPreferences.getID(), authHeader).observe(this, new Observer<ParkingWorkerData>() {
            @Override
            public void onChanged(ParkingWorkerData parkingWorkerData) {
                if(parkingWorkerData.getImage() != null){
                    Bitmap imgBitmap = convertBase64ToBitmap(parkingWorkerData.getImage());
                    profileImage.setImageBitmap(imgBitmap);
                    Log.i("Toolbar Profile", "Success");
                }
                profileName.setText(String.format("%s %s", parkingWorkerData.getFirstName(), parkingWorkerData.getLastName()));

            }
        });

        profileImage.setOnClickListener(view1 -> {
            navigateToProfile();
        });

        MenuItem notificationMenuItem = menu.findItem(R.id.worker_toolbar_notification);
        View badgeLayout = notificationMenuItem.getActionView();

        ImageView notificationImgBtn = badgeLayout.findViewById(R.id.toolbar_notification_image_btn);
        TextView badge = badgeLayout.findViewById(R.id.toolbar_notification_badge);

        getCountUnOpenedByUserIdViewModel.getCountUnopenedByUserId(sessionSharedPreferences.getID(), authHeader).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer counter) {
                if(counter > 0){
                    Log.i("Counter Notifications", "counter : " + String.valueOf(counter));
                    badge.setText(counter.toString());
                    badge.setVisibility(View.VISIBLE);
                }
                else {
                    Log.i("Counter Notifications", "counter : " + String.valueOf(counter));
                    badge.setVisibility(View.GONE);
                }
            }
        });

        notificationImgBtn.setOnClickListener(view1 -> {
            navigateToNotifications();
        });


        return super.onCreateOptionsMenu(menu);
    }

    private void navigateToProfile() {
        NavController navController = Navigation.findNavController(CleaningWorkerActivity.this, R.id.nav_host_fragment_cleaning_worker);
        if (navController.getCurrentDestination().getId() != R.id.cleaningWorkerProfileFragment) {
            navController.navigate(R.id.cleaningWorkerProfileFragment);
        }
    }

    private void navigateToNotifications() {
        NavController navController = Navigation.findNavController(CleaningWorkerActivity.this, R.id.nav_host_fragment_cleaning_worker);
        if (navController.getCurrentDestination().getId() != R.id.cleaningWorkerNotificationFragment) {
            navController.navigate(R.id.cleaningWorkerNotificationFragment);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        NavController navController = Navigation.findNavController(CleaningWorkerActivity.this, R.id.nav_host_fragment_cleaning_worker);
        if(itemId == R.id.nav_profile){
            if(navController.getCurrentDestination().getId() != R.id.cleaningWorkerProfileFragment){
                navController.navigate(R.id.cleaningWorkerProfileFragment);
            }
        }
        else if(itemId == R.id.nav_active_status){
            if(navController.getCurrentDestination().getId() != R.id.cleaningWorkerAvailablityFragment){
                navController.navigate(R.id.cleaningWorkerAvailablityFragment);
            }
        }
        else if(itemId == R.id.nav_terms){
            if(navController.getCurrentDestination().getId() != R.id.cleaningWorkerTermsFragment){
                navController.navigate(R.id.cleaningWorkerTermsFragment);
            }
        }
        else if(itemId == R.id.nav_logout){
            sessionSharedPreferences.clearUserDataSession();
            loginViewModel.logout();
            startActivity(new Intent(getApplicationContext(), AuthActivity.class));
            finish();
        }

        binding.cleaningWorkerDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}