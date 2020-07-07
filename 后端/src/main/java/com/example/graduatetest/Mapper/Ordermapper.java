package com.example.graduatetest.Mapper;

import com.example.graduatetest.Entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface Ordermapper {
    @Insert("insert into roder(Order_phonenumber,Order_position,Order_detail,Order_date,Order_cat,Order_status) values(#{Order_phonenumber},#{Order_position},#{Order_detail},#{Order_date},#{Order_cat},#{Order_status})")
    void add(Order order);
    @Select("select * from roder where Order_phonenumber =#{name} ")
    List<Order> searchorder(String name);
    @Update ({ "update roder set Order_phonenumber = #{Order_phonenumber},Order_position= #{Order_position},Order_detail = #{Order_detail},Order_date = #{Order_date},Order_cat = #{Order_cat},Order_status = #{Order_status} where Order_id = #{Order_id}"})
    int updateorder(Order order);
    @Delete ("delete from roder where Order_id = #{Order_id}")
    int deleorder(String Order_id);
    @Select("select * from roder  ")
    List<Order> getallorder();

}
