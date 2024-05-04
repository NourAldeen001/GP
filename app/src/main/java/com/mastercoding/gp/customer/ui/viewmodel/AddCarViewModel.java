package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.data.CustomerCarData;
import com.mastercoding.gp.customer.data.CustomerCarDataBody;
import com.mastercoding.gp.customer.ui.repository.AddCarRepository;

public class AddCarViewModel extends ViewModel {

    AddCarRepository addCarRepository = new AddCarRepository();

    public AddCarViewModel() {
    }

    public LiveData<CustomerCarData> addCar(CustomerCarDataBody customerCarDataBody, String authHeader){
        return addCarRepository.getAddedCustomerCarDataMutableLiveData(customerCarDataBody, authHeader);
    }

    public LiveData<String> getAddCarStatus(){
        return addCarRepository.getAddedCustomerCarDataStatus();
    }
}
