package com.example.graduatetest.Service;

import com.example.graduatetest.Entity.Order;

import java.util.List;

public interface OrderService {
    void add(Order order);
    List<Order> searchorder(String number);
    int updateorder(Order order);
    int deleorder(String Order_id);
    List<Order> getallorder();

}
