package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.data.Service;
import com.mastercoding.gp.customer.ui.repository.GetAllCleaningServicesRepository;

import java.util.List;

public class GetAllCleaningServicesViewModel extends ViewModel {

    GetAllCleaningServicesRepository cleaningServicesRepository = new GetAllCleaningServicesRepository();

    public GetAllCleaningServicesViewModel() {

    }

    public LiveData<List<Service>> getAllCleaningServices(String authHeader){
        return cleaningServicesRepository.getAllCleaningServicesMutableLiveData(authHeader);
    }
}
