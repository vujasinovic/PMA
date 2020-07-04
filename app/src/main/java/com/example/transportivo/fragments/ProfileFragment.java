package com.example.transportivo.fragments;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportivo.R;
import com.example.transportivo.adapters.CommentAdapter;
import com.example.transportivo.model.Comment;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.stream.Collectors;

public class ProfileFragment extends BaseFragment {

    private static final String COMMENTS_COLLECTION_NAME = "comments";
    private FirebaseUser user;
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

        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(COMMENTS_COLLECTION_NAME)
                .whereEqualTo(Comment.Fields.userId, user.getUid())
                .get()
                .addOnCompleteListener(l -> {
                            List<Comment> comments = l.getResult().getDocuments().stream()
                                    .map(d -> objectMapper.convertValue(d.getData(), Comment.class))
                                    .collect(Collectors.toList());

                            Comment[] commentsArray = objectMapper.convertValue(comments, Comment[].class);

                            adapter = new CommentAdapter(commentsArray);
                            recyclerView.setAdapter(adapter);
                        }
                );

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

    private void displayUserInfo(View view) {
        EditText userName = view.findViewById(R.id.userLastnameAndName);
        EditText userEmail = view.findViewById(R.id.userEmail);
        TextView username = view.findViewById(R.id.usernameText);
        username.setText(user.getDisplayName());
        userName.setText(user.getDisplayName());
        userEmail.setText(user.getEmail());
    }

    private void updateUser(View view) {
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
