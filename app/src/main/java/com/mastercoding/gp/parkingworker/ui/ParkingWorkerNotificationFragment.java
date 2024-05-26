package com.mastercoding.gp.parkingworker.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.databinding.FragmentParkingWorkerNotificationBinding;
import com.mastercoding.gp.shareddata.Notification;
import com.mastercoding.gp.shareddata.adapter.NotificationsAdapter;
import com.mastercoding.gp.shareddata.viewmodel.GetAllNotificationByUserIdViewModel;

import java.util.List;


public class ParkingWorkerNotificationFragment extends Fragment implements NotificationsAdapter.OnNotificationItemClickListener {

    FragmentParkingWorkerNotificationBinding binding;

    GetAllNotificationByUserIdViewModel getAllNotificationByUserIdViewModel;

    NotificationsAdapter notificationsAdapter;

    SessionSharedPreferences sessionSharedPreferences;

    String userName, password, base, authHeader;

    public ParkingWorkerNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentParkingWorkerNotificationBinding.inflate(inflater, container, false);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        getAllNotificationByUserIdViewModel = new ViewModelProvider(this).get(GetAllNotificationByUserIdViewModel.class);

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        getAllNotificationByUserIdViewModel.getAllNotificationByUserId(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notifications) {
                notificationsAdapter = new NotificationsAdapter(notifications);
                notificationsAdapter.setOnNotificationItemClickListener(ParkingWorkerNotificationFragment.this);
                binding.parkingWorkerNotificationsRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false));
                binding.parkingWorkerNotificationsRecyclerView.setAdapter(notificationsAdapter);
            }
        });


        binding.parkingWorkerNotificationsClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

        binding.parkingWorkerNotificationsBackBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        return binding.getRoot();
    }

    @Override
    public void OnNotificationDeleteItemClick(int notificationId) {

    }

    @Override
    public void OnNotificationItemClick(int notificationId) {

    }
}