package com.example.graduatetest.Serviceimpl;

import com.example.graduatetest.Entity.Count;
import com.example.graduatetest.Mapper.Countmapper;
import com.example.graduatetest.Service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Countimpl implements CountService {
    @Autowired
    private Countmapper countmapper;
    @Override
    public int addcount(Count count) {
        countmapper.addcount(count);
        return 0;
    }

    @Override
    public List<Count> searchcount() {

        return countmapper.searchcount();
    }

    @Override
    public int updatecount(Count count) {
        countmapper.updatecount(count);
        return 0;
    }

    @Override
    public int delecount(String news_id) {
        countmapper.delecount(news_id);
        return 0;
    }

    @Override
    public List<Count> searchmycount(String Phonenumber) {
        return countmapper.searchmycount(Phonenumber);
    }
}
