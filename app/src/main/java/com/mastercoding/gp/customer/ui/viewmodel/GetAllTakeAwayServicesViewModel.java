package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.data.Service;
import com.mastercoding.gp.customer.ui.repository.GetAllTakeAwayServicesRepository;

import java.util.List;

public class GetAllTakeAwayServicesViewModel extends ViewModel {

    GetAllTakeAwayServicesRepository takeAwayServicesRepository = new GetAllTakeAwayServicesRepository();

    public GetAllTakeAwayServicesViewModel() {
    }

    public LiveData<List<Service>> getAllTakeAwayServices(String authHeader){
        return takeAwayServicesRepository.getAllTakeAwayServicesMutableLiveData(authHeader);
    }
}
