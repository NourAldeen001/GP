package com.mastercoding.gp.customer.ui.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllMaintenanceServicesRepository {

    ApiService apiService;

    private MutableLiveData<List<Service>> allMaintenanceServicesMutableLiveData = new MutableLiveData<>();

    public GetAllMaintenanceServicesRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<List<Service>> getAllMaintenanceServicesMutableLiveData(String authHeader) {

        Call<List<Service>> initiate = apiService.getAllMaintenanceServices(authHeader);

        initiate.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if(response.isSuccessful()){
                    allMaintenanceServicesMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {

            }
        });

        return allMaintenanceServicesMutableLiveData;
    }
}
