package com.smanegeri1sindang.edusasi.News;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.appbar.AppBarLayout;
import com.smanegeri1sindang.edusasi.News.database.PostsDatabase;
import com.smanegeri1sindang.edusasi.News.models.post.Post;
import com.smanegeri1sindang.edusasi.R;

import org.jsoup.Jsoup;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends AppCompatActivity {

    public static final String ARG_TITLE = "ARG_TITLE";
    public static final String ARG_IMAGE = "ARG_IMAGE";
    public static final String ARG_DATESTRING = "ARG_DATESTRING";
    public static final String ARG_POSTID = "ARG_POSTID";
    public static final String ARG_OFFLINE = "ARG_OFFLINE";
    public static final String ARG_POST_URL = "ARG_URL";
    public static final String ARG_POST_SLUG = "ARG_SLUG";
    public static final String ARG_CATEGORY = "ARG_CATEGORY";


    private String titlePost, imgPost, datePost, catPost;
    private int postId;
    private String slug;
    private boolean offlinePost = false;
    private List<String> images = new ArrayList<>();
    private Post currentPost;
    private TextView titleText, catText, dateText;
    private WebView postWebview;
    private ImageView postImageView;
    private ImageButton saveButton, shareButton;
    private ProgressBar progressBar;
    private LinearLayout loadingView;

    @BindView(R.id.post_app_bar)
    public AppBarLayout appBarLayout;

    @BindView(R.id.ReleatedPostFrame)
    public FrameLayout releatedPost;

    @BindView(R.id.ReleatedPost)
    public LinearLayout releadetPostLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(NewsConfig.DEF_SHAREF_PREF, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbarPost);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsDetailActivity.super.onBackPressed();
            }
        });
        getSupportActionBar().setHomeAsUpIndicator(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back, null));

        if (getIntent() != null) {
            titlePost = getIntent().getStringExtra(ARG_TITLE);
            imgPost = getIntent().getStringExtra(ARG_IMAGE);
            catPost = getIntent().getStringExtra(ARG_CATEGORY);
            datePost = getIntent().getStringExtra(ARG_DATESTRING);
            postId = getIntent().getIntExtra(ARG_POSTID, 0);
            offlinePost = getIntent().getBooleanExtra(ARG_OFFLINE, false);
            slug = getIntent().getStringExtra(ARG_POST_SLUG);
        }

        if (imgPost == null) {
            appBarLayout.setExpanded(false);
        }

        titleText = findViewById(R.id.PostTitle);
        dateText = findViewById(R.id.PostDate);
        catText = findViewById(R.id.PostCategory);
        postImageView = findViewById(R.id.PostImg);
        progressBar = findViewById(R.id.PostProgressBar);
        loadingView = findViewById(R.id.loadingView);

        postWebview = findViewById(R.id.PostWebview);
        postWebview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        postWebview.setLongClickable(false);
        postWebview.setHapticFeedbackEnabled(false);
        loadData();

        if (offlinePost) {
            new fetchFromDatabase().execute(postId);
        } else {
            sendRequest();
        }

        saveButton = findViewById(R.id.SavedPost);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePost();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NewsConfig.SHARE_IMG_WITH_POST) {
                    if (currentPost.getBetterFeaturedImage()!= null
                            && currentPost.getBetterFeaturedImage().getSourceUrl() != null) {
                        new ShareTask(getApplicationContext()).execute(currentPost.getBetterFeaturedImage().getSourceUrl());
                    } else {
                        shareLink();
                    }
                } else {
                    shareLink();
                }
            }
        });

    }

    class ShareTask extends AsyncTask<String, Void, File> {
        private final Context context;
        public ShareTask(Context context) {
            this.context = context;
        }

        @Override
        protected File doInBackground(String... strings) {
            String url = strings[0];
            try{
                return Glide.with(context)
                        .downloadOnly()
                        .load(url)
                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(File file) {
            if (file == null) {
                return;
            }
            Toast.makeText(context,"Share with", Toast.LENGTH_SHORT).show();
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName()+".provider", file);
            share(uri);
        }

        private void share(Uri result) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_SUBJECT, currentPost.getTitle().getRendered());
            intent.putExtra(Intent.EXTRA_TEXT, currentPost.getTitle().getRendered() + "\n\n" + currentPost.getLink());
            intent.putExtra(Intent.EXTRA_STREAM, result);
            startActivity(Intent.createChooser(intent, "Share News To"));
        }
    }

    private void shareLink() {
        if (currentPost != null) {
            Intent send = new Intent();
            send.setAction(Intent.ACTION_SEND);
            send.putExtra(Intent.EXTRA_TEXT, currentPost.getTitle().getRendered() + "\n\n" + currentPost.getLink());
            send.setType("text/plain");
            startActivity(send);
        }

    }

    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmenTag,
                                   @Nullable String backStackStateName) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmenTag)
                .disallowAddToBackStack()
                .commitAllowingStateLoss();
    }

    private void savePost() {

    }

    public class fetchFromDatabase extends AsyncTask<Integer, Integer, Post> {

        @Override
        protected void onPostExecute(Post post) {
            super.onPostExecute(post);
            if (post != null) {
                loadingView.setVisibility(View.GONE);
                currentPost = post;
                loadData(post);
                writeWebView(post);
            }
        }

        @Override
        protected Post doInBackground(Integer... integers) {
            PostsDatabase database = PostsDatabase.getAppDatabase(getApplicationContext());
            return database.postsDao().findByPostId(integers[0]);
        }

    }
    private void writeWebView(Post post) {

    }

    private void sendRequest() {

    }

    private void loadData(Post post) {
        titleText.setText(Jsoup.parse(post.getTitle().getRendered()).text());
        catText.setText(Jsoup.parse(post.getCategoriesDetail().toString()).text());
        DateFormat format = new SimpleDateFormat("dd MM yyyy");
        try {
            Date date = format.parse(post.getDate());
            format = new SimpleDateFormat("dd MM, yyyy");
            String s = format.format(date);
            dateText.setText(s);
        } catch (ParseException e) {
            e.printStackTrace();
            dateText.setText(post.getDate());
        }

        if (post.getBetterFeaturedImage() != null) {
            Glide.with(getApplicationContext()).load(post.getBetterFeaturedImage().getPostThumbnail()).into(postImageView);
        } else {
            Glide.with(getApplicationContext()).load("https://cdn.dribbble.com/users/1753953/screenshots/3818675/animasi-emptystate.gif")
                    .into(postImageView);
        }
    }

    private void loadData() {
        if (titlePost != null) {
            titleText.setText(Jsoup.parse(titlePost).text());
        }

        if (catPost != null) {
            catText.setText(Jsoup.parse(catPost).text());
        }

        if (datePost != null) {
            dateText.setText(Jsoup.parse(datePost).text());
        }

        if (imgPost != null) {
            Glide.with(getApplicationContext()).load(imgPost).into(postImageView);
        }

    }
}
