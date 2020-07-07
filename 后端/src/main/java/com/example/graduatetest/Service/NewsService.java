package com.example.graduatetest.Service;

import com.example.graduatetest.Entity.News;

import java.util.List;

public interface NewsService {
    int add(News news);
    List<News> searchnews() ;
    int updatenews(News news);
    int delenews(String news_idr);
}
