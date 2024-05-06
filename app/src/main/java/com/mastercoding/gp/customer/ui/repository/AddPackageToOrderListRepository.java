package com.mastercoding.gp.customer.ui.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.AddPackageToOrderListBody;
import com.mastercoding.gp.customer.data.AddPackageToOrderListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPackageToOrderListRepository {

    ApiService apiService;

    private MutableLiveData<String> addedPackageToOrderListStatusMutableLiveData = new MutableLiveData<>();

    public AddPackageToOrderListRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public void addPackageToOrderListRemote(AddPackageToOrderListBody addPackageToOrderListBody, String authHeader){

        addedPackageToOrderListStatusMutableLiveData.postValue("Process Adding ...");

        Call<AddPackageToOrderListResponse> initiate = apiService.addPackageToOrderList(addPackageToOrderListBody, authHeader);

        initiate.enqueue(new Callback<AddPackageToOrderListResponse>() {
            @Override
            public void onResponse(Call<AddPackageToOrderListResponse> call, Response<AddPackageToOrderListResponse> response) {
                if(response.isSuccessful()){
                    addedPackageToOrderListStatusMutableLiveData.postValue("Added Successfully\uD83C\uDF89");
                }
                else {
                    Log.i("Add Package to Order List", response.message());
                    addedPackageToOrderListStatusMutableLiveData.postValue("This Package is Already in Your Cart");
                }
            }

            @Override
            public void onFailure(Call<AddPackageToOrderListResponse> call, Throwable t) {
                addedPackageToOrderListStatusMutableLiveData.postValue("Network Connection Failed, Try Again");
            }
        });
    }

    public MutableLiveData<String> getAddedPackageToOrderListStatusMutableLiveData() {
        return addedPackageToOrderListStatusMutableLiveData;
    }
}
