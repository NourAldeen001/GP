package com.mastercoding.gp.parkingworker.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.parkingworker.data.ParkingWorkerGetCountVisitationResponse;
import com.mastercoding.gp.parkingworker.ui.repository.ParkingWorkerGetCountVisitationOfBranchRepository;

public class ParkingWorkerGetCountVisitationOfBranchViewModel extends ViewModel {

    ParkingWorkerGetCountVisitationOfBranchRepository parkingWorkerGetCountVisitationOfBranchRepository = new ParkingWorkerGetCountVisitationOfBranchRepository();

    public ParkingWorkerGetCountVisitationOfBranchViewModel() {

    }

    public LiveData<ParkingWorkerGetCountVisitationResponse> getCountVisitationOfBranch(int workerId, String authHeader){
        return parkingWorkerGetCountVisitationOfBranchRepository.parkingWorkerGetCountVisitationRemote(workerId, authHeader);
    }
}
