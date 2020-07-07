package com.example.graduatetest.Serviceimpl;

import com.example.graduatetest.Entity.User;
import com.example.graduatetest.Mapper.Usermapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Userimpl {


        @Autowired
        private Usermapper usermapper;



        public void add(User user) {

            usermapper.add(user);
        }
        public User get(String phonenumber) {

            System.out.println(usermapper.getUser(phonenumber).getUser_phonenumber());
        return  usermapper.getUser(phonenumber);
        }
        public void update(User user) {
            System.out.println(user.getUser_phonenumber());
        usermapper.updateSysRoleById(user);

    }
    public List<User> getall() {


        return  usermapper.getallUSer();
    }
    public String deleuser(String phonenumber){
            usermapper.deleuser(phonenumber);
            return  "deletesuccess";
    }

    }

