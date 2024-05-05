package com.mastercoding.gp.customer.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.customer.ItemClickListener;
import com.mastercoding.gp.customer.adapter.CleanServiceAdapter;
import com.mastercoding.gp.customer.adapter.MaintenServiceAdapter;
import com.mastercoding.gp.customer.adapter.TakeAwayServiceAdapter;
import com.mastercoding.gp.customer.data.ImageService;
import com.mastercoding.gp.customer.data.Service;
import com.mastercoding.gp.customer.ui.viewmodel.GetAllCleaningServicesViewModel;
import com.mastercoding.gp.customer.ui.viewmodel.GetAllMaintenanceServicesViewModel;
import com.mastercoding.gp.customer.ui.viewmodel.GetAllTakeAwayServicesViewModel;
import com.mastercoding.gp.databinding.FragmentCustomerHomeBinding;

import java.util.List;


public class CustomerHomeFragment extends Fragment implements CleanServiceAdapter.OnCleanItemClickListener,
                                                              MaintenServiceAdapter.OnMaintenItemClickListener,
                                                              TakeAwayServiceAdapter.OnTakeAwayItemClickListener{

    FragmentCustomerHomeBinding binding;

    GetAllCleaningServicesViewModel getAllCleaningServicesViewModel;
    CleanServiceAdapter cleanServiceAdapter;
    GetAllMaintenanceServicesViewModel getAllMaintenanceServicesViewModel;
    MaintenServiceAdapter maintenServiceAdapter;
    GetAllTakeAwayServicesViewModel getAllTakeAwayServicesViewModel;
    TakeAwayServiceAdapter takeAwayServiceAdapter;

    SessionSharedPreferences sessionSharedPreferences;

    String userName, password, base, authHeader;



    public CustomerHomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCustomerHomeBinding.inflate(inflater, container, false);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        getAllCleaningServicesViewModel = new ViewModelProvider(this).get(GetAllCleaningServicesViewModel.class);

        getAllMaintenanceServicesViewModel = new ViewModelProvider(this).get(GetAllMaintenanceServicesViewModel.class);

        getAllTakeAwayServicesViewModel = new ViewModelProvider(this).get(GetAllTakeAwayServicesViewModel.class);

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);


        getAllCleaningServicesViewModel.getAllCleaningServices(authHeader).observe(getViewLifecycleOwner(), new Observer<List<Service>>() {
            @Override
            public void onChanged(List<Service> services) {
                cleanServiceAdapter = new CleanServiceAdapter(services);
                cleanServiceAdapter.setOnCleanItemClickListener(CustomerHomeFragment.this);
                binding.cleanRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.HORIZONTAL, false));
                binding.cleanRecyclerView.setAdapter(cleanServiceAdapter);
            }
        });


        getAllMaintenanceServicesViewModel.getAllMaintenanceServices(authHeader).observe(getViewLifecycleOwner(), new Observer<List<Service>>() {
            @Override
            public void onChanged(List<Service> services) {
                maintenServiceAdapter = new MaintenServiceAdapter(services);
                maintenServiceAdapter.setOnMaintenItemClickListener(CustomerHomeFragment.this);
                binding.maintenRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.HORIZONTAL, false));
                binding.maintenRecyclerView.setAdapter(maintenServiceAdapter);
            }
        });

        getAllTakeAwayServicesViewModel.getAllTakeAwayServices(authHeader).observe(getViewLifecycleOwner(), new Observer<List<Service>>() {
            @Override
            public void onChanged(List<Service> services) {
                takeAwayServiceAdapter = new TakeAwayServiceAdapter(services);
                takeAwayServiceAdapter.setOnTakeAwayItemClickListener(CustomerHomeFragment.this);
                binding.takeAwayRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.HORIZONTAL, false));
                binding.takeAwayRecyclerView.setAdapter(takeAwayServiceAdapter);
            }
        });

        binding.maintenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.cleanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.packsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.maintenViewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.cleanViewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.takeAwayViewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return binding.getRoot();
    }

    private void navigateToServiceDetailsFragment(int serviceId) {
        // Use Safe Args to pass data to another fragment
        CustomerHomeFragmentDirections.ActionCustomerHomeFragmentToServiceDetailsFragment action =
                CustomerHomeFragmentDirections.actionCustomerHomeFragmentToServiceDetailsFragment(serviceId);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onCleanItemClick(int serviceId) {
        navigateToServiceDetailsFragment(serviceId);
    }

    @Override
    public void onMaintenItemClick(int serviceId) {
        navigateToServiceDetailsFragment(serviceId);
    }

    @Override
    public void onTakeAwayItemClick(int serviceId) {
        navigateToServiceDetailsFragment(serviceId);
    }
}