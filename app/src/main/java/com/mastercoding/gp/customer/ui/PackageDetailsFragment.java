package com.mastercoding.gp.customer.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.R;
import com.mastercoding.gp.databinding.FragmentPackageDetailsBinding;


public class PackageDetailsFragment extends Fragment {


    FragmentPackageDetailsBinding binding;

    int packageId;

    public PackageDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPackageDetailsBinding.inflate(inflater, container, false);

        packageId = PackageDetailsFragmentArgs.fromBundle(getArguments()).getPackageId();

        Log.i("packageId :", Integer.toString(packageId));
        binding.packageDetailsBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        return binding.getRoot();
    }
}