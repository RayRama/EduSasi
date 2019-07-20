package com.smanegeri1sindang.edusasi.News.database.convertors;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.smanegeri1sindang.edusasi.News.models.post.BetterFeaturedImage;

public class BetterFeaturedImageConvertor {
    @TypeConverter
    public static BetterFeaturedImage fromString(String value) {
        Gson gson = new Gson();
        BetterFeaturedImage betterFeaturedImage = gson.fromJson(value,BetterFeaturedImage.class);
        return betterFeaturedImage;
    }

    @TypeConverter
    public static String fromArrayList(BetterFeaturedImage betterFeaturedImage) {
        Gson gson = new Gson();
        String json = gson.toJson(betterFeaturedImage);
        return json;
    }
}