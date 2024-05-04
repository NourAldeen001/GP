package com.mastercoding.gp.auth.login.ui;

import android.app.Application;
import android.util.Log;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.auth.login.data.LoginBody;
import com.mastercoding.gp.auth.login.data.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    ApiService apiService;

    Application application;

    public LoginRepository(Application application) {
        this.application = application;
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public void loginRemote(LoginBody loginBody, ILoginResponse iLoginResponse){

        Call<LoginResponse> initiate = apiService.login(loginBody);

        initiate.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    iLoginResponse.onResponse(response.body());
                    Log.i("Success", response.message());
                }
                else{
                    iLoginResponse.onFailure(new Throwable(response.message()));
                    Log.i("FAIL1", response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                iLoginResponse.onFailure(t);
                Log.i("FAIL2", t.getMessage());
            }
        });

    }

    public interface ILoginResponse{
        void onResponse(LoginResponse loginResponse);
        void onFailure(Throwable t);
    }
}
