package com.mastercoding.gp.customer.ui.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.ClearOrderResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClearOrderRepository {

    ApiService apiService;

    public ClearOrderRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public void clearOrderRemote(int customerId, String authHeader){

        Call<ClearOrderResponse> initiate = apiService.clearOrder(customerId, authHeader);

        initiate.enqueue(new Callback<ClearOrderResponse>() {
            @Override
            public void onResponse(Call<ClearOrderResponse> call, Response<ClearOrderResponse> response) {
                if(response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<ClearOrderResponse> call, Throwable t) {

            }
        });
    }
}
