package com.mastercoding.gp.shareddata.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCountUnOpenedByUserIdRepository {

    ApiService apiService;

    private MutableLiveData<Integer> countUnopenedMutableLiveData = new MutableLiveData<>();

    public GetCountUnOpenedByUserIdRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<Integer> getCountUnopenedMutableLiveData(int userId, String authHeader) {

        Call<Integer> initiate = apiService.getCountUnopenedByUserId(userId, authHeader);

        initiate.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    countUnopenedMutableLiveData.setValue(response.body());
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });

        return countUnopenedMutableLiveData;
    }
}
