package com.mastercoding.gp.parkingworker.ui.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerGetCountVisitationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingWorkerGetCountVisitationOfBranchRepository {

    ApiService apiService;

    private MutableLiveData<ParkingWorkerGetCountVisitationResponse> parkingWorkerGetCountVisitationResponseMutableLiveData = new MutableLiveData<>();

    public ParkingWorkerGetCountVisitationOfBranchRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<ParkingWorkerGetCountVisitationResponse> parkingWorkerGetCountVisitationRemote(int workerId, String authHeader){

        Call<ParkingWorkerGetCountVisitationResponse> initiate = apiService.parkingWorkerGetCountVisitationOfBranch(workerId, authHeader);

        initiate.enqueue(new Callback<ParkingWorkerGetCountVisitationResponse>() {
            @Override
            public void onResponse(@NonNull Call<ParkingWorkerGetCountVisitationResponse> call, @NonNull Response<ParkingWorkerGetCountVisitationResponse> response) {
                if(response.isSuccessful()){
                    parkingWorkerGetCountVisitationResponseMutableLiveData.setValue(response.body());
                    Log.i("parkingWorkerGetCountVisitationRemote", "Success");
                }
                else {
                    Log.i("parkingWorkerGetCountVisitationRemote", "Fail1");
                }
            }

            @Override
            public void onFailure(Call<ParkingWorkerGetCountVisitationResponse> call, Throwable t) {
                Log.i("parkingWorkerGetCountVisitationRemote", "Fail2 :" + t.getMessage() );
            }
        });

        return parkingWorkerGetCountVisitationResponseMutableLiveData;
    }
}
