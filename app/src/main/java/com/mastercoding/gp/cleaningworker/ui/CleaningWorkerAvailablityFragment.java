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
import android.widget.CompoundButton;

import com.mastercoding.gp.CustomDialogFragment;
import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.databinding.FragmentCleaningWorkerAvailablityBinding;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerData;
import com.mastercoding.gp.parkingworker.ui.viewmodel.GetParkingWorkerByIdViewModel;
import com.mastercoding.gp.parkingworker.ui.viewmodel.ParkingWorkerChangeWorkerStatusViewModel;


public class CleaningWorkerAvailablityFragment extends Fragment {

    FragmentCleaningWorkerAvailablityBinding binding;

    GetParkingWorkerByIdViewModel parkingWorkerByIdViewModel;

    ParkingWorkerChangeWorkerStatusViewModel parkingWorkerChangeWorkerStatusViewModel;

    SessionSharedPreferences sessionSharedPreferences;

    String userName, password, base, authHeader;

    public CleaningWorkerAvailablityFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCleaningWorkerAvailablityBinding.inflate(inflater, container, false);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        parkingWorkerByIdViewModel = new ViewModelProvider(this).get(GetParkingWorkerByIdViewModel.class);

        parkingWorkerChangeWorkerStatusViewModel = new ViewModelProvider(this).get(ParkingWorkerChangeWorkerStatusViewModel.class);

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        parkingWorkerByIdViewModel.getParkingWorkerById(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<ParkingWorkerData>() {
            @Override
            public void onChanged(ParkingWorkerData parkingWorkerData) {
                switch (parkingWorkerData.getWorkerStatus()) {
                    case "AVAILABLE":
                        binding.cleaningWorkerAvailabilitySwitch.setChecked(true);
                        break;
                    case "UN_AVAILABLE":
                        binding.cleaningWorkerAvailabilitySwitch.setChecked(false);
                        break;
                }
            }
        });


        binding.cleaningWorkerAvailabilitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                parkingWorkerChangeWorkerStatusViewModel.parkingWorkerChangeWorkerStatus(sessionSharedPreferences.getID(), authHeader);
            }
        });

        binding.cleaningWorkerActiveStatusBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });


        return binding.getRoot();
    }
}