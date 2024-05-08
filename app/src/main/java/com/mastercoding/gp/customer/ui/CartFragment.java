package com.mastercoding.gp.customer.ui;

import android.os.Bundle;

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
import com.mastercoding.gp.customer.adapter.PackageOnOrderListAdapter;
import com.mastercoding.gp.customer.adapter.ServiceOnOrderListAdapter;
import com.mastercoding.gp.customer.data.CustomerData;
import com.mastercoding.gp.customer.data.OrderList;
import com.mastercoding.gp.customer.ui.viewmodel.DeletePackageFromOrderListViewModel;
import com.mastercoding.gp.customer.ui.viewmodel.DeleteServiceFromOrderListViewModel;
import com.mastercoding.gp.customer.ui.viewmodel.GetCustomerByIdViewModel;
import com.mastercoding.gp.customer.ui.viewmodel.GetNonConfirmOrderViewModel;
import com.mastercoding.gp.databinding.FragmentCartBinding;

public class CartFragment extends Fragment implements ServiceOnOrderListAdapter.OnServiceDeleteItemClickListener,
                                                      PackageOnOrderListAdapter.OnPackageDeleteItemClickListener{

    FragmentCartBinding binding;

    GetCustomerByIdViewModel customerByIdViewModel;

    GetNonConfirmOrderViewModel getNonConfirmOrderViewModel;

    DeleteServiceFromOrderListViewModel deleteServiceFromOrderListViewModel;

    DeletePackageFromOrderListViewModel deletePackageFromOrderListViewModel;

    ServiceOnOrderListAdapter serviceOnOrderListAdapter;

    PackageOnOrderListAdapter packageOnOrderListAdapter;

    SessionSharedPreferences sessionSharedPreferences;

    String userName, password, base, authHeader;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        customerByIdViewModel = new ViewModelProvider(this).get(GetCustomerByIdViewModel.class);

        getNonConfirmOrderViewModel = new ViewModelProvider(this).get(GetNonConfirmOrderViewModel.class);

        deleteServiceFromOrderListViewModel = new ViewModelProvider(this).get(DeleteServiceFromOrderListViewModel.class);

        deletePackageFromOrderListViewModel = new ViewModelProvider(this).get(DeletePackageFromOrderListViewModel.class);

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        customerByIdViewModel.getCustomerById(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<CustomerData>() {
            @Override
            public void onChanged(CustomerData customerData) {

                getNonConfirmOrderViewModel.getNonConfirmOrder(sessionSharedPreferences.getID(), customerData.getCurrentBranchId(), authHeader).observe(getViewLifecycleOwner(), new Observer<OrderList>() {
                    @Override
                    public void onChanged(OrderList orderList) {

                        serviceOnOrderListAdapter = new ServiceOnOrderListAdapter(orderList.getServices());
                        serviceOnOrderListAdapter.setOnServiceDeleteItemClickListener(CartFragment.this);
                        binding.servicesCartRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false));
                        binding.servicesCartRecyclerView.setAdapter(serviceOnOrderListAdapter);


                        packageOnOrderListAdapter = new PackageOnOrderListAdapter(orderList.getPackages());
                        packageOnOrderListAdapter.setOnPackageDeleteItemClickListener(CartFragment.this);
                        binding.packagesCartRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false));
                        binding.packagesCartRecyclerView.setAdapter(packageOnOrderListAdapter);

                        binding.cartTotalCost.setText(String.format("$ %s", orderList.getOrderTotalCost()));
                        binding.cartTotalTime.setText(String.format("%s minute", orderList.getTotalRequiredTime()));

                    }
                });
            }
        });

        binding.cartBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void OnPackageDeleteItemClick(int packageId) {
        deletePackageFromOrderListViewModel.deletePackageFromOrderList(packageId, authHeader);
        refreshDataAfterDelete();
    }

    @Override
    public void OnServiceDeleteItemClick(int serviceId) {
        deleteServiceFromOrderListViewModel.deleteServiceFromOrderList(serviceId, authHeader);
        refreshDataAfterDelete();
    }

    public void refreshDataAfterDelete(){
        customerByIdViewModel.getCustomerById(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<CustomerData>() {
            @Override
            public void onChanged(CustomerData customerData) {

                getNonConfirmOrderViewModel.getNonConfirmOrder(sessionSharedPreferences.getID(), customerData.getCurrentBranchId(), authHeader).observe(getViewLifecycleOwner(), new Observer<OrderList>() {
                    @Override
                    public void onChanged(OrderList orderList) {

                        serviceOnOrderListAdapter = new ServiceOnOrderListAdapter(orderList.getServices());
                        serviceOnOrderListAdapter.setOnServiceDeleteItemClickListener(CartFragment.this);
                        binding.servicesCartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                        binding.servicesCartRecyclerView.setAdapter(serviceOnOrderListAdapter);


                        packageOnOrderListAdapter = new PackageOnOrderListAdapter(orderList.getPackages());
                        packageOnOrderListAdapter.setOnPackageDeleteItemClickListener(CartFragment.this);
                        binding.packagesCartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                        binding.packagesCartRecyclerView.setAdapter(packageOnOrderListAdapter);

                        binding.cartTotalCost.setText(String.format("$ %s", orderList.getOrderTotalCost()));
                        binding.cartTotalTime.setText(String.format("%s minute", orderList.getTotalRequiredTime()));

                    }
                });
            }
        });
    }
}