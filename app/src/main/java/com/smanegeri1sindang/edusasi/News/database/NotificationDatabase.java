package com.smanegeri1sindang.edusasi.News.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.smanegeri1sindang.edusasi.News.models.Notification;

@Database(entities = {Notification.class}, version = 1, exportSchema = false)
public abstract class NotificationDatabase extends RoomDatabase {

    public static NotificationDatabase INSTANCE;

    public abstract NotificationDao notificationsDao();

    public static NotificationDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context, NotificationDatabase.class, "notifications")
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
