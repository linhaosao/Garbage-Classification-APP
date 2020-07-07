package com.example.graduatetest.Service;

import com.example.graduatetest.Entity.Count;

import java.util.List;

public interface CountService {
    int addcount(Count count);
    List<Count> searchcount() ;
    int updatecount(Count count);
    int delecount(String news_id);
    List<Count> searchmycount(String Phonenumber) ;
}
