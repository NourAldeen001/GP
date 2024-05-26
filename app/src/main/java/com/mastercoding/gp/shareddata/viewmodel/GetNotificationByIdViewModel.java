package com.mastercoding.gp.shareddata.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.shareddata.Notification;
import com.mastercoding.gp.shareddata.repository.GetAllNotificationByUserIdRepository;
import com.mastercoding.gp.shareddata.repository.GetNotificationByIdRepository;

public class GetNotificationByIdViewModel extends ViewModel {

    GetNotificationByIdRepository getNotificationByUserIdRepository = new GetNotificationByIdRepository();

    public GetNotificationByIdViewModel() {

    }

    public LiveData<Notification> getNotificationById(int notificationId, String authHeader){
        return getNotificationByUserIdRepository.getNotificationMutableLiveData(notificationId, authHeader);
    }
}
