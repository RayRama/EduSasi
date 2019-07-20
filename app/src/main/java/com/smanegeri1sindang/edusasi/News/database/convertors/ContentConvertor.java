package com.smanegeri1sindang.edusasi.News.database.convertors;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.smanegeri1sindang.edusasi.News.models.post.Content;

public class ContentConvertor {

    @TypeConverter
    public static Content fromString(String value) {
        Gson gson = new Gson();
        return gson.fromJson(value,Content.class);
    }

    @TypeConverter
    public static String fromContent(Content content) {
        Gson gson = new Gson();
        return gson.toJson(content);
    }

}
