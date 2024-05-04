package com.mastercoding.gp.auth.login.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.cleaningworker.CleaningWorkerActivity;
import com.mastercoding.gp.CustomDialogFragment;
import com.mastercoding.gp.customer.CustomerActivity;
import com.mastercoding.gp.maintenanceworker.MaintenanceWorkerActivity;
import com.mastercoding.gp.parkingworker.ParkingWorkerActivity;
import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.auth.login.data.LoginResponse;
import com.mastercoding.gp.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    private LoginViewModel loginViewModel;

    private CustomDialogFragment dialogFragment;

    SessionSharedPreferences sessionSharedPreferences;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create Dialog
        dialogFragment = new CustomDialogFragment();

        loginViewModel.getLoginMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (dialogFragment != null && dialogFragment.isAdded()) {
                    dialogFragment.updateMessage(message);
                }
            }
        });

        loginViewModel.getLoginResponse().observe(getViewLifecycleOwner(), new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                Log.i("LOOGGGIIIINNRRRR", loginResponse.getUserRole());
                if(loginResponse != null) {
                    handleSessionLoginSuccess(loginResponse.getId(), loginResponse.getUserName(),
                            loginResponse.getPassword(), loginResponse.getUserRole());
                }
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = binding.loginUsernameEditTxt.getText().toString();
                String password = binding.loginPassEditTxt.getText().toString();
                dialogFragment.show(getChildFragmentManager(), "Custom Dialog");
                loginViewModel.login(userName, password);
                loginViewModel.getIsLoggedIn().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isLoggedIn) {
                        if(isLoggedIn){
                            sessionSharedPreferences = new SessionSharedPreferences(getContext());
                            Log.i("NNNNNOOOUUR", sessionSharedPreferences.getRole());
                            //Toast.makeText(getContext(), sessionSharedPreferences.getRole(), Toast.LENGTH_SHORT).show();
                            if(sessionSharedPreferences.getRole().equals("CUSTOMER")){
                                startActivity(new Intent(getContext(), CustomerActivity.class));
                                getActivity().finish();
                            }
                            else if(sessionSharedPreferences.getRole().equals("PARKING_WORKER")) {
                                startActivity(new Intent(getContext(), ParkingWorkerActivity.class));
                                getActivity().finish();
                            }
                            else if(sessionSharedPreferences.getRole().equals("CLEANING_WORKER")) {
                                startActivity(new Intent(getContext(), CleaningWorkerActivity.class));
                                getActivity().finish();
                            }
                            else if(sessionSharedPreferences.getRole().equals("MAINTENANCE_WORKER")) {
                                startActivity(new Intent(getContext(), MaintenanceWorkerActivity.class));
                                getActivity().finish();
                            }
                        }
                    }
                });
            }
        });

        binding.loginToSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signupFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void handleSessionLoginSuccess(int id, String userName, String password, String role) {
        SessionSharedPreferences sessionSharedPreferences = new SessionSharedPreferences(getContext());
        sessionSharedPreferences.saveUserDetails(id, userName, password, role);
    }
}