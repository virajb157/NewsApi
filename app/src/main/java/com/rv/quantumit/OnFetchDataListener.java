package com.rv.quantumit;

import com.rv.quantumit.bean.ArticlesBean;

import java.util.List;

public interface OnFetchDataListener<NewsBean> {
    void onFetchData(List<ArticlesBean> list, String message);
    void onError(String message);
}
