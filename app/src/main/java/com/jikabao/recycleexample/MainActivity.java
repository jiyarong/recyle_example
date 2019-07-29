package com.jikabao.recycleexample;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jikabao.recycleexample.model.Post;
import com.jikabao.recycleexample.model.PostList;
import com.jikabao.recycleexample.util.API;
import com.jikabao.recycleexample.util.ApiFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements InfiniteScrollListener.OnLoadMoreListener {
    private RecyclerView recycle_view;
    private ActivityAdapter adapter;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context = this;
    InfiniteScrollListener infiniteScrollListener;

    List<Post> data =  new ArrayList<>();
    API api = ApiFactory.getInstance();
    Integer currentPage = 1;
    boolean loading = true;
    boolean lastPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //找到xml里对应的recycle_view
        recycle_view = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        //定义一个layoutManager，这是recycle_view的必选项
        layoutManager = new LinearLayoutManager(this);
        infiniteScrollListener = new InfiniteScrollListener(layoutManager, this);

        //实例化一个adapter，负责管理列表的数据和渲染，也是必选项，adapter会在之后设置
        adapter = new ActivityAdapter(data);

        recycle_view.setLayoutManager(layoutManager);
        recycle_view.setAdapter(adapter);
        infiniteScrollListener.setLoaded();
        recycle_view.addOnScrollListener(infiniteScrollListener);

        adapter.setOnItemClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int itemPosition = recycle_view.getChildLayoutPosition(view);
                Post post = data.get(itemPosition);
                Intent intent = new Intent(context, PostDetailActivity.class);
                String message = post.getId();
                intent.putExtra("PostId", message);
                startActivity(intent);
            }
        });

        fetchData();
    }

    void fetchData () {
        loading = true;
//        adapter.addNullData();
        api.PostList(this.currentPage).enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                PostList postList = response.body();
                lastPage = postList.isLastPage();
//                adapter.removeNull();
                adapter.addData(postList.getPosts());
                //data.addAll(response.body().getPosts());
                currentPage += 1;
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {

            }
        });
    };

    void refresh() {
        data.removeAll(data);
        currentPage = 1;
        adapter.notifyDataSetChanged();
        fetchData();
    }

    @Override
    public void onLoadMore() {
        fetchData();
    }
}
