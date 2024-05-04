package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.data.CustomerCarData;
import com.mastercoding.gp.customer.ui.repository.GetCarByCustomerIdRepository;

public class GetCarByCustomerIdViewModel extends ViewModel {

    GetCarByCustomerIdRepository getCarByCustomerIdRepository = new GetCarByCustomerIdRepository();

    public GetCarByCustomerIdViewModel() {
    }

    public LiveData<CustomerCarData> getCarByCustomerId(int customerId, String authHeader){
        return getCarByCustomerIdRepository.getCustomerCarDataMutableLiveData(customerId, authHeader);
    }
}
