package com.smanegeri1sindang.edusasi.News;

import java.util.ArrayList;
import java.util.List;

public class NewsConfig {

    public static String WEB_URL = "http://terabyte.smanegeri1sindang.sch.id";

    public static final String DEFAULT_IMG = "https://s25006.pcdn.co/wp-content/uploads/2015/09/google.png";

    public static final boolean ENABLE_SLIDER = true;

    public static final boolean SET_AS_WALPAPPER = false;

    public static final boolean DOWNLOAD_IMG = true;

    public static final boolean OPEN_EXTERNAL_LINK = true;

    public static final int POST_IN_CAROUSEL = 6;

    public static final boolean SHARE_IMG_WITH_POST = false;

    public static final boolean ALLOW_ADD_CARDS = false;

    public static final int POST_IN_EACH_SECTION = 20;

    public static final int DEFAULT_POST_TYPE = 1;


    public static List<String> tab_names = new ArrayList<>();

    public static List<Integer> tab_ids = new ArrayList<>();

    public static int DEF_SITE = 0;

    public static final String ARG_CATEGORY = "arg_category";
    public static final String ARG_SEARCH = "arg_search";
    public static final String ARG_EXCLUDE = "arg_exclude";
    public static final String ARG_FORCE = "arg_force";
    public static final String ARG_COUNT = "arg_count";
    public static final String SECTION_PREFIX = "section_";

    public static final String DEF_SHAREF_PREF = WEB_URL.replace("/","")+"_shared_pref";
    public static final String WEB_URL_INDEX = "site_url_index";

    public static String API_BASE_URL = "https://www.googleapis.com/youtube/v3/playlistItems";
    public static String LIST_OF_PLAYLIST = "https://www.googleapis.com/youtube/v3/playlists";
    public static String YT_SEARCH = "https://www.googleapis.com/youtube/v3/search";
//    public static String CHANNEL_ID = "UCq-Fj5jknLsUf-MWSy4_brA";
//    public static final String API_KEY = "AIzaSyBtENkElp2qwBTgroYErJCGlMY8XPL06hA";
}
