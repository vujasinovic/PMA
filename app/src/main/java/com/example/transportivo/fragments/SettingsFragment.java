package com.example.transportivo.fragments;

import android.transition.Visibility;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.transportivo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import lombok.NonNull;

public class SettingsFragment extends BaseFragment {
    private FirebaseUser user;


    public SettingsFragment() {
        super(R.layout.settings);
        user = FirebaseAuth.getInstance().getCurrentUser();

    }

    @Override
    protected View initializeView(View view) {

        TextInputLayout textInputLayout = view.findViewById(R.id.editPassword);
        TextInputLayout odlPassword = view.findViewById(R.id.editOldPassword);
        Button saveButton = view.findViewById(R.id.savePassword);
        saveButton.setVisibility(View.GONE);
        textInputLayout.setVisibility(View.GONE);
        odlPassword.setVisibility(View.GONE);
        TextView changePassword = view.findViewById(R.id.changePassword);
        changePassword.setOnClickListener(v -> {
            textInputLayout.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);
            odlPassword.setVisibility(View.VISIBLE);

        });

        saveButton.setOnClickListener(v -> {
            updatePassword(view);
        });

        return view;
    }

    private void updatePassword(View view) {

        TextInputEditText password = view.findViewById(R.id.newPassword);
        TextInputLayout textInputLayout = view.findViewById(R.id.editPassword);
        TextInputLayout odlPassword = view.findViewById(R.id.editOldPassword);
        TextInputEditText old = view.findViewById(R.id.oldPassword);


        Button saveButton = view.findViewById(R.id.savePassword);

        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), old.getText().toString());

        user.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.updatePassword(password.getText().toString()).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                saveButton.setVisibility(View.GONE);
                                textInputLayout.setVisibility(View.GONE);
                                odlPassword.setVisibility(View.GONE);
                                Log.d("CHANGE PASSWORD", "Password updated");
                            } else {
                                Log.d("CHANGE PASSWORD", "Error password not updated");
                            }
                        });
                    } else {
                        Log.d("CHANGE PASSWORD", "Error auth failed");
                    }
                });


    }


}
