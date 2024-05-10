package com.mastercoding.gp.parkingworker.ui.repository;

import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerCheckoutBody;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerCheckoutResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingWorkerCheckoutRepository {

    ApiService apiService;

    private MutableLiveData<ParkingWorkerCheckoutResponse> checkoutDataResponse = new MutableLiveData<>();

    private MutableLiveData<String> checkoutStatusMutableLiveData = new MutableLiveData<>();

    public ParkingWorkerCheckoutRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<ParkingWorkerCheckoutResponse> parkingWorkerCheckoutRemote(ParkingWorkerCheckoutBody parkingWorkerCheckoutBody, String authHeader){

        checkoutStatusMutableLiveData.postValue("Process Checkout ...");

        Call<ParkingWorkerCheckoutResponse> initiate = apiService.parkingWorkerCheckout(parkingWorkerCheckoutBody, authHeader);

        initiate.enqueue(new Callback<ParkingWorkerCheckoutResponse>() {
            @Override
            public void onResponse(Call<ParkingWorkerCheckoutResponse> call, Response<ParkingWorkerCheckoutResponse> response) {
                if(response.isSuccessful()){
                    checkoutDataResponse.setValue(response.body());
                    checkoutStatusMutableLiveData.postValue("Checkout Successfully\uD83C\uDF89");
                }
                else {
                    checkoutStatusMutableLiveData.postValue("This Car doesn't exist Or Already Checkout");
                }
            }

            @Override
            public void onFailure(Call<ParkingWorkerCheckoutResponse> call, Throwable t) {
                checkoutStatusMutableLiveData.postValue("Network Connection Failed, Try Again");
            }
        });
        return checkoutDataResponse;
    }

    public MutableLiveData<String> getCheckoutStatusMutableLiveData() {
        return checkoutStatusMutableLiveData;
    }
}
