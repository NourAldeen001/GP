package com.mastercoding.gp.parkingworker.ui.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerProfileBody;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerProfileData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditParkingWorkerProfileRepository {

    ApiService apiService;

    private MutableLiveData<ParkingWorkerProfileData> parkingWorkerProfileDataMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<String> editParkingWorkerDataStatus = new MutableLiveData<>();

    public EditParkingWorkerProfileRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<ParkingWorkerProfileData> getParkingWorkerProfileDataMutableLiveData(int workerId, ParkingWorkerProfileBody parkingWorkerProfileBody, String authHeader) {

        editParkingWorkerDataStatus.postValue("Process Updating ...");

        Call<ParkingWorkerProfileData> initiate = apiService.editParkingWorkerProfile(workerId, parkingWorkerProfileBody, authHeader);

        initiate.enqueue(new Callback<ParkingWorkerProfileData>() {
            @Override
            public void onResponse(@NonNull Call<ParkingWorkerProfileData> call, @NonNull Response<ParkingWorkerProfileData> response) {
                if(response.isSuccessful()){
                    editParkingWorkerDataStatus.postValue("Updated Successfully\uD83C\uDF89");
                    parkingWorkerProfileDataMutableLiveData.setValue(response.body());
                }
                else {
                    editParkingWorkerDataStatus.postValue("Sorry, Try Again");
                    Log.i("ParkingWorkerEditProfile", response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ParkingWorkerProfileData> call, @NonNull Throwable t) {
                editParkingWorkerDataStatus.postValue("Network Connection Failed, Try Again");
            }
        });

        return parkingWorkerProfileDataMutableLiveData;
    }

    public MutableLiveData<String> getEditParkingWorkerDataStatus() {
        return editParkingWorkerDataStatus;
    }
}
