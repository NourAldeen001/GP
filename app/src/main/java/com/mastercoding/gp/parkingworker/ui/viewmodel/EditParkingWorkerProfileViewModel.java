package com.mastercoding.gp.parkingworker.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.parkingworker.data.ParkingWorkerProfileBody;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerProfileData;
import com.mastercoding.gp.parkingworker.ui.repository.EditParkingWorkerProfileRepository;

public class EditParkingWorkerProfileViewModel extends ViewModel {

    EditParkingWorkerProfileRepository editParkingWorkerProfileRepository = new EditParkingWorkerProfileRepository();

    public EditParkingWorkerProfileViewModel() {

    }

    public LiveData<ParkingWorkerProfileData> editParkingWorkerProfile(int workerId, ParkingWorkerProfileBody parkingWorkerProfileBody, String authHeader){
        return editParkingWorkerProfileRepository.getParkingWorkerProfileDataMutableLiveData(workerId, parkingWorkerProfileBody, authHeader);
    }

    public LiveData<String> getEditProfileDataStatus(){
        return editParkingWorkerProfileRepository.getEditParkingWorkerDataStatus();
    }
}
