package com.mastercoding.gp.parkingworker.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.parkingworker.data.ParkingWorkerFinishTaskBody;
import com.mastercoding.gp.parkingworker.ui.repository.ParkingWorkerFinishTaskRepository;

public class ParkingWorkerFinishTaskViewModel extends ViewModel {

    ParkingWorkerFinishTaskRepository parkingWorkerFinishTaskRepository = new ParkingWorkerFinishTaskRepository();

    public ParkingWorkerFinishTaskViewModel() {

    }

    public void finishTask(ParkingWorkerFinishTaskBody parkingWorkerFinishTaskBody, String authHeader){
        parkingWorkerFinishTaskRepository.parkingWorkerFinishTaskRemote(parkingWorkerFinishTaskBody, authHeader);
    }

    public LiveData<String> getFinishTaskStatus(){
        return parkingWorkerFinishTaskRepository.getFinishTaskStatusMutableLiveData();
    }
}
