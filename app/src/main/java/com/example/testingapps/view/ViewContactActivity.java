package com.example.testingapps.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.testingapps.R;
import com.example.testingapps.data.DataContact;
import com.example.testingapps.data.DataContactProfile;
import com.example.testingapps.model.ResponseJsonArray;
import com.example.testingapps.network.ApiClient;
import com.example.testingapps.network.ApiServices;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewContactActivity extends AppCompatActivity {

    EditText tvLastName, tvFirstName, tvAge;
    ImageView ivFotoProfile;
    ImageButton ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        tvFirstName = findViewById(R.id.et_profile_firstname);
        tvLastName = findViewById(R.id.et_profile_lastname);
        tvAge = findViewById(R.id.et_profile_age);
        ivFotoProfile = findViewById(R.id.iv_profile_foto);
        ibBack = findViewById(R.id.ib_backprofile);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
        tvLastName.setText(lastName);
        tvFirstName.setText(firstName);
        tvAge.setText(ageUser);
        Glide.with(this).load(photoUser).apply(options).fitCenter().centerCrop().into(ivFotoProfile);

        Log.d("View Contact", "id user: " + photoUser);
    }
}