package com.smanegeri1sindang.edusasi.News.models.post;

import com.google.gson.annotations.SerializedName;

public class BetterFeaturedImage{

    @SerializedName("alt_text")
    private String altText;

    @SerializedName("post_thumbnail")
    private String postThumbnail;

    @SerializedName("caption")
    private String caption;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private int id;

    @SerializedName("medium_large")
    private String mediumLarge;

    @SerializedName("source_url")
    private String sourceUrl;

    public void setAltText(String altText){
        this.altText = altText;
    }

    public String getAltText(){
        return altText;
    }

    public void setPostThumbnail(String postThumbnail){
        this.postThumbnail = postThumbnail;
    }

    public String getPostThumbnail(){
        return postThumbnail;
    }

    public void setCaption(String caption){
        this.caption = caption;
    }

    public String getCaption(){
        return caption;
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

    public void setMediumLarge(String mediumLarge){
        this.mediumLarge = mediumLarge;
    }

    public String getMediumLarge(){
        return mediumLarge;
    }

    public void setSourceUrl(String sourceUrl){
        this.sourceUrl = sourceUrl;
    }

    public String getSourceUrl(){
        return sourceUrl;
    }
}