package com.smanegeri1sindang.edusasi.YouTubeDL.YouTubeDownloaderComplete;

import androidx.annotation.NonNull;
import androidx.annotation.StyleableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.muddzdev.styleabletoast.StyleableToast;
import com.smanegeri1sindang.edusasi.R;
import com.smanegeri1sindang.edusasi.YouTubeDL.YouTubeDownloader.DownloadActivity;
import com.smanegeri1sindang.edusasi.YouTubeDL.YouTubeDownloaderFinishedReceiver.YouTubeDLReceiver;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadActivityComplete extends AppCompatActivity {

    private static final int ITAG_AUDIO = 140;
    private static Button btn;
    private ProgressBar progressBar;
    private Button buttonDownload;
    private EditText urlEdit;
    private static final String IMG_BASE_URL = "http://img.youtube.com/vi/";
    private static String pattern, youtubeDownloadLink;
    private String size = null;
    private LinearLayout downloadLayout;
    private List<YtFragmentedVideo> list;
    private TextView VideoTitle;
    private ImageView youtubeImg;
    private ProgressBar loading;
    private String video_id, Link, Url;
    static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 9293;
    private Pattern compiledPattern;
    private Matcher matcher;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_complete);
        loading = (ProgressBar) findViewById(R.id.progressBarLoading);
        loading.setVisibility(View.GONE);
        downloadLayout = (LinearLayout)  findViewById(R.id.downloadLayout);
        buttonDownload = (Button) findViewById(R.id.getDownloadLink);
        urlEdit = (EditText) findViewById(R.id.urlEditLink);
        youtubeImg = (ImageView) findViewById(R.id.img_thumbnailComplete);
        VideoTitle = (TextView) findViewById(R.id.videoTitleComplete);
        broadcastReceiver = new YouTubeDLReceiver();
        pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";

        checkPermission();
        AsyncTaskDownload();
        getURL();
    }

    OkHttpClient client = new OkHttpClient();
    public String run(String youtubeDownloadLink) throws Exception {
        Request request = new Request.Builder().url(youtubeDownloadLink).head().build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            size = response.header("Content-Length").toString();
            return size;
        } else return "";
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        broadcastReceiver = new YouTubeDLReceiver();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE");
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void AsyncTaskDownload() {
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        ClipData clipData = appLinkIntent.getClipData();
        Uri appLinkData = appLinkIntent.getData();
        Url = null;

        if (appLinkData != null) {
            Url = appLinkData.toString();
        } else if (clipData != null) {
            if (clipData.getItemCount() > 0)
                Url = clipData.getItemAt(0).getText().toString();
        }

        if (Url != null) {
            Link = urlEdit.getText().toString().trim();
            if (TextUtils.isEmpty(Link) || Link.equals("") || Link == null || Link.contains("://youtu.be/") || Link.contains("youtube.com/watch?v=")) {
                urlEdit.setText(Url);
                loading.setVisibility(View.VISIBLE);
                youtubeDownloadLink = Url;
                getYoutubeDownloadUrl(youtubeDownloadLink);
                buttonDownload.setEnabled(false);
            } else {
                /*StyleableToast.makeText(getApplicationContext(), "Not Valid YouTube Url or Empty", R.style.alert_scan).show();*/
                urlEdit.getText().clear();
                urlEdit.setText(Url);
                loading.setVisibility(View.VISIBLE);
                youtubeDownloadLink = Url;
                getYoutubeDownloadUrl(youtubeDownloadLink);
                buttonDownload.setEnabled(false);
            }
        }
    }

    public void getURL() {
        Link = urlEdit.getText().toString().trim();
        if (TextUtils.isEmpty(Link) || Link.equals("") || Link == null) {
            buttonDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StyleableToast.makeText(getApplicationContext(), "Url is empty", R.style.alert_scan).show();
                }
            });
        }

        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                youtubeDownloadLink = urlEdit.getText().toString();
                if (youtubeDownloadLink.contains("://youtu.be/") || youtubeDownloadLink.contains("youtube.com/watch?v=")) {
                    getYoutubeDownloadUrl(youtubeDownloadLink);
                } else {
                    StyleableToast.makeText(getApplicationContext(), "Not Valid YouTube URL", R.style.alert_scan).show();
                    loading.setVisibility(View.GONE);
                }
            }
        });
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

                    if (ytFile.getFormat().getHeight() == -1 || ytFile.getFormat().getItag() == 17
                    || ytFile.getFormat().getItag() == 242 || ytFile.getFormat().getItag() == 18 || ytFile.getFormat().getItag() == 244
                    || ytFile.getFormat().getItag() == 22 || ytFile.getFormat().getHeight() >= 144
                        /*ytFile.getFormat().getHeight() >= 144*/) {
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
        }.extract(youtubeDownloadLink, true, true);
    }

    private void addFormatToList(YtFile ytFile, SparseArray<YtFile> ytFiles) {
        int height = ytFile.getFormat().getHeight();
        if (height != -1) {
            for (DownloadActivityComplete.YtFragmentedVideo fragmentedVideo : list) {
                if (fragmentedVideo.height == height && (fragmentedVideo.videoFile == null
                        || fragmentedVideo.videoFile.getFormat().getFps() == ytFile.getFormat().getFps())) {
                    return;
                }
            }
        }
        DownloadActivityComplete.YtFragmentedVideo fragmentedVideo = new DownloadActivityComplete.YtFragmentedVideo();
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

    private void addButton(final String videoTitle, final DownloadActivityComplete.YtFragmentedVideo ytFragmentedVideo) {
        String btnText;
        String a = size;
        if (ytFragmentedVideo.height == -1)
            btnText = "Audio only " + ytFragmentedVideo.audioFile.getFormat().getAudioBitrate() + " kbit/s " + size;
        else
            btnText = (ytFragmentedVideo.videoFile.getFormat().getFps() == 60) ?
                    ytFragmentedVideo.height + "p60 " + a :
                    ytFragmentedVideo.height + "p " + a;

        loading.setVisibility(View.GONE);
        compiledPattern = Pattern.compile(pattern,
                Pattern.CASE_INSENSITIVE);
        matcher = compiledPattern.matcher(Url);
        if (matcher.find()) {
            video_id = matcher.group(1);
        }
        String img = IMG_BASE_URL + video_id + "/maxresdefault.jpg";
        String another_img = IMG_BASE_URL + video_id + "/hqdefault.jpg";
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
                /*finish();*/
            }
        });
        downloadLayout.addView(btn);
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
