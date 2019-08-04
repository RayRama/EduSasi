package com.smanegeri1sindang.edusasi.News.network;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.smanegeri1sindang.edusasi.News.models.post.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anubhav on 24/11/17 for personal use only.
 * Not permitted to use without permission
 */

public class GetRecentPost {

    private static String CONTEXT_EMBED = "embed";

    public interface Listner{
        void onSuccessful(List<Post> postList, int totalPosts, int totalPages);

        void onError(String msg);
    }

    public GetRecentPost(ApiInterface apiInterface, Context context) {
        this.apiInterface = apiInterface;
        this.context = context;
    }

    private ApiInterface apiInterface;
    private int page;
    private int totalPages;
    private int totalPosts;
    private Integer perPage;
    private String category;
    private String search;
    private Listner listner;
    private Context context;
    private String excluded;
    private String tempdata;

    public String getTempdata() {
        return tempdata;
    }

    public void setTempdata(String tempdata) {
        this.tempdata = tempdata;
    }

    public void setApiInterface(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setOnCompleteListner(Listner listner) {
        this.listner = listner;
    }

    public void setExcluded(String excluded) {
        this.excluded = excluded;
    }

    public void execute(){
        final Call<List<Post>> posts = apiInterface.getPostList(page,category,search,perPage,CONTEXT_EMBED,excluded);
        Log.e("Making Request"," To Url "+posts.request().url());
        posts.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()){
                    try {
                        totalPages = Integer.parseInt(response.headers().get("x-wp-totalpages"));
                        totalPosts = Integer.parseInt(response.headers().get("x-wp-total"));
                    }catch (NumberFormatException e){
                        Toast.makeText(context,"Site not working properly", Toast.LENGTH_SHORT).show();
                        /*Crashlytics.log(e.getLocalizedMessage());*/
                        e.printStackTrace();
                    }
                    allSuccessful(response.body());
                }else{
                    listner.onError("Unknown Error");
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                listner.onError(t.getMessage());
            }
        });
    }

    private void allSuccessful(List<Post> list){
        Log.e("GetRecentPosts","Invoked");
        listner.onSuccessful(list,totalPosts,totalPages);
    }

}
