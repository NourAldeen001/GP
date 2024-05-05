package com.mastercoding.gp.customer.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.customer.adapter.ViewAllCleanServicesAdapter;
import com.mastercoding.gp.customer.data.ImageService;
import com.mastercoding.gp.customer.data.Service;
import com.mastercoding.gp.customer.ui.viewmodel.GetAllCleaningServicesViewModel;
import com.mastercoding.gp.databinding.FragmentCleaningServicesBinding;

import java.util.List;


public class CleaningServicesFragment extends Fragment implements ViewAllCleanServicesAdapter.OnCleanItemClickListener {

    FragmentCleaningServicesBinding binding;

    GetAllCleaningServicesViewModel getAllCleaningServicesViewModel;

    ViewAllCleanServicesAdapter viewAllCleanServicesAdapter;

    SessionSharedPreferences sessionSharedPreferences;

    String userName, password, base, authHeader;

    public CleaningServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCleaningServicesBinding.inflate(inflater, container, false);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        getAllCleaningServicesViewModel = new ViewModelProvider(this).get(GetAllCleaningServicesViewModel.class);

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        getAllCleaningServicesViewModel.getAllCleaningServices(authHeader).observe(getViewLifecycleOwner(), new Observer<List<Service>>() {
            @Override
            public void onChanged(List<Service> services) {
                viewAllCleanServicesAdapter = new ViewAllCleanServicesAdapter(services);
                viewAllCleanServicesAdapter.setOnCleanItemClickListener(CleaningServicesFragment.this);
                binding.cleanServicesRecyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), 2));
                binding.cleanServicesRecyclerView.setAdapter(viewAllCleanServicesAdapter);
            }
        });

        binding.cleanServicesBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        return binding.getRoot();
    }

    private void navigateToServiceDetailsFragment(int serviceId) {
        // Use Safe Args to pass data to another fragment
        CleaningServicesFragmentDirections.ActionCleaningServicesFragmentToServiceDetailsFragment action =
                CleaningServicesFragmentDirections.actionCleaningServicesFragmentToServiceDetailsFragment(serviceId);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onCleanItemClick(int serviceId) {
        navigateToServiceDetailsFragment(serviceId);
    }
}