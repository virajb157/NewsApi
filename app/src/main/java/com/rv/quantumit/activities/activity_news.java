package com.rv.quantumit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.rv.quantumit.OnFetchDataListener;
import com.rv.quantumit.R;
import com.rv.quantumit.ReqManager;
import com.rv.quantumit.adapter.NewsAdapter;
import com.rv.quantumit.bean.ArticlesBean;
import com.rv.quantumit.bean.NewsApiBean;

import java.util.List;

public class activity_news extends AppCompatActivity {
    RecyclerView recyclerView;
    NewsAdapter adapter;
    ProgressDialog dialog;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        searchView = findViewById(R.id.Search_new);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Fetching news articles of "+query);
                dialog.show();
                ReqManager manager = new ReqManager(activity_news.this);
                manager.getNewsHeadlines(listener, "general", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching news articles..");
        dialog.show();

        ReqManager manager = new ReqManager(this);
        manager.getNewsHeadlines(listener, "general", null);
    }
    private final OnFetchDataListener<NewsApiBean> listener = new OnFetchDataListener<NewsApiBean>() {
        @Override
        public void onFetchData(List<ArticlesBean> list, String message) {
            if (list.isEmpty()){
                Toast.makeText(activity_news.this, "No data found!!",Toast.LENGTH_LONG).show();
            }
            else{
                showNews(list);
                dialog.dismiss();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(activity_news.this, "An error occured!!",Toast.LENGTH_LONG).show();
        }
    };

    private void showNews(List<ArticlesBean> list) {
        recyclerView = findViewById(R.id.recycler_news);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new NewsAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }
}