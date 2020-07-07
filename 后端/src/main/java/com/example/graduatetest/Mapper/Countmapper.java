package com.example.graduatetest.Mapper;

import com.example.graduatetest.Entity.Count;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface Countmapper {
    @Insert ("insert into cot(Phonenumber,Count_acquire,Count_number,Count_date) values(#{Phonenumber},#{Count_acquire},#{Count_number},#{Count_date})")
    int addcount(Count count);
    @Select ("select * from cot")
    List<Count> searchcount();
    @Update ({ "update cot set Count_date= #{Count_date},Phonenumber= #{Phonenumber},Count_acquire = #{Count_acquire},Count_number = #{Count_number} where Count_id = #{Count_id}"})
    int updatecount(Count count);
    @Delete("delete from cot where Count_id = #{Count_id}")
    int delecount(String news_id);
    @Select ("select * from cot where Phonenumber = #{Phonenumber}")
    List<Count> searchmycount(String Phonenumber);
}
