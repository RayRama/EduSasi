package com.smanegeri1sindang.edusasi.News.network;

import android.content.Context;
import android.util.Log;

import com.smanegeri1sindang.edusasi.News.models.post.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anubhav on 14/12/17.
 */

public class GetPost {
    private Post post;
    private ApiInterface apiInterface;
    private Context context;
    private Integer postId;
    private String password;
    private String slug;
    private boolean isMediaFetched,isAuthorFetched;
    private Listner listner;

    public void setListner(Listner listner) {
        this.listner = listner;
    }

    public interface Listner{
        void onSuccess(Post post);
        void onError(String msg);
    }

    public GetPost(ApiInterface apiInterface, Context context){
        this.apiInterface = apiInterface;
        this.context = context;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void execute(){
        if(postId==0){
            Call<List<Post>> call = apiInterface.getPostBySlug(slug,password);
            Log.e("Making Request","URL: "+call.request().url());
            call.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    if(response.isSuccessful()){
                        if(response.body()!=null&&response.body().size()>0) {
                            post = response.body().get(0);
                            listner.onSuccess(post);
                        }else {
                            listner.onError("");
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    listner.onError("");
                }
            });
        }else {
            Call<Post> call = apiInterface.getPost(postId, slug, password);
            Log.e("Request URL","URL: "+call.request().url());
            call.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    if(response.isSuccessful()&&response.body()!=null){
                        listner.onSuccess(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    listner.onError("");
                }
            });
        }
    }
}
