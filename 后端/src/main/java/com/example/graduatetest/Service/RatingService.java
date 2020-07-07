package com.example.graduatetest.Service;

import com.example.graduatetest.Entity.Rating;

import java.util.List;

public interface RatingService {
    void addrating(Rating rating);
    int updaterating(Rating rating);
    List<Rating> getallrating();
    int delerating(String phonenumber);
}
