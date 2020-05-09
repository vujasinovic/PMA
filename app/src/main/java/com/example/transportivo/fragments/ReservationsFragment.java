package com.example.transportivo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.transportivo.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ReservationsFragment extends Fragment {


    private ActiveReservationFragment activeReservationFragment;
    private HistoryFragment historyFragment;
    private OfferFragment offerFragment;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.reservations_fragment, container, false);


        activeReservationFragment = new ActiveReservationFragment();
        historyFragment = new HistoryFragment();
        offerFragment = new OfferFragment();
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

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
