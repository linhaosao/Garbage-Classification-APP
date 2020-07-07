package com.example.graduatetest.Serviceimpl;

import com.example.graduatetest.Entity.News;
import com.example.graduatetest.Mapper.Newsmapper;
import com.example.graduatetest.Service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Newsimpl implements NewsService {
    @Autowired
    private Newsmapper newsmapper;


    @Override
    public int add(News news) {
        System.out.println("6666666666666666");
        return newsmapper.add(news);
    }

    @Override
    public List<News> searchnews() {
        return newsmapper.searchnews();
    }

    @Override
    public int updatenews(News news) {
        return newsmapper.updatenews(news);
    }

    @Override
    public int delenews(String news_id) {
        return newsmapper.delenews(news_id);
    }
}
