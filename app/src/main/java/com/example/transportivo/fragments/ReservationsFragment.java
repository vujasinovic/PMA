package com.example.transportivo.fragments;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.transportivo.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ReservationsFragment extends BaseFragment {
    public ReservationsFragment() {
        super(R.layout.reservations_fragment);
    }

    @Override
    protected View initializeView(View view) {
        final ActiveReservationFragment activeReservationFragment = new ActiveReservationFragment();
        final HistoryFragment historyFragment = new HistoryFragment();
        final OfferFragment offerFragment = new OfferFragment();
        final TabLayout tabLayout = view.findViewById(R.id.tabs);
        final ViewPager viewPager = view.findViewById(R.id.viewPager);

        tabLayout.setupWithViewPager(viewPager);

        ViewAdapter viewAdapter = new ViewAdapter(getFragmentManager(), 0);
        viewAdapter.addFragment(offerFragment, "Offers");
        viewAdapter.addFragment(activeReservationFragment, "Active");
        viewAdapter.addFragment(historyFragment, "History");
        viewPager.setAdapter(viewAdapter);

        return view;
    }

    private class ViewAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitles = new ArrayList<>();

        public ViewAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment f, String title) {
            fragments.add(f);
            fragmentTitles.add(title);

        }

        @NonNull
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }

    }
}
