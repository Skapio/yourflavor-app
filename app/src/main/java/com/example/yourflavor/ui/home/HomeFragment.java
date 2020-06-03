package com.example.yourflavor.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

//            final Bitmap photo = (Bitmap) data.getExtras().get("data");
//            File file = savebitmap(photo);
//
//            PhotoService photoService = ApiHelper.getRetrofit().create(PhotoService.class);
//
//            MultipartBody.Part filePart = MultipartBody.Part.createFormData("photo", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
//
//            Call<Void> call = photoService.getPhoto();
//            call.enqueue(new Callback<Void>() {
//                @Override
//                public void onResponse(Call<Void> call, Response<Void> response) {
//                    Void json = response.body();
//                    imageView.setImageBitmap(photo);
//                    Toast.makeText(getActivity(), "Success",  Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onFailure(Call<Void> call, Throwable t) {
//                    t.getLocalizedMessage();
//                }
//            });

        }
    }

    private File createImageFile(){
        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/App Folder/";

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "AppName_" + timeStamp;

        String file = dir +imageFileName+ ".jpg" ;
        File imageFile = new File(file);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {


            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

//    private File savebitmap(Bitmap bmp) {
//        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
//        OutputStream outStream = null;
//        // String temp = null;
//        File file = new File(extStorageDirectory, "temp.png");
//        if (file.exists()) {
//            file.delete();
//            file = new File(extStorageDirectory, "temp.png");
//
//        }
//
//        try {
//            outStream = new FileOutputStream(file);
//            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
//            outStream.flush();
//            outStream.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        return file;
//    }

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
                    Toast.makeText(getContext(), "Added successfully", Toast.LENGTH_LONG).show();
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

//    private File createImageFile() throws IOException {
        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );

        // Save a file: path for use with ACTION_VIEW intents
//        currentPhotoPath = image.getAbsolutePath();
//        return image;
//    }

//    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {
//
//        File direct = new File(Environment.getExternalStorageDirectory() + "/DirName");
//
//        if (!direct.exists()) {
//            File wallpaperDirectory = new File("/sdcard/DirName/");
//            wallpaperDirectory.mkdirs();
//        }
//
//        File file = new File("/sdcard/DirName/", fileName);
//        if (file.exists()) {
//            file.delete();
//        }
//        try {
//            FileOutputStream out = new FileOutputStream(file);
//            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
