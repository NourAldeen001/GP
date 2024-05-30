package com.mastercoding.gp.shareddata.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.shareddata.repository.DeleteAllNotificationsByUserIdRepository;

public class DeleteAllNotificationsByUserIdViewModel extends ViewModel {

    DeleteAllNotificationsByUserIdRepository deleteAllNotificationsByUserIdRepository = new DeleteAllNotificationsByUserIdRepository();

    public DeleteAllNotificationsByUserIdViewModel() {

    }

    public void deleteAllNotificationsByUserId(int userId, String authHeader){
        deleteAllNotificationsByUserIdRepository.deleteAllNotificationsByUserIdRemote(userId, authHeader);
    }

    public LiveData<String> deleteAllNotificationsStatus(){
        return deleteAllNotificationsByUserIdRepository.getDeleteAllNotificationsStatusMutableLiveData();
    }
}
