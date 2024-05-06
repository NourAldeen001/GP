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
import com.mastercoding.gp.customer.data.AddPackageToOrderListBody;
import com.mastercoding.gp.customer.data.CustomerData;
import com.mastercoding.gp.customer.data.Package;
import com.mastercoding.gp.customer.ui.viewmodel.AddPackageToOrderListViewModel;
import com.mastercoding.gp.customer.ui.viewmodel.GetCustomerByIdViewModel;
import com.mastercoding.gp.customer.ui.viewmodel.GetPackageByIdAndBranchByIdViewModel;
import com.mastercoding.gp.databinding.FragmentPackageDetailsBinding;


public class PackageDetailsFragment extends Fragment {


    FragmentPackageDetailsBinding binding;

    GetCustomerByIdViewModel customerByIdViewModel;

    GetPackageByIdAndBranchByIdViewModel getPackageByIdAndBranchByIdViewModel;

    AddPackageToOrderListViewModel addPackageToOrderListViewModel;

    SessionSharedPreferences sessionSharedPreferences;

    CustomDialogFragment dialogFragment;

    String userName, password, base, authHeader;

    int packageId;

    public PackageDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPackageDetailsBinding.inflate(inflater, container, false);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        packageId = PackageDetailsFragmentArgs.fromBundle(getArguments()).getPackageId();
        Log.i("packageId :", Integer.toString(packageId));

        customerByIdViewModel = new ViewModelProvider(this).get(GetCustomerByIdViewModel.class);

        getPackageByIdAndBranchByIdViewModel = new ViewModelProvider(this).get(GetPackageByIdAndBranchByIdViewModel.class);

        addPackageToOrderListViewModel = new ViewModelProvider(this).get(AddPackageToOrderListViewModel.class);

        dialogFragment = new CustomDialogFragment();

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        customerByIdViewModel.getCustomerById(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<CustomerData>() {
            @Override
            public void onChanged(CustomerData customerData) {

                getPackageByIdAndBranchByIdViewModel.getPackageByIdAndBranchId(packageId, customerData.getCurrentBranchId(), authHeader).observe(getViewLifecycleOwner(), new Observer<Package>() {
                    @Override
                    public void onChanged(Package aPackage) {
                        Glide.with(container.getContext()).load(convertBase64ToBitmap(aPackage.getImage())).into(binding.packageDetailsImg);
                        binding.packageDetailsNameTxt.setText(aPackage.getName());
                        binding.packageDetailsDescTxt.setText(aPackage.getDescription());
                        binding.packageDetailsPriceTxt.setText(aPackage.getCurrentPackagePrice());
                        binding.packageDetailsServiceTimeTxt.setText(aPackage.getOriginalTotalRequiredTime());


                        if(aPackage.getAvailableInBranch() == null){
                            binding.packageDetailsAvailableTxt.setText("");
                        }
                        else if(aPackage.getAvailableInBranch()) {
                            binding.packageDetailsAvailableTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                            binding.packageDetailsAvailableTxt.setText("Yes");
                        }
                        else {
                            binding.packageDetailsAvailableTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                            binding.packageDetailsAvailableTxt.setText("No");
                        }
                    }
                });
            }
        });

        binding.packageDetailsAddToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment.show(getChildFragmentManager(), "Add Package To Cart Dialog");
                addPackageToOrderListViewModel.addPackageToOrderList(new AddPackageToOrderListBody(Integer.toString(sessionSharedPreferences.getID()), Integer.toString(packageId)), authHeader);
            }
        });

        addPackageToOrderListViewModel.getAddPackageToOrderListStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if(dialogFragment != null && dialogFragment.isAdded()) {
                    dialogFragment.updateMessage(message);
                }
            }
        });


        binding.packageDetailsBackBtn.setOnClickListener(new View.OnClickListener() {
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