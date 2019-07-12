package com.smanegeri1sindang.edusasi.Scanner.Result;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.muddzdev.styleabletoast.StyleableToast;
import com.smanegeri1sindang.edusasi.R;
import com.smanegeri1sindang.edusasi.Scanner.History.Entity.History;
import com.smanegeri1sindang.edusasi.YouTubeDL.YouTubeDownloader.DownloadActivity;

import java.util.List;

public class ScanResult extends AppCompatActivity {

    TextView dateData, contentData;
    Button open, share, download;
    private List<History> historyList;
    private String ScanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        Intent intent = getIntent();
        ScanResult = intent.getStringExtra("content");
        String mydate = intent.getStringExtra("date");

        dateData = (TextView) findViewById(R.id.date_data);
        dateData.setText(mydate);
        contentData = (TextView) findViewById(R.id.content_data);
        contentData.setText(ScanResult);

        share = (Button) findViewById(R.id.share_result);
        download = (Button) findViewById(R.id.download_result);

        if(Patterns.WEB_URL.matcher(ScanResult).matches()) {
            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StyleableToast.makeText(getApplicationContext(), "This feature is disabled", R.style.alert_scan).show();
                }
            });
        } else {
            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isOnline()) {
                        Intent i= new Intent(ScanResult.this, DownloadActivity.class);
                        i.putExtra("video_id", ScanResult);
                        startActivity(i);
                        finish();
                    } else {
                        StyleableToast.makeText(ScanResult.this, "Error, no internet available", R.style.alert_scan).show();
                    }
                }
            });
        }

        open = (Button) findViewById(R.id.open_result);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url;
                if(Patterns.WEB_URL.matcher(ScanResult).matches()) {
                    url = ScanResult;
                } else {
                    url = "http://www.google.com/#q=" + ScanResult;
                }
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    protected boolean isOnline() {
        ConnectivityManager connectivityManager =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}
