package com.mastercoding.gp.customer.ui.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.AddServiceToOrderListBody;
import com.mastercoding.gp.customer.data.AddServiceToOrderListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddServiceToOrderListRepository {

    ApiService apiService;

    private MutableLiveData<String> addedServiceToOrderListStatusMutableLiveData = new MutableLiveData<>();

    public AddServiceToOrderListRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public void addServiceToOrderListRemote(AddServiceToOrderListBody addServiceToOrderListBody, String authHeader){

        addedServiceToOrderListStatusMutableLiveData.postValue("Process Adding ...");

        Call<AddServiceToOrderListResponse> initiate = apiService.addServiceToOrderList(addServiceToOrderListBody, authHeader);

        initiate.enqueue(new Callback<AddServiceToOrderListResponse>() {
            @Override
            public void onResponse(Call<AddServiceToOrderListResponse> call, Response<AddServiceToOrderListResponse> response) {
                if(response.isSuccessful()){
                    addedServiceToOrderListStatusMutableLiveData.postValue("Added Successfully\uD83C\uDF89");
                }
                else {
                    addedServiceToOrderListStatusMutableLiveData.postValue("This Service is Already in Your Cart");
                }
            }

            @Override
            public void onFailure(Call<AddServiceToOrderListResponse> call, Throwable t) {
                addedServiceToOrderListStatusMutableLiveData.postValue("Network Connection Failed, Try Again");
            }
        });
    }

    public MutableLiveData<String> getAddedServiceToOrderListStatusMutableLiveData() {
        return addedServiceToOrderListStatusMutableLiveData;
    }
}
