package com.example.transportivo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.transportivo.broadcast.NotificationTokenUploader;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.HashSet;

import static androidx.navigation.Navigation.findNavController;
import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;

public class TransportivoActivity extends AppCompatActivity {
    private final NotificationTokenUploader connectivityStatusReceiver;
    
    private static final int[] navItems = {
            R.id.nav_home,
            R.id.nav_profile,
            R.id.nav_add_offer,
            R.id.nav_notifications,
            R.id.nav_reservations,
            R.id.nav_settings,
            R.id.nav_search
    };

    public TransportivoActivity() {
        connectivityStatusReceiver = new NotificationTokenUploader();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    protected void onResume() {
        super.onResume();
        
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityStatusReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (connectivityStatusReceiver != null) {
            unregisterReceiver(connectivityStatusReceiver);
        }
    }
}
