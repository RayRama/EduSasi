package com.smanegeri1sindang.edusasi.News.models.post;

import com.google.gson.annotations.SerializedName;

public class CategoriesDetailItem{

    @SerializedName("parent")
    private int parent;

    @SerializedName("name")
    private String name;

    @SerializedName("count")
    private int count;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private int id;

    @SerializedName("slug")
    private String slug;

    public void setParent(int parent){
        this.parent = parent;
    }

    public int getParent(){
        return parent;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setCount(int count){
        this.count = count;
    }

    public int getCount(){
        return count;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

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
