package com.example.transportivo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import static java.util.Objects.nonNull;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

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
            startTransportivoActivity();
        }
    }

    private void startTransportivoActivity() {
        Intent intent = new Intent(this, TransportivoActivity.class);
        startActivity(intent);
    }


}
