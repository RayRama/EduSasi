package com.smanegeri1sindang.edusasi.News;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.smanegeri1sindang.edusasi.R;

public class NewsDetailActivity extends AppCompatActivity {

    public static final String ARG_TITLE = "ARG_TITLE";
    public static final String ARG_IMAGE = "ARG_IMAGE";
    public static final String ARG_DATESTRING = "ARG_DATESTRING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
    }
}
