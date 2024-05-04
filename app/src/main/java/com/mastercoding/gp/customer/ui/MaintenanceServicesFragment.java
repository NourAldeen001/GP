package com.mastercoding.gp.customer.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.R;
import com.mastercoding.gp.customer.adapter.ViewAllMaintenanceServicesAdapter;
import com.mastercoding.gp.databinding.FragmentMaintenanceServicesBinding;


public class MaintenanceServicesFragment extends Fragment {

    FragmentMaintenanceServicesBinding binding;

    ViewAllMaintenanceServicesAdapter viewAllMaintenanceServicesAdapter;

    public MaintenanceServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMaintenanceServicesBinding.inflate(inflater, container, false);



        binding.mainServicesBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        return binding.getRoot();
    }
}