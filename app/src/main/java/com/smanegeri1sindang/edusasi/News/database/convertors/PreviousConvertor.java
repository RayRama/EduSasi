package com.smanegeri1sindang.edusasi.News.database.convertors;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.smanegeri1sindang.edusasi.News.models.post.Previous;

public class PreviousConvertor {
    @TypeConverter
    public static Previous fromString(String value) {
        Gson gson = new Gson();
        Previous previous = gson.fromJson(value,Previous.class);
        return previous;
    }

    @TypeConverter
    public static String fromExcerpt(Previous previous) {
        Gson gson = new Gson();
        return gson.toJson(previous);
    }
}