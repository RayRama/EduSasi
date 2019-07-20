package com.smanegeri1sindang.edusasi.News;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.smanegeri1sindang.edusasi.News.models.Notification;
import com.smanegeri1sindang.edusasi.News.models.settings.SectionsItem;

import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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


        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationReceivedHandler(new OneSignal.NotificationReceivedHandler() {
                    @Override
                    public void notificationReceived(OSNotification notification) {
                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
                        String date = df.format(Calendar.getInstance().getTime());
                        Notification noti = new Notification();
                        if (notification.payload.body!=null)
                            noti.setTitle(Jsoup.parse(notification.payload.body).text());
                        if (notification.payload.bigPicture!=null)
                            noti.setImg(notification.payload.bigPicture);
                        noti.setTimestamp(date);
                        JSONObject data = notification.payload.additionalData;
                        String type = data.optString("type");
                        switch (type){
                            case "post":
                                noti.setType(1);
                                noti.setPostId(data.optInt("value"));
                                break;
                            case "web":
                                String url = data.optString("value");
                                if (url!=null)
                                    noti.setUrl(url);
                                noti.setType(2);
                                break;
                            case "message":
                                String title, message;
                                title = data.optString("title");
                                message = data.optString("message");
                                noti.setType(3);
                                noti.setMsgTitle(title);
                                noti.setMsgBody(message);
                                break;

                        }
                        new addToDB().execute(noti);
                    }
                })
                .setNotificationOpenedHandler(new OneSignal.NotificationOpenedHandler() {
                    @Override
                    public void notificationOpened(OSNotificationOpenResult result) {
                        reset();
                        OSNotificationAction.ActionType actionType = result.action.type;
                        JSONObject data = result.notification.payload.additionalData;
                        String type;

                        if (data != null) {
                            type = data.optString("type");
                            switch (type) {
                                case "post": {
                                    int postId = data.optInt("value");
                                    Log.i("OneSignalExample", "Customkey set with value: " + postId);
                                    Intent intent = new Intent(getApplicationContext(), NewsDetailActivity.class);
//                                    intent.putExtra(NewsDetailActivity.ARG_POSTID, postId);
                                }
                            }
                        }
                    }
                }).init();
    }

    private void fetchSections() {
        int count = sharedPreferences.getInt("section_size", 0);
        for (int i=0; i<count; i++) {
            SectionsItem sectionsItem = new SectionsItem();
            sectionsItem.setTitle(sharedPreferences.getString(NewsConfig.SECTION_PREFIX+i+"title", "Example Title"));
            sectionsItem.setImage(sharedPreferences.getString(NewsConfig.SECTION_PREFIX+i+"img", "Example"));
            sectionsItem.setType(sharedPreferences.getInt(NewsConfig.SECTION_PREFIX+i+"type", 0));
            sectionsItem.setPostCount(sharedPreferences.getInt(NewsConfig.SECTION_PREFIX+i+"count", 0));
            sectionsItem.setCategoryId(Integer.parseInt(sharedPreferences.getString(NewsConfig.SECTION_PREFIX+i+"category", "Example")));
            sections.add(sectionsItem);
        }
    }

    private void fetchTabs() {
        int count = sharedPreferences.getInt("tab_size", 0);
        Log.e("TabSize", "Count: "+count);
        NewsConfig.tab_ids.clear();
        NewsConfig.tab_names.clear();
        NewsConfig.tab_ids.add(0);
        NewsConfig.tab_names.add("Latest");

        for (int i=0;i<count;i++) {
            NewsConfig.tab_ids.add(sharedPreferences.getInt("tabs_id"+i, 0));
            NewsConfig.tab_names.add(sharedPreferences.getString("tab_name_"+i, "NULL"));
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void reset() {
        NewsMainApplication.isFirstPostLoaded = false;
        NewsMainApplication.isViewPagerLoaded = false;
        NewsMainApplication.isMainFragmentLoaded = false;
        NewsMainApplication.isSimpleHomeWithSliderLoaded = false;
    }

    class addToDB extends AsyncTask<Notification,Void,Void> {
        @Override
        protected Void doInBackground(Notification... notifications) {
            return null;
        }
    }
}
