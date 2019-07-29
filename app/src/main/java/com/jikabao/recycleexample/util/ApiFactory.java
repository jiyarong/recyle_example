package com.jikabao.recycleexample.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {
    static API instance;

    public static API getInstance() {
        if (instance == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://vblog.peterji.cn/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            instance = retrofit.create(API.class);

        }

        return instance;
    }
}
