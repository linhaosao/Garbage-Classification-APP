package com.example.graduatetest.Serviceimpl;

import com.example.graduatetest.Entity.Order;
import com.example.graduatetest.Mapper.Ordermapper;
import com.example.graduatetest.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Orderimpl implements OrderService {
    @Autowired
    private Ordermapper ordermapper;


    @Override
    public void add(Order order) {
        System.out.println(order.getOrder_phonenumber());
        System.out.println(order.getOrder_date());
        System.out.println(order.getOrder_detail());
        System.out.println(order.getOrder_position());
        ordermapper.add(order);
    }

    @Override
    public List<Order> searchorder(String number) {
        return ordermapper.searchorder(number);
    }

    @Override
    public int updateorder(Order order) {
        return ordermapper.updateorder(order);
    }

    @Override
    public int deleorder(String Order_id) {
        return ordermapper.deleorder(Order_id);
    }

    @Override
    public List<Order> getallorder() {
        return ordermapper.getallorder();
    }


}
