package com.mahbub.tourmate.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mahbub.tourmate.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GralleryFragment extends Fragment {


    public GralleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grallery, container, false);
        return view;
    }

}
