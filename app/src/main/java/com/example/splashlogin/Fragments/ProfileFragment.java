package com.example.splashlogin.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.splashlogin.Activities.ProfileUpdateActivity;
import com.example.splashlogin.LoginActivity;
import com.example.splashlogin.R;


public class ProfileFragment extends Fragment {

    ImageView avatarImage;
    TextView usernameText, fullNameText, emailText, phoneNumText;
    Button updateProfile;

    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        avatarImage = rootView.findViewById(R.id.avatarImage);
        usernameText = rootView.findViewById(R.id.usernameText);
        fullNameText = rootView.findViewById(R.id.fullNameText);
        emailText = rootView.findViewById(R.id.emailText);
        phoneNumText = rootView.findViewById(R.id.phoneNumText);
        updateProfile = rootView.findViewById(R.id.updateProfileBtn);

        usernameText.setText("@" + LoginActivity.currentUserUserName);
        fullNameText.setText(LoginActivity.currentUserFullName);
        emailText.setText(LoginActivity.currentUserEmail);
        phoneNumText.setText(LoginActivity.currentUserPhoneNum);

        updateProfile.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), ProfileUpdateActivity.class));
        });

        return rootView;
    }
}