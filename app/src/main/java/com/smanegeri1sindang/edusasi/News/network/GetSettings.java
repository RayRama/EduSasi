package com.smanegeri1sindang.edusasi.News.network;

import android.content.Context;
import android.util.Log;

import com.itsanubhav.wordroid3.models.settings.Setting;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by anubhav on 23/01/18.
 */

public class GetSettings {

    public interface onComplete{
        void onSuccess(Setting settings);
        void onError(String msg);
    }

    private onComplete listner;
    private ApiInterface apiInterface;
    private Context context;
    private Setting settings;

    public void setListner(onComplete listner) {
        this.listner = listner;
    }

    public GetSettings(ApiInterface apiInterface, Context context) {
        this.apiInterface = apiInterface;
        this.context = context;
    }

    public void execute(){
        Call<Setting> settingsCall = apiInterface.getSettings();
        Log.e("Making Request","To: "+settingsCall.request().url());
        settingsCall.enqueue(new Callback<Setting>() {
            @Override
            public void onResponse(Call<Setting> call, Response<Setting> response) {
                if (response.isSuccessful()) {
                    settings = response.body();
                    Log.e("Update","Update Title: "+settings.getUpdateTitle());
                    if(settings!=null) {
                        listner.onSuccess(settings);
                    }
                }else {
                    listner.onError("Response Code: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<Setting> call, Throwable t) {
                Log.e("Error","Mesg: "+t.getMessage());
                listner.onError("Error retrieving settings: "+t.getMessage());
            }
        });
    }
}
