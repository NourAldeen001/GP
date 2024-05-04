package com.mastercoding.gp.auth.login.ui;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mastercoding.gp.auth.login.data.LoginBody;
import com.mastercoding.gp.auth.login.data.LoginResponse;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<String> loginMessageMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<LoginResponse> loginResponseMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<Boolean> isLoggedInMutableLiveData = new MutableLiveData<>();

    private SharedPreferences loginStatusSharedPreferences;

    LoginRepository loginRepository;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository = new LoginRepository(application);
        loginStatusSharedPreferences = application.getSharedPreferences("IS_LOGGED", Context.MODE_PRIVATE);
        isLoggedInMutableLiveData.setValue(isLoggedIn());
    }

    public void login(String userName, String password){
        loginMessageMutableLiveData.postValue("Checking...");
        loginRepository.loginRemote(new LoginBody(userName, password), new LoginRepository.ILoginResponse() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
                loginMessageMutableLiveData.postValue("Successfully\uD83C\uDF89");
                loginResponseMutableLiveData.setValue(loginResponse);
                SharedPreferences.Editor editor = loginStatusSharedPreferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.apply();
                isLoggedInMutableLiveData.postValue(true);
            }

            @Override
            public void onFailure(Throwable t) {
                loginMessageMutableLiveData.postValue("Invalid, Please Try Again");
            }
        });
    }


    public void logout() {
        // Clear the login status from SharedPreferences
        Log.v("NOUR", "inside Logout");
        SharedPreferences.Editor editor = loginStatusSharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        // Update LiveData to reflect the new login status
        isLoggedInMutableLiveData.postValue(false);
    }
    private boolean isLoggedIn() {
        // Retrieve the login status from SharedPreferences
        return loginStatusSharedPreferences.getBoolean("isLoggedIn", false);
    }

    public LiveData<Boolean> getIsLoggedIn(){
        return isLoggedInMutableLiveData;
    }

    public LiveData<LoginResponse> getLoginResponse(){
        return loginResponseMutableLiveData;
    }

    public LiveData<String> getLoginMessage() {
        return loginMessageMutableLiveData;
    }
}
