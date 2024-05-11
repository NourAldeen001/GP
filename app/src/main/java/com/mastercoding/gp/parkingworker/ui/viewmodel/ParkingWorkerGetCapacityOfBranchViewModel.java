package com.mastercoding.gp.parkingworker.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.parkingworker.data.ParkingWorkerGetCapacityResponse;
import com.mastercoding.gp.parkingworker.ui.repository.ParkingWorkerGetCapacityOfBranchRepository;

public class ParkingWorkerGetCapacityOfBranchViewModel extends ViewModel {

    ParkingWorkerGetCapacityOfBranchRepository parkingWorkerGetCapacityOfBranchRepository = new ParkingWorkerGetCapacityOfBranchRepository();

    public ParkingWorkerGetCapacityOfBranchViewModel() {

    }

    public LiveData<ParkingWorkerGetCapacityResponse> parkingWorkerGetCapacityOfBranch(int workerId, String authHeader){
        return parkingWorkerGetCapacityOfBranchRepository.parkingWorkerGetCapacityRemote(workerId, authHeader);
    }
}
