package com.example.transportivo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static java.util.Objects.isNull;

public abstract class BaseFragment extends Fragment {
    private final int layout;
    private View view;

    public BaseFragment(int contentLayoutId) {
        super(contentLayoutId);

        this.layout = contentLayoutId;
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (isNull(view)) {
            view = inflater.inflate(layout, container, false);
            view = initializeView(view);
        }

        return view;
    }

    protected View initializeView(View view) {
        return view;
    };

    @Nullable
    @Override
    public View getView() {
        return view;
    }
}
