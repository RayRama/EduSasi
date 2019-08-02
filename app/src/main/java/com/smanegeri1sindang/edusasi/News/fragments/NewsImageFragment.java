package com.smanegeri1sindang.edusasi.News.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.smanegeri1sindang.edusasi.News.ImageViewerActivity;
import com.smanegeri1sindang.edusasi.News.NewsDetailActivity;
import com.smanegeri1sindang.edusasi.R;

public class NewsImageFragment extends Fragment {

    private String imgUrl;
    private Toolbar toolbar;
    private ImageView imageView;
    private Button downloadBtn;

    public static NewsImageFragment newInstance(String image) {
        NewsImageFragment fragment = new NewsImageFragment();
        Bundle args = new Bundle();
        args.putString(NewsDetailActivity.ARG_IMAGE, image);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imgUrl = getArguments().getString(NewsDetailActivity.ARG_IMAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_image, container, false);
        imageView = view.findViewById(R.id.imageView);
        downloadBtn = view.findViewById(R.id.downloadimgBtn);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ImageViewerActivity)getActivity()).askPermission();
            }
        });
        Glide.with(getActivity()).load(imgUrl).into(imageView);
        return view;
    }
}
