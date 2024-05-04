package com.mastercoding.gp.customer.ui.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.CustomerCarData;
import com.mastercoding.gp.customer.data.CustomerCarDataBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCarRepository {

    ApiService apiService;

    private MutableLiveData<CustomerCarData> addedCustomerCarDataMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<String> addedCustomerCarDataStatus = new MutableLiveData<>();

    public AddCarRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<CustomerCarData> getAddedCustomerCarDataMutableLiveData(CustomerCarDataBody customerCarDataBody, String authHeader) {

        addedCustomerCarDataStatus.postValue("Process Adding ... ");

        Call<CustomerCarData> initiate = apiService.addCar(customerCarDataBody, authHeader);

        initiate.enqueue(new Callback<CustomerCarData>() {
            @Override
            public void onResponse(Call<CustomerCarData> call, Response<CustomerCarData> response) {
                if(response.isSuccessful()){
                    addedCustomerCarDataMutableLiveData.setValue(response.body());
                    addedCustomerCarDataStatus.postValue("Added Successfully\uD83C\uDF89");
                }
                else {
                    addedCustomerCarDataStatus.postValue("You Can't Add Car, You Have Already Car");
                }
            }

            @Override
            public void onFailure(Call<CustomerCarData> call, Throwable t) {
                addedCustomerCarDataStatus.postValue("Network Connection Failed, Try Again");
            }
        });
        return addedCustomerCarDataMutableLiveData;
    }

    public MutableLiveData<String> getAddedCustomerCarDataStatus() {
        return addedCustomerCarDataStatus;
    }
}
