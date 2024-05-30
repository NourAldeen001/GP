package com.mastercoding.gp.shareddata.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.shareddata.data.DeleteNotificationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteNotificationByIdRepository {

    ApiService apiService;

    private MutableLiveData<String> deleteNotificationStatusMutableLiveData = new MutableLiveData<>();

    public DeleteNotificationByIdRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public void deleteNotificationByIdRemote(int notificationId, String authHeader){

        deleteNotificationStatusMutableLiveData.postValue("Process Deleting ...");

        Call<DeleteNotificationResponse> initiate = apiService.deleteNotificationById(notificationId, authHeader);

        initiate.enqueue(new Callback<DeleteNotificationResponse>() {
            @Override
            public void onResponse(Call<DeleteNotificationResponse> call, Response<DeleteNotificationResponse> response) {
                if(response.isSuccessful()){
                    deleteNotificationStatusMutableLiveData.postValue("Deleted Successfully\uD83C\uDF89");
                }
                else {
                    deleteNotificationStatusMutableLiveData.postValue("You Can't Delete it if it doesn't Open");
                }
            }

            @Override
            public void onFailure(Call<DeleteNotificationResponse> call, Throwable t) {
                deleteNotificationStatusMutableLiveData.postValue("Network Connection Failed, Try Again");
            }
        });
    }

    public MutableLiveData<String> getDeleteNotificationStatusMutableLiveData() {
        return deleteNotificationStatusMutableLiveData;
    }
}
