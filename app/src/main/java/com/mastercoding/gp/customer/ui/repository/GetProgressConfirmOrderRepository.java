package com.mastercoding.gp.customer.ui.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.GetProgressConfirmOrderResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetProgressConfirmOrderRepository {

    ApiService apiService;

    private MutableLiveData<GetProgressConfirmOrderResponse> progressConfirmOrderResponseMutableLiveData = new MutableLiveData<>();

    public GetProgressConfirmOrderRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<GetProgressConfirmOrderResponse> getProgressConfirmOrderResponseMutableLiveData(int customerId, String authHeader){

        Call<GetProgressConfirmOrderResponse> initiate = apiService.getProgressConfirmOrder(customerId, authHeader);

        initiate.enqueue(new Callback<GetProgressConfirmOrderResponse>() {
            @Override
            public void onResponse(Call<GetProgressConfirmOrderResponse> call, Response<GetProgressConfirmOrderResponse> response) {
                if(response.isSuccessful()){
                    progressConfirmOrderResponseMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<GetProgressConfirmOrderResponse> call, Throwable t) {

            }
        });
        return progressConfirmOrderResponseMutableLiveData;
    }
}
