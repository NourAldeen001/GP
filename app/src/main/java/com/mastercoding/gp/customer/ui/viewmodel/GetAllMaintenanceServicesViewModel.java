package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.data.Service;
import com.mastercoding.gp.customer.ui.repository.GetAllMaintenanceServicesRepository;

import java.util.List;

public class GetAllMaintenanceServicesViewModel extends ViewModel {

    GetAllMaintenanceServicesRepository maintenanceServicesRepository = new GetAllMaintenanceServicesRepository();

    public GetAllMaintenanceServicesViewModel() {
    }

    public LiveData<List<Service>> getAllMaintenanceServices(String authHeader){
        return maintenanceServicesRepository.getAllMaintenanceServicesMutableLiveData(authHeader);
    }
}
