package com.mastercoding.gp.customer.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.R;
import com.mastercoding.gp.customer.adapter.ViewAllPackagesServicesAdapter;
import com.mastercoding.gp.databinding.FragmentPackagesServicesBinding;


public class PackagesServicesFragment extends Fragment {

    FragmentPackagesServicesBinding binding;

    ViewAllPackagesServicesAdapter viewAllPackagesServicesAdapter;

    public PackagesServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPackagesServicesBinding.inflate(inflater, container, false);


        binding.packServicesBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        return binding.getRoot();
    }
}