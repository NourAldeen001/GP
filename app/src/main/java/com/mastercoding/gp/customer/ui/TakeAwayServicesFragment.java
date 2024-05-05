package com.mastercoding.gp.customer.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.mastercoding.gp.customer.adapter.ViewAllTakeAwayServicesAdapter;
import com.mastercoding.gp.customer.data.Service;
import com.mastercoding.gp.customer.ui.repository.GetAllTakeAwayServicesRepository;
import com.mastercoding.gp.customer.ui.viewmodel.GetAllTakeAwayServicesViewModel;
import com.mastercoding.gp.databinding.FragmentTakeAwayServicesBinding;

import java.util.List;


public class TakeAwayServicesFragment extends Fragment implements ViewAllTakeAwayServicesAdapter.OnTakeAwayItemClickListener {

    FragmentTakeAwayServicesBinding binding;

    ViewAllTakeAwayServicesAdapter viewAllTakeAwayServicesAdapter;

    GetAllTakeAwayServicesViewModel getAllTakeAwayServicesViewModel;

    SessionSharedPreferences sessionSharedPreferences;

    String userName, password, base, authHeader;

    public TakeAwayServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTakeAwayServicesBinding.inflate(inflater, container, false);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        getAllTakeAwayServicesViewModel = new ViewModelProvider(this).get(GetAllTakeAwayServicesViewModel.class);

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        getAllTakeAwayServicesViewModel.getAllTakeAwayServices(authHeader).observe(getViewLifecycleOwner(), new Observer<List<Service>>() {
            @Override
            public void onChanged(List<Service> services) {
                viewAllTakeAwayServicesAdapter = new ViewAllTakeAwayServicesAdapter(services);
                viewAllTakeAwayServicesAdapter.setOnTakeAwayItemClickListener(TakeAwayServicesFragment.this);
                binding.takeServicesRecyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), 2));
                binding.takeServicesRecyclerView.setAdapter(viewAllTakeAwayServicesAdapter);
            }
        });


        binding.takeServicesBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        return binding.getRoot();
    }

    private void navigateToServiceDetailsFragment(int serviceId) {
        // Use Safe Args to pass data to another fragment
        TakeAwayServicesFragmentDirections.ActionTakeAwayServicesFragmentToServiceDetailsFragment action =
                TakeAwayServicesFragmentDirections.actionTakeAwayServicesFragmentToServiceDetailsFragment(serviceId);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onTakeAwayItemClick(int serviceId) {
        navigateToServiceDetailsFragment(serviceId);
    }
}