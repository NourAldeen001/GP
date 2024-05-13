package com.mastercoding.gp.parkingworker.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.parkingworker.ui.repository.ParkingWorkerChangeWorkerStatusRepository;

public class ParkingWorkerChangeWorkerStatusViewModel extends ViewModel {

    ParkingWorkerChangeWorkerStatusRepository parkingWorkerChangeWorkerStatusRepository = new ParkingWorkerChangeWorkerStatusRepository();

    public ParkingWorkerChangeWorkerStatusViewModel() {

    }

    public void parkingWorkerChangeWorkerStatus(int workerId, String authHeader){
        parkingWorkerChangeWorkerStatusRepository.changeWorkerStatusRemote(workerId, authHeader);
    }

}
