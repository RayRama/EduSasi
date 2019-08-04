package com.smanegeri1sindang.edusasi.News.network;


import com.smanegeri1sindang.edusasi.News.models.category.Category;
import com.smanegeri1sindang.edusasi.News.models.comment.Comment;
import com.smanegeri1sindang.edusasi.News.models.media.Media;
import com.smanegeri1sindang.edusasi.News.models.playlist.Playlist;
import com.smanegeri1sindang.edusasi.News.models.playlistvideos.MPlaylistVideos;
import com.smanegeri1sindang.edusasi.News.models.post.Post;
import com.smanegeri1sindang.edusasi.News.models.searchPlaylist.PlaylistSearch;
import com.smanegeri1sindang.edusasi.News.models.settings.SectionsItem;
import com.smanegeri1sindang.edusasi.News.models.settings.Setting;
import com.smanegeri1sindang.edusasi.News.models.tag.Tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Anubhav on 19-07-2017.
 */

public interface ApiInterface {
    //Get Post List By Page, By Categories, By Search
    @GET("wp/v2/posts")
    Call<List<Post>> getPostList(
            @Query("page") Integer page,
            @Query("categories") String categories,
            @Query("search") String search,
            @Query("per_page") Integer perPage,
            @Query("context") String context,
            @Query("exclude") String postId
    );

    //Fetch Single Post by id
    @GET("wp/v2/posts/{id}")
    Call<Post> getPost(
            @Path("id") Integer postId,
            @Query("slug") String slug,
            @Query("password") String password
    );

    @GET("wp/v2/posts/")
    Call<List<Post>> getPostBySlug(
            @Query("slug") String slug,
            @Query("password") String password
    );

    //Fetch List of categories
    @GET("wp/v2/categories")
    Call<List<Category>> getCategoriesList(
            @Query("page") Integer page,
            @Query("search") String search,
            @Query("orderby") String orderby,
            @Query("parent") Integer parent,
            @Query("order") String order,
            @Query("hide_empty") Integer hide_empty,
            @Query("post") Integer post,
            @Query("slug") String slug
    );

    @GET("wp/v2/categories")
    Call<List<Category>> get100CategoriesList(
            @Query("page") Integer page,
            @Query("hide_empty") Integer hide_empty,
            @Query("per_page") Integer perPage
    );

    @PUT("api/v1/players/{id}")
    Call<String> setCategoryForNotification(
            @Path("id") String id
    );

    //Fetch Media List
    @GET("wp/v2/media")
    Call<List<Media>> getMediaList(
            @Query("page") Integer page,
            @Query("orderby") String orderby,
            @Query("search") String order,
            @Query("context") String context
    );

    //Fetch Single Media
    @GET("wp/v2/media/{id}")
    Call<Media> getMedia(
            @Path("id") Integer mediaId
    );



    /*//Fetch Single Author
    @GET("wp/v2/users/{id}")
    Call<Author> getAuthor(
            @Path("id") Integer authorId
    );



    //Fetch Single Category
    @GET("wp/v2/categories/{id}")
    Call<Category> getCategory(
            @Path("id") Integer categoryId
    );*/

    @POST("wp/v2/comments")
    Call<Comment> postComment(
            @Query("post") int post,
            @Query("parent") int parent,
            @Query("author_name") String name,
            @Query("author_email") String email,
            @Query("content") String comment
    );

    @GET("wp/v2/comments")
    Call<List<Comment>> getCommentByPostId(
            @Query("post") Integer post,
            @Query("parent") Integer parent,
            @Query("page") Integer page,
            @Query("search") String search,
            @Query("orderby") String orderby
    );

    @GET("wordroid/v2/category/")
    Call<List<SectionsItem>> getCategorySection(
            @Query("id") String id,
            @Query("count") int count
    );

    @GET("wp/v2/tags")
    Call<List<Tag>> getTags(
            @Query("page") int page,
            @Query("search") String query,
            @Query("exclude") String exclude
    );

    /*@GET("wp/v2/pages")
    Call<List<Page>> getpageList(
            @Query("page") Integer page,
            @Query("search") String search,
            @Query("context") String context
    );

    @GET("wp/v2/pages/{id}")
    Call<Page> getPageById(@Path("id") Integer id);*/

    @GET("wordroid/v2/settings")
    Call<Setting> getSettings();

    /*@GET("dashboard/api.php")
    Call<AuthApi> getAuth(
            @Query("key") String key
    );*/

    @GET
    Call<MPlaylistVideos> getPlaylistVideos(@Url String url,
                                            @Query("key") String DevloperKey,
                                            @Query("channelId") String channelId,
                                            @Query("pageToken") String pageToken,
                                            @Query("playlistId") String playlistId,
                                            @Query("part") String part,
                                            @Query("maxResults") int maxResults);

    @GET
    Call<Playlist> getPlaylists(@Url String url,
                                @Query("key") String DevloperKey,
                                @Query("channelId") String channelId,
                                @Query("pageToken") String pageToken,
                                @Query("part") String part,
                                @Query("maxResults") int maxResults

    );

    @GET
    Call<PlaylistSearch> searchPlaylists(@Url String url,
                                         @Query("key") String DevloperKey,
                                         @Query("q") String searchQuery,
                                         @Query("channelId") String channelId,
                                         @Query("pageToken") String pageToken,
                                         @Query("part") String part,
                                         @Query("type") String type,
                                         @Query("maxResults") int maxResults

    );

}
