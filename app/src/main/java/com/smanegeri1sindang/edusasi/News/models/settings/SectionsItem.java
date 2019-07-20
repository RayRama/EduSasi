package com.smanegeri1sindang.edusasi.News.models.settings;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SectionsItem{

    public SectionsItem() {
    }

    public SectionsItem(int type) {
        this.type = type;
    }

    @SerializedName("image")
    private String image;

    @SerializedName("category_id")
    private int categoryId;

    @SerializedName("post_count")
    private int postCount;

    @SerializedName("title")
    private String title;

    @SerializedName("type")
    private int type;

    @SerializedName("posts")
    private List<PostsItem> posts;

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return image;
    }

    public void setCategoryId(int categoryId){
        this.categoryId = categoryId;
    }

    public int getCategoryId(){
        return categoryId;
    }

    public void setPostCount(int postCount){
        this.postCount = postCount;
    }

    public int getPostCount(){
        return postCount;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setType(int type){
        this.type = type;
    }

    public int getType(){
        return type;
    }

    public void setPosts(List<PostsItem> posts){
        this.posts = posts;
    }

    public List<PostsItem> getPosts(){
        return posts;
    }
}
