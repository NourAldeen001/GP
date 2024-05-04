package com.mastercoding.gp.customer.ui.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.CustomerCarData;
import com.mastercoding.gp.customer.data.CustomerData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCarByCustomerIdRepository {

    ApiService apiService;

    private MutableLiveData<CustomerCarData> customerCarDataMutableLiveData = new MutableLiveData<>();

    public GetCarByCustomerIdRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<CustomerCarData> getCustomerCarDataMutableLiveData(int customerId, String authHeader){

        Call<CustomerCarData> initiate = apiService.getCarByCustomerId(customerId, authHeader);

        initiate.enqueue(new Callback<CustomerCarData>() {
            @Override
            public void onResponse(Call<CustomerCarData> call, Response<CustomerCarData> response) {
                CustomerCarData customerCarData = response.body();
                if(customerCarData != null && response.isSuccessful()){
                    customerCarDataMutableLiveData.setValue(customerCarData);
                }
            }

            @Override
            public void onFailure(Call<CustomerCarData> call, Throwable t) {

            }
        });
        return customerCarDataMutableLiveData;
    }
}
