package com.smanegeri1sindang.edusasi.News.models.category;

import com.google.gson.annotations.SerializedName;

public class TerraFields {

    @SerializedName("category_image")
    private String categoryImage;

    @SerializedName("category_color")
    private String categoryColor;

    @SerializedName("hide_category")
    private String hideCategory;

    public String getHideCategory() {
        return hideCategory;
    }

    public void setHideCategory(String hideCategory) {
        this.hideCategory = hideCategory;
    }

    public void setCategoryImage(String categoryImage){
        this.categoryImage = categoryImage;
    }

    public String getCategoryImage(){
        return categoryImage;
    }

    public void setCategoryColor(String categoryColor){
        this.categoryColor = categoryColor;
    }

    public String getCategoryColor(){
        return categoryColor;
    }
}