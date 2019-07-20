package com.smanegeri1sindang.edusasi.News.models.post;

import com.google.gson.annotations.SerializedName;

public class Title{

    private int id;

    @SerializedName("rendered")
    private String rendered;

    public void setRendered(String rendered){
        this.rendered = rendered;
    }

    public String getRendered(){
        return rendered;
    }
}