package com.smanegeri1sindang.edusasi.News.models.post;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "next")
public class Next{

    @PrimaryKey(autoGenerate = true)
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