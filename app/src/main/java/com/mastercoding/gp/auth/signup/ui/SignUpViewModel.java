package com.mastercoding.gp.auth.signup.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mastercoding.gp.auth.signup.data.SignUpBody;
import com.mastercoding.gp.auth.signup.data.SignUpResponse;


public class SignUpViewModel extends ViewModel {

    MutableLiveData<String> signUpMessageMutableLiveData = new MutableLiveData<>();

    MutableLiveData<Boolean> signUpStatusMutableLiveData = new MutableLiveData<>();

    SignUpRepository signUpRepository = new SignUpRepository();

    public SignUpViewModel() {
        signUpStatusMutableLiveData.postValue(false);
    }

    public void signUp(String firstName, String lastName, String userName, String password){
        signUpMessageMutableLiveData.postValue("Loading...");

        signUpRepository.signUpRemote(new SignUpBody(firstName, lastName, userName, password), new SignUpRepository.ISignUpResponse() {
            @Override
            public void onResponse(SignUpResponse signUpResponse) {
                signUpMessageMutableLiveData.postValue("Successfully\uD83C\uDF89");
                signUpStatusMutableLiveData.postValue(true);
            }

            @Override
            public void onFailure(Throwable t) {
                signUpMessageMutableLiveData.postValue("Invalid, Please Try Again");
            }
        });
    }

    public LiveData<String> getSignUpMessage() {
        return signUpMessageMutableLiveData;
    }

    public LiveData<Boolean> getSignUpStatus() {
        return signUpStatusMutableLiveData;
    }
}
