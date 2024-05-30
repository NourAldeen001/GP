package com.mastercoding.gp.shareddata.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.shareddata.data.Notification;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllNotificationByUserIdRepository {

    ApiService apiService;

    private MutableLiveData<List<Notification>> getAllNotificationMutableLiveData = new MutableLiveData<>();

    public GetAllNotificationByUserIdRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<List<Notification>> getGetAllNotificationMutableLiveData(int userId, String authHeader) {

        Call<List<Notification>> initiate = apiService.getAllNotificationsByUserId(userId, authHeader);

        initiate.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if(response.isSuccessful()){
                    getAllNotificationMutableLiveData.setValue(response.body());
                    Log.i("GetAllNotifications", "Success");
                }
                else {
                    Log.i("GetAllNotifications", "Fail1");
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                Log.i("GetAllNotifications", "Fail2 : " + t.getCause());
            }
        });

        return getAllNotificationMutableLiveData;
    }
}
