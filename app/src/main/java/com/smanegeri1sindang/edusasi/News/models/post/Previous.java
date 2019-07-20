package com.smanegeri1sindang.edusasi.News.models.post;

import com.google.gson.annotations.SerializedName;

public class Previous{

    @SerializedName("id")
    private int id;

    @SerializedName("slug")
    private String slug;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setSlug(String slug){
        this.slug = slug;
    }

    public String getSlug(){
        return slug;
    }
}