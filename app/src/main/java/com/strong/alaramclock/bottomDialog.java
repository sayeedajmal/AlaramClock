package com.strong.alaramclock;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.strong.alaramclock.databinding.FragmentBottomDialogBinding;

import java.util.Objects;

public class bottomDialog extends Fragment {
    FragmentBottomDialogBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((View) requireView().getParent()).setBackgroundColor(Color.TRANSPARENT);
        binding = FragmentBottomDialogBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }
}