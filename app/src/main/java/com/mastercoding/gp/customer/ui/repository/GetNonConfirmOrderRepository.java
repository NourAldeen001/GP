package com.mastercoding.gp.customer.ui.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.OrderList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetNonConfirmOrderRepository {

    ApiService apiService;

    private MutableLiveData<OrderList> nonConfirmOrderListMutableLiveData = new MutableLiveData<>();

    public GetNonConfirmOrderRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<OrderList> getNonConfirmOrderListMutableLiveData(int customerId, Long branchId, String authHeader) {

        Call<OrderList> initiate = apiService.getNonConfirmOrder(customerId, branchId, authHeader);

        initiate.enqueue(new Callback<OrderList>() {
            @Override
            public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                if(response.isSuccessful()){
                    nonConfirmOrderListMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<OrderList> call, Throwable t) {

            }
        });

        return nonConfirmOrderListMutableLiveData;
    }
}
