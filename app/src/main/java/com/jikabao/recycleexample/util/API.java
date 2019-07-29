package com.jikabao.recycleexample.util;

import com.jikabao.recycleexample.model.Post;
import com.jikabao.recycleexample.model.PostList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @GET(value = "api/posts")
    Call<PostList> PostList(@Query("page") Integer page);

    @GET(value = "api/posts/{id}")
    Call<Post> PostDetail(@Path("id") String id);
}

