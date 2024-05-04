package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.ui.repository.DeleteCarRepository;

public class DeleteCarViewModel extends ViewModel {

    DeleteCarRepository deleteCarRepository = new DeleteCarRepository();

    public DeleteCarViewModel() {
    }

    public void deleteCar(int carId, String authHeader){
        deleteCarRepository.deleteCarRemote(carId, authHeader);
    }

    public LiveData<String> getDeleteStatus(){
        return deleteCarRepository.getDeletedCarStatusMutableLiveData();
    }
}
