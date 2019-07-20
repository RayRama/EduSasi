package com.smanegeri1sindang.edusasi.News.models.settings;

import com.google.gson.annotations.SerializedName;

public class PostsItem{

    @SerializedName("img")
    private String img;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    public void setImg(String img){
        this.img = img;
    }

    public String getImg(){
        return img;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}