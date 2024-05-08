package com.mastercoding.gp.customer.ui.repository;


import android.util.Log;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.DeleteServiceFromOrderListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteServiceFromOrderListRepository {

    ApiService apiService;

    public DeleteServiceFromOrderListRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public void deleteServiceFromOrderListRemote(int serviceId, String authHeader){

        Call<DeleteServiceFromOrderListResponse> initiate = apiService.deleteServiceFromOrderList(serviceId, authHeader);

        initiate.enqueue(new Callback<DeleteServiceFromOrderListResponse>() {
            @Override
            public void onResponse(Call<DeleteServiceFromOrderListResponse> call, Response<DeleteServiceFromOrderListResponse> response) {
                if(response.isSuccessful()){
                    Log.i("Delete Service From Order List", "Success");
                }
                else {
                    Log.i("Delete Service From Order List", "Fail1");
                }
            }

            @Override
            public void onFailure(Call<DeleteServiceFromOrderListResponse> call, Throwable t) {
                Log.i("Delete Service From Order List", "Fail2");
            }
        });
    }
}
