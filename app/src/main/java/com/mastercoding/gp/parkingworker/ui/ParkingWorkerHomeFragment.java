package com.mastercoding.gp.parkingworker.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.CustomDialogFragment;
import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.databinding.FragmentParkingWorkerHomeBinding;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerFinishTaskBody;
import com.mastercoding.gp.parkingworker.ui.viewmodel.ParkingWorkerFinishTaskViewModel;


public class ParkingWorkerHomeFragment extends Fragment {

    FragmentParkingWorkerHomeBinding binding;

    ParkingWorkerFinishTaskViewModel parkingWorkerFinishTaskViewModel;

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

        dialogFragment = new CustomDialogFragment();

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);


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
}