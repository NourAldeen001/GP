package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.data.CustomerData;
import com.mastercoding.gp.customer.ui.repository.GetCustomerByIdRepository;

public class GetCustomerByIdViewModel extends ViewModel {

    GetCustomerByIdRepository getCustomerByIdRepository = new GetCustomerByIdRepository();

    public GetCustomerByIdViewModel() {
    }

    public LiveData<CustomerData> getCustomerById(int customerId, String authHeader){
        return getCustomerByIdRepository.getCustomerDataMutableLiveData(customerId, authHeader);
    }

}
