package com.mastercoding.gp.parkingworker.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.CustomDialogFragment;
import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.customer.ui.CustomerHomeFragmentDirections;
import com.mastercoding.gp.databinding.FragmentParkingWorkerHomeBinding;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerFinishTaskBody;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerGetCapacityResponse;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerGetCountVisitationResponse;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerRecordBody;
import com.mastercoding.gp.parkingworker.ui.viewmodel.ParkingWorkerFinishTaskViewModel;
import com.mastercoding.gp.parkingworker.ui.viewmodel.ParkingWorkerGetCapacityOfBranchViewModel;
import com.mastercoding.gp.parkingworker.ui.viewmodel.ParkingWorkerGetCountVisitationOfBranchViewModel;
import com.mastercoding.gp.parkingworker.ui.viewmodel.ParkingWorkerRecordViewModel;


public class ParkingWorkerHomeFragment extends Fragment {

    FragmentParkingWorkerHomeBinding binding;

    ParkingWorkerRecordViewModel parkingWorkerRecordViewModel;

    ParkingWorkerFinishTaskViewModel parkingWorkerFinishTaskViewModel;

    ParkingWorkerGetCountVisitationOfBranchViewModel parkingWorkerGetCountVisitationOfBranchViewModel;

    ParkingWorkerGetCapacityOfBranchViewModel parkingWorkerGetCapacityOfBranchViewModel;

    SessionSharedPreferences sessionSharedPreferences;

    CustomDialogFragment dialogFragment;

    String userName, password, base, authHeader;

    public ParkingWorkerHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentParkingWorkerHomeBinding.inflate(inflater, container, false);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        parkingWorkerFinishTaskViewModel = new ViewModelProvider(this).get(ParkingWorkerFinishTaskViewModel.class);

        parkingWorkerRecordViewModel = new ViewModelProvider(this).get(ParkingWorkerRecordViewModel.class);

        parkingWorkerGetCountVisitationOfBranchViewModel = new ViewModelProvider(this).get(ParkingWorkerGetCountVisitationOfBranchViewModel.class);

        parkingWorkerGetCapacityOfBranchViewModel = new ViewModelProvider(this).get(ParkingWorkerGetCapacityOfBranchViewModel.class);

        dialogFragment = new CustomDialogFragment();

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        parkingWorkerGetCountVisitationOfBranchViewModel.getCountVisitationOfBranch(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<ParkingWorkerGetCountVisitationResponse>() {
            @Override
            public void onChanged(ParkingWorkerGetCountVisitationResponse parkingWorkerGetCountVisitationResponse) {
                parkingWorkerGetCapacityOfBranchViewModel.parkingWorkerGetCapacityOfBranch(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<ParkingWorkerGetCapacityResponse>() {
                    @Override
                    public void onChanged(ParkingWorkerGetCapacityResponse parkingWorkerGetCapacityResponse) {
                        Log.i("Capacity", "Capacity : " + parkingWorkerGetCapacityResponse.getCapacity());
                        binding.parkingWorkerHomeTotalCarToCapacity.setText(String.format("%s / %s", parkingWorkerGetCountVisitationResponse.getVisitationCount(), parkingWorkerGetCapacityResponse.getCapacity()));
                    }
                });
            }
        });



        binding.parkingWorkerHomeRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment.show(getChildFragmentManager(), "Record Dialog");
                String carPlateNumber = binding.parkingWorkerHomeCarPlateEditTxt.getText().toString();
                parkingWorkerRecordViewModel.record(new ParkingWorkerRecordBody(carPlateNumber, Integer.toString(sessionSharedPreferences.getID())), authHeader);
            }
        });

        parkingWorkerRecordViewModel.getRecordStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if(dialogFragment != null && dialogFragment.isAdded()){
                    dialogFragment.updateMessage(message);
                }
            }
        });

        binding.parkingWorkerHomeCheckoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String carPlateNumber = binding.parkingWorkerHomeCarPlateEditTxt.getText().toString();
                navigateToParkingWorkerCheckoutFragment(carPlateNumber);
            }
        });

        binding.parkingWorkerHomeFinishTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment.show(getChildFragmentManager(), "Finish Task Dialog");
                String carPlateNumber = binding.parkingWorkerHomeCarPlateEditTxt.getText().toString();
                parkingWorkerFinishTaskViewModel.finishTask(new ParkingWorkerFinishTaskBody(carPlateNumber, Integer.toString(sessionSharedPreferences.getID())), authHeader);
            }
        });

        parkingWorkerFinishTaskViewModel.getFinishTaskStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if(dialogFragment != null && dialogFragment.isAdded()){
                    dialogFragment.updateMessage(message);
                }
            }
        });

        return binding.getRoot();
    }

    private void navigateToParkingWorkerCheckoutFragment(String carPlateNumber) {
        // Use Safe Args to pass data to another fragment
        ParkingWorkerHomeFragmentDirections.ActionParkingWorkerHomeFragmentToParkingWorkerCheckoutFragment action =
                ParkingWorkerHomeFragmentDirections.actionParkingWorkerHomeFragmentToParkingWorkerCheckoutFragment(carPlateNumber);
        Navigation.findNavController(requireView()).navigate(action);
    }
}