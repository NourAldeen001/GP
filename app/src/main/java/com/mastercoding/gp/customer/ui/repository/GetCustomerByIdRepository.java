package com.mastercoding.gp.customer.ui.repository;

import androidx.lifecycle.MutableLiveData;

import com.google.android.material.snackbar.Snackbar;
import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.CustomerData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCustomerByIdRepository {

    ApiService apiService;

    private MutableLiveData<CustomerData> customerDataMutableLiveData = new MutableLiveData<>();

    public GetCustomerByIdRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<CustomerData> getCustomerDataMutableLiveData(int customerId, String authHeader){

        Call<CustomerData> initiate = apiService.getCustomerById(customerId, authHeader);

        initiate.enqueue(new Callback<CustomerData>() {
            @Override
            public void onResponse(Call<CustomerData> call, Response<CustomerData> response) {
                CustomerData customerData = response.body();
                if(customerData != null && response.isSuccessful()){
                    customerDataMutableLiveData.setValue(customerData);
                }
            }

            @Override
            public void onFailure(Call<CustomerData> call, Throwable t) {

            }
        });
        return customerDataMutableLiveData;
    }
}
