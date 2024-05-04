package com.mastercoding.gp.customer.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.R;
import com.mastercoding.gp.customer.adapter.ViewAllCleanServicesAdapter;
import com.mastercoding.gp.customer.data.ImageService;
import com.mastercoding.gp.databinding.FragmentCleaningServicesBinding;

import java.util.List;


public class CleaningServicesFragment extends Fragment {

    FragmentCleaningServicesBinding binding;

    ViewAllCleanServicesAdapter viewAllCleanServicesAdapter;

    public CleaningServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCleaningServicesBinding.inflate(inflater, container, false);


        binding.cleanServicesBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        return binding.getRoot();
    }
}