package com.mastercoding.gp.customer.ui.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.customer.data.Package;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllPackagesRepository {

    ApiService apiService;

    private MutableLiveData<List<Package>> allPackagesMutableLiveData = new MutableLiveData<>();

    public GetAllPackagesRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<List<Package>> getAllPackagesMutableLiveData(String authHeader) {

        Call<List<Package>> initiate = apiService.getAllPackages(authHeader);

        initiate.enqueue(new Callback<List<Package>>() {
            @Override
            public void onResponse(Call<List<Package>> call, Response<List<Package>> response) {
                if(response.isSuccessful()){
                    allPackagesMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Package>> call, Throwable t) {

            }
        });
        return allPackagesMutableLiveData;
    }
}
