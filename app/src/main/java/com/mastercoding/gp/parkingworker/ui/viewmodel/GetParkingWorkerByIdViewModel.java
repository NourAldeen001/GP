package com.mastercoding.gp.parkingworker.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.parkingworker.data.ParkingWorkerData;
import com.mastercoding.gp.parkingworker.ui.repository.GetParkingWorkerByIdRepository;

public class GetParkingWorkerByIdViewModel extends ViewModel {

    GetParkingWorkerByIdRepository getParkingWorkerByIdRepository = new GetParkingWorkerByIdRepository();

    public GetParkingWorkerByIdViewModel() {

    }

    public LiveData<ParkingWorkerData> getParkingWorkerById(int workerId, String authHeader){
        return getParkingWorkerByIdRepository.getParkingWorkerDataMutableLiveData(workerId, authHeader);
    }
}
