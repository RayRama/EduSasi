package com.smanegeri1sindang.edusasi.MainActivity.Fragment;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;
import com.smanegeri1sindang.edusasi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private AdvanceDrawerLayout drawerLayout;
    ImageButton NavBtn, NavBtn1;
    VideoView videoView;
    RelativeLayout header1, header, header2;
    ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        header1 = (RelativeLayout) view.findViewById(R.id.headerHome1);
        header = (RelativeLayout) view.findViewById(R.id.headerHome);
        header2 = (RelativeLayout) view.findViewById(R.id.toolbar);
        scrollView = (ScrollView) view.findViewById(R.id.ScrollView);

        header1.setAlpha(0f);
        header.setAlpha(1f);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int maxDistance = header2.getHeight();
                int movement = scrollView.getScrollY();
                float alphaFactor = ((movement * 1.0f) / (maxDistance - header1.getHeight()));
                float alphaFactor2 = ((movement * 0f) + (maxDistance + header.getHeight()));

                if (movement > 0 && movement < maxDistance) {
                    header.setAlpha(0f);
                    header1.setAlpha(alphaFactor);
                } else {
                    header.setAlpha(alphaFactor2);
                }
            }
        });

        videoView = (VideoView) view.findViewById(R.id.videoHeader);
        String path = "android.resource://"+getActivity().getPackageName()+"/"+R.raw.videoheader;

        Uri uri = Uri.parse(path);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });


        drawerLayout = getActivity().findViewById(R.id.DrawLayout);

        NavBtn1 = view.findViewById(R.id.navBtn1);
        NavBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavBtn = view.findViewById(R.id.navBtn);
        NavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //Handle click

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
