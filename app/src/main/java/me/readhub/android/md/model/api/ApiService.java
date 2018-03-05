package me.readhub.android.md.model.api;

import me.readhub.android.md.model.entity.News;
import me.readhub.android.md.model.entity.Pageable;
import me.readhub.android.md.model.entity.Topic;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("topic")
    Call<Pageable<Topic>> getTopicList(
            @Query("lastCursor") Long lastCursor,
            @Query("pageSize") int pageSize
    );

    @GET("news")
    Call<Pageable<News>> getNewsList(
            @Query("lastCursor") Long lastCursor,
            @Query("pageSize") int pageSize
    );

    @GET("technews")
    Call<Pageable<News>> getTechNewsList(
            @Query("lastCursor") Long lastCursor,
            @Query("pageSize") int pageSize
    );

    @GET("blockchain")
    Call<Pageable<News>> getBlockChainNewsList(
            @Query("lastCursor") Long lastCursor,
            @Query("pageSize") int pageSize
    );

}
