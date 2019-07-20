package com.smanegeri1sindang.edusasi.News.models.post;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.smanegeri1sindang.edusasi.News.database.convertors.BetterFeaturedImageConvertor;
import com.smanegeri1sindang.edusasi.News.database.convertors.CategoryConvertors;
import com.smanegeri1sindang.edusasi.News.database.convertors.ContentConvertor;
import com.smanegeri1sindang.edusasi.News.database.convertors.ExcerptConvertor;
import com.smanegeri1sindang.edusasi.News.database.convertors.NextConvertor;
import com.smanegeri1sindang.edusasi.News.database.convertors.PreviousConvertor;
import com.smanegeri1sindang.edusasi.News.database.convertors.TitleConvertor;

import java.util.List;

@Entity(tableName = "posts")
public class Post{

    @SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    private int id;

    @SerializedName("date")
    @ColumnInfo(name = "date")
    private String date;

    @SerializedName("title")
    @ColumnInfo(name = "title")
    @TypeConverters(TitleConvertor.class)
    private Title title;

    @SerializedName("author_name")
    @ColumnInfo(name = "authorName")
    private String authorName;

    @SerializedName("link")
    @ColumnInfo(name = "link")
    private String link;

    @SerializedName("type")
    @ColumnInfo(name = "type")
    private String type;

    @SerializedName("comment_status")
    @Ignore
    private String comment_status;

    @SerializedName("categories_detail")
    @ColumnInfo(name = "categories")
    @TypeConverters(CategoryConvertors.class)
    private List<CategoriesDetailItem> categoriesDetail;

    @SerializedName("better_featured_image")
    @ColumnInfo(name = "better_featured_image")
    @TypeConverters(BetterFeaturedImageConvertor.class)
    private BetterFeaturedImage betterFeaturedImage;

    @SerializedName("excerpt")
    @ColumnInfo(name = "excerpt")
    @TypeConverters(ExcerptConvertor.class)
    private Excerpt excerpt;

    @SerializedName("comment_count")
    @ColumnInfo(name = "comment_count")
    private int commentCount;

    @SerializedName("content")
    @ColumnInfo(name = "content")
    @TypeConverters(ContentConvertor.class)
    private Content content;

    @SerializedName("slug")
    @ColumnInfo(name = "slug")
    private String slug;

    @SerializedName("next")
    @ColumnInfo(name = "next")
    @TypeConverters(NextConvertor.class)
    private Next next;

    @SerializedName("previous")
    @ColumnInfo(name = "previous")
    @TypeConverters(PreviousConvertor.class)
    private Previous previous;

    @ColumnInfo(name = "tag")
    private String tempfield;

    @SerializedName("categories")
    @Ignore
    private List<Integer> categories;

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getComment_status() {
        return comment_status;
    }

    public void setComment_status(String comment_status) {
        this.comment_status = comment_status;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getTempfield() {
        return tempfield;
    }

    public void setTempfield(String tempfield) {
        this.tempfield = tempfield;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    public void setAuthorName(String authorName){
        this.authorName = authorName;
    }

    public String getAuthorName(){
        return authorName;
    }

    public void setNext(Next next){
        this.next = next;
    }

    public Next getNext(){
        return next;
    }

    public void setPrevious(Previous previous){
        this.previous = previous;
    }

    public Previous getPrevious(){
        return previous;
    }

    public void setLink(String link){
        this.link = link;
    }

    public String getLink(){
        return link;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setTitle(Title title){
        this.title = title;
    }

    public Title getTitle(){
        return title;
    }

    public void setCategoriesDetail(List<CategoriesDetailItem> categoriesDetail){
        this.categoriesDetail = categoriesDetail;
    }

    public List<CategoriesDetailItem> getCategoriesDetail(){
        return categoriesDetail;
    }

    public void setBetterFeaturedImage(BetterFeaturedImage betterFeaturedImage){
        this.betterFeaturedImage = betterFeaturedImage;
    }

    public BetterFeaturedImage getBetterFeaturedImage(){
        return betterFeaturedImage;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setExcerpt(Excerpt excerpt){
        this.excerpt = excerpt;
    }

    public Excerpt getExcerpt(){
        return excerpt;
    }

    public void setSlug(String slug){
        this.slug = slug;
    }

    public String getSlug(){
        return slug;
    }
}