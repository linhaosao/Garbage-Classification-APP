package com.example.graduatetest.Serviceimpl;

import com.example.graduatetest.Entity.Rating;
import com.example.graduatetest.Mapper.Ratingmapper;
import com.example.graduatetest.Service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Ratingimpl implements RatingService {
    @Autowired
    private Ratingmapper ratingmapper;
    @Override
    public void addrating(Rating rating) {
        ratingmapper.addrating(rating);
    }

    @Override
    public int updaterating(Rating rating) {
        ratingmapper.updaterating(rating);
        return 0;
    }

    @Override
    public List<Rating> getallrating() {

        return ratingmapper.getallrating();
    }

    @Override
    public int delerating(String phonenumber) {
        ratingmapper.delerating(phonenumber);
        return 0;
    }
}
