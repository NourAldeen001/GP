package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.data.AddServiceToOrderListBody;
import com.mastercoding.gp.customer.ui.repository.AddServiceToOrderListRepository;

public class AddServiceToOrderListViewModel extends ViewModel {

    AddServiceToOrderListRepository addServiceToOrderListRepository = new AddServiceToOrderListRepository();

    public AddServiceToOrderListViewModel() {

    }

    public void addServiceToOrderList(AddServiceToOrderListBody addServiceToOrderListBody, String authHeader){
        addServiceToOrderListRepository.addServiceToOrderListRemote(addServiceToOrderListBody, authHeader);
    }

    public LiveData<String> getAddServiceToOrderListStatus(){
        return addServiceToOrderListRepository.getAddedServiceToOrderListStatusMutableLiveData();
    }
}
