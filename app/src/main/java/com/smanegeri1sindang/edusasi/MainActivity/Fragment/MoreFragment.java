package com.smanegeri1sindang.edusasi.MainActivity.Fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;
import com.smanegeri1sindang.edusasi.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private AdvanceDrawerLayout drawerLayout;
    ImageButton NavBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drawerLayout = getActivity().findViewById(R.id.DrawLayout);

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
