package com.mastercoding.gp.cleaningworker.ui;

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

import com.mastercoding.gp.CustomDialogFragment;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.databinding.FragmentCleaningWorkerNotificationBinding;
import com.mastercoding.gp.shareddata.adapter.NotificationsAdapter;
import com.mastercoding.gp.shareddata.data.Notification;
import com.mastercoding.gp.shareddata.viewmodel.DeleteAllNotificationsByUserIdViewModel;
import com.mastercoding.gp.shareddata.viewmodel.DeleteNotificationByIdViewModel;
import com.mastercoding.gp.shareddata.viewmodel.GetAllNotificationByUserIdViewModel;

import java.util.List;


public class CleaningWorkerNotificationFragment extends Fragment implements NotificationsAdapter.OnNotificationItemClickListener {

   FragmentCleaningWorkerNotificationBinding binding;

   GetAllNotificationByUserIdViewModel getAllNotificationByUserIdViewModel;

   DeleteNotificationByIdViewModel deleteNotificationByIdViewModel;

   DeleteAllNotificationsByUserIdViewModel deleteAllNotificationsByUserIdViewModel;

   NotificationsAdapter notificationsAdapter;

   SessionSharedPreferences sessionSharedPreferences;

   CustomDialogFragment deleteDialogFragment, deleteAllDialogFragment;

   String userName, password, base, authHeader;

    public CleaningWorkerNotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCleaningWorkerNotificationBinding.inflate(inflater, container, false);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        getAllNotificationByUserIdViewModel = new ViewModelProvider(this).get(GetAllNotificationByUserIdViewModel.class);

        deleteNotificationByIdViewModel = new ViewModelProvider(this).get(DeleteNotificationByIdViewModel.class);

        deleteAllNotificationsByUserIdViewModel = new ViewModelProvider(this).get(DeleteAllNotificationsByUserIdViewModel.class);

        deleteDialogFragment = new CustomDialogFragment();
        deleteAllDialogFragment = new CustomDialogFragment();

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        getAllNotificationByUserIdViewModel.getAllNotificationByUserId(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notifications) {
                notificationsAdapter = new NotificationsAdapter(notifications);
                notificationsAdapter.setOnNotificationItemClickListener(CleaningWorkerNotificationFragment.this);
                binding.cleaningWorkerNotificationsRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false));
                binding.cleaningWorkerNotificationsRecyclerView.setAdapter(notificationsAdapter);
            }
        });


        deleteNotificationByIdViewModel.deleteNotificationStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (deleteDialogFragment != null && deleteDialogFragment.isAdded()) {
                    deleteDialogFragment.updateMessage(message);
                }
            }
        });


        binding.cleaningWorkerNotificationsClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAllDialogFragment.show(getChildFragmentManager(), "Delete All Dialog");
                deleteAllNotificationsByUserIdViewModel.deleteAllNotificationsByUserId(sessionSharedPreferences.getID(), authHeader);
                refreshNotification();
            }
        });

        deleteAllNotificationsByUserIdViewModel.deleteAllNotificationsStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (deleteAllDialogFragment != null && deleteAllDialogFragment.isAdded()) {
                    deleteAllDialogFragment.updateMessage(message);
                }
            }
        });

        binding.cleaningWorkerNotificationsBackBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).popBackStack();
        });

        return binding.getRoot();
    }

    private void navigateToParkingWorkerNotificationDetailsFragment(int notificationId) {
        // Use Safe Args to pass data to another fragment
        CleaningWorkerNotificationFragmentDirections.ActionCleaningWorkerNotificationFragmentToCleaningWorkerNotificationDetailsFragment action =
                CleaningWorkerNotificationFragmentDirections.actionCleaningWorkerNotificationFragmentToCleaningWorkerNotificationDetailsFragment(notificationId);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void OnNotificationDeleteItemClick(int notificationId) {
        deleteDialogFragment.show(getChildFragmentManager(), "Delete Dialog");
        deleteNotificationByIdViewModel.deleteNotificationById(notificationId, authHeader);
        refreshNotification();
    }

    @Override
    public void OnNotificationItemClick(int notificationId) {
        navigateToParkingWorkerNotificationDetailsFragment(notificationId);
    }

    public void refreshNotification(){
        getAllNotificationByUserIdViewModel.getAllNotificationByUserId(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notifications) {
                notificationsAdapter = new NotificationsAdapter(notifications);
                notificationsAdapter.setOnNotificationItemClickListener(CleaningWorkerNotificationFragment.this);
                binding.cleaningWorkerNotificationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                binding.cleaningWorkerNotificationsRecyclerView.setAdapter(notificationsAdapter);
            }
        });
    }
}