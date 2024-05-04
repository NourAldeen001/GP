package com.mastercoding.gp.customer.ui.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.CustomerCarData;
import com.mastercoding.gp.customer.data.CustomerCarDataBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCarRepository {

    ApiService apiService;

    private MutableLiveData<CustomerCarData> updatedCustomerCarDataMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<String> updatedCustomerCarDataStatus = new MutableLiveData<>();

    public UpdateCarRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<CustomerCarData> getUpdatedCustomerCarDataMutableLiveData(int carId, CustomerCarDataBody customerCarDataBody, String authHeader) {

        updatedCustomerCarDataStatus.postValue("Process Updating ... ");

        Call<CustomerCarData> initiate = apiService.updateCar(carId, customerCarDataBody, authHeader);

        initiate.enqueue(new Callback<CustomerCarData>() {
            @Override
            public void onResponse(Call<CustomerCarData> call, Response<CustomerCarData> response) {
                if(response.isSuccessful()){
                    updatedCustomerCarDataStatus.postValue("Updated Successfully\uD83C\uDF89");
                }
                else {
                    updatedCustomerCarDataStatus.postValue("You Don't Have A Car To Update its Information");
                }
            }

            @Override
            public void onFailure(Call<CustomerCarData> call, Throwable t) {
                updatedCustomerCarDataStatus.postValue("Network Connection Failed, Try Again");
            }
        });
        return updatedCustomerCarDataMutableLiveData;
    }

    public MutableLiveData<String> getUpdatedCustomerCarDataStatus() {
        return updatedCustomerCarDataStatus;
    }
}
