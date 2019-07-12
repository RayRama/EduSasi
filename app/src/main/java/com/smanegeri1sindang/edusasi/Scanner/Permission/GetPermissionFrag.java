package com.smanegeri1sindang.edusasi.Scanner.Permission;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.smanegeri1sindang.edusasi.R;
import com.smanegeri1sindang.edusasi.Scanner.ScanActivity;

import static android.Manifest.permission.CAMERA;

/**
 * A simple {@link Fragment} subclass.
 */
public class GetPermissionFrag extends Fragment {

    View getView;
    Context context;
    Button enableCamera;

    private  static  final int REQUEST_CAMERA = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getView = inflater.inflate(R.layout.fragment_get_permission_scan, container, false);
        context = getContext();

        enableCamera = getView.findViewById(R.id.enable_camera);
        enableCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                requestPermissions();
            }
        });

        return getView;
    }

    private void requestPermissions(){
        requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode){
            case REQUEST_CAMERA :
                if (grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Intent intent = new Intent(getActivity(), ScanActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    else{
                        Toast.makeText(context, "Akses ditolak", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if (shouldShowRequestPermissionRationale(CAMERA)){
                                displayAlertMessage("You need to allow for both permission",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                                    requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }


    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNegativeButton("CANCEL", null)
                .create()
                .show();
    }
}
