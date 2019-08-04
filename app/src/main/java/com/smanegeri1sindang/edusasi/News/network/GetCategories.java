package com.smanegeri1sindang.edusasi.News.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.itsanubhav.wordroid3.database.CategoryDatabase;
import com.itsanubhav.wordroid3.models.category.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anubhav on 24/11/17 for personal use only.
 * Not permitted to use without permission
 */

public class GetCategories {

    private List<Category> categoryList = new ArrayList<>();

    public interface Listner{
        void onSuccessful(List<Category> list, int totalPages);

        void onError(String msg);
    }

    private Listner listner;
    private ApiInterface apiInterface;
    private static String context_embed = "embed";
    private boolean contextEmbedEnabled;
    private Context context;
    private int totalPages;
    private Integer page,post,hideEmpty,parent;
    private String search,orderBy,order,slug;
    private boolean loadall;

    public GetCategories(ApiInterface apiInterface, Context context) {
        this.apiInterface = apiInterface;
        this.context = context;
    }

    public void setContextEmbedEnabled(boolean contextEmbedEnabled) {
        this.contextEmbedEnabled = contextEmbedEnabled;
    }

    public boolean isLoadall() {
        return loadall;
    }

    public void setLoadall(boolean loadall) {
        this.loadall = loadall;
    }

    public void setListner(Listner listner) {
        this.listner = listner;
    }

    public void setApiInterface(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setPost(Integer post) {
        this.post = post;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public void setHideEmpty(Integer hideEmpty) {
        this.hideEmpty = hideEmpty;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void execute(){
        if(isNetworkAvailable()) {
            if (loadall){
                Call<List<Category>> categories = apiInterface.get100CategoriesList(page,hideEmpty,100);
                Log.e("Making Request to", " url:" + categories.request().url());
                categories.enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        if (response.isSuccessful()) {
                            Log.e("Successful", "Posts returned " + response.body().size());
                            try {
                                totalPages = Integer.parseInt(response.headers().get("x-wp-totalpages"));
                            }catch (NumberFormatException e){
                                totalPages = 0;
                                Toast.makeText(context,"Invalid total page count", Toast.LENGTH_SHORT).show();
                                Crashlytics.log(e.getLocalizedMessage());
                            }
                            categoryList = response.body();
                            listner.onSuccessful(categoryList, totalPages);
                            new addToDatabase().execute(response.body().toArray(new Category[response.body().size()]));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        listner.onError("Error connecting to server");
                    }
                });

            }else {
                Call<List<Category>> categories = apiInterface.getCategoriesList(page, search, orderBy, parent, order, 1, post, slug);
                Log.e("Making Request to", " url:" + categories.request().url());
                categories.enqueue(new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        if (response.isSuccessful()) {
                            Log.e("Successful", "Posts returned " + response.body().size());
                            try {
                                totalPages = Integer.parseInt(response.headers().get("x-wp-totalpages"));
                            }catch (NumberFormatException e){
                                Toast.makeText(context,"Site not working properly", Toast.LENGTH_SHORT).show();
                            }

                            categoryList = response.body();
                            listner.onSuccessful(categoryList, totalPages);
                            new addToDatabase().execute(response.body().toArray(new Category[response.body().size()]));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        listner.onError("Error connecting to server");
                    }
                });
            }
        }else {
            listner.onError("Error connecting to server");
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public class addToDatabase extends AsyncTask<Category, Integer, Void> {

        @Override
        protected Void doInBackground(Category... posts) {
            if (posts.length>0) {
                List<Category> list = new ArrayList<>(Arrays.asList(posts));
                CategoryDatabase db = CategoryDatabase.getAppDatabase(context);
                db.categoryDao().insertAllCategories(list);
                Log.e("PostsDao","Added "+posts.length+" posts");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }


}
