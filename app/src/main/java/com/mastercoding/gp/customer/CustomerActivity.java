package com.mastercoding.gp.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.auth.AuthActivity;
import com.mastercoding.gp.auth.login.ui.LoginViewModel;
import com.mastercoding.gp.customer.data.CustomerData;
import com.mastercoding.gp.customer.ui.viewmodel.GetCustomerByIdViewModel;
import com.mastercoding.gp.databinding.ActivityCustomerBinding;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    SessionSharedPreferences sessionSharedPreferences;
    LoginViewModel loginViewModel;
    GetCustomerByIdViewModel customerByIdViewModel;
    ActivityCustomerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        customerByIdViewModel = new ViewModelProvider(this).get(GetCustomerByIdViewModel.class);
        sessionSharedPreferences = new SessionSharedPreferences(getApplicationContext());

        // Important Data to call Authorization APIs
        String userName = sessionSharedPreferences.getUsername();
        String password = sessionSharedPreferences.getPass();

        String base = userName + ":" + password;

        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);



        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        binding.toolbar.setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_hamburger);

        binding.navigationDrawer.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar,
                                                                                R.string.open_nav, R.string.close_nav);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        View headerView = binding.navigationDrawer.getHeaderView(0);
        ImageView headerProfileImage = headerView.findViewById(R.id.circleImageView);
        TextView headerText = headerView.findViewById(R.id.fullNameProfile);

        customerByIdViewModel.getCustomerById(sessionSharedPreferences.getID(), authHeader).observe(this, new Observer<CustomerData>() {
            @Override
            public void onChanged(CustomerData customerData) {
                if(customerData != null){
                    Bitmap bitmapImg = convertBase64ToBitmap(customerData.getImage());
                    headerProfileImage.setImageBitmap(bitmapImg);
                    Log.i("Drawer", "Success");
                }
                headerText.setText(String.format("%s %s", customerData.getFirstName(), customerData.getLastName()));
            }
        });


        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                NavController navController = Navigation.findNavController(CustomerActivity.this, R.id.nav_host_fragment_customer);
                if(itemId == R.id.bottom_home){
                    if(navController.getCurrentDestination().getId() != R.id.customerHomeFragment){
                        navController.navigate(R.id.customerHomeFragment);
                    }
                    return true;
                }
                else if(itemId == R.id.bottom_statusMenu){
                    if(navController.getCurrentDestination().getId() != R.id.customerOrderStatusFragment){
                        navController.navigate(R.id.customerOrderStatusFragment);
                    }
                    return true;
                }
                else if(itemId == R.id.bottom_maps){
                    return true;
                }
                return false;
            }
        });

//        logoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sessionSharedPreferences = new SessionSharedPreferences(getApplicationContext());
//                sessionSharedPreferences.clearUserDataSession();
//                loginViewModel.logout();
//                startActivity(new Intent(getApplicationContext(), AuthActivity.class));
//                finish();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.toolbar_profile);
        View view = menuItem.getActionView();

        CircleImageView profileImage = view.findViewById(R.id.toolbar_profile_image);
        TextView profileName = view.findViewById(R.id.toolbar_profile_name);

        // Important Data to call Authorization APIs
        String userName = sessionSharedPreferences.getUsername();
        String password = sessionSharedPreferences.getPass();

        String base = userName + ":" + password;

        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);


        customerByIdViewModel.getCustomerById(sessionSharedPreferences.getID(), authHeader).observe(this, new Observer<CustomerData>() {
            @Override
            public void onChanged(CustomerData customerData) {
                if(customerData.getImage() != null){
                    Bitmap imgBitmap = convertBase64ToBitmap(customerData.getImage());
                    profileImage.setImageBitmap(imgBitmap);
                    Log.i("Toolbar Profile", "Success");
                }
                profileName.setText(String.format("%s %s", customerData.getFirstName(), customerData.getLastName()));

            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CustomerActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(CustomerActivity.this, R.id.nav_host_fragment_customer);
        if(item.getItemId() == R.id.toolbar_notification){
            if(navController.getCurrentDestination().getId() != R.id.customerNotificationFragment){
                navController.navigate(R.id.customerNotificationFragment);
            }
        }
        else if(item.getItemId() == R.id.toolbar_profile){
            if(navController.getCurrentDestination().getId() != R.id.customerProfileFragment){
                navController.navigate(R.id.customerProfileFragment);
            }
        }
        else if(item.getItemId() == R.id.toolbar_cart) {
            if(navController.getCurrentDestination().getId() != R.id.cartFragment){
                navController.navigate(R.id.cartFragment);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        NavController navController = Navigation.findNavController(CustomerActivity.this, R.id.nav_host_fragment_customer);
        if(itemId == R.id.nav_profile){
            if(navController.getCurrentDestination().getId() != R.id.customerProfileFragment){
                navController.navigate(R.id.customerProfileFragment);
            }
        }
        else if(itemId == R.id.nav_terms){
            if(navController.getCurrentDestination().getId() != R.id.customerTermsFragment){
                navController.navigate(R.id.customerTermsFragment);
            }
        }
        else if(itemId == R.id.nav_logout){
            sessionSharedPreferences.clearUserDataSession();
            loginViewModel.logout();
            startActivity(new Intent(getApplicationContext(), AuthActivity.class));
            finish();
        }
        
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}