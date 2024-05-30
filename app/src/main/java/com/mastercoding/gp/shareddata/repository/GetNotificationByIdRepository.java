package com.mastercoding.gp.shareddata.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.shareddata.data.Notification;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetNotificationByIdRepository {

    ApiService apiService;

    private MutableLiveData<Notification> notificationMutableLiveData = new MutableLiveData<>();

    public GetNotificationByIdRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<Notification> getNotificationMutableLiveData(int notificationId, String authHeader) {

        Call<Notification> initiate = apiService.getNotificationById(notificationId, authHeader);

        initiate.enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                if(response.isSuccessful()){
                    notificationMutableLiveData.setValue(response.body());
                    Log.i("GetNotificationById", "Success");
                }
                else {
                    Log.i("GetNotificationById", "Fail1");
                }
            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {
                Log.i("GetNotificationById", "Fail2");
            }
        });
        return notificationMutableLiveData;
    }
}
