package com.mastercoding.gp.customer.ui.repository;

import android.util.Log;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.DeletePackageFromOrderListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeletePackageFromOrderListRepository {

    ApiService apiService;

    public DeletePackageFromOrderListRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public void deletePackageFromOrderListRemote(int packageId, String authHeader){

        Call<DeletePackageFromOrderListResponse> initiate = apiService.deletePackageFromOrderList(packageId, authHeader);

        initiate.enqueue(new Callback<DeletePackageFromOrderListResponse>() {
            @Override
            public void onResponse(Call<DeletePackageFromOrderListResponse> call, Response<DeletePackageFromOrderListResponse> response) {
                if(response.isSuccessful()){
                    Log.i("Delete Package From Order List", "Success");
                }
                else {
                    Log.i("Delete Package From Order List", "Fail1");
                }
            }

            @Override
            public void onFailure(Call<DeletePackageFromOrderListResponse> call, Throwable t) {
                Log.i("Delete Package From Order List", "Fail2");
            }
        });
    }
}
