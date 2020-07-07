package com.example.graduatetest.PagesController;

import com.example.graduatetest.Entity.*;
import com.example.graduatetest.Serviceimpl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping ( value = "/hello",produces = "application/json;charset=utf-8")
public class Welcome {
    @Autowired
    private Garbageimpl garbageimpl ;
    @Autowired
    private Orderimpl orderimpl ;
    @Autowired
    private Ratingimpl ratingimpl ;
    @Autowired
    private Newsimpl newsimpl ;
    @Autowired
    private Userimpl userimpl ;
    @Autowired
    private Countimpl countimpl;
    @RequestMapping ("/login")
    @ResponseBody
    public String index(){
        return "hello";
    }

    @RequestMapping("/garbage")
    @ResponseBody
    public List<Garbage> selectgarbage(){
        List<Garbage> list =garbageimpl.getallgarbage();
        System.out.println(list.get(0));
        return garbageimpl.getallgarbage();
    }
    @RequestMapping("/getgarbage")
    @ResponseBody
    public List<Garbage>  Administrator(String name){
        List<Garbage> garbage=garbageimpl.searchgarbage(name);
        return  garbageimpl.searchgarbage(name);
    }
    @RequestMapping("/orderinsert")
    @ResponseBody
    public String  Order(Order order){
        String time =null;
        System.out.println(order.getOrder_phonenumber());
        System.out.println(order.getOrder_date());
        System.out.println(order.getOrder_detail());
        System.out.println(order.getOrder_position());
        System.out.println(order.getOrder_status());
        System.out.println("长度为："+order.getOrder_date().length());
        System.out.println(order.getOrder_date().length()>15);
        if(order.getOrder_date().length()>15){
            if(Integer.parseInt(order.getOrder_date().substring(5,7))<=10)
            {time=order.getOrder_date().substring(0,5)+order.getOrder_date().substring(6,10);}else
            {
                time=order.getOrder_date().substring(0,10);
            }
            System.out.println(time);
            order.setOrder_date(time);
        }
        if(order.getOrder_date().length()<12)
            order.setOrder_date("预约日期: "+time);
        orderimpl.add(order);
        return  "sucess";

    }
    @RequestMapping("/searchnews")
    @ResponseBody
    public List<News> searchnews() {
        return newsimpl.searchnews();
    }
    @RequestMapping("/addnews")
    @ResponseBody
    public void addnews(News news) {
        System.out.println(news.getNews_url());
        newsimpl.add(news);
    }
    @RequestMapping("/userinsert")
    @ResponseBody
    public String  User( User user){
        System.out.println(user);
        System.out.println(user.getUser_phonenumber());
        System.out.println(user.getUser_av());
        System.out.println(user.getUser_name());
        userimpl.add(user);
        return  "sucess";

    }
    @RequestMapping("/alluser")
    @ResponseBody
    public List<User>  allUser(){

        return  userimpl.getall();

    }
    @RequestMapping("/selectuser")
    @ResponseBody
    public User getUser(String phonenumber){
        return  userimpl.get(phonenumber);
    }
    @RequestMapping("/searchorder")
    @ResponseBody
    public List<Order>  Comeman(String name){
        return orderimpl.searchorder(name);
    }
    @RequestMapping("/updateuser")
    @ResponseBody
    public void getUser(User user){
       userimpl.update(user);
    }
    @RequestMapping("/deleuser")
    @ResponseBody
    public String deleUser(User user){
       return  userimpl.deleuser(   user.getUser_phonenumber());
    }
    @RequestMapping("/updateorder")
    @ResponseBody
    public int updateorder(Order order) {
        System.out.println(order.getOrder_status());
        return orderimpl.updateorder(order);
    }
    @RequestMapping("/deleorder")
    @ResponseBody
    public int deleorder(Order order) {
        System.out.println(String.valueOf( order.getOrder_id()));
        return orderimpl.deleorder(String.valueOf(  order.getOrder_id()));
    }
    @RequestMapping("/updatenews")
    @ResponseBody
    public int updatenews(News news) {
        return newsimpl.updatenews(news);
    }
    @RequestMapping("/delenews")
    @ResponseBody
    public int delenews(News news) {
        return newsimpl.delenews(String.valueOf(    news.getNews_id()));
    }
    @RequestMapping("/addgarbage")
    @ResponseBody
    public void addgarbage(Garbage garbage) {
        garbageimpl.addgarbage(garbage);
    }
    @RequestMapping("/updategarbage")
    @ResponseBody
    public int updategarbage(Garbage garbage) {
        return garbageimpl.updategarbage(garbage);
    }
    @RequestMapping("/delegarbage")
    @ResponseBody
    public void delegarbage(Garbage garbage) {
        garbageimpl.delegarbage(    garbage.getGarbage_id());
    }
    @RequestMapping("/addrating")
    @ResponseBody
    public void addrating(Rating rating) {
        System.out.println("666");
        String time = null;
        if(rating.getRating_date().length()>11){
            if(Integer.parseInt(rating.getRating_date().substring(5,7))<=10)
            {time=rating.getRating_date().substring(0,5)+rating.getRating_date().substring(6,10);}else
            {
                time=rating.getRating_date().substring(0,10);
            }
            System.out.println(time);
            rating.setRating_date(time);
        }
        ratingimpl.addrating(rating);
    }

    @RequestMapping("/updaterating")
    @ResponseBody
    public int updaterating(Rating rating) {
        ratingimpl.updaterating(rating);
        return 0;
    }

    @RequestMapping("/getallrating")
    @ResponseBody
    public List<Rating> getallrating() {

        return ratingimpl.getallrating();
    }
    @RequestMapping("/delerating")
    @ResponseBody
    public int delerating(Rating rating) {
        ratingimpl.delerating( rating.getRating_id());
        return 0;
    }
    @RequestMapping("/addcount")
    @ResponseBody
    public void addcount(Count count) {
        System.out.println(count.getCount_date());
        System.out.println(count.getCount_acquire());
        System.out.println(count.getCount_number());
        System.out.println(count.getPhonenumber());
        System.out.println("666");
        String time = null;
        if(count.getCount_date().length()>11)
        { if(Integer.parseInt(count.getCount_date().substring(5,7))<=10)
        {time=count.getCount_date().substring(0,5)+count.getCount_date().substring(6,10);}else
        {
            time=count.getCount_date().substring(0,10);
        }
        System.out.println(time);
        count.setCount_date(time);}
        countimpl.addcount(count);
    }

    @RequestMapping("/updatecount")
    @ResponseBody
    public int updatecount(Count count) {
        countimpl.updatecount(count);
        return 0;
    }

    @RequestMapping("/getallcount")
    @ResponseBody
    public List<Count> getallcount() {
        return countimpl.searchcount();
    }
    @RequestMapping("/delecount")
    @ResponseBody
    public int delecount(Count count) {
        countimpl.delecount( count.getCount_id());
        return 0;
    }
    @RequestMapping("/getcount")
    @ResponseBody
    public List<Count> getcount(String Phonenumber) {

        return countimpl.searchmycount(Phonenumber);
    }
    @RequestMapping("/getallorder")
    @ResponseBody
    public List<Order> getallorder() {
        return orderimpl.getallorder();
    }
}
