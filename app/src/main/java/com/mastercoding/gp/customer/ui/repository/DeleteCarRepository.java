package com.mastercoding.gp.customer.ui.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.DeleteCarResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteCarRepository {

    ApiService apiService;

    MutableLiveData<String> deletedCarStatusMutableLiveData = new MutableLiveData<>();

    public DeleteCarRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public void deleteCarRemote(int carId, String authHeader){

        deletedCarStatusMutableLiveData.postValue("Process Deleting ...");

        Call<DeleteCarResponse> initiate = apiService.deleteCar(carId, authHeader);

        initiate.enqueue(new Callback<DeleteCarResponse>() {
            @Override
            public void onResponse(Call<DeleteCarResponse> call, Response<DeleteCarResponse> response) {
                if(response.isSuccessful()){
                    deletedCarStatusMutableLiveData.postValue("Deleted Successfully\uD83C\uDF89");
                }
                else {
                    deletedCarStatusMutableLiveData.postValue("You Can't Delete This Car That Still in Branch");
                    Log.i("Delete Car", response.toString());
                }
            }

            @Override
            public void onFailure(Call<DeleteCarResponse> call, Throwable t) {
                deletedCarStatusMutableLiveData.postValue("Network Connection Failed, Try Again");
                Log.i("Delete Car", t.getMessage());
            }
        });
    }

    public MutableLiveData<String> getDeletedCarStatusMutableLiveData() {
        return deletedCarStatusMutableLiveData;
    }
}
