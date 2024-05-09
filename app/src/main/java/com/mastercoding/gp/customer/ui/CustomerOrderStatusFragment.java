package com.mastercoding.gp.customer.ui;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.customer.adapter.ServiceOnOrderStatusAdapter;
import com.mastercoding.gp.customer.data.GetProgressConfirmOrderResponse;
import com.mastercoding.gp.customer.ui.viewmodel.GetProgressConfirmOrderViewModel;
import com.mastercoding.gp.databinding.FragmentCustomerOrderStatusBinding;


public class CustomerOrderStatusFragment extends Fragment {

    FragmentCustomerOrderStatusBinding binding;

    GetProgressConfirmOrderViewModel progressConfirmOrderViewModel;

    ServiceOnOrderStatusAdapter serviceOnOrderStatusAdapter;

    SessionSharedPreferences sessionSharedPreferences;

    String userName, password, base, authHeader;

    public CustomerOrderStatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCustomerOrderStatusBinding.inflate(inflater, container, false);

        progressConfirmOrderViewModel = new ViewModelProvider(this).get(GetProgressConfirmOrderViewModel.class);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        progressConfirmOrderViewModel.getProgressConfirmOrder(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<GetProgressConfirmOrderResponse>() {
            @Override
            public void onChanged(GetProgressConfirmOrderResponse getProgressConfirmOrderResponse) {
                serviceOnOrderStatusAdapter = new ServiceOnOrderStatusAdapter(getProgressConfirmOrderResponse.getServices());
                binding.customerOrderStatusRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false));
                binding.customerOrderStatusRecyclerView.setAdapter(serviceOnOrderStatusAdapter);

                switch (getProgressConfirmOrderResponse.getStatus()) {
                    case "WAITING":
                        binding.customerOrderStatusTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                        binding.customerOrderStatusTxt.setText("waiting");
                        break;
                    case "ON_WORKING":
                        binding.customerOrderStatusTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.blue));
                        binding.customerOrderStatusTxt.setText("on working");
                        break;
                    case "FINISHED":
                        binding.customerOrderStatusTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                        binding.customerOrderStatusTxt.setText("finished");
                        break;
                }
            }
        });

        binding.customerOrderStatusCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });


        return binding.getRoot();
    }
}