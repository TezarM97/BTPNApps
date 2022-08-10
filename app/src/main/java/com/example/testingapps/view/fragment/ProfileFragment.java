package com.example.testingapps.view.fragment;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.testingapps.R;


public class ProfileFragment extends Fragment {

    ConstraintLayout clEditPassword, clEditProfile, clPrivacyPolicy, clTermandCondition;
    Button btnLogout;
    ImageView ivProfile;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        clEditPassword = view.findViewById(R.id.cl_editpassword);
        clEditProfile = view.findViewById(R.id.cl_editprofile);
        clPrivacyPolicy = view.findViewById(R.id.cl_privacypolicy);
        clTermandCondition = view.findViewById(R.id.cl_termandconditions);
        btnLogout = view.findViewById(R.id.btn_logout);
        ivProfile = view.findViewById(R.id.iv_profile);

        Glide.with(getContext()).load("https://p4.wallpaperbetter.com/wallpaper/610/341/171/spider-man-homecoming-4k-tom-holland-wallpaper-preview.jpg").fitCenter().centerCrop().into(ivProfile);




        clEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Edit Profile : Sorry, that feature it's temporary not available", Toast.LENGTH_SHORT).show();
            }
        });

        clEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Edit Password : Sorry, that feature it's temporary not available", Toast.LENGTH_SHORT).show();
            }
        });
        clPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Privacy Policy : Sorry, that feature it's temporary not available", Toast.LENGTH_SHORT).show();
            }
        });

        clTermandCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Term and Conditions : Sorry, that feature it's temporary not available", Toast.LENGTH_SHORT).show();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Logout : Sorry, that feature it's temporary not available", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}