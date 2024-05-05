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
import com.mastercoding.gp.customer.adapter.ViewAllPackagesServicesAdapter;
import com.mastercoding.gp.customer.data.Package;
import com.mastercoding.gp.customer.ui.viewmodel.GetAllPackagesViewModel;
import com.mastercoding.gp.databinding.FragmentPackagesServicesBinding;

import java.util.List;


public class PackagesServicesFragment extends Fragment implements ViewAllPackagesServicesAdapter.OnPackageItemClickListener {

    FragmentPackagesServicesBinding binding;

    ViewAllPackagesServicesAdapter viewAllPackagesServicesAdapter;

    GetAllPackagesViewModel getAllPackagesViewModel;

    SessionSharedPreferences sessionSharedPreferences;

    String userName, password, base, authHeader;

    public PackagesServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPackagesServicesBinding.inflate(inflater, container, false);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        getAllPackagesViewModel = new ViewModelProvider(this).get(GetAllPackagesViewModel.class);

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        getAllPackagesViewModel.getAllPackages(authHeader).observe(getViewLifecycleOwner(), new Observer<List<Package>>() {
            @Override
            public void onChanged(List<Package> packages) {
                viewAllPackagesServicesAdapter = new ViewAllPackagesServicesAdapter(packages);
                viewAllPackagesServicesAdapter.setOnPackageItemClickListener(PackagesServicesFragment.this);
                binding.packServicesRecyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), 2));
                binding.packServicesRecyclerView.setAdapter(viewAllPackagesServicesAdapter);
            }
        });



        binding.packServicesBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        return binding.getRoot();
    }

    private void navigateToPackageDetailsFragment(int packageId) {
        // Use Safe Args to pass data to another fragment
        PackagesServicesFragmentDirections.ActionPackagesServicesFragmentToPackageDetailsFragment action =
                PackagesServicesFragmentDirections.actionPackagesServicesFragmentToPackageDetailsFragment(packageId);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onPackageItemClick(int packageId) {
        navigateToPackageDetailsFragment(packageId);
    }
}