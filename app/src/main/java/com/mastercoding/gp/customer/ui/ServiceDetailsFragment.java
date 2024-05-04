package com.mastercoding.gp.customer.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.R;
import com.mastercoding.gp.databinding.FragmentServiceDetailsBinding;

public class ServiceDetailsFragment extends Fragment {

    FragmentServiceDetailsBinding binding;

    int serviceId;

    public ServiceDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentServiceDetailsBinding.inflate(inflater, container, false);

        serviceId = ServiceDetailsFragmentArgs.fromBundle(getArguments()).getServiceId();

        Log.i("serviceId :", Integer.toString(serviceId));


        return binding.getRoot();
    }
}