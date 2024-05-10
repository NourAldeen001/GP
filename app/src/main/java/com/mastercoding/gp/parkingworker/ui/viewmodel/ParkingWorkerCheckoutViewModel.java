package com.mastercoding.gp.parkingworker.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.parkingworker.data.ParkingWorkerCheckoutBody;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerCheckoutResponse;
import com.mastercoding.gp.parkingworker.ui.repository.ParkingWorkerCheckoutRepository;

public class ParkingWorkerCheckoutViewModel extends ViewModel {

    ParkingWorkerCheckoutRepository parkingWorkerCheckoutRepository = new ParkingWorkerCheckoutRepository();

    public ParkingWorkerCheckoutViewModel() {

    }

    public LiveData<ParkingWorkerCheckoutResponse> checkout(ParkingWorkerCheckoutBody parkingWorkerCheckoutBody, String authHeader){
        return parkingWorkerCheckoutRepository.parkingWorkerCheckoutRemote(parkingWorkerCheckoutBody, authHeader);
    }

    public LiveData<String> getCheckoutStatus(){
        return parkingWorkerCheckoutRepository.getCheckoutStatusMutableLiveData();
    }
}
