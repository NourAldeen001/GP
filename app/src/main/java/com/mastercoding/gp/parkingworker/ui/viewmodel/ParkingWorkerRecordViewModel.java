package com.mastercoding.gp.parkingworker.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.parkingworker.data.ParkingWorkerRecordBody;
import com.mastercoding.gp.parkingworker.ui.repository.ParkingWorkerRecordRepository;

public class ParkingWorkerRecordViewModel extends ViewModel {

    ParkingWorkerRecordRepository parkingWorkerRecordRepository = new ParkingWorkerRecordRepository();

    public ParkingWorkerRecordViewModel() {

    }

    public void record(ParkingWorkerRecordBody parkingWorkerRecordBody, String authHeader){
        parkingWorkerRecordRepository.parkingWorkerRecordRemote(parkingWorkerRecordBody, authHeader);
    }

    public LiveData<String> getRecordStatus(){
        return parkingWorkerRecordRepository.getRecordStatusMutableLiveData();
    }
}
