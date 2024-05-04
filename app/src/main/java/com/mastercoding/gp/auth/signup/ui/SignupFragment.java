package com.mastercoding.gp.auth.signup.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.CustomDialogFragment;
import com.mastercoding.gp.R;
import com.mastercoding.gp.databinding.FragmentSignupBinding;


public class SignupFragment extends Fragment {

    private FragmentSignupBinding binding;

    SignUpViewModel signUpViewModel;

    CustomDialogFragment dialogFragment;

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater, container, false);

        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialogFragment = new CustomDialogFragment();

        signUpViewModel.getSignUpMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (dialogFragment != null && dialogFragment.isAdded()) {
                    dialogFragment.updateMessage(message);
                }
            }
        });

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = binding.signupFirstNameEditTxt.getText().toString();
                String lastName = binding.signupLastNameEditTxt.getText().toString();
                String userName = binding.signupUsernameEditTxt.getText().toString();
                String password = binding.signupPassEditTxt.getText().toString();
                dialogFragment.show(getChildFragmentManager(), "Custom Dialog");
                signUpViewModel.signUp(firstName, lastName, userName, password);
                signUpViewModel.getSignUpStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean signUpStatus) {
                        if(signUpStatus){
                            Navigation.findNavController(view).navigate(R.id.action_signupFragment_to_loginFragment);
                        }
                    }
                });
            }
        });

        binding.signupToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view)
                        .navigate(R.id.action_signupFragment_to_loginFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}