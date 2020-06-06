package com.example.transportivo;

import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static androidx.navigation.Navigation.*;
import static androidx.navigation.ui.NavigationUI.*;

public class MainActivity extends AppCompatActivity {
    private static final int[] navItems = {
            R.id.nav_home,
            R.id.nav_profile,
            R.id.nav_add_offer,
            R.id.nav_notifications,
            R.id.nav_reservations,
            R.id.nav_settings
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        final NavController navController = findNavController(this, R.id.nav_host_fragment);

        setSupportActionBar(toolbar);

        setupToggle(toolbar, drawerLayout);
        setupActionBarWithNavController(this, navController,
                new AppBarConfiguration.Builder(new HashSet(Collections.singletonList(navItems)))
                        .setDrawerLayout(drawerLayout)
                        .build());
        setupWithNavController(navigationView, navController);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        final NavController navController = findNavController(this, R.id.nav_host_fragment);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

}
