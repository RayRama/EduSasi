package com.smanegeri1sindang.edusasi.News.database.convertors;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.smanegeri1sindang.edusasi.News.models.post.Next;

public class NextConvertor {
    @TypeConverter
    public static Next fromString(String value) {
        Gson gson = new Gson();
        Next next = gson.fromJson(value,Next.class);
        return next;
    }

    @TypeConverter
    public static String fromExcerpt(Next next) {
        Gson gson = new Gson();
        return gson.toJson(next);
    }
}
