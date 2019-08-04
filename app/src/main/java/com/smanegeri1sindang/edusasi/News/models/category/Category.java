package com.smanegeri1sindang.edusasi.News.models.category;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.smanegeri1sindang.edusasi.News.database.convertors.CmbConvertor;

@Entity(tableName = "categories")
public class Category{

    @SerializedName("parent")
    @ColumnInfo(name = "parent")
    private int parent;

    @SerializedName("cmb2")
    @ColumnInfo(name = "cmb2")
    @TypeConverters(CmbConvertor.class)
    private Cmb2 cmb2;

    @SerializedName("count")
    @ColumnInfo(name = "count")
    private int count;

    @SerializedName("link")
    @ColumnInfo(name = "link")
    private String link;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    private int id;

    @SerializedName("taxonomy")
    @ColumnInfo(name = "taxonomy")
    private String taxonomy;

    @SerializedName("slug")
    @ColumnInfo(name = "slug")
    private String slug;

    @Ignore
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setParent(int parent){
        this.parent = parent;
    }

    public int getParent(){
        return parent;
    }

    public void setCmb2(Cmb2 cmb2){
        this.cmb2 = cmb2;
    }

    public Cmb2 getCmb2(){
        return cmb2;
    }

    public void setCount(int count){
        this.count = count;
    }

    public int getCount(){
        return count;
    }

    public void setLink(String link){
        this.link = link;
    }

    public String getLink(){
        return link;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
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

    public void setTaxonomy(String taxonomy){
        this.taxonomy = taxonomy;
    }

    public String getTaxonomy(){
        return taxonomy;
    }

    public void setSlug(String slug){
        this.slug = slug;
    }

    public String getSlug(){
        return slug;
    }
}