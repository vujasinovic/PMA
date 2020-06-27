package com.example.transportivo.fragments;

import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;
import com.example.transportivo.adapters.CommentAdapter;
import com.example.transportivo.adapters.NotificationAdapter;
import com.example.transportivo.ui.TransportivoDatePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import lombok.NonNull;

public class ProfileFragment extends BaseFragment {

    private FirebaseUser user;
    private String[] users = {"Dragan Torbica", "Momo", "Dule postar"};
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public ProfileFragment() {
        super(R.layout.profile_fragment);
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    protected View initializeView(View view) {

        EditText userName = view.findViewById(R.id.userLastnameAndName);
        EditText userEmail = view.findViewById(R.id.userEmail);

        final RecyclerView recyclerView = view.findViewById(R.id.comments);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        adapter = new CommentAdapter(users);
        recyclerView.setAdapter(adapter);

        AppCompatButton saveButton = view.findViewById(R.id.saveButton);
        ImageButton editButton = view.findViewById(R.id.editButton);
        saveButton.setVisibility(View.GONE);

        editButton.setOnClickListener(v -> {
            userName.setEnabled(true);
            userEmail.setEnabled(true);
            saveButton.setVisibility(View.VISIBLE);
        });
        saveButton.setOnClickListener(v -> {
            updateUser(view);
            saveButton.setVisibility(View.GONE);

        });
        displayUserInfo(view);

        return view;
    }

    public void displayUserInfo(View view) {
        EditText userName = view.findViewById(R.id.userLastnameAndName);
        EditText userEmail = view.findViewById(R.id.userEmail);
        TextView username = view.findViewById(R.id.usernameText);
        username.setText(user.getDisplayName());
        userName.setText(user.getDisplayName());
        userEmail.setText(user.getEmail());


    }


    public void updateUser(View view) {

        EditText userName = view.findViewById(R.id.userLastnameAndName);
        EditText userEmail = view.findViewById(R.id.userEmail);
        TextView username = view.findViewById(R.id.usernameText);

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(userName.getText().toString())
                .build();
        username.setText(userName.getText().toString());
        user.updateProfile(profileUpdates).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("User update");
            }
        });
        if (!userEmail.getText().toString().equals(user.getEmail())) {
            user.updateEmail(userEmail.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    System.out.println("User email address updated.");
                }
            });
        }
  /*     if(!userPassword.getText().toString().equals("Password")) {
           user.updatePassword(userPassword.getText().toString()).addOnCompleteListener(task -> {
               if (task.isSuccessful()) {
                   System.out.println("User password updated.");
               }
           });
       }*/

        //     AuthCredential credential = EmailAuthProvider.getCredential("user1@gmail.com","2001jovana");
        //     user.reauthenticate(credential).addOnCompleteListener(task -> System.out.println("User re-authenticated."));
    }

}
