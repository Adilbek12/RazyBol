package com.gratanet.mailanovadilbek.razybol.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.SignInButton;
import com.gratanet.mailanovadilbek.razybol.MainActivity;
import com.gratanet.mailanovadilbek.razybol.R;

public class LoginFragment extends Fragment {

    private SignInButton signInButton;
    private MainActivity mainActivity;

    public LoginFragment() {
    }

    @SuppressLint("ValidFragment")
    public LoginFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        signInButton = root.findViewById(R.id.login);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.login();
            }
        });

        return root;
    }

}
