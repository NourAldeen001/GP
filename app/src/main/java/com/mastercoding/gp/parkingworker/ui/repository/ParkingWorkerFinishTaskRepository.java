package com.mastercoding.gp.parkingworker.ui.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerFinishTaskBody;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerFinishTaskResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingWorkerFinishTaskRepository {

    ApiService apiService;

    MutableLiveData<String> finishTaskStatusMutableLiveData = new MutableLiveData<>();

    public ParkingWorkerFinishTaskRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public void parkingWorkerFinishTaskRemote(ParkingWorkerFinishTaskBody parkingWorkerFinishTaskBody, String authHeader){

        finishTaskStatusMutableLiveData.postValue("Process Finishing ...");

        Call<ParkingWorkerFinishTaskResponse> initiate = apiService.parkingWorkerFinishTask(parkingWorkerFinishTaskBody, authHeader);

        initiate.enqueue(new Callback<ParkingWorkerFinishTaskResponse>() {
            @Override
            public void onResponse(Call<ParkingWorkerFinishTaskResponse> call, Response<ParkingWorkerFinishTaskResponse> response) {
                if(response.isSuccessful()){
                    finishTaskStatusMutableLiveData.postValue("Finished Successfully\uD83C\uDF89");
                }
                else {
                    finishTaskStatusMutableLiveData.postValue("You doesn't work on This Car");
                }
            }

            @Override
            public void onFailure(Call<ParkingWorkerFinishTaskResponse> call, Throwable t) {
                finishTaskStatusMutableLiveData.postValue("Network Connection Failed, Try Again");
            }
        });
    }

    public MutableLiveData<String> getFinishTaskStatusMutableLiveData() {
        return finishTaskStatusMutableLiveData;
    }
}
