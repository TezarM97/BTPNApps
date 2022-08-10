package com.example.testingapps.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.testingapps.R;
import com.example.testingapps.data.DataContactProfile;
import com.example.testingapps.network.ApiClient;
import com.example.testingapps.network.ApiServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDataActivity extends AppCompatActivity {

    ImageView ivProfileUpdate;
    EditText etFirstName, etLastName, etUmur;
    FloatingActionButton fab_updatefoto;
    AppCompatButton btnReset;
    Button btnUpdate;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        etFirstName = findViewById(R.id.et_update_firstname);
        etLastName = findViewById(R.id.et_update_lastname);
        etUmur = findViewById(R.id.et_update_age);
        ivProfileUpdate = findViewById(R.id.iv_profileupdate);
        fab_updatefoto = findViewById(R.id.btn_updatefoto);

        btnUpdate = findViewById(R.id.btn_saveupdate);
        btnReset = findViewById(R.id.btn_reset_update);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setIndeterminate(true);
        progressDialog.setIndeterminateDrawable(getDrawable(R.drawable.custom_progress_dialog));

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etFirstName.setText("");
                etLastName.setText("");
                etUmur.setText("");
            }
        });

        fab_updatefoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditDataActivity.this, "Sorry, that feature it's temporary not available", Toast.LENGTH_SHORT).show();
            }
        });


        Intent intent = getIntent();
        String idUser = intent.getStringExtra("idUser");
        String firstName = intent.getStringExtra("firstName");
        String lastName = intent.getStringExtra("lastName");
        String ageUser = intent.getStringExtra("ageUser");
        String photoUser = intent.getStringExtra("photoUser");

        RequestOptions options = new RequestOptions().fitCenter()
                .centerCrop()
                .placeholder(R.drawable.peterparker)
                .error(R.drawable.peterparker);
        etFirstName.setText(firstName);
        etLastName.setText(lastName);
        etUmur.setText(ageUser);
        Glide.with(this).load(photoUser).apply(options).fitCenter().centerCrop().into(ivProfileUpdate);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaDepan = etFirstName.getText().toString();
                String namaBelakang = etLastName.getText().toString();
                String umurUser = etUmur.getText().toString();

                if (namaDepan.isEmpty()){
                    etFirstName.setError("Please field first name ");
                } else if (namaBelakang.isEmpty()){
                    etLastName.setError("Please field last name");
                } else if (umurUser.isEmpty()){
                    etUmur.setError("Please field your age");
                } else {
                    updateData(idUser,namaDepan, namaBelakang, umurUser, photoUser);
                }
            }
        });

        ImageButton btnBack = findViewById(R.id.ib_backupdate);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditDataActivity.this, MenuUtamaActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateData(String idUser, String namaDepan, String namaBelakang, String umurUser, String photoUser) {
        try {
            progressDialog.show();
            RequestBody rNameDepan = RequestBody.create(MediaType.parse("text/plain"), namaDepan);

            RequestBody rNamaBelakang = RequestBody.create(MediaType.parse("text/plain"), namaBelakang);

            RequestBody rUmurUser = RequestBody.create(MediaType.parse("text/plain"), umurUser);
            RequestBody rNamaFoto = RequestBody.create(MediaType.parse("text/plain"), photoUser);

            ApiServices apiServices = ApiClient.getInstance().create(ApiServices.class);
            Call<DataContactProfile> customerCall = apiServices.updateData(idUser,rNameDepan, rNamaBelakang, rUmurUser, rNamaFoto);
            customerCall.enqueue(new Callback<DataContactProfile>() {
                @Override
                public void onResponse(Call<DataContactProfile> call, Response<DataContactProfile> response) {
//                    Log.d("EditDataActivity", "onResponse: "+response.code());
                    if (response.code() == 201) {
                        progressDialog.dismiss();
                        Toast.makeText(EditDataActivity.this, "Success, Saving Data", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditDataActivity.this, MenuUtamaActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(EditDataActivity.this, "Failed, your data unsaved", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DataContactProfile> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(EditDataActivity.this, "Failed, your data unsaved", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(EditDataActivity.this, "Error, Please check your connection", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EditDataActivity.this, MenuUtamaActivity.class);
        startActivity(intent);
        finish();
    }
}