package com.mastercoding.gp.shareddata.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.shareddata.repository.GetCountUnOpenedByUserIdRepository;

public class GetCountUnOpenedByUserIdViewModel extends ViewModel {

    GetCountUnOpenedByUserIdRepository getCountUnOpenedByUserIdRepository = new GetCountUnOpenedByUserIdRepository();

    public GetCountUnOpenedByUserIdViewModel() {

    }

    public LiveData<Integer> getCountUnopenedByUserId(int userId, String authHeader){
        return getCountUnOpenedByUserIdRepository.getCountUnopenedMutableLiveData(userId, authHeader);
    }
}
