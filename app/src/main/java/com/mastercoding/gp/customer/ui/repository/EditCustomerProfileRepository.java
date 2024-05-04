package com.mastercoding.gp.customer.ui.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.CustomerData;
import com.mastercoding.gp.customer.data.CustomerProfileBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCustomerProfileRepository {

    ApiService apiService;

    private MutableLiveData<CustomerData> updatedCustomerDataMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<String> updatedCustomerDataStatus = new MutableLiveData<>();

    public EditCustomerProfileRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }


    public MutableLiveData<CustomerData> getUpdatedCustomerDataMutableLiveData(int customerId, String authHeader, CustomerProfileBody customerProfileBody) {

        updatedCustomerDataStatus.postValue("Process Updating ... ");

        Call<CustomerData> initiate = apiService.editCustomerProfile(customerId, authHeader, customerProfileBody);
        
        initiate.enqueue(new Callback<CustomerData>() {
            @Override
            public void onResponse(Call<CustomerData> call, Response<CustomerData> response) {
                CustomerData customerData = response.body();
                if(customerData != null && response.isSuccessful()){
                    updatedCustomerDataMutableLiveData.setValue(customerData);
                    updatedCustomerDataStatus.postValue("Updated Successfully\uD83C\uDF89");
                    Log.i("Updated NOUR", "Success");
                }
                else {
                    updatedCustomerDataStatus.postValue("Sorry, Try Again");
                    Log.i("Updated NOUR", "Fail1" + response.message());
                }
            }

            @Override
            public void onFailure(Call<CustomerData> call, Throwable t) {
                updatedCustomerDataStatus.postValue("Network Connection Failed, Try Again");
                Log.i("Updated NOUR", "Fail2 : " + t.getMessage());
            }
        });
        return updatedCustomerDataMutableLiveData;
    }

    public MutableLiveData<String> getUpdatedCustomerDataStatus() {
        return updatedCustomerDataStatus;
    }
}
