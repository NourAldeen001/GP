package com.mastercoding.gp.customer.ui.repository;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetServiceByIdAndBranchByIdRepository {

    ApiService apiService;

    MutableLiveData<Service> serviceByIdAndBranchIdMutableLiveData = new MutableLiveData<>();

    public GetServiceByIdAndBranchByIdRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<Service> getServiceByIdAndBranchIdMutableLiveData(int serviceId, Long branchId, String authHeader) {

        Call<Service> initiate = apiService.getServiceByIdAndBranchId(serviceId, branchId, authHeader);

        initiate.enqueue(new Callback<Service>() {
            @Override
            public void onResponse(Call<Service> call, Response<Service> response) {
                if(response.isSuccessful()){
                    serviceByIdAndBranchIdMutableLiveData.setValue(response.body());
                    Log.i("GetServiceByIdAndBranchByIdRepository", "Success");
                }
                else {
                    Log.i("GetServiceByIdAndBranchByIdRepository", "Fail1: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Service> call, Throwable t) {
                Log.i("GetServiceByIdAndBranchByIdRepository", "Fail2: " + t.getMessage());
            }
        });
        return serviceByIdAndBranchIdMutableLiveData;
    }
}
