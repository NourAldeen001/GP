package com.mastercoding.gp.customer.ui.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.ConfirmOrderResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmOrderRepository {

    ApiService apiService;

    private MutableLiveData<String> confirmOrderStatusMutableLiveData = new MutableLiveData<>();

    public ConfirmOrderRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public void confirmOrderRemote(int customerId, String authHeader){

        confirmOrderStatusMutableLiveData.postValue("Process Confirming ...");

        Call<ConfirmOrderResponse> initiate = apiService.confirmOrder(customerId, authHeader);

        initiate.enqueue(new Callback<ConfirmOrderResponse>() {
            @Override
            public void onResponse(Call<ConfirmOrderResponse> call, Response<ConfirmOrderResponse> response) {
                if(response.isSuccessful()){
                    confirmOrderStatusMutableLiveData.postValue("Confirmed Successfully\uD83C\uDF89");
                }
                else {
                    confirmOrderStatusMutableLiveData.postValue("Confirmation is not available without\n" +
                            " The items in your cart are available in the branch and you should be in branch");
                }
            }

            @Override
            public void onFailure(Call<ConfirmOrderResponse> call, Throwable t) {
                confirmOrderStatusMutableLiveData.postValue("Network Connection Failed, Try Again");
            }
        });
    }

    public MutableLiveData<String> getConfirmOrderStatusMutableLiveData() {
        return confirmOrderStatusMutableLiveData;
    }
}
