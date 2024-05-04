package com.mastercoding.gp.customer.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.CustomDialogFragment;
import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.customer.data.CustomerData;
import com.mastercoding.gp.customer.data.CustomerProfileBody;
import com.mastercoding.gp.customer.ui.viewmodel.EditCustomerProfileViewModel;
import com.mastercoding.gp.customer.ui.viewmodel.GetCustomerByIdViewModel;
import com.mastercoding.gp.databinding.FragmentCustomerEditProfileBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class CustomerEditProfileFragment extends Fragment {

    FragmentCustomerEditProfileBinding binding;

    private ActivityResultLauncher<Intent> galleryLauncher;

    String imgBase64AfterGetFromGallery;

    String image;

    String currentImage;

    CustomDialogFragment dialogFragment;

    EditCustomerProfileViewModel editCustomerProfileViewModel;

    GetCustomerByIdViewModel customerByIdViewModel;

    SessionSharedPreferences sessionSharedPreferences;

    public CustomerEditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCustomerEditProfileBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        customerByIdViewModel = new ViewModelProvider(this).get(GetCustomerByIdViewModel.class);

        editCustomerProfileViewModel = new ViewModelProvider(this).get(EditCustomerProfileViewModel.class);

        dialogFragment = new CustomDialogFragment();

        String userName = sessionSharedPreferences.getUsername();
        String password = sessionSharedPreferences.getPass();

        String base = userName + ":" + password;

        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        customerByIdViewModel.getCustomerById(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<CustomerData>() {
            @Override
            public void onChanged(CustomerData customerData) {
                currentImage = customerData.getImage();
                if(currentImage != null){
                    Bitmap imgBitmap = convertBase64ToBitmap(customerData.getImage());
                    binding.customEditProfileImg.setImageBitmap(imgBitmap);
                }
                binding.customEditFullNameTxt.setText(String.format("%s %s", customerData.getFirstName(), customerData.getLastName()));
                binding.customEditFirstNameEdtxt.setText(customerData.getFirstName());
                binding.customEditLastNameEdtxt.setText(customerData.getLastName());
                binding.customEditPassEdtxt.setText(customerData.getPassword());
                binding.customEditEmailEdtxt.setText(customerData.getEmail());
                binding.customEditGenderEdtxt.setText(customerData.getGender());
                binding.customEditBirthEdtxt.setText(customerData.getBirthday());
                binding.customEditPhoneEdtxt.setText(customerData.getPhoneNumber());
            }
        });

        binding.customEditProfileSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgBase64AfterGetFromGallery != null){
                    image = imgBase64AfterGetFromGallery;
                }
                else if(currentImage != null){
                    image = currentImage;
                }

                String firstName = binding.customEditFirstNameEdtxt.getText().toString();
                String lastName = binding.customEditLastNameEdtxt.getText().toString();
                String email = binding.customEditEmailEdtxt.getText().toString();
                String password = binding.customEditPassEdtxt.getText().toString();
                String birth = binding.customEditBirthEdtxt.getText().toString();
                String phone = binding.customEditPhoneEdtxt.getText().toString();
                String gender = binding.customEditGenderEdtxt.getText().toString();
                dialogFragment.show(getChildFragmentManager(), "Custom Dialog");
                editCustomerProfileViewModel.editCustomerProfile(sessionSharedPreferences.getID(), authHeader,
                        new CustomerProfileBody(firstName, lastName, password, email, image, phone, birth, gender));

                if(sessionSharedPreferences.getRole() != password){
                    sessionSharedPreferences.setPass(password);
                }

            }
        });

        editCustomerProfileViewModel.getUpdatedCustomerStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (dialogFragment != null && dialogFragment.isAdded()) {
                    dialogFragment.updateMessage(message);
                }
            }
        });


        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    if(data != null){
                        Uri imageUri = data.getData();
                        if(imageUri != null){
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                                binding.customEditProfileImg.setImageBitmap(bitmap);
                                imgBase64AfterGetFromGallery = convertBitmapToBase64(bitmap);
                            }
                            catch(IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        binding.customEditProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        binding.customEditProfileCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_customerEditProfileFragment_to_customerHomeFragment);
            }
        });

        binding.customEditProfileBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        return binding.getRoot();
    }


    private void openGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(galleryIntent);
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }


}