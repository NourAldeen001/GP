package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.data.Service;
import com.mastercoding.gp.customer.ui.repository.GetServiceByIdAndBranchByIdRepository;


public class GetServiceByIdAndBranchByIdViewModel extends ViewModel {

    GetServiceByIdAndBranchByIdRepository getServiceByIdAndBranchByIdRepository = new GetServiceByIdAndBranchByIdRepository();

    public GetServiceByIdAndBranchByIdViewModel() {

    }

    public LiveData<Service> getServiceByIdAndBranchId(int serviceId, Long branchId, String authHeader){
        return getServiceByIdAndBranchByIdRepository.getServiceByIdAndBranchIdMutableLiveData(serviceId, branchId, authHeader);
    }
}
