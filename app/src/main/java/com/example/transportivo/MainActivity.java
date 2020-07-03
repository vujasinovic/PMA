package com.example.transportivo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.transportivo.model.NotificationToken;
import com.example.transportivo.provider.FirebaseClient;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.NonNull;

import static java.util.Objects.nonNull;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        if (isUserAuthenticated()) {
            startTransportivoActivity();
        } else {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build());

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setLogo(R.mipmap.ic_app_foreground)
                            .setTheme(R.style.LoginTheme)
                            .build(),
                    RC_SIGN_IN);
        }

    }

    private boolean isUserAuthenticated() {
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        boolean isUserAuthenticated = nonNull(firebaseAuth) && nonNull(firebaseAuth.getCurrentUser());
        return isUserAuthenticated;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK) {
            generatingToken();
            startTransportivoActivity();
        }
    }

    private void startTransportivoActivity() {
        Intent intent = new Intent(this, TransportivoActivity.class);
        startActivity(intent);
    }

    private void generatingToken() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.i("INSTANCE FAILED", "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String token = task.getResult().getToken();
                    NotificationToken notificationToken = new NotificationToken();
                    notificationToken.setToken_id(token);
                    FirebaseClient<NotificationToken> firebaseClient = new FirebaseClient<>();
                    Map<String, Object> query = new HashMap<>();
                    query.put("owner", FirebaseAuth.getInstance().getUid());
                    firebaseClient.getAll(NotificationToken.class, query, result -> {
                        if (result.length > 0) {
                            result[0].setToken_id(FirebaseInstanceId.getInstance().getToken());
                            firebaseClient.update(result[0], o -> Log.i("UPDATE TOKEN", "Successfully updated token"));
                        } else {

                            firebaseClient.create(notificationToken, o -> Log.i("CREATE TOKEN", "Successfully added new offer"));
                        }
                    });
                });


    }
}
