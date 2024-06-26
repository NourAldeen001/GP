package com.mastercoding.gp.parkingworker.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.databinding.FragmentParkingWorkerNotificationDetailsBinding;
import com.mastercoding.gp.shareddata.data.Notification;
import com.mastercoding.gp.shareddata.viewmodel.GetNotificationByIdViewModel;

public class ParkingWorkerNotificationDetailsFragment extends Fragment {

    FragmentParkingWorkerNotificationDetailsBinding binding;

    GetNotificationByIdViewModel getNotificationByIdViewModel;

    SessionSharedPreferences sessionSharedPreferences;

    String userName, password, base, authHeader;

    int notificationId;

    public ParkingWorkerNotificationDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentParkingWorkerNotificationDetailsBinding.inflate(inflater, container, false);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        notificationId = ParkingWorkerNotificationDetailsFragmentArgs.fromBundle(getArguments()).getNotificationId();

        getNotificationByIdViewModel = new ViewModelProvider(this).get(GetNotificationByIdViewModel.class);

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        getNotificationByIdViewModel.getNotificationById(notificationId, authHeader).observe(getViewLifecycleOwner(), new Observer<Notification>() {
            @Override
            public void onChanged(Notification notification) {
                binding.parkingWorkerNotificationDetailsTitle.setText(notification.getNotificationTitle());
                binding.parkingWorkerNotificationDetailsContent.setText(notification.getNotificationContent());
            }
        });

        binding.parkingWorkerNotificationDetailsBackBtn.setOnClickListener( view -> {
            Navigation.findNavController(view).popBackStack();
        });

        return binding.getRoot();
    }
}