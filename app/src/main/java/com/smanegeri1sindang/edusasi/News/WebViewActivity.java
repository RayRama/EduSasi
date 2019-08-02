package com.smanegeri1sindang.edusasi.News;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.smanegeri1sindang.edusasi.R;

;

public class WebViewActivity extends AppCompatActivity {

    String url, title;
    private TextView titleView, textViewUrl;
    private String downloadUrl, type, disposition;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        if (getIntent() != null) {
            title = getIntent().getStringExtra("title");
            url = getIntent().getStringExtra("URL");
        }

        Toolbar toolbar = findViewById(R.id.webviewToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        titleView = findViewById(R.id.webviewTitle);
        textViewUrl = findViewById(R.id.webviewUrl);
        textViewUrl.setText(url);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final ProgressBar progressBar = findViewById(R.id.webViewProgressBar);
        

    }
}
