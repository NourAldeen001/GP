package com.mastercoding.gp.shareddata.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.shareddata.repository.DeleteNotificationByIdRepository;

public class DeleteNotificationByIdViewModel extends ViewModel {

    DeleteNotificationByIdRepository deleteNotificationByIdRepository = new DeleteNotificationByIdRepository();

    public DeleteNotificationByIdViewModel() {

    }

    public void deleteNotificationById(int notificationId, String authHeader){
        deleteNotificationByIdRepository.deleteNotificationByIdRemote(notificationId, authHeader);
    }

    public LiveData<String> deleteNotificationStatus(){
        return deleteNotificationByIdRepository.getDeleteNotificationStatusMutableLiveData();
    }
}
