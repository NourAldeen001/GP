package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.data.Package;
import com.mastercoding.gp.customer.ui.repository.GetAllPackagesRepository;

import java.util.List;

public class GetAllPackagesViewModel extends ViewModel {

    GetAllPackagesRepository getAllPackagesRepository = new GetAllPackagesRepository();

    public GetAllPackagesViewModel() {

    }

    public LiveData<List<Package>> getAllPackages(String authHeader){
        return getAllPackagesRepository.getAllPackagesMutableLiveData(authHeader);
    }
}
