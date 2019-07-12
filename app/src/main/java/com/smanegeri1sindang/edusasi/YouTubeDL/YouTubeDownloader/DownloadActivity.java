package com.smanegeri1sindang.edusasi.YouTubeDL.YouTubeDownloader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;
import com.smanegeri1sindang.edusasi.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class DownloadActivity extends AppCompatActivity {

    private static final int ITAG_AUDIO = 140;
    private static final String IMAGE_BASE_URL = "http://img.youtube.com/vi/";
    private static String  youtubeLink, youtubeDownloadLink;
    private List<YtFragmentedVideo> list;
    private LinearLayout buttonLayout;
    private ProgressBar progressBar;
    static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 9293;
    private static Button btn;
    private TextView VideoTitle;
    private ImageView youtubeImg;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_youtube);
        buttonLayout = findViewById(R.id.ButtonLayout);
        progressBar = findViewById(R.id.progressBar);
        youtubeImg = findViewById(R.id.img_thumbnail);
        VideoTitle = findViewById(R.id.videoTitle);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }

        Intent intent = getIntent();
        id = intent.getStringExtra("video_id");

        youtubeLink = "https://youtube.com/watch?v=";
        youtubeDownloadLink = youtubeLink + id;
        getYoutubeDownloadUrl(youtubeDownloadLink);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
                return;
            }
        }
    }

    private void getYoutubeDownloadUrl(String youtubeDownloadLink) {
        new YouTubeExtractor(this) {

            @Override
            protected void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta videoMeta) {

                list = new ArrayList<>();
                for (int i = 0, itag; i < ytFiles.size(); i++) {
                    itag = ytFiles.keyAt(i);
                    YtFile ytFile = ytFiles.get(itag);

                    if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getHeight() >= 144) {
                        addFormatToList(ytFile, ytFiles);
                    }
                }
                Collections.sort(list, new Comparator<YtFragmentedVideo>() {
                    @Override
                    public int compare(YtFragmentedVideo o1, YtFragmentedVideo o2) {
                        return o1.height - o2.height;
                    }
                });
                for (YtFragmentedVideo files : list) {
                    addButton(videoMeta.getTitle(), files);
                }
            }
        }.extract(youtubeDownloadLink, true, false);
    }

    private void addFormatToList(YtFile ytFile, SparseArray<YtFile> ytFiles) {
        int height = ytFile.getFormat().getHeight();
        if (height != -1) {
            for (YtFragmentedVideo fragmentedVideo : list) {
                if (fragmentedVideo.height == height && (fragmentedVideo.videoFile == null
                || fragmentedVideo.videoFile.getFormat().getFps() == ytFile.getFormat().getFps())) {
                    return;
                }
            }
        }
        YtFragmentedVideo fragmentedVideo = new YtFragmentedVideo();
        fragmentedVideo.height = height;
        if (ytFile.getFormat().isDashContainer()) {
            if (height > 0) {
                fragmentedVideo.videoFile = ytFile;
                fragmentedVideo.audioFile = ytFiles.get(ITAG_AUDIO);
            } else {
                fragmentedVideo.audioFile = ytFile;
            }
        } else {
            fragmentedVideo.videoFile = ytFile;
        }
        list.add(fragmentedVideo);
    }

    private void addButton(final String videoTitle, final YtFragmentedVideo ytFragmentedVideo) {
        String btnText;
        if (ytFragmentedVideo.height == -1)
            btnText = "Audio only " + ytFragmentedVideo.audioFile.getFormat().getAudioBitrate() + " kbit/s";
        else
            btnText = (ytFragmentedVideo.videoFile.getFormat().getFps() == 60) ?
                    ytFragmentedVideo.height + "p60" :
                    ytFragmentedVideo.height + "p";

        progressBar.setVisibility(View.GONE);
        String img = IMAGE_BASE_URL + id + "/maxresdefault.jpg";
        String another_img = IMAGE_BASE_URL + id + "/hqdefault.jpg";
        VideoTitle.setText(videoTitle);
        Picasso.get().load(img).resize(1280, 720).into(youtubeImg, new Callback() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load(another_img).resize(1280, 720).into(youtubeImg);
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        });
        btn = new Button(this);
        btn.setAllCaps(false);
        btn.setText(btnText);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename;
                if (videoTitle.length() > 55) {
                    filename = videoTitle.substring(0, 55);
                } else {
                    filename = videoTitle;
                }
                filename = filename.replaceAll("[\\\\><\"|*?%:#/]", "");
                filename += (ytFragmentedVideo.height == -1) ? "" : "-" + ytFragmentedVideo.height + "p";
                String downloadIds = "";
                boolean hideAudioDownloadNotif = false;
                if (ytFragmentedVideo.videoFile != null) {
                    downloadIds += downloadFromUrl(ytFragmentedVideo.videoFile.getUrl(), videoTitle,
                            filename + "." + ytFragmentedVideo.videoFile.getFormat().getExt(), false);
                    downloadIds += "-";
                    hideAudioDownloadNotif = true;
                }
                if (ytFragmentedVideo.audioFile != null) {
                    downloadIds += downloadFromUrl(ytFragmentedVideo.audioFile.getUrl(), videoTitle,
                            filename + "." + ytFragmentedVideo.audioFile.getFormat().getExt(), hideAudioDownloadNotif);
                }
                if (ytFragmentedVideo.audioFile != null)
                    cacheDownloadIds(downloadIds);
                onBackPressed();
            }
        });
        buttonLayout.addView(btn);
    }

    private void cacheDownloadIds(String downloadIds) {
        File cache = new File(this.getCacheDir().getAbsolutePath() + "/" + downloadIds);
        try {
            cache.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long downloadFromUrl(String youtubeDlUrl, String downloadTitle, String fileName, boolean hide) {
        Uri uri = Uri.parse(youtubeDlUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(downloadTitle);
        if (hide) {
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            request.setVisibleInDownloadsUi(false);
        } else
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        return manager.enqueue(request);
    }

    private class YtFragmentedVideo {
        int height;
        YtFile audioFile;
        YtFile videoFile;
    }
}

/*if (isOnline()) {
        getYoutubeDownloadUrl(youtubeDownloadLink);
        buttonLayout.addView(btn);
        } else {
        StyleableToast.makeText(DownloadActivity.this, "Error, no internet available", R.style.alert_scan).show();
        Button buttonError = new Button(DownloadActivity.this);
        buttonError.setText("Check internet connection");
        buttonError.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        if (isOnline()) {
        Toast.makeText(DownloadActivity.this, "Internet is connected", Toast.LENGTH_SHORT).show();
        getYoutubeDownloadUrl(youtubeDownloadLink);
        } else {
        StyleableToast.makeText(DownloadActivity.this, "Error, no internet available", R.style.alert_scan).show();
        }
        }
        });
        buttonLayout.addView(buttonError);
        }
        return;*/




