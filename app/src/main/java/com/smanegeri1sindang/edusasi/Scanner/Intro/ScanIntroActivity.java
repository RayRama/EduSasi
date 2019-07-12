package com.smanegeri1sindang.edusasi.Scanner.Intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smanegeri1sindang.edusasi.R;
import com.smanegeri1sindang.edusasi.Scanner.Permission.GetPermissionFrag;
import com.smanegeri1sindang.edusasi.Scanner.ScanActivity;

import static android.Manifest.permission.CAMERA;

public class ScanIntroActivity extends AppCompatActivity {
    Button getStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_intro);

        getStarted = (Button)findViewById(R.id.btn_start_scan);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enable_Camera();
            }
        });

        if (checkPermission()) {
            savePrefData();
        }

        if (restorePrefData()) {
            Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public boolean checkPermission() {
        return(ContextCompat.checkSelfPermission(this, CAMERA)== PackageManager.PERMISSION_GRANTED);
    }

    private void enable_Camera() {
        FragmentManager fm = getSupportFragmentManager();
        GetPermissionFrag fragment = new GetPermissionFrag();
        fm.beginTransaction().replace(R.id.scaner_intro, fragment).commit();
    }


    private  void savePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("scanPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("introClicked", true);
        editor.commit();
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("scanPref", MODE_PRIVATE);
        Boolean introClicked = pref.getBoolean("introClicked", false);
        return introClicked;
    }
}
