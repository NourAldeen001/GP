package com.mastercoding.gp.parkingworker.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;
import com.mastercoding.gp.CustomDialogFragment;
import com.mastercoding.gp.R;
import com.mastercoding.gp.SessionSharedPreferences;
import com.mastercoding.gp.customer.ui.CustomerHomeFragmentDirections;
import com.mastercoding.gp.databinding.FragmentParkingWorkerHomeBinding;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerFinishTaskBody;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerGetCapacityResponse;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerGetCountVisitationResponse;
import com.mastercoding.gp.parkingworker.data.ParkingWorkerRecordBody;
import com.mastercoding.gp.parkingworker.ui.viewmodel.ParkingWorkerFinishTaskViewModel;
import com.mastercoding.gp.parkingworker.ui.viewmodel.ParkingWorkerGetCapacityOfBranchViewModel;
import com.mastercoding.gp.parkingworker.ui.viewmodel.ParkingWorkerGetCountVisitationOfBranchViewModel;
import com.mastercoding.gp.parkingworker.ui.viewmodel.ParkingWorkerRecordViewModel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class ParkingWorkerHomeFragment extends Fragment {

    FragmentParkingWorkerHomeBinding binding;

    ParkingWorkerRecordViewModel parkingWorkerRecordViewModel;

    ParkingWorkerFinishTaskViewModel parkingWorkerFinishTaskViewModel;

    ParkingWorkerGetCountVisitationOfBranchViewModel parkingWorkerGetCountVisitationOfBranchViewModel;

    ParkingWorkerGetCapacityOfBranchViewModel parkingWorkerGetCapacityOfBranchViewModel;

    SessionSharedPreferences sessionSharedPreferences;

    CustomDialogFragment dialogFragment;

    String userName, password, base, authHeader;

    private Bitmap imageBitmap;

    private ActivityResultLauncher<Intent> someActivityResultLauncher;

    private ActivityResultLauncher<String> requestPermissionLauncher;

    private final Set<String> countriesToFilter = new HashSet<>(Arrays.asList("EGYPT", "USA", "KSA"));

    public ParkingWorkerHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentParkingWorkerHomeBinding.inflate(inflater, container, false);

        sessionSharedPreferences = new SessionSharedPreferences(getContext());

        parkingWorkerFinishTaskViewModel = new ViewModelProvider(this).get(ParkingWorkerFinishTaskViewModel.class);

        parkingWorkerRecordViewModel = new ViewModelProvider(this).get(ParkingWorkerRecordViewModel.class);

        parkingWorkerGetCountVisitationOfBranchViewModel = new ViewModelProvider(this).get(ParkingWorkerGetCountVisitationOfBranchViewModel.class);

        parkingWorkerGetCapacityOfBranchViewModel = new ViewModelProvider(this).get(ParkingWorkerGetCapacityOfBranchViewModel.class);

        dialogFragment = new CustomDialogFragment();

        userName = sessionSharedPreferences.getUsername();
        password = sessionSharedPreferences.getPass();

        base = userName + ":" + password;

        authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        parkingWorkerGetCountVisitationOfBranchViewModel.getCountVisitationOfBranch(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<ParkingWorkerGetCountVisitationResponse>() {
            @Override
            public void onChanged(ParkingWorkerGetCountVisitationResponse parkingWorkerGetCountVisitationResponse) {
                parkingWorkerGetCapacityOfBranchViewModel.parkingWorkerGetCapacityOfBranch(sessionSharedPreferences.getID(), authHeader).observe(getViewLifecycleOwner(), new Observer<ParkingWorkerGetCapacityResponse>() {
                    @Override
                    public void onChanged(ParkingWorkerGetCapacityResponse parkingWorkerGetCapacityResponse) {
                        Log.i("Capacity", "Capacity : " + parkingWorkerGetCapacityResponse.getCapacity());
                        binding.parkingWorkerHomeTotalCarToCapacity.setText(String.format("%s / %s", parkingWorkerGetCountVisitationResponse.getVisitationCount(), parkingWorkerGetCapacityResponse.getCapacity()));
                    }
                });
            }
        });



        binding.parkingWorkerHomeRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment.show(getChildFragmentManager(), "Record Dialog");
                String carPlateNumber = binding.parkingWorkerHomeCarPlateEditTxt.getText().toString();
                parkingWorkerRecordViewModel.record(new ParkingWorkerRecordBody(carPlateNumber, Integer.toString(sessionSharedPreferences.getID())), authHeader);
            }
        });

        parkingWorkerRecordViewModel.getRecordStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if(dialogFragment != null && dialogFragment.isAdded()){
                    dialogFragment.updateMessage(message);
                }
            }
        });

        binding.parkingWorkerHomeCheckoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String carPlateNumber = binding.parkingWorkerHomeCarPlateEditTxt.getText().toString();
                navigateToParkingWorkerCheckoutFragment(carPlateNumber);
            }
        });

        binding.parkingWorkerHomeFinishTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment.show(getChildFragmentManager(), "Finish Task Dialog");
                String carPlateNumber = binding.parkingWorkerHomeCarPlateEditTxt.getText().toString();
                parkingWorkerFinishTaskViewModel.finishTask(new ParkingWorkerFinishTaskBody(carPlateNumber, Integer.toString(sessionSharedPreferences.getID())), authHeader);
            }
        });

        parkingWorkerFinishTaskViewModel.getFinishTaskStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if(dialogFragment != null && dialogFragment.isAdded()){
                    dialogFragment.updateMessage(message);
                }
            }
        });

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == requireActivity().RESULT_OK) {
                            Intent data = result.getData();
                            assert data != null;
                            Bundle extras = data.getExtras();
                            imageBitmap = (Bitmap) extras.get("data");
                            detectText();
                        }
                    }
                });

        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        Toast.makeText(requireContext(), "Permissions Granted..", Toast.LENGTH_SHORT).show();
                        captureImage();
                    } else {
                        Toast.makeText(requireContext(), "Permissions Denied..", Toast.LENGTH_SHORT).show();
                    }
                });

        binding.parkingWorkerHomeCameraBtn.setOnClickListener(view -> {
            if (checkPermissions()) {
                captureImage();
            } else {
                requestPermission();
            }
        });

        return binding.getRoot();
    }

    private boolean checkPermissions() {
        int cameraPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA);
        return cameraPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA);
    }


    private void captureImage() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(requireActivity().getPackageManager()) != null) {
            someActivityResultLauncher.launch(takePicture);
        }
    }

    private void detectText() {
        if (imageBitmap == null) {
            Toast.makeText(requireContext(), "Please capture an image first", Toast.LENGTH_SHORT).show();
            return;
        }
        InputImage image = InputImage.fromBitmap(imageBitmap, 0);
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        recognizer.process(image)
                .addOnSuccessListener(text -> {
                    StringBuilder result = new StringBuilder();
                    for(Text.TextBlock block: text.getTextBlocks()){
                        String blockText = block.getText();
                        result.append(filterCountries(blockText));
                    }
                    binding.parkingWorkerHomeCarPlateEditTxt.setText(result);
                })
                .addOnFailureListener(e -> Toast.makeText(requireContext(), "Failed to detect text from image: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private String filterCountries(String text) {
        String[] words = text.split("\\s+");
        StringBuilder filteredText = new StringBuilder();
        for (String word : words) {
            if (!countriesToFilter.contains(word)) {
                filteredText.append(word);
            }
        }
        return filteredText.toString().trim();
    }

    private void navigateToParkingWorkerCheckoutFragment(String carPlateNumber) {
        // Use Safe Args to pass data to another fragment
        ParkingWorkerHomeFragmentDirections.ActionParkingWorkerHomeFragmentToParkingWorkerCheckoutFragment action =
                ParkingWorkerHomeFragmentDirections.actionParkingWorkerHomeFragmentToParkingWorkerCheckoutFragment(carPlateNumber);
        Navigation.findNavController(requireView()).navigate(action);
    }
}