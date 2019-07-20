package com.smanegeri1sindang.edusasi.News.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.smanegeri1sindang.edusasi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsMixedContent extends Fragment {


    public NewsMixedContent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_mixed_content, container, false);
    }

    public NewsMixedContent newInstance(boolean b) {
        NewsMixedContent fragment = new NewsMixedContent();
        Bundle args = new Bundle();
        args.putBoolean("forceload",b);
        fragment.setArguments(args);
        return fragment;
    }
}
