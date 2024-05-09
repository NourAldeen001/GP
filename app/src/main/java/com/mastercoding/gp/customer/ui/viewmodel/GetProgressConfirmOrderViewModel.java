package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.data.GetProgressConfirmOrderResponse;
import com.mastercoding.gp.customer.ui.repository.GetProgressConfirmOrderRepository;

public class GetProgressConfirmOrderViewModel extends ViewModel {

    GetProgressConfirmOrderRepository confirmOrderRepository = new GetProgressConfirmOrderRepository();

    public GetProgressConfirmOrderViewModel() {

    }

    public LiveData<GetProgressConfirmOrderResponse> getProgressConfirmOrder(int customerId, String authHeader){
        return confirmOrderRepository.getProgressConfirmOrderResponseMutableLiveData(customerId, authHeader);
    }
}
