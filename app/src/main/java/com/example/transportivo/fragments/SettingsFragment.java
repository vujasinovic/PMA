package com.example.transportivo.fragments;

import android.transition.Visibility;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.transportivo.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsFragment extends BaseFragment {
    private FirebaseUser user;


    public SettingsFragment() {
        super(R.layout.settings);
        user = FirebaseAuth.getInstance().getCurrentUser();

    }

    @Override
    protected View initializeView(View view) {

        TextInputLayout textInputLayout = view.findViewById(R.id.editPassword);
        Button saveButton = view.findViewById(R.id.savePassword);
        saveButton.setVisibility(View.GONE);
        textInputLayout.setVisibility(View.GONE);
        TextView changePassword = view.findViewById(R.id.changePassword);
        changePassword.setOnClickListener(v -> {
            textInputLayout.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.VISIBLE);

        });

        saveButton.setOnClickListener(v->{
            updatePassword(view);
        });

        return view;
    }

    private void updatePassword(View view) {

        TextInputEditText password = view.findViewById(R.id.newPassword);
        TextInputLayout textInputLayout = view.findViewById(R.id.editPassword);
        Button saveButton = view.findViewById(R.id.savePassword);



        user.updatePassword(password.getText().toString()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                saveButton.setVisibility(View.GONE);
                textInputLayout.setVisibility(View.GONE);
                System.out.println("User password updated.");
            }
        });




    }


}
