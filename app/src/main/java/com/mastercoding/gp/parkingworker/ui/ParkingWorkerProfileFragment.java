package com.mastercoding.gp.parkingworker.ui;

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
import com.mastercoding.gp.databinding.FragmentParkingWorkerProfileBinding;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerData;
import com.mastercoding.gp.parkingworker.ui.viewmodel.GetParkingWorkerByIdViewModel;


public class ParkingWorkerProfileFragment extends Fragment {

    FragmentParkingWorkerProfileBinding binding;

    GetParkingWorkerByIdViewModel parkingWorkerByIdViewModel;

    SessionSharedPreferences sessionSharedPreferences;

    String userName, password, base, authHeader;

    public ParkingWorkerProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentParkingWorkerProfileBinding.inflate(inflater, container, false);
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
                    binding.parkingWorkerProfileImg.setImageBitmap(imgBitmap);
                }
                binding.parkingWorkerFullNameTxt.setText(String.format("%s %s", parkingWorkerData.getFirstName(), parkingWorkerData.getLastName()));
                binding.parkingWorkerUserNameTxt.setText(userName);
                binding.parkingWorkerFirstNameTxt.setText(parkingWorkerData.getFirstName());
                binding.parkingWorkerLastNameTxt.setText(parkingWorkerData.getLastName());
                binding.parkingWorkerPassTxt.setText(parkingWorkerData.getPassword());
                binding.parkingWorkerGenderTxt.setText(parkingWorkerData.getGender());
                binding.parkingWorkerPhoneTxt.setText(parkingWorkerData.getPhoneNumber());
                binding.parkingWorkerEmailTxt.setText(parkingWorkerData.getEmail());
                binding.parkingWorkerBirthTxt.setText(parkingWorkerData.getBirthday());
            }
        });

        binding.parkingWorkerProfileEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.parkingWorkerEditProfileFragment);
            }
        });


        binding.parkingWorkerProfileBackBtn.setOnClickListener(new View.OnClickListener() {
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