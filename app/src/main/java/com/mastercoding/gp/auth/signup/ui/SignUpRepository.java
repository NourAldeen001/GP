package com.mastercoding.gp.auth.signup.ui;

import android.util.Log;

import com.mastercoding.gp.api.ApiService;
import com.mastercoding.gp.api.RetrofitInstance;
import com.mastercoding.gp.auth.signup.data.SignUpBody;
import com.mastercoding.gp.auth.signup.data.SignUpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpRepository {

    ApiService apiService;

    public SignUpRepository() {
        this.apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public void signUpRemote(SignUpBody signUpBody, ISignUpResponse iSignUpResponse){
        Call<SignUpResponse> initiate = apiService.signup(signUpBody);

        initiate.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if(response.isSuccessful()){
                    iSignUpResponse.onResponse(response.body());
                    Log.i("SignUp Success", response.message());
                }
                else {
                    iSignUpResponse.onFailure(new Throwable(response.message()));
                    Log.i("SignUp Fail1", response.message());
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                iSignUpResponse.onFailure(t);
                Log.i("SignUp Fail2", t.getMessage());
            }
        });

    }

    public interface ISignUpResponse{
        void onResponse(SignUpResponse signUpResponse);
        void onFailure(Throwable t);
    }
}
