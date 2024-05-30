package com.mastercoding.gp.cleaningworker.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.databinding.FragmentCleaningWorkerNotificationDetailsBinding;
import com.mastercoding.gp.parkingworker.ui.ParkingWorkerNotificationDetailsFragmentArgs;
import com.mastercoding.gp.shareddata.data.Notification;
import com.mastercoding.gp.shareddata.viewmodel.GetNotificationByIdViewModel;


public class CleaningWorkerNotificationDetailsFragment extends Fragment {

    FragmentCleaningWorkerNotificationDetailsBinding binding;

    GetNotificationByIdViewModel getNotificationByIdViewModel;

    SessionSharedPreferences sessionSharedPreferences;

    String userName, password, base, authHeader;

    int notificationId;

    public CleaningWorkerNotificationDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCleaningWorkerNotificationDetailsBinding.inflate(inflater, container, false);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        notificationId = CleaningWorkerNotificationDetailsFragmentArgs.fromBundle(getArguments()).getNotificationId();

        getNotificationByIdViewModel = new ViewModelProvider(this).get(GetNotificationByIdViewModel.class);

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        getNotificationByIdViewModel.getNotificationById(notificationId, authHeader).observe(getViewLifecycleOwner(), new Observer<Notification>() {
            @Override
            public void onChanged(Notification notification) {
                binding.cleaningWorkerNotificationDetailsTitle.setText(notification.getNotificationTitle());
                binding.cleaningWorkerNotificationDetailsContent.setText(notification.getNotificationContent());
            }
        });

        binding.cleaningWorkerNotificationDetailsBackBtn.setOnClickListener( view -> {
            Navigation.findNavController(view).popBackStack();
        });

        return binding.getRoot();
    }
}