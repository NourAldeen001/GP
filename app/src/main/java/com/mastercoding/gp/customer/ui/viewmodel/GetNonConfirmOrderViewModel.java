package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.data.OrderList;
import com.mastercoding.gp.customer.ui.repository.GetNonConfirmOrderRepository;

public class GetNonConfirmOrderViewModel extends ViewModel {

    GetNonConfirmOrderRepository getNonConfirmOrderRepository = new GetNonConfirmOrderRepository();

    public GetNonConfirmOrderViewModel() {

    }

    public LiveData<OrderList> getNonConfirmOrder(int customerId, Long branchId, String authHeader){
        return getNonConfirmOrderRepository.getNonConfirmOrderListMutableLiveData(customerId, branchId, authHeader);
    }
}
