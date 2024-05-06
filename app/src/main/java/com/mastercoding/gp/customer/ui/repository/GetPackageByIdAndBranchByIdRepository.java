package com.mastercoding.gp.customer.ui.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.Package;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetPackageByIdAndBranchByIdRepository {

    ApiService apiService;

    private MutableLiveData<Package> packageByIdAndBranchIdMutableLiveData = new MutableLiveData<>();

    public GetPackageByIdAndBranchByIdRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<Package> getPackageByIdAndBranchIdMutableLiveData(int packageId, Long branchId, String authHeader) {

        Call<Package> initiate = apiService.getPackageByIdAndBranchId(packageId, branchId, authHeader);

        initiate.enqueue(new Callback<Package>() {
            @Override
            public void onResponse(Call<Package> call, Response<Package> response) {
                if(response.isSuccessful()){
                    packageByIdAndBranchIdMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Package> call, Throwable t) {

            }
        });

        return packageByIdAndBranchIdMutableLiveData;
    }
}
