package com.example.li.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LoadMoreRecyclerView mRecyclerView;
    private List<Object> mDatas;
    private BaseAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("周六学习RecyclerView并封装");
        setSupportActionBar(toolbar);
        mDatas = new ArrayList<Object>();
        initData();



        mRecyclerView = (LoadMoreRecyclerView) findViewById(R.id.id_recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);


        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        initData();
                        mRecyclerView.notifyMoreFinish(true, 1);
                    }
                }, 3000);
            }
        });
//new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //  mRecyclerView.setLayoutManager( new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter = new BaseAdapter(this, R.layout.item_rec, mDatas));
        mAdapter.setOnItemClickLitener(new BaseAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));

        mRecyclerView.setAutoLoadMoreEnable(true);
        View v = LinearLayout.inflate(this, R.layout.head, null);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "hahahhaha", Toast.LENGTH_SHORT).show();
            }
        });
//        mRecyclerView.getRootView();
        mRecyclerView.addHeaderView(v);
        //mRecyclerView.setLoadingMore(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        mRecyclerView.setLoadingMore(true);
                        initData();
                        mRecyclerView.notifyMoreFinish(true, 0);
                    }
                }, 2000);

            }
        });
    }

    protected void initData() {

        for (int i = 0; i < 50; i++) {
            User user = new User();
            user.setText("a" + i);
            mDatas.add(user);
        }
    }
}
