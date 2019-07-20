package com.smanegeri1sindang.edusasi.News.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.smanegeri1sindang.edusasi.News.models.Notification;

import java.util.List;

@Dao
public interface NotificationDao {
    @Query("SELECT * FROM notifications ORDER BY notifications.id DESC")
    List<Notification> getAll();

    @Query("SELECT * FROM notifications WHERE notifications.id = :id")
    Notification getNotification(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotification(Notification notification);

    @Delete
    void deleteNotification(Notification notification);
}
