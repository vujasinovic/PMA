package com.example.transportivo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.transportivo.model.NotificationToken;
import com.example.transportivo.model.Offer;
import com.example.transportivo.provider.FirebaseClient;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static androidx.navigation.Navigation.findNavController;
import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;

public class TransportivoActivity extends AppCompatActivity {
    private static final int[] navItems = {
            R.id.nav_home,
            R.id.nav_profile,
            R.id.nav_add_offer,
            R.id.nav_notifications,
            R.id.nav_reservations,
            R.id.nav_settings,
            R.id.nav_search
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("TOKEN_PREF", Context.MODE_PRIVATE);
        String retrivedToken = preferences.getString("token", null);

        createOrUpdateToken(retrivedToken);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        final NavController navController = findNavController(this, R.id.nav_host_fragment);

        setSupportActionBar(toolbar);
        displayLoggedUserInfo(navigationView);
        initializeListeners(navigationView);

        setupToggle(toolbar, drawerLayout);
        setupActionBarWithNavController(this, navController,
                new AppBarConfiguration.Builder(new HashSet(Collections.singletonList(navItems)))
                        .setDrawerLayout(drawerLayout)
                        .build());
        setupWithNavController(navigationView, navController);
        createNotificationChannel();
    }

    private void initializeListeners(NavigationView navigationView) {
        final MenuItem logoutItem = navigationView.getMenu().findItem(R.id.nav_logout);
        logoutItem.setOnMenuItemClickListener(this::performLogout);
    }

    private boolean performLogout(@Nullable MenuItem menuItem) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    private void displayLoggedUserInfo(NavigationView navigationView) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final View headerView = navigationView.getHeaderView(0);
        final TextView headerTitle = headerView.findViewById(R.id.nav_header_title);
        final TextView headerSubtitle = headerView.findViewById(R.id.nav_header_subtitle);

        headerTitle.setText(user.getDisplayName());
        headerSubtitle.setText(user.getEmail());
    }

    private void setupToggle(Toolbar toolbar, DrawerLayout drawerLayout) {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onSupportNavigateUp() {
        final NavController navController = findNavController(this, R.id.nav_host_fragment);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }


    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel_id", "channel_id", importance);
            channel.setDescription("You have one offer");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void createOrUpdateToken(String token) {

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
    }

}
