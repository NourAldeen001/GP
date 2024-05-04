package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.data.CustomerData;
import com.mastercoding.gp.customer.data.CustomerProfileBody;
import com.mastercoding.gp.customer.ui.repository.EditCustomerProfileRepository;

public class EditCustomerProfileViewModel extends ViewModel {

    EditCustomerProfileRepository editCustomerProfileRepository = new EditCustomerProfileRepository();

    public EditCustomerProfileViewModel() {

    }

    public LiveData<CustomerData> editCustomerProfile(int customerId, String authHeader, CustomerProfileBody customerProfileBody){
        return editCustomerProfileRepository.getUpdatedCustomerDataMutableLiveData(customerId, authHeader, customerProfileBody);
    }

    public LiveData<String> getUpdatedCustomerStatus(){
        return editCustomerProfileRepository.getUpdatedCustomerDataStatus();
    }
}
