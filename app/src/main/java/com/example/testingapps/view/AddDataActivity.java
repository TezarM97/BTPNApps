package com.example.testingapps.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testingapps.R;
import com.example.testingapps.data.DataContact;
import com.example.testingapps.data.DataContactProfile;
import com.example.testingapps.network.ApiClient;
import com.example.testingapps.network.ApiServices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDataActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etAge;
    LinearLayout llCamera, llGaleri, llSnapPicture;
    ImageButton ibAddCamera, ibAddGaleri;
    TextView tvHeadPicture;
    ImageView ivHasilPotret;
    Button btnSave;
    AppCompatButton btnReset;
    ProgressDialog progressDialog;
    String namaFoto;
    final int REQUEST_GALLERY = 9544;
    final int REQUEST_GALLERY2 = 9545;
    final int REQUEST_CAMERA_CODE = 100;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "You must accept this permission", Toast.LENGTH_LONG).show();

            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        etFirstName = findViewById(R.id.et_firstname);
        etLastName = findViewById(R.id.et_lastname);
        etAge = findViewById(R.id.et_age);
        ivHasilPotret = findViewById(R.id.iv_yourpicture);
        ibAddCamera = findViewById(R.id.ib_addcamera);
        ibAddGaleri = findViewById(R.id.ib_addgaleri);
        tvHeadPicture = findViewById(R.id.tv_headingpicture);
        llCamera = findViewById(R.id.ll_addcamera);
        llGaleri = findViewById(R.id.ll_addgaleri);
        llSnapPicture = findViewById(R.id.ll_snappicture);
        btnReset = findViewById(R.id.btn_reset);
        btnSave = findViewById(R.id.btn_save);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");

        progressDialog.setIndeterminate(true);
        progressDialog.setIndeterminateDrawable(getDrawable(R.drawable.custom_progress_dialog));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaDepan = etFirstName.getText().toString();
                String namaBelakang = etLastName.getText().toString();
                String umurUser = etAge.getText().toString();

                if (namaDepan.isEmpty()) {
                    etFirstName.setError("Please field first name ");
                } else if (namaBelakang.isEmpty()) {
                    etLastName.setError("Please field last name");
                } else if (umurUser.isEmpty()) {
                    etAge.setError("Please field your age");
                } else if (namaFoto == "") {
                    Toast.makeText(AddDataActivity.this, "Please take a picture", Toast.LENGTH_SHORT).show();
                } else {
                    saveData(namaDepan, namaBelakang, umurUser, namaFoto);
                }

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llCamera.setVisibility(View.VISIBLE);
                llGaleri.setVisibility(View.VISIBLE);
                llSnapPicture.setVisibility(View.GONE);
                tvHeadPicture.setText("Take a picture with");
                etFirstName.setText("");
                etLastName.setText("");
                etAge.setText("");
            }
        });

        ibAddCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llCamera.setVisibility(View.GONE);
                llGaleri.setVisibility(View.GONE);
                llSnapPicture.setVisibility(View.VISIBLE);
                tvHeadPicture.setText("Your Profile Picture");

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                try {
                    startActivityForResult(takePictureIntent, REQUEST_GALLERY2);
                } catch (ActivityNotFoundException e) {
                    Log.d("AddDataActivity", "onClick: Error = " + e);
                }

            }
        });

        ibAddGaleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llCamera.setVisibility(View.GONE);
                llGaleri.setVisibility(View.GONE);
                llSnapPicture.setVisibility(View.VISIBLE);
                tvHeadPicture.setText("Your Profile Picture");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Buka Galeri"), REQUEST_GALLERY);
            }
        });


        ImageButton btnBack = findViewById(R.id.ib_buttonback);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddDataActivity.this, MenuUtamaActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveData(String namaDepan, String namaBelakang, String umurUser, String namaFoto) {
        try {
            progressDialog.show();
            File fotoProfile = new File(namaFoto);
            RequestBody rNameDepan = RequestBody.create(MediaType.parse("text/plain"), namaDepan);

            RequestBody rNamaBelakang = RequestBody.create(MediaType.parse("text/plain"), namaBelakang);

            RequestBody rUmurUser = RequestBody.create(MediaType.parse("text/plain"), umurUser);
            RequestBody rNamaFoto = RequestBody.create(MediaType.parse("text/plain"), namaFoto);

            ApiServices apiServices = ApiClient.getInstance().create(ApiServices.class);
            Call<DataContactProfile> customerCall = apiServices.postDataBaru(rNameDepan, rNamaBelakang, rUmurUser, rNamaFoto);
            customerCall.enqueue(new Callback<DataContactProfile>() {
                @Override
                public void onResponse(Call<DataContactProfile> call, Response<DataContactProfile> response) {
                    if (response.code() == 201) {
                        progressDialog.dismiss();
                        Toast.makeText(AddDataActivity.this, "Success, Saving Data", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddDataActivity.this, MenuUtamaActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(AddDataActivity.this, "Failed, your data unsaved", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DataContactProfile> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(AddDataActivity.this, "Failed, your data unsaved", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(AddDataActivity.this, "Error, Please check your connection", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image1 = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        // Save a file: path for use with ACTION_VIEW intents
        namaFoto = image1.getAbsolutePath();

        return image1;
    }

    private File createImageFile2() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        namaFoto = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALLERY) {
                try {
                    // Creating file
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        Log.d(AddDataActivity.class.getSimpleName(), "Error occurred while creating the file");
                    }

                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(photoFile);
                    // Copying
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();

                    ivHasilPotret.setImageBitmap(BitmapFactory.decodeFile(namaFoto));

                } catch (Exception e) {
                    Toast.makeText(AddDataActivity.this, "Failed, please take a picture again..", Toast.LENGTH_SHORT).show();
                    llCamera.setVisibility(View.VISIBLE);
                    llGaleri.setVisibility(View.VISIBLE);
                    llSnapPicture.setVisibility(View.GONE);
                    tvHeadPicture.setText("Take a picture with");
                    Log.d(AddDataActivity.class.getSimpleName(), "onActivityResult: " + e.toString());
                }

            }
            if (requestCode == REQUEST_GALLERY2) {

                try {


//                    Log.d("Data Foto Porfil", "onActivityResult: "+extras);
//                    File photoFile2 = null;
//                    try {
//                        photoFile2 = createImageFile2();
//                    } catch (IOException ex) {
//                        Log.d(AddDataActivity.class.getSimpleName(), "Error occurred while creating the file");
//                    }
//
//                    InputStream inputStream2 = getContentResolver().openInputStream(data.getData());
//                    FileOutputStream fileOutputStream2 = new FileOutputStream(photoFile2);
//                    // Copying
//                    copyStream2(inputStream2, fileOutputStream2);
//                    fileOutputStream2.close();
//                    inputStream2.close();
//
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ivHasilPotret.setImageBitmap(imageBitmap);
                    savebitmap(imageBitmap, data);
                    Log.d("Data Nama Bitmap", "onActivityResult: " + namaFoto);

//                    ivHasilPotret.setImageBitmap(BitmapFactory.decodeFile(namaFoto));
//                    Log.d("Nama Foto Camera", "onActivityResult: "+namaFoto);
                } catch (Exception e) {
                    Toast.makeText(AddDataActivity.this, "Failed, please take a picture again", Toast.LENGTH_SHORT).show();
                    llCamera.setVisibility(View.VISIBLE);
                    llGaleri.setVisibility(View.VISIBLE);
                    llSnapPicture.setVisibility(View.GONE);
                    tvHeadPicture.setText("Take a picture with");
                    Log.d(AddDataActivity.class.getSimpleName(), "onActivityResult: " + e.toString());
                }
            }

        }


    }

    private File savebitmap(Bitmap bmp, Intent data) throws IOException {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        // String temp = null;

        File file = new File(extStorageDirectory, "fotoprofil.jpg");
        Log.d("BITMAP ", "savebitmap: " + file);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(extStorageDirectory);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        namaFoto = file.getAbsolutePath();

        Log.d("Nama Foto Bitmap", "savebitmap: " + namaFoto);
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, "fotoprofil.jpg");


        }


        return file;
    }

    private void copyStream(InputStream inputStream, FileOutputStream fileOutputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, bytesRead);
        }

    }

    private void copyStream2(InputStream inputStream, FileOutputStream fileOutputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, bytesRead);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddDataActivity.this, MenuUtamaActivity.class);
        startActivity(intent);
        finish();
    }
}