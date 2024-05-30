package com.mastercoding.gp.cleaningworker.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.mastercoding.gp.databinding.FragmentCleaningWorkerProfileBinding;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerData;
import com.mastercoding.gp.parkingworker.ui.viewmodel.GetParkingWorkerByIdViewModel;


public class CleaningWorkerProfileFragment extends Fragment {

    FragmentCleaningWorkerProfileBinding binding;

    GetParkingWorkerByIdViewModel parkingWorkerByIdViewModel;

    SessionSharedPreferences sessionSharedPreferences;

    String userName, password, base, authHeader;

    public CleaningWorkerProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCleaningWorkerProfileBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        parkingWorkerByIdViewModel = new ViewModelProvider(this).get(GetParkingWorkerByIdViewModel.class);

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        parkingWorkerByIdViewModel.getParkingWorkerById(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<ParkingWorkerData>() {
            @Override
            public void onChanged(ParkingWorkerData parkingWorkerData) {
                if(parkingWorkerData.getImage() != null){
                    Bitmap imgBitmap = convertBase64ToBitmap(parkingWorkerData.getImage());
                    binding.cleaningWorkerProfileImg.setImageBitmap(imgBitmap);
                }
                binding.cleaningWorkerFullNameTxt.setText(String.format("%s %s", parkingWorkerData.getFirstName(), parkingWorkerData.getLastName()));
                binding.cleaningWorkerUserNameTxt.setText(userName);
                binding.cleaningWorkerFirstNameTxt.setText(parkingWorkerData.getFirstName());
                binding.cleaningWorkerLastNameTxt.setText(parkingWorkerData.getLastName());
                binding.cleaningWorkerPassTxt.setText(parkingWorkerData.getPassword());
                binding.cleaningWorkerGenderTxt.setText(parkingWorkerData.getGender());
                binding.cleaningWorkerPhoneTxt.setText(parkingWorkerData.getPhoneNumber());
                binding.cleaningWorkerEmailTxt.setText(parkingWorkerData.getEmail());
                binding.cleaningWorkerBirthTxt.setText(parkingWorkerData.getBirthday());
            }
        });

        binding.cleaningWorkerProfileEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.parkingWorkerEditProfileFragment);
            }
        });


        binding.cleaningWorkerProfileBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        return binding.getRoot();
    }

    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}