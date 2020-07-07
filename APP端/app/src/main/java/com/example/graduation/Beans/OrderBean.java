package com.example.graduation.Beans;

public class OrderBean {
    private String Order_id;
    private String Order_phonenumber;
    private String Order_position;
    private String Order_detail;
    private String Order_date;
    private String Order_status;
    private String Order_cat;

    public String getOrder_id() {
        return Order_id;
    }

    public void setOrder_id(String order_id) {
        Order_id = order_id;
    }

    public String getOrder_phonenumber() {
        return Order_phonenumber;
    }

    public OrderBean(String order_id, String order_phonenumber, String order_position, String order_detail, String order_date, String order_status, String order_cat) {
        Order_id = order_id;
        Order_phonenumber = order_phonenumber;
        Order_position = order_position;
        Order_detail = order_detail;
        Order_date = order_date;
        Order_status = order_status;
        Order_cat = order_cat;
    }

    public void setOrder_phonenumber(String order_phonenumber) {
        Order_phonenumber = order_phonenumber;
    }

    public String getOrder_position() {
        return Order_position;
    }

    public void setOrder_position(String order_position) {
        Order_position = order_position;
    }

    public String getOrder_detail() {
        return Order_detail;
    }

    public void setOrder_detail(String order_detail) {
        Order_detail = order_detail;
    }

    public String getOrder_date() {
        return Order_date;
    }

    public void setOrder_date(String order_date) {
        Order_date = order_date;
    }

    public String getOrder_status() {
        return Order_status;
    }

    public void setOrder_status(String order_status) {
        Order_status = order_status;
    }

    public String getOrder_cat() {
        return Order_cat;
    }

    public void setOrder_cat(String order_cat) {
        Order_cat = order_cat;
    }
}
