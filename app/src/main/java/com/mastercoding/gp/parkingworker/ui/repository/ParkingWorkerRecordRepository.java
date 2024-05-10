package com.mastercoding.gp.parkingworker.ui.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerRecordBody;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerRecordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingWorkerRecordRepository {

    ApiService apiService;

    MutableLiveData<String> recordStatusMutableLiveData = new MutableLiveData<>();

    public ParkingWorkerRecordRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public void parkingWorkerRecordRemote(ParkingWorkerRecordBody parkingWorkerRecordBody, String authHeader){

        recordStatusMutableLiveData.postValue("Process Recording ...");

        Call<ParkingWorkerRecordResponse> initiate = apiService.parkingWorkerRecord(parkingWorkerRecordBody, authHeader);

        initiate.enqueue(new Callback<ParkingWorkerRecordResponse>() {
            @Override
            public void onResponse(Call<ParkingWorkerRecordResponse> call, Response<ParkingWorkerRecordResponse> response) {
                if(response.isSuccessful()){
                    recordStatusMutableLiveData.postValue("Record Successfully\uD83C\uDF89");
                }
                else {
                    recordStatusMutableLiveData.postValue("This Car with This Plate Number Already Recorded");
                }
            }

            @Override
            public void onFailure(Call<ParkingWorkerRecordResponse> call, Throwable t) {
                recordStatusMutableLiveData.postValue("Network Connection Failed, Try Again");
            }
        });
    }

    public MutableLiveData<String> getRecordStatusMutableLiveData() {
        return recordStatusMutableLiveData;
    }
}
