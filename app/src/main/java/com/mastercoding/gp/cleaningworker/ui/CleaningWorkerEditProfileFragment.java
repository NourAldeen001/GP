package com.mastercoding.gp.cleaningworker.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercoding.gp.CustomDialogFragment;
import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.databinding.FragmentCleaningWorkerEditProfileBinding;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerData;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerProfileBody;
import com.mastercoding.gp.parkingworker.ui.viewmodel.EditParkingWorkerProfileViewModel;
import com.mastercoding.gp.parkingworker.ui.viewmodel.GetParkingWorkerByIdViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class CleaningWorkerEditProfileFragment extends Fragment {

   FragmentCleaningWorkerEditProfileBinding binding;

    private ActivityResultLauncher<Intent> galleryLauncher;

    String imgBase64AfterGetFromGallery;

    String image;

    String currentImage;

    GetParkingWorkerByIdViewModel parkingWorkerByIdViewModel;

    EditParkingWorkerProfileViewModel editParkingWorkerProfileViewModel;

    CustomDialogFragment dialogFragment;

    SessionSharedPreferences sessionSharedPreferences;

    public CleaningWorkerEditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCleaningWorkerEditProfileBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        parkingWorkerByIdViewModel = new ViewModelProvider(this).get(GetParkingWorkerByIdViewModel.class);

        editParkingWorkerProfileViewModel = new ViewModelProvider(this).get(EditParkingWorkerProfileViewModel.class);

        dialogFragment = new CustomDialogFragment();

        String userName = sessionSharedPreferences.getUsername();
        String password = sessionSharedPreferences.getPass();

        String base = userName + ":" + password;

        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        parkingWorkerByIdViewModel.getParkingWorkerById(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<ParkingWorkerData>() {
            @Override
            public void onChanged(ParkingWorkerData parkingWorkerData) {
                currentImage = parkingWorkerData.getImage();
                if(currentImage != null){
                    Bitmap imgBitmap = convertBase64ToBitmap(parkingWorkerData.getImage());
                    binding.cleaningWorkerEditProfileImg.setImageBitmap(imgBitmap);
                }
                binding.cleaningWorkerEditFullNameTxt.setText(String.format("%s %s", parkingWorkerData.getFirstName(), parkingWorkerData.getLastName()));
                binding.cleaningWorkerEditFirstNameEdtxt.setText(parkingWorkerData.getFirstName());
                binding.cleaningWorkerEditLastNameEdtxt.setText(parkingWorkerData.getLastName());
                binding.cleaningWorkerEditPassEdtxt.setText(parkingWorkerData.getPassword());
                binding.cleaningWorkerEditEmailEdtxt.setText(parkingWorkerData.getEmail());
                binding.cleaningWorkerEditGenderEdtxt.setText(parkingWorkerData.getGender());
                binding.cleaningWorkerEditBirthEdtxt.setText(parkingWorkerData.getBirthday());
                binding.cleaningWorkerEditPhoneEdtxt.setText(parkingWorkerData.getPhoneNumber());
            }
        });

        binding.cleaningWorkerEditProfileSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgBase64AfterGetFromGallery != null){
                    image = imgBase64AfterGetFromGallery;
                    Log.i("imgBase64AfterGetFromGallery", "imgBase64AfterGetFromGallery");
                }
                else if(currentImage != null){
                    image = currentImage;
                    Log.i("currentImage", "currentImage");
                }
                String firstName = binding.cleaningWorkerEditFirstNameEdtxt.getText().toString();
                String lastName = binding.cleaningWorkerEditLastNameEdtxt.getText().toString();
                String email = binding.cleaningWorkerEditEmailEdtxt.getText().toString();
                String password = binding.cleaningWorkerEditPassEdtxt.getText().toString();
                String birth = binding.cleaningWorkerEditBirthEdtxt.getText().toString();
                String phone = binding.cleaningWorkerEditPhoneEdtxt.getText().toString();
                String gender = binding.cleaningWorkerEditGenderEdtxt.getText().toString();
                dialogFragment.show(getChildFragmentManager(), "Edit Profile Dialog");
                editParkingWorkerProfileViewModel.editParkingWorkerProfile(sessionSharedPreferences.getID(),
                        new ParkingWorkerProfileBody(firstName, lastName, password, email, image, phone, birth, gender), authHeader);

                binding.cleaningWorkerEditFullNameTxt.setText(String.format("%s %s", firstName, lastName));

                if(!(sessionSharedPreferences.getPass().equals(password))){
                    sessionSharedPreferences.setPass(password);
                }
            }
        });

        editParkingWorkerProfileViewModel.getEditProfileDataStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
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
                                binding.cleaningWorkerEditProfileImg.setImageBitmap(bitmap);
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

        binding.cleaningWorkerEditProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        binding.cleaningWorkerEditProfileCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.cleaningWorkerHomeFragment);
            }
        });

        binding.cleaningWorkerEditProfileBackBtn.setOnClickListener(new View.OnClickListener() {
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