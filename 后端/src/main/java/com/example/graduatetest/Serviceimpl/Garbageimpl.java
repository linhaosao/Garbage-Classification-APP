package com.example.graduatetest.Serviceimpl;

import com.example.graduatetest.Entity.Garbage;
import com.example.graduatetest.Mapper.Garbagemapper;
import com.example.graduatetest.Service.GarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Garbageimpl implements GarbageService {
    @Autowired
    private Garbagemapper garbagemapper  ;
    @Override
    public List<Garbage> getallgarbage() {
        return garbagemapper.getallgarbage();
    }

    @Override
    public List<Garbage> searchgarbage(String name) {

        return garbagemapper.searchgarbage(name);
    }

    @Override
    public void addgarbage(Garbage garbage) {
        garbagemapper.addgarbage(garbage);
    }

    @Override
    public int updategarbage(Garbage garbage) {
       return garbagemapper.updategarbage(garbage);
    }

    @Override
    public void delegarbage(String garbage_id) {
        garbagemapper.delegarbage(garbage_id);
    }


}
