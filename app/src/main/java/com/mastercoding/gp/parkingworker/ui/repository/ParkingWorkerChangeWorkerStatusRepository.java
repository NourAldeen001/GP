package com.mastercoding.gp.parkingworker.ui.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerActiveStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingWorkerChangeWorkerStatusRepository {

    ApiService apiService;

    public ParkingWorkerChangeWorkerStatusRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public void changeWorkerStatusRemote(int workerId, String authHeader){

        Call<ParkingWorkerActiveStatus> initiate = apiService.parkingWorkerChangeWorkerStatus(workerId, authHeader);

        initiate.enqueue(new Callback<ParkingWorkerActiveStatus>() {
            @Override
            public void onResponse(Call<ParkingWorkerActiveStatus> call, Response<ParkingWorkerActiveStatus> response) {
                if(response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<ParkingWorkerActiveStatus> call, Throwable t) {
            }
        });
    }

}
