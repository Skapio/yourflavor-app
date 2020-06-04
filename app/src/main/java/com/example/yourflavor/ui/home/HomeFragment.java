package com.example.yourflavor.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.yourflavor.MainActivity;
import com.example.yourflavor.R;
import com.example.yourflavor.entity.UserFoodCollection;
import com.example.yourflavor.request.AddUserFoodCollectionRequest;
import com.example.yourflavor.service.HomeService;
import com.example.yourflavor.service.PhotoService;
import com.example.yourflavor.service.UserFoodCollectionService;
import com.example.yourflavor.util.ApiHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    static final int REQUEST_IMAGE_CAPTURE = 1;


    String mCurrentPhotoPath;

    ImageView imageView;
    Button pictureButton, addButton;
    EditText mCountry, mCity, mRestaurantName, mRestaurantAddress;
    RatingBar mRatingBar;
    AutoCompleteTextView mDishName;

    private HomeService mHomeService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        imageView = root.findViewById(R.id.image_view);
        pictureButton = (Button) root.findViewById(R.id.photo_button);
        addButton = (Button) root.findViewById(R.id.save_button);
        mCountry = (EditText) root.findViewById(R.id.countryMy);
        mCity = (EditText) root.findViewById(R.id.cityMy);
        mRestaurantName = (EditText) root.findViewById(R.id.restaurantName);
        mRestaurantAddress = (EditText) root.findViewById(R.id.restaurantAddress);
        mRatingBar = (RatingBar) root.findViewById(R.id.ratingBar3);
        mDishName = (AutoCompleteTextView) root.findViewById(R.id.autoDishName);

        mHomeService = ApiHelper.getHomeService();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserFoodCollection(createRequest());
            }
        });

        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        return root;
    }

    private String getRandomFileName() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<10; ++i) {
           int nextChar = random.nextInt() % 74 + 48;
           sb.append(nextChar);
        }

        return sb.toString() + ".png";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            File file = new File(currentPhotoPath);

            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                        getContext().getContentResolver(), Uri.fromFile(file));
                imageView.setImageBitmap(thumbnail);
            } catch (IOException e) {

            }
        }
    }

    private void uploadPhoto(String photoPath, Integer userFoodCollectionId) {
        File file = new File(photoPath);
        RequestBody filePart = RequestBody.create(file, MediaType.parse("image/*"));

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", getRandomFileName(), filePart);

        PhotoService photoService = ApiHelper.getRetrofit().create(PhotoService.class);

        Call<ResponseBody> call = photoService.sendPhoto(body, userFoodCollectionId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.v("onResponseonResponse", ""+response.code());

                Toast.makeText(getContext(), "Added successfully", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.v("onFailureonFailure", ""+t.getLocalizedMessage());
            }
        });
    }

    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        Log.v("createImageFile", currentPhotoPath);
        return image;
    }

    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.yourflavor",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public AddUserFoodCollectionRequest createRequest(){
        AddUserFoodCollectionRequest addUserFoodCollectionRequest = new AddUserFoodCollectionRequest();

        addUserFoodCollectionRequest.setCountry(mCountry.getText().toString());
        addUserFoodCollectionRequest.setCity(mCity.getText().toString());
        addUserFoodCollectionRequest.setRestaurantName(mRestaurantName.getText().toString());
        addUserFoodCollectionRequest.setRestaurantAddress(mRestaurantAddress.getText().toString());
        addUserFoodCollectionRequest.setRate((int) mRatingBar.getRating());
        addUserFoodCollectionRequest.setAppFoodCollectionId(1);

        return addUserFoodCollectionRequest;
    }

    public void saveUserFoodCollection(AddUserFoodCollectionRequest addUserFoodCollectionRequest)
    {
        Call<UserFoodCollection> userFoodCollectionCall = ApiHelper.getHomeService().addUserFoodCollectionItems(addUserFoodCollectionRequest);
        userFoodCollectionCall.enqueue(new Callback<UserFoodCollection>() {
            @Override
            public void onResponse(Call<UserFoodCollection> call, Response<UserFoodCollection> response) {

                if (response.isSuccessful())
                {
                    uploadPhoto(currentPhotoPath, response.body().getUserFoodCollectionId());
                }
                else {
                    Toast.makeText(getContext(), "Added failed xyz", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserFoodCollection> call, Throwable t) {
                Toast.makeText(getContext(), "Added failed "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
