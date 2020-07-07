package com.example.graduatetest.Mapper;

import com.example.graduatetest.Entity.Rating;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface Ratingmapper {
    @Insert ("insert into rating(Rating_bar,Rating_phonenumer,Rating_date) values( #{Rating_bar},#{Rating_phonenumer},#{Rating_date})")
    void addrating(Rating rating);
    @Update ({ "update rating set Rating_bar = #{Rating_bar},Rating_phonenumer= #{Rating_phonenumer},Rating_date = #{Rating_date} where Rating_id = #{Rating_id}"})
    int updaterating(Rating rating);
    @Select ("select * from rating ")
    List<Rating> getallrating();
    @Delete("delete from rating where Rating_id = #{Rating_id}")
    int delerating(String phonenumber);

}
