package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.data.CustomerCarData;
import com.mastercoding.gp.customer.data.CustomerCarDataBody;
import com.mastercoding.gp.customer.ui.repository.UpdateCarRepository;

public class UpdateCarViewModel extends ViewModel {

    UpdateCarRepository updateCarRepository = new UpdateCarRepository();

    public UpdateCarViewModel() {
    }

    public LiveData<CustomerCarData> updateCar(int carId, CustomerCarDataBody customerCarDataBody, String authHeader){
        return updateCarRepository.getUpdatedCustomerCarDataMutableLiveData(carId, customerCarDataBody, authHeader);
    }

    public LiveData<String> getUpdateCarStatus(){
        return updateCarRepository.getUpdatedCustomerCarDataStatus();
    }
}
