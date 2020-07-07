package com.example.graduatetest.Service;

import com.example.graduatetest.Entity.Garbage;

import java.util.List;

public interface GarbageService {
    List<Garbage> getallgarbage() ;
    List<Garbage> searchgarbage(String name);
    void addgarbage(Garbage garbage);
    int updategarbage(Garbage garbage);
    void delegarbage(String garbage_id);
}
