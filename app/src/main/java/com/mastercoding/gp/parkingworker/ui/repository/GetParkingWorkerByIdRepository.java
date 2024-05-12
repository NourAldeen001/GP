package com.mastercoding.gp.parkingworker.ui.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetParkingWorkerByIdRepository {

    ApiService apiService;

    MutableLiveData<ParkingWorkerData> parkingWorkerDataMutableLiveData = new MutableLiveData<>();

    public GetParkingWorkerByIdRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<ParkingWorkerData> getParkingWorkerDataMutableLiveData(int workerId, String authHeader) {

        Call<ParkingWorkerData> initiate = apiService.getParkingWorkerById(workerId, authHeader);

        initiate.enqueue(new Callback<ParkingWorkerData>() {
            @Override
            public void onResponse(Call<ParkingWorkerData> call, Response<ParkingWorkerData> response) {
                ParkingWorkerData parkingWorkerData = response.body();
                if(response.isSuccessful() && parkingWorkerData != null){
                    parkingWorkerDataMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ParkingWorkerData> call, Throwable t) {

            }
        });

        return parkingWorkerDataMutableLiveData;
    }
}
