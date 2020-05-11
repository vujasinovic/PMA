package com.example.transportivo.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.transportivo.R;

public final class FragmentHelper {
    private final FragmentManager fragmentManager;

    public FragmentHelper(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void switchToFragment(Fragment fragment) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.mainActivity, fragment)
                .commit();
    }

}
