package com.mastercoding.gp.customer.ui.viewmodel;

import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.customer.ui.repository.DeletePackageFromOrderListRepository;

public class DeletePackageFromOrderListViewModel extends ViewModel {

    DeletePackageFromOrderListRepository deletePackageFromOrderListRepository = new DeletePackageFromOrderListRepository();

    public DeletePackageFromOrderListViewModel() {

    }

    public void deletePackageFromOrderList(int packageId, String authHeader){
        deletePackageFromOrderListRepository.deletePackageFromOrderListRemote(packageId, authHeader);
    }
}
