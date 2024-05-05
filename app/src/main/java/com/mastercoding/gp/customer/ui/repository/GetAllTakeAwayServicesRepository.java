package com.mastercoding.gp.customer.ui.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllTakeAwayServicesRepository {

    ApiService apiService;

    MutableLiveData<List<Service>> allTakeAwayServicesMutableLiveData = new MutableLiveData<>();

    public GetAllTakeAwayServicesRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<List<Service>> getAllTakeAwayServicesMutableLiveData(String authHeader) {

        Call<List<Service>> initiate = apiService.getAllTakeAwayServices(authHeader);

        initiate.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if(response.isSuccessful()){
                    allTakeAwayServicesMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {

            }
        });


        return allTakeAwayServicesMutableLiveData;
    }
}
