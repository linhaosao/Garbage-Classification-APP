package com.example.graduatetest.Mapper;

import com.example.graduatetest.Entity.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface Newsmapper {
    @Insert("insert into news(news_title,news_pic,news_url,news_content) values(#{news_title},#{news_pic},#{news_url},#{news_content})")
    int add(News news);
    @Select ("select * from news")
    @Results (id = "news", value = {
            @Result (column = "news_id", property = "news_id"),
            @Result (column = "news_title", property = "news_title"),

            @Result (column = "news_pic", property = "news_pic"),

            @Result (column = "news_url", property = "news_url"),
            @Result (column = "news_content",property = "news_content")

    })
    List<News> searchnews() ;
    @Update ({ "update news set news_title= #{news_title},news_pic= #{news_pic},news_url = #{news_url},news_content = #{news_content} where news_id = #{news_id}"})
    int updatenews(News news);
    @Delete("delete from news where news_id = #{news_id}")
    int delenews(String news_id);
}
