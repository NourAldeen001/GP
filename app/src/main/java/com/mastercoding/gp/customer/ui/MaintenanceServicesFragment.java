package com.mastercoding.gp.customer.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.customer.adapter.ViewAllMaintenanceServicesAdapter;
import com.mastercoding.gp.customer.data.Service;
import com.mastercoding.gp.customer.ui.viewmodel.GetAllMaintenanceServicesViewModel;
import com.mastercoding.gp.databinding.FragmentMaintenanceServicesBinding;

import java.util.List;


public class MaintenanceServicesFragment extends Fragment implements ViewAllMaintenanceServicesAdapter.OnMaintenItemClickListener {

    FragmentMaintenanceServicesBinding binding;

    GetAllMaintenanceServicesViewModel getAllMaintenanceServicesViewModel;

    ViewAllMaintenanceServicesAdapter viewAllMaintenanceServicesAdapter;

    SessionSharedPreferences sessionSharedPreferences;

    String userName, password, base, authHeader;

    public MaintenanceServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMaintenanceServicesBinding.inflate(inflater, container, false);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        getAllMaintenanceServicesViewModel = new ViewModelProvider(this).get(GetAllMaintenanceServicesViewModel.class);

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        getAllMaintenanceServicesViewModel.getAllMaintenanceServices(authHeader).observe(getViewLifecycleOwner(), new Observer<List<Service>>() {
            @Override
            public void onChanged(List<Service> services) {
                viewAllMaintenanceServicesAdapter = new ViewAllMaintenanceServicesAdapter(services);
                viewAllMaintenanceServicesAdapter.setOnMaintenItemClickListener(MaintenanceServicesFragment.this);
                binding.maintenServicesRecyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), 2));
                binding.maintenServicesRecyclerView.setAdapter(viewAllMaintenanceServicesAdapter);
            }
        });


        binding.mainServicesBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        return binding.getRoot();
    }

    private void navigateToServiceDetailsFragment(int serviceId) {
        // Use Safe Args to pass data to another fragment
        MaintenanceServicesFragmentDirections.ActionMaintenanceServicesFragmentToServiceDetailsFragment action =
                MaintenanceServicesFragmentDirections.actionMaintenanceServicesFragmentToServiceDetailsFragment(serviceId);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onMaintenItemClick(int serviceId) {
        navigateToServiceDetailsFragment(serviceId);
    }
}