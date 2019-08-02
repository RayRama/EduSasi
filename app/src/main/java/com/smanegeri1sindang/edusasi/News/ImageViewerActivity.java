package com.smanegeri1sindang.edusasi.News;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import com.smanegeri1sindang.edusasi.News.fragments.NewsImageFragment;
import com.smanegeri1sindang.edusasi.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageViewerActivity extends AppCompatActivity {

    ViewPager viewPager;
    private int currentPosition;
    private NavViewPagePagerAdapter adapter;
    private List<String> images = new ArrayList<>();
    private int length, position;
    private Toolbar toolbar;
    private DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        toolbar = findViewById(R.id.imageViewerToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back, null));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        if (getIntent() != null) {
            length= getIntent().getIntExtra("size", 0);
            position = getIntent().getIntExtra("index", 0);

            for (int i=0;i<length;i++) {
                images.add(getIntent().getStringExtra("image_" +i));
            }
        }

        getSupportActionBar().setTitle((position+1) + "/" + length);
        viewPager = findViewById(R.id.imageViewePager);
        setViewPager();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] granResults) {
        super.onRequestPermissionsResult(requestCode, permissions, granResults);
        if (requestCode == REQUEST_WRITE_STORAGE) {
            downloadImage(images.get(currentPosition));
        }
    }

    private static final int REQUEST_WRITE_STORAGE = 112;
    public void askPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE
                );
            } else {
                downloadImage(images.get(currentPosition));
            }
        } else {
            downloadImage(images.get(currentPosition));
        }
    }

    public void invalidFragmentMenus(int position) {
        for (int i = 0; i < adapter.getCount(); i++) {
            adapter.getItem(i).setHasOptionsMenu(i == position);
        }
        invalidateOptionsMenu();
    }

    private void setViewPager() {
        adapter = new NavViewPagePagerAdapter(getSupportFragmentManager());
        for (String s:images) {
            adapter.addFragment(new NewsImageFragment().newInstance(s));
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                invalidFragmentMenus(position);
                currentPosition = position;
                getSupportActionBar().setTitle((position+1)+"/"+length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOffscreenPageLimit(images.size());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position,false);
        invalidFragmentMenus(viewPager.getCurrentItem());
    }

    private void downloadImage(String url) {
        String filename;
        if (url.contains("?")) {
            filename = url.substring(url.lastIndexOf("/") + 1, url.indexOf("?"));
        } else {
            filename = url.substring(url.lastIndexOf("/") + 1, url.length());
        }

        File image = new File(Environment.getExternalStorageDirectory() + "/EduSasi/Images/News");
        if (!image.exists()) {
            image.mkdirs();
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle(filename);
        request.setDescription("Downloading " + filename);
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir("/EduSasi/Images/News", filename);
        downloadManager.enqueue(request);

        Toast.makeText(getApplicationContext(), "Saved as " + image + "/" + filename, Toast.LENGTH_LONG).show();
    }
}
