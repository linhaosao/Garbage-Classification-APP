package com.example.graduatetest.Mapper;

import com.example.graduatetest.Entity.Garbage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface Garbagemapper {



    @Results (id = "GarbageMap", value = {
            @Result (column = "garbage_id", property = "garbage_id"),
            @Result (column = "garbage_name", property = "garbage_name"),

            @Result (column = "garbage_cat", property = "garbage_cat"),

            @Result (column = "garbage_other", property = "garbage_other"),

    })
    @Select ("select * from garbage ")
    List<Garbage> getallgarbage() ;

    @Results (id = "Garbage", value = {
            @Result (column = "garbage_id", property = "garbage_id"),
            @Result (column = "garbage_name", property = "garbage_name"),

            @Result (column = "garbage_cat", property = "garbage_cat"),

            @Result (column = "garbage_other", property = "garbage_other")

    })
    @Select("select * from garbage where garbage_name  LIKE CONCAT('%',#{name},'%')  ")
    List<Garbage> searchgarbage(String name) ;
    @Insert ("insert into garbage(garbage_name,garbage_cat,garbage_other) values(#{garbage_name},#{garbage_cat},#{garbage_other})")
    void addgarbage(Garbage garbage);
    @Update ({ "update garbage set garbage_name = #{garbage_name},garbage_cat= #{garbage_cat},garbage_other = #{garbage_other} where garbage_id = #{garbage_id}"})
    int updategarbage(Garbage garbage);
    @Delete("delete from garbage where garbage_id = #{garbage_id}")
    void delegarbage(String garbage_id);
}
