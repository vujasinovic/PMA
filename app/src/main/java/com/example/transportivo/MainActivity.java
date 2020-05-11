package com.example.transportivo;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.transportivo.fragments.AddOfferFragment;
import com.example.transportivo.fragments.HomePageFragment;
import com.example.transportivo.fragments.NotificationFragment;
import com.example.transportivo.fragments.ProfileFragment;
import com.example.transportivo.fragments.ReservationsFragment;
import com.example.transportivo.utils.FragmentHelper;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentHelper fragmentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentHelper = new FragmentHelper(getSupportFragmentManager());

        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        fragmentHelper.switchToFragment(new HomePageFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        fragmentHelper.switchToFragment(getFragmentForId(item.getItemId()));
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    public Fragment getFragmentForId(int id) {
        Fragment fragment;

        switch (id) {
            case R.id.nav_profile:
                fragment = new ProfileFragment();
                break;
            case R.id.nav_add_offer:
                fragment = new AddOfferFragment();
                break;
            case R.id.nav_reservations:
                fragment = new ReservationsFragment();
                break;
            case R.id.nav_notifications:
                fragment = NotificationFragment.newInstance();
                break;
            default:
                throw new IllegalArgumentException("No Fragment For ID=" + id);
        }

        return fragment;
    }

}
