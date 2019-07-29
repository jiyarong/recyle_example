package com.jikabao.recycleexample.model;

import java.util.List;

import lombok.Data;

@Data
public class Post {
    String id;
    String title;
    String updated_at;
    String content;
    List<Tag> tags;
}
