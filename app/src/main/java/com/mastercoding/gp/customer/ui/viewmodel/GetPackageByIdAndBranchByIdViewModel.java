package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.data.Package;
import com.mastercoding.gp.customer.ui.repository.GetPackageByIdAndBranchByIdRepository;

public class GetPackageByIdAndBranchByIdViewModel extends ViewModel {

    GetPackageByIdAndBranchByIdRepository getPackageByIdAndBranchByIdRepository = new GetPackageByIdAndBranchByIdRepository();

    public GetPackageByIdAndBranchByIdViewModel() {

    }

    public LiveData<Package> getPackageByIdAndBranchId(int packageId, Long branchId, String authHeader){
        return getPackageByIdAndBranchByIdRepository.getPackageByIdAndBranchIdMutableLiveData(packageId, branchId, authHeader);
    }
}
