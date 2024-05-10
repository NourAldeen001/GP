package com.mastercoding.gp.parkingworker.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.CustomDialogFragment;
import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.customer.data.PackageOnOrderList;
import com.mastercoding.gp.customer.data.ServiceOnOrderList;
import com.mastercoding.gp.customer.ui.ServiceDetailsFragmentArgs;
import com.mastercoding.gp.databinding.FragmentParkingWorkerCheckoutBinding;
import com.mastercoding.gp.parkingworker.adapter.PackageOnCheckoutAdapter;
import com.mastercoding.gp.parkingworker.adapter.ServiceOnCheckoutAdapter;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerCheckoutBody;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerCheckoutResponse;
import com.mastercoding.gp.parkingworker.ui.viewmodel.ParkingWorkerCheckoutViewModel;

import java.util.List;


public class ParkingWorkerCheckoutFragment extends Fragment {

    FragmentParkingWorkerCheckoutBinding binding;

    ParkingWorkerCheckoutViewModel parkingWorkerCheckoutViewModel;

    ServiceOnCheckoutAdapter serviceOnCheckoutAdapter;

    PackageOnCheckoutAdapter packageOnCheckoutAdapter;

    SessionSharedPreferences sessionSharedPreferences;

    CustomDialogFragment dialogFragment;

    String userName, password, base, authHeader, carPlateNumber;

    public ParkingWorkerCheckoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentParkingWorkerCheckoutBinding.inflate(inflater, container, false);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        carPlateNumber = ParkingWorkerCheckoutFragmentArgs.fromBundle(getArguments()).getCarPlateNumber();
        Log.i("serviceId : ", carPlateNumber);

        parkingWorkerCheckoutViewModel = new ViewModelProvider(this).get(ParkingWorkerCheckoutViewModel.class);

        dialogFragment = new CustomDialogFragment();

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        dialogFragment.show(getChildFragmentManager(), "Checkout Dialog");

        parkingWorkerCheckoutViewModel.checkout(new ParkingWorkerCheckoutBody(carPlateNumber, Integer.toString(sessionSharedPreferences.getID())), authHeader).observe(getViewLifecycleOwner(), new Observer<ParkingWorkerCheckoutResponse>() {
            @Override
            public void onChanged(ParkingWorkerCheckoutResponse parkingWorkerCheckoutResponse) {

                binding.parkingWorkerCheckoutPlateNumTxt.setText(parkingWorkerCheckoutResponse.getCarPlateNumber());
                binding.parkingWorkerCheckoutCustomNameTxt.setText(parkingWorkerCheckoutResponse.getCustomerName());
                binding.parkingWorkerCheckoutParkPeriodTxt.setText(parkingWorkerCheckoutResponse.getParkingPeriod());
                binding.parkingWorkerCheckoutParkPriceTxt.setText(parkingWorkerCheckoutResponse.getParkingPrice());

                List<ServiceOnOrderList> services = parkingWorkerCheckoutResponse.getOrder().getServices();
                serviceOnCheckoutAdapter = new ServiceOnCheckoutAdapter(services);
                binding.parkingWorkerCheckoutServicesRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false));
                binding.parkingWorkerCheckoutServicesRecyclerView.setAdapter(serviceOnCheckoutAdapter);

                List<PackageOnOrderList> packages = parkingWorkerCheckoutResponse.getOrder().getPackages();
                packageOnCheckoutAdapter = new PackageOnCheckoutAdapter(packages);
                binding.parkingWorkerCheckoutPackagesRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false));
                binding.parkingWorkerCheckoutPackagesRecyclerView.setAdapter(packageOnCheckoutAdapter);

                binding.parkingWorkerCheckoutOrderCostTxt.setText(parkingWorkerCheckoutResponse.getOrder().getOrderTotalCost());
                binding.parkingWorkerCheckoutTotalCostTxt.setText(parkingWorkerCheckoutResponse.getTotalCost());
            }
        });

        parkingWorkerCheckoutViewModel.getCheckoutStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if(dialogFragment != null && dialogFragment.isAdded()){
                    dialogFragment.updateMessage(message);
                }
            }
        });

        binding.parkingWorkerCheckoutCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.parkingWorkerHomeFragment);
            }
        });

        binding.parkingWorkerCheckoutPrintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.parkingWorkerCheckoutBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        return binding.getRoot();
    }
}