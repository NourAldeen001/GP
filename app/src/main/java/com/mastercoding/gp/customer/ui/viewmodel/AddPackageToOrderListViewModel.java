package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.data.AddPackageToOrderListBody;
import com.mastercoding.gp.customer.ui.repository.AddPackageToOrderListRepository;

public class AddPackageToOrderListViewModel extends ViewModel {

    AddPackageToOrderListRepository addPackageToOrderListRepository = new AddPackageToOrderListRepository();

    public AddPackageToOrderListViewModel() {

    }

    public void addPackageToOrderList(AddPackageToOrderListBody addPackageToOrderListBody, String authHeader){
        addPackageToOrderListRepository.addPackageToOrderListRemote(addPackageToOrderListBody, authHeader);
    }

    public LiveData<String> getAddPackageToOrderListStatus(){
        return addPackageToOrderListRepository.getAddedPackageToOrderListStatusMutableLiveData();
    }
}
