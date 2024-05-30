package com.mastercoding.gp.shareddata.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.shareddata.data.Notification;
import com.mastercoding.gp.shareddata.repository.GetAllNotificationByUserIdRepository;

import java.util.List;

public class GetAllNotificationByUserIdViewModel extends ViewModel {

    GetAllNotificationByUserIdRepository getAllNotificationByUserIdRepository = new GetAllNotificationByUserIdRepository();

    public GetAllNotificationByUserIdViewModel() {

    }

    public LiveData<List<Notification>> getAllNotificationByUserId(int userId, String authHeader) {
        return getAllNotificationByUserIdRepository.getGetAllNotificationMutableLiveData(userId, authHeader);
    }
}
