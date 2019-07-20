package com.smanegeri1sindang.edusasi.News.database.convertors;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.smanegeri1sindang.edusasi.News.models.post.Excerpt;

public class ExcerptConvertor {
    @TypeConverter
    public static Excerpt fromString(String value) {
        Gson gson = new Gson();
        Excerpt excerpt = gson.fromJson(value, Excerpt.class);
        return excerpt;
    }

    @TypeConverter
    public static String fromExcerpt(Excerpt excerpt) {
        Gson gson = new Gson();
        return gson.toJson(excerpt);
    }
}
