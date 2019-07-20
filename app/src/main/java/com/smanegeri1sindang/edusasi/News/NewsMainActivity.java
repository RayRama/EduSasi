package com.smanegeri1sindang.edusasi.News;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.google.android.material.navigation.NavigationView;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;
import com.smanegeri1sindang.edusasi.News.fragments.NewsMixedContent;
import com.smanegeri1sindang.edusasi.News.models.post.Post;
import com.smanegeri1sindang.edusasi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AHBottomNavigation bottomNavigation;
    private AHBottomNavigationViewPager viewPager;
    private AdvanceDrawerLayout drawerLayout;
    private NavViewPagePagerAdapter adapter;
    public SharedPreferences.Editor editor;
    public SharedPreferences sharedPreferences;
    public boolean switchFrag;
    public Post[] posts;
    private int item;
    private int currentItem = 0;
    private TextView toolbarTitle;

    @BindView(R.id.newsToolbar)
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbarTitle = findViewById(R.id.toolbar_title);
        drawerLayout = findViewById(R.id.News_nav_views);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (getIntent() != null) {
            switchFrag = getIntent().getBooleanExtra("themechanged", false);
            currentItem = getIntent().getIntExtra("tabIndex", 0);
        }

        NavigationView navigationView = findViewById(R.id.News_nav_views);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        viewPager = findViewById(R.id.News_view_pager);
        bottomNavigation = findViewById(R.id.News_bottom_navigation);
        setBottomNavigationView();

        adapter = new NavViewPagePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewsMixedContent().newInstance(true));
        //cat
        //notif
        //setting

        viewPager.setOffscreenPageLimit(adapter.getCount());
        if (switchFrag) {
            viewPager.setCurrentItem(4);
        }
        viewPager.setAdapter(adapter);

        if (!isNetworkConnected()) {
            showOfflineDialog();
        }

    }

    private void showOfflineDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ooppss....");
        builder.setMessage("No internet connection or Slow network connection. Please retry or see Saved Posts");
        builder.setPositiveButton("Saved Posts", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /*startActivity(new Intent(getApplicationContext(), OfflinePost.class));*/
            }
        });
        builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                recreate();
            }
        });

        builder.setCancelable(true);
        builder.show();

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void setBottomNavigationView() {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
