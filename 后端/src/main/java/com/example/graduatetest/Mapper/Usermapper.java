package com.example.graduatetest.Mapper;

import com.example.graduatetest.Entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface Usermapper {
    @Select ("select * from user WHERE User_phonenumber= #{phonenumber}")
    User getUser(String phonenumber);
    @Insert ("insert into user(User_phonenumber,User_name,User_av) values( #{User_phonenumber},#{User_name},#{User_av})")
    void add(User user);
    @Update ({ "update User set User_name = #{User_name},User_av= #{User_av} where User_phonenumber=#{User_phonenumber}"})
    int updateSysRoleById(User user);
    @Select ("select * from User ")
    List<User> getallUSer();
    @Delete("delete from User where User_phonenumber = #{phonenumber}")
    int deleuser(String phonenumber);
}
