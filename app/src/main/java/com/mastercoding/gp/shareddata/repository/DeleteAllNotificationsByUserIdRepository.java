package com.mastercoding.gp.shareddata.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.shareddata.data.DeleteNotificationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteAllNotificationsByUserIdRepository {

    ApiService apiService;

    private MutableLiveData<String> deleteAllNotificationsStatusMutableLiveData = new MutableLiveData<>();

    public DeleteAllNotificationsByUserIdRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public void deleteAllNotificationsByUserIdRemote(int userId, String authHeader){

        deleteAllNotificationsStatusMutableLiveData.postValue("Process Deleting ...");

        Call<DeleteNotificationResponse> initiate = apiService.deleteAllNotificationsByUserId(userId, authHeader);

        initiate.enqueue(new Callback<DeleteNotificationResponse>() {
            @Override
            public void onResponse(Call<DeleteNotificationResponse> call, Response<DeleteNotificationResponse> response) {
                if(response.isSuccessful()){
                    deleteAllNotificationsStatusMutableLiveData.postValue("Deleted Successfully\uD83C\uDF89");
                }
                else {
                    deleteAllNotificationsStatusMutableLiveData.postValue("You Can't Delete All if There Notification Not Open");
                }
            }

            @Override
            public void onFailure(Call<DeleteNotificationResponse> call, Throwable t) {
                deleteAllNotificationsStatusMutableLiveData.postValue("Network Connection Failed, Try Again");
            }
        });

    }

    public MutableLiveData<String> getDeleteAllNotificationsStatusMutableLiveData() {
        return deleteAllNotificationsStatusMutableLiveData;
    }
}
