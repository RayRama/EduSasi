package com.smanegeri1sindang.edusasi.MainActivity.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;
import com.muddzdev.styleabletoast.StyleableToast;
import com.smanegeri1sindang.edusasi.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeatureFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    TextView dateTime;
    Button Test;
    String mydate;
    TextView Time;
    ImageButton NavBtn;

    private AdvanceDrawerLayout drawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feature, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drawerLayout = getActivity().findViewById(R.id.DrawLayout);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        if (getActivity() == null)
                            return;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy, HH:mm:ss");
                                mydate = dateFormat.format(Calendar.getInstance().getTimeInMillis());
                                dateTime = view.findViewById(R.id.getTime);
                                dateTime.setText(mydate);
                            }
                        });
                    }
                }catch (InterruptedException e){
                }
            }
        };thread.start();

        NavBtn = view.findViewById(R.id.navBtn);
        NavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        /*Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        drawerLayout = (AdvanceDrawerLayout) view.findViewById(R.id.drawLayout);
        drawerLayout.openDrawer(GravityCompat.START);
        drawerLayout.closeDrawer(GravityCompat.START);

        NavigationView navigationView = view.findViewById(R.id.nav_views);
        navigationView.setNavigationItemSelectedListener(this);*/

        Test = (Button) view.findViewById(R.id.test);
        Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StyleableToast.makeText(getActivity(), "Test", R.style.alert_scan).show();
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
