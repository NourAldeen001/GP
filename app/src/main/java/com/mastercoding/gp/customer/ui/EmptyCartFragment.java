package com.mastercoding.gp.customer.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.R;
import com.mastercoding.gp.databinding.FragmentEmptyCartBinding;


public class EmptyCartFragment extends Fragment {

    FragmentEmptyCartBinding binding;

    public EmptyCartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEmptyCartBinding.inflate(inflater, container, false);

        binding.emptyCartViewServicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack(R.id.customerHomeFragment, false);
            }
        });

        binding.emptyCartBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack(R.id.customerHomeFragment, false);
            }
        });

        return binding.getRoot();
    }
}