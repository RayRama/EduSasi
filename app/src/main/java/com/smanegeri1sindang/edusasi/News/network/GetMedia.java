package com.smanegeri1sindang.edusasi.News.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.smanegeri1sindang.edusasi.News.models.media.Media;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMedia {

    public interface Listner{
        void onSuccessful(List<Media> mediaList, int mediaCount, int totalPages);

        void onError(String msg);
    }

    private ApiInterface apiInterface;
    private int page;
    private int totalPages;
    private int totalPosts;
    private String search;
    private String orderBy;
    private Listner listner;
    private Context context;

    public void setPage(int page) {
        this.page = page;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setListner(Listner listner) {
        this.listner = listner;
    }

    public GetMedia(ApiInterface apiInterface, Context context) {
        this.apiInterface = apiInterface;
        this.context = context;
    }


    public void execute(){
        Call<List<Media>> media = apiInterface.getMediaList(page,orderBy,search,"embed");
        Log.e("Making Request",media.request().url().toString());
        media.enqueue(new Callback<List<Media>>() {
            @Override
            public void onResponse(Call<List<Media>> call, Response<List<Media>> response) {
                if(response.isSuccessful()&&response.body().size()>0){
                    Log.e("Returned","Page Returned");
                    try {
                        totalPages = Integer.parseInt(response.headers().get("x-wp-totalpages"));
                        totalPosts = Integer.parseInt(response.headers().get("x-wp-total"));
                    }catch (NumberFormatException e){
                        Toast.makeText(context,"Site not working properly",Toast.LENGTH_SHORT).show();
                    }
                    listner.onSuccessful(response.body(),totalPosts,totalPages);
                }
            }

            @Override
            public void onFailure(Call<List<Media>> call, Throwable t) {
                listner.onError("Unknown Error");
            }
        });
    }

}
