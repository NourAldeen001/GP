package com.mastercoding.gp.customer.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.CustomDialogFragment;
import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.customer.data.CustomerCarData;
import com.mastercoding.gp.customer.data.CustomerData;
import com.mastercoding.gp.customer.ui.viewmodel.DeleteCarViewModel;
import com.mastercoding.gp.customer.ui.viewmodel.GetCarByCustomerIdViewModel;
import com.mastercoding.gp.customer.ui.viewmodel.GetCustomerByIdViewModel;
import com.mastercoding.gp.databinding.FragmentCustomerProfileBinding;


public class CustomerProfileFragment extends Fragment {

    FragmentCustomerProfileBinding binding;

    GetCustomerByIdViewModel customerByIdViewModel;

    GetCarByCustomerIdViewModel carByCustomerIdViewModel;

    DeleteCarViewModel deleteCarViewModel;

    SessionSharedPreferences sessionSharedPreferences;

    CustomDialogFragment dialogFragment;

    String userName, password, base, authHeader;

    int carId;

    public CustomerProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCustomerProfileBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        customerByIdViewModel = new ViewModelProvider(this).get(GetCustomerByIdViewModel.class);

        carByCustomerIdViewModel = new ViewModelProvider(this).get(GetCarByCustomerIdViewModel.class);

        deleteCarViewModel = new ViewModelProvider(this).get(DeleteCarViewModel.class);

        dialogFragment = new CustomDialogFragment();

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        customerByIdViewModel.getCustomerById(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<CustomerData>() {
            @Override
            public void onChanged(CustomerData customerData) {
                if(customerData.getImage() != null){
                    Bitmap imgBitmap = convertBase64ToBitmap(customerData.getImage());
                    binding.customProfileImg.setImageBitmap(imgBitmap);
                }
                //binding.customProfileImg
                binding.customFullNameTxt.setText(String.format("%s %s", customerData.getFirstName(), customerData.getLastName()));
                binding.customUserNameTxt.setText(userName);
                binding.customFirstNameTxt.setText(customerData.getFirstName());
                binding.customLastNameTxt.setText(customerData.getLastName());
                binding.customEmailTxt.setText(customerData.getEmail());
                binding.customBirthTxt.setText(customerData.getBirthday());
                binding.customPassTxt.setText(customerData.getPassword());
                binding.customGenderTxt.setText(customerData.getGender());
                binding.customPhoneTxt.setText(customerData.getPhoneNumber());
            }
        });


        carByCustomerIdViewModel.getCarByCustomerId(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<CustomerCarData>() {
            @Override
            public void onChanged(CustomerCarData customerCarData) {
                carId = customerCarData.getId();
                binding.customCarPlateNumTxt.setText(customerCarData.getCarPlateNumber());
                binding.customCarTypeTxt.setText(customerCarData.getCarType());
                binding.customCarModelTxt.setText(customerCarData.getModel());

            }
        });


        binding.customProfileEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_customerProfileFragment_to_customerEditProfileFragment);
            }
        });

        binding.customUpdateCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_customerProfileFragment_to_customerEditCarFragment);
            }
        });

        binding.customDeleteCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment.show(getChildFragmentManager(), "Delete Car Dialog");
                Log.i("Car ID : ", Integer.toString(carId));
                deleteCarViewModel.deleteCar(carId, authHeader);
            }
        });

        deleteCarViewModel.getDeleteStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if(dialogFragment != null && dialogFragment.isAdded()) {
                    dialogFragment.updateMessage(message);
                }
                if(message.equals("Deleted Successfully\uD83C\uDF89")){
                    Log.i("Update UI After Deleted", "SSSSS");
                    // Update UI Car Data
                    carByCustomerIdViewModel.getCarByCustomerId(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<CustomerCarData>() {
                        @Override
                        public void onChanged(CustomerCarData customerCarData) {
                            binding.customCarPlateNumTxt.setText("");
                            binding.customCarTypeTxt.setText("");
                            binding.customCarModelTxt.setText("");
                        }
                    });
                }
            }
        });

        binding.customProfileBackBtn.setOnClickListener(new View.OnClickListener() {
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