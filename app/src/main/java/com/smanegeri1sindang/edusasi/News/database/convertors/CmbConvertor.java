package com.smanegeri1sindang.edusasi.News.database.convertors;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

public class CmbConvertor {
    @TypeConverter
    public static Cmb2 fromString(String value) {
        Gson gson = new Gson();
        Cmb2 cmb2 = gson.fromJson(value,Cmb2.class);
        return cmb2;
    }

    @TypeConverter
    public static String fromArrayList(Cmb2 cmb2) {
        Gson gson = new Gson();
        String json = gson.toJson(cmb2);
        return json;
    }
}
