package com.example.graduatetest.Entity;

public class News {
    private    Integer news_id;
    private String news_title;
    private String news_pic;
    private String news_url;
    private String news_content;

    public News(Integer news_id, String news_title, String news_pic, String news_url, String news_content) {
        this.news_id = news_id;
        this.news_title = news_title;
        this.news_pic = news_pic;
        this.news_url = news_url;
        this.news_content = news_content;
    }

    public Integer getNews_id() {
        return news_id;
    }

    public void setNews_id(Integer news_id) {
        this.news_id = news_id;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_pic() {
        return news_pic;
    }

    public void setNews_pic(String news_pic) {
        this.news_pic = news_pic;
    }

    public String getNews_url() {
        return news_url;
    }

    public void setNews_url(String news_url) {
        this.news_url = news_url;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }
}



