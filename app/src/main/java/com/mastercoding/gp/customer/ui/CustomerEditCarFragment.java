package com.mastercoding.gp.customer.ui;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.mastercoding.gp.CustomDialogFragment;
import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.customer.adapter.CarTypeAdapter;
import com.mastercoding.gp.customer.data.CustomerCarData;
import com.mastercoding.gp.customer.data.CustomerCarDataBody;
import com.mastercoding.gp.customer.ui.viewmodel.AddCarViewModel;
import com.mastercoding.gp.customer.ui.viewmodel.GetCarByCustomerIdViewModel;
import com.mastercoding.gp.customer.ui.viewmodel.UpdateCarViewModel;
import com.mastercoding.gp.databinding.FragmentCustomerEditCarBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CustomerEditCarFragment extends Fragment {

    FragmentCustomerEditCarBinding binding;

    List<String> carTypes = Arrays.asList("Abarth", "Acura", "AlfaRomeo", "AstonMartin", "Audi", "Baic", "Bajaj", "Benelli", "Bentley", "Bestune", "BMW", "Borgward", "Brilliance", "Bugatti", "Buick", "Byd", "Cadillac", "Chana", "Changan", "Chery", "Chevrolet", "Chrysler", "Citroën", "Cupra", "Daewoo", "Daihatsu", "Datsun", "DFSK", "Dodge", "Dongfeng", "Dorcen", "DS", "Ducati", "ElWahab", "Emgrand", "Exeed", "Fahd", "Faw", "Ferrari", "Fiat", "Ford", "Forthing", "Foton", "GAC", "Gaz", "Geely", "Genesis", "GMC", "GoldenDragon",
            "GreatWall", "Hafei", "Haima", "Halawa", "Hanteng", "Haojiang", "Haojue", "HarleyDavidson", "Haval", "Hawa", "Hawtai", "Honda", "Hongqi", "Hummer", "Hyundai", "Infiniti", "Isuzu", "Jac", "Jaguar", "Jeep", "Jetour", "Jinbei", "JMC", "Jonway", "Joylong", "Kaiyi", "Karry", "Kawasaki", "Keeway", "Kenbo", "Keyton", "KGM", "Kia", "KingLong", "KYC", "KYMCO", "Lada", "Lamborghini", "Lancia", "LandRover", "Landwind", "Leapmotor", "Lexus", "Lifan", "Lincoln", "LML", "Lotus", "LynkCo", "Mahindra", "Maserati", "Maxus",
            "Mazda", "McLaren", "MercedesBenz", "Mercury", "MG", "Mini", "Mitsubishi", "Nissan", "Opel", "Perodua", "Peugeot", "Polestar", "Pontiac", "Porsche", "Proton", "Pullman", "Renault", "RollsRoyce", "Saab", "Saipa", "Scion", "Seat", "Senova", "Shineray", "Skoda", "Skywell", "Smart", "Sokon", "Soueast", "Speranza", "SsangYong", "Subaru", "Suzuki", "SYM", "Tank", "Tata", "Tesla", "Toyota", "TVS", "Vgv", "Victory", "Volkswagen", "Volvo", "Wuyang", "Yadea", "Yamaha", "Zeekr", "ZNA", "Zontes");

    GetCarByCustomerIdViewModel getCarByCustomerIdViewModel;

    AddCarViewModel addCarViewModel;

    UpdateCarViewModel updateCarViewModel;

    SessionSharedPreferences sessionSharedPreferences;

    CustomDialogFragment dialogFragment;

    String userName, password, base, authHeader, carTypeSelected;

    int carId;

    public CustomerEditCarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCustomerEditCarBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        getCarByCustomerIdViewModel = new ViewModelProvider(this).get(GetCarByCustomerIdViewModel.class);

        addCarViewModel = new ViewModelProvider(this).get(AddCarViewModel.class);

        updateCarViewModel = new ViewModelProvider(this).get(UpdateCarViewModel.class);


        CarTypeAdapter adapter = new CarTypeAdapter(getContext(), carTypes);

        binding.customEditCarTypeDropList.setDropDownVerticalOffset(118);
        binding.customEditCarTypeDropList.setDropDownWidth(563);
        binding.customEditCarTypeDropList.setAdapter(adapter);

        dialogFragment = new CustomDialogFragment();

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        getCarByCustomerIdViewModel.getCarByCustomerId(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<CustomerCarData>() {
            @Override
            public void onChanged(CustomerCarData customerCarData) {
                if(customerCarData != null){
                    carId = customerCarData.getId();
                    binding.customEditCarPlateNumEdtxt.setText(customerCarData.getCarPlateNumber());
                    binding.customEditCarTypeDropList.setSelection(carTypes.indexOf(customerCarData.getCarType()));
                    binding.customEditCarModelEdtxt.setText(customerCarData.getModel());
                }
            }
        });

//        int index = carTypes.indexOf();
//
//        binding.customEditCarTypeDropList.setSelection(index);

        binding.customEditCarTypeDropList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                carTypeSelected = adapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.customEditCarAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String carPlateNumber = binding.customEditCarPlateNumEdtxt.getText().toString();
                String carModel = binding.customEditCarModelEdtxt.getText().toString();
                dialogFragment.show(getChildFragmentManager(), "Add Car Dialog");
                addCarViewModel.addCar(new CustomerCarDataBody(carPlateNumber, Integer.toString(sessionSharedPreferences.getID()), carTypeSelected, carModel), authHeader);
            }
        });

        addCarViewModel.getAddCarStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (dialogFragment != null && dialogFragment.isAdded()) {
                    dialogFragment.updateMessage(message);
                }
            }
        });


        binding.customEditCarUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String carPlateNumber = binding.customEditCarPlateNumEdtxt.getText().toString();
                String carModel = binding.customEditCarModelEdtxt.getText().toString();
                Log.i("Update Car", carPlateNumber + " " + carModel + " " + carTypeSelected);
                dialogFragment.show(getChildFragmentManager(), "Update Car Dialog");
                updateCarViewModel.updateCar(carId, new CustomerCarDataBody(carPlateNumber, Integer.toString(sessionSharedPreferences.getID()), carTypeSelected, carModel), authHeader);
            }
        });

        updateCarViewModel.getUpdateCarStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (dialogFragment != null && dialogFragment.isAdded()) {
                    dialogFragment.updateMessage(message);
                }
            }
        });

        binding.customProfileEditBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });


        return binding.getRoot();
    }
}