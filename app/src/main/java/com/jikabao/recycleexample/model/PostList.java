package com.jikabao.recycleexample.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PostList {
    @SerializedName("posts")
    private List<Post> posts;

    @SerializedName("last_page")
    private boolean lastPage;
}
