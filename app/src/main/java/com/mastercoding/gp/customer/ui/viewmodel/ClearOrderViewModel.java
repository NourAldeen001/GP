package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.data.ClearOrderResponse;
import com.mastercoding.gp.customer.ui.repository.ClearOrderRepository;

public class ClearOrderViewModel extends ViewModel {

    ClearOrderRepository clearOrderRepository = new ClearOrderRepository();

    public ClearOrderViewModel() {
    }

    public void clearOrder(int customerId, String authHeader){
        clearOrderRepository.clearOrderRemote(customerId, authHeader);
    }
}
