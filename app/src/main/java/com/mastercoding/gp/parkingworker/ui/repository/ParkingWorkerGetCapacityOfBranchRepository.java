package com.mastercoding.gp.parkingworker.ui.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerGetCapacityResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingWorkerGetCapacityOfBranchRepository {

    ApiService apiService;

    private MutableLiveData<ParkingWorkerGetCapacityResponse> parkingWorkerGetCapacityResponseMutableLiveData = new MutableLiveData<>();

    public ParkingWorkerGetCapacityOfBranchRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<ParkingWorkerGetCapacityResponse> parkingWorkerGetCapacityRemote(int workerId, String authHeader){

        Call<ParkingWorkerGetCapacityResponse> initiate = apiService.parkingWorkerGetCapacityOfBranch(workerId, authHeader);

        initiate.enqueue(new Callback<ParkingWorkerGetCapacityResponse>() {
            @Override
            public void onResponse(@NonNull Call<ParkingWorkerGetCapacityResponse> call, @NonNull Response<ParkingWorkerGetCapacityResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    parkingWorkerGetCapacityResponseMutableLiveData.setValue(response.body());
                    Log.i("parkingWorkerGetCapacityRemote", "Success");
                }
                else {
                    Log.i("parkingWorkerGetCapacityRemote", "Fail1");
                }
            }

            @Override
            public void onFailure(Call<ParkingWorkerGetCapacityResponse> call, Throwable t) {
                Log.i("parkingWorkerGetCapacityRemote", "Fail2: " + t.getMessage() );
            }
        });

        return parkingWorkerGetCapacityResponseMutableLiveData;
    }

}
