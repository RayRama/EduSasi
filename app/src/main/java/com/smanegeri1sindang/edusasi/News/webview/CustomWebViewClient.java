package com.smanegeri1sindang.edusasi.News.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

import com.smanegeri1sindang.edusasi.News.ImageViewerActivity;
import com.smanegeri1sindang.edusasi.News.NewsConfig;
import com.smanegeri1sindang.edusasi.News.WebViewActivity;

import java.util.List;

public class CustomWebViewClient extends WebViewClient {

    private Context context;
    private List<String> images;

    public CustomWebViewClient(Context context,List<String> img) {
        this.context = context;
        this.images = img;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if(request.getUrl().toString().contains(".jpg")||request.getUrl().toString().contains(".png")){
            for (int i=0;i<images.size();i++){
                if (images.get(i).equals(request.getUrl().toString())){
                    //openImages(i);
                }
            }
            return true;
        }else {
            Log.e("Url","Url: "+request.getUrl().toString());
            openWebUrl(request.getUrl().toString());
            return true;
        }
    }

    @Deprecated
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(url.contains(".jpg")||url.contains(".jpg")){
            for (int i=0;i<images.size();i++){
                if (images.get(i).equals(url)){
                    openImages(i);
                }
            }
            return true;
        }else {
            openWebUrl(url);
            return true;
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

    }

    private void openWebUrl(String url){
        if (NewsConfig.OPEN_EXTERNAL_LINKS_IN_APP){
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("URL",url);
            context.startActivity(intent);

        }else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        }
    }

    private void openImages(int i){
        Intent intent = new Intent(context, ImageViewerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("size",images.size());
        intent.putExtra("index",i);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        for(int j=0;j<images.size();j++)
            intent.putExtra("image_"+j,""+images.get(j));
        context.startActivity(intent);
    }
}