package com.smanegeri1sindang.edusasi.News.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.smanegeri1sindang.edusasi.News.models.post.Post;

import java.util.List;

@Dao
public interface PostsDao {
    @Query("SELECT * FROM posts")
    List<Post> getAll();

    @Query("SELECT * FROM posts where posts.id = :id")
    Post findByPostId(int id);

    @Query("SELECT COUNT(*) from posts")
    int countDownloads();

    @Query("SELECT * FROM posts WHERE posts.tag = :tag")
    List<Post> findPostsByTag(String tag);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllPosts(List<Post> posts);

    @Delete
    void delete(Post download);
}
