package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.ui.repository.DeleteServiceFromOrderListRepository;

public class DeleteServiceFromOrderListViewModel extends ViewModel {

    DeleteServiceFromOrderListRepository deleteServiceFromOrderListRepository = new DeleteServiceFromOrderListRepository();

    public DeleteServiceFromOrderListViewModel() {

    }

    public void deleteServiceFromOrderList(int serviceId, String authHeader){
        deleteServiceFromOrderListRepository.deleteServiceFromOrderListRemote(serviceId, authHeader);
    }
}
