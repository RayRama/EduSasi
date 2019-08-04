package com.smanegeri1sindang.edusasi.News.network;


import android.content.Context;
import android.util.Log;

import com.itsanubhav.wordroid3.models.settings.SectionsItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetSectionItem {

    public interface onComplete{
        void onSuccess(List<SectionsItem> sectionsItems);
        void onError(String msg);
    }

    private onComplete listner;
    private ApiInterface apiInterface;
    private Context context;
    private SectionsItem sectionsItem;

    private int count;
    private String ids;

    public GetSectionItem(ApiInterface apiInterface, Context context) {
        this.apiInterface = apiInterface;
        this.context = context;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setId(String ids) {
        this.ids = ids;
    }

    public void setListner(onComplete listner) {
        this.listner = listner;
    }

    public void execute(){
        Call<List<SectionsItem>> call = apiInterface.getCategorySection(ids,count);
        Log.e("MakingRequest","To: "+call.request().url());
        call.enqueue(new Callback<List<SectionsItem>>() {
            @Override
            public void onResponse(Call<List<SectionsItem>> call, Response<List<SectionsItem>> response) {
                if (response.isSuccessful()){
                    listner.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<SectionsItem>> call, Throwable t) {
                listner.onError(t.getMessage());
            }
        });
    }
}

