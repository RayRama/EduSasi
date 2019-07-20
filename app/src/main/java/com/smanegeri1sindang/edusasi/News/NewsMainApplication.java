package com.smanegeri1sindang.edusasi.News;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.multidex.MultiDexApplication;

import com.smanegeri1sindang.edusasi.News.models.settings.SectionsItem;

import java.util.ArrayList;
import java.util.List;

public class NewsMainApplication extends MultiDexApplication {

    public static boolean isFirstPostLoaded;
    public static boolean isViewPagerLoaded;
    public static boolean isMainFragmentLoaded;
    public static boolean isSimpleHomeWithSliderLoaded;
    public static List<Integer> post_id = new ArrayList<>();
    public static List<SectionsItem> sections = new ArrayList<>();
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences(NewsConfig.DEF_SHAREF_PREF, Context.MODE_PRIVATE);
        NewsConfig.DEF_SITE = sharedPreferences.getInt(NewsConfig.WEB_URL_INDEX, 0);
        NewsConfig.WEB_URL = sharedPreferences.getString("demo_site_url", NewsConfig.WEB_URL);
        fetchTabs();
        fetchSections();
    }

    private void fetchSections() {

    }

    private void fetchTabs() {

    }

    private void reset() {
        NewsMainApplication.isFirstPostLoaded = false;
        NewsMainApplication.isViewPagerLoaded = false;
        NewsMainApplication.isMainFragmentLoaded = false;
        NewsMainApplication.isSimpleHomeWithSliderLoaded = false;
    }

}
