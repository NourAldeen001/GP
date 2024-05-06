package com.mastercoding.gp.customer.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mastercoding.gp.CustomDialogFragment;
import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.customer.data.AddServiceToOrderListBody;
import com.mastercoding.gp.customer.data.CustomerData;
import com.mastercoding.gp.customer.data.Service;
import com.mastercoding.gp.customer.ui.viewmodel.AddServiceToOrderListViewModel;
import com.mastercoding.gp.customer.ui.viewmodel.GetCustomerByIdViewModel;
import com.mastercoding.gp.customer.ui.viewmodel.GetServiceByIdAndBranchByIdViewModel;
import com.mastercoding.gp.databinding.FragmentServiceDetailsBinding;

public class ServiceDetailsFragment extends Fragment {

    FragmentServiceDetailsBinding binding;

    GetCustomerByIdViewModel customerByIdViewModel;

    GetServiceByIdAndBranchByIdViewModel getServiceByIdAndBranchByIdViewModel;

    AddServiceToOrderListViewModel addServiceToOrderListViewModel;

    SessionSharedPreferences sessionSharedPreferences;

    CustomDialogFragment dialogFragment;

    String userName, password, base, authHeader;

    int serviceId;

    public ServiceDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentServiceDetailsBinding.inflate(inflater, container, false);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        serviceId = ServiceDetailsFragmentArgs.fromBundle(getArguments()).getServiceId();
        Log.i("serviceId :", Integer.toString(serviceId));

        customerByIdViewModel = new ViewModelProvider(this).get(GetCustomerByIdViewModel.class);

        getServiceByIdAndBranchByIdViewModel = new ViewModelProvider(this).get(GetServiceByIdAndBranchByIdViewModel.class);

        addServiceToOrderListViewModel = new ViewModelProvider(this).get(AddServiceToOrderListViewModel.class);

        dialogFragment = new CustomDialogFragment();

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        customerByIdViewModel.getCustomerById(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<CustomerData>() {
            @Override
            public void onChanged(CustomerData customerData) {

                getServiceByIdAndBranchByIdViewModel.getServiceByIdAndBranchId(serviceId, customerData.getCurrentBranchId(), authHeader).observe(getViewLifecycleOwner(), new Observer<Service>() {
                    @Override
                    public void onChanged(Service service) {
                        Glide.with(container.getContext()).load(convertBase64ToBitmap(service.getImage())).into(binding.serviceDetailsImg);
                        binding.serviceDetailsNameTxt.setText(service.getName());
                        binding.serviceDetailsDescTxt.setText(service.getDescription());
                        binding.serviceDetailsPriceTxt.setText(service.getPrice());
                        binding.serviceDetailsServiceTimeTxt.setText(service.getRequiredTime());

                        if(service.getAvailableInBranch() == null){
                            binding.serviceDetailsAvailableTxt.setText("");
                        }
                        else if(service.getAvailableInBranch()) {
                            binding.serviceDetailsAvailableTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                            binding.serviceDetailsAvailableTxt.setText("Yes");
                        }
                        else {
                            binding.serviceDetailsAvailableTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                            binding.serviceDetailsAvailableTxt.setText("No");
                        }

                        switch (service.getServiceCategory()) {
                            case "CLEANING_SERVICE":
                                binding.serviceDetailsTitle.setText("Cleaning Service");
                                break;
                            case "TAKE_AWAY_SERVICE":
                                binding.serviceDetailsTitle.setText("Take Away Service");
                                break;
                            case "MAINTENANCE_SERVICE":
                                binding.serviceDetailsTitle.setText("Maintenance Service");
                                break;
                        }
                    }
                });
            }
        });

        binding.serviceDetailsAddToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment.show(getChildFragmentManager(), "Add Service To Cart Dialog");
                addServiceToOrderListViewModel.addServiceToOrderList(new AddServiceToOrderListBody(Integer.toString(sessionSharedPreferences.getID()), Integer.toString(serviceId)), authHeader);
            }
        });

        addServiceToOrderListViewModel.getAddServiceToOrderListStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if(dialogFragment != null && dialogFragment.isAdded()) {
                    dialogFragment.updateMessage(message);
                }
            }
        });


        binding.serviceDetailsBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });


        return binding.getRoot();
    }

    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}