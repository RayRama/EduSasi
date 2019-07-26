package com.smanegeri1sindang.edusasi.News.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.smanegeri1sindang.edusasi.News.database.convertors.CategoryConvertors;
import com.smanegeri1sindang.edusasi.News.models.post.Post;

@Database(entities = {Post.class}, version = 1, exportSchema = false)
@TypeConverters({CategoryConvertors.class})
public abstract class PostsDatabase extends RoomDatabase {

    private static PostsDatabase INSTANCE;

    public abstract PostsDao postsDao();

    public static PostsDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), PostsDatabase.class, "posts")
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
