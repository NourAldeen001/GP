package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.ui.repository.ConfirmOrderRepository;

public class ConfirmOrderViewModel extends ViewModel {

    ConfirmOrderRepository confirmOrderRepository = new ConfirmOrderRepository();

    public ConfirmOrderViewModel() {

    }

    public void confirmOrder(int customerId, String authHeader){
        confirmOrderRepository.confirmOrderRemote(customerId, authHeader);
    }

    public LiveData<String> getConfirmOrderStatus(){
        return confirmOrderRepository.getConfirmOrderStatusMutableLiveData();
    }
}
