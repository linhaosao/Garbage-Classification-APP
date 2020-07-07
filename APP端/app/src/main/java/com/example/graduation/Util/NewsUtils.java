package com.example.graduation.Util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.example.graduation.Activity.FirstActivity;
import com.example.graduation.Beans.NewsBean;
import com.example.graduation.R;
import com.example.graduation.fragment.FristFragment;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsUtils {

    public static ArrayList<NewsBean> getAllNews() throws IOException {
        ArrayList<NewsBean> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            NewsBean newsBean1 = new NewsBean();
            newsBean1.title = "鸟瞰暴雨后的武汉 全市已转移16万人次";
            newsBean1.des = "7月5-6日，武汉普降暴雨-大暴雨，中心城区、蔡甸部分地区出现特大暴雨。江夏大道汤逊湖大桥段，被湖水冲破的路障。记者贾代腾飞 陈卓摄";
            //newsBean1.icon = Drawable.createFromStream(new URL("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584846640134&di=c939b2525473ff3cf9d4636abcafb96e&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F78%2F52%2F01200000123847134434529793168.jpg").openStream(), "image.jpg");
            //newsBean1.icon = (ImageView)  R.drawable.ic_launcher_background;
            newsBean1.news_url = "http://slide.news.sina.com.cn/s/slide_1_2841_101020.html#p=1";
            arrayList.add(newsBean1);

            NewsBean newsBean2 = new NewsBean();
            newsBean2.title = "安徽暴雨 三四十条鳄鱼逃至附近农田";
            newsBean2.des = "因强降雨造成内涝，安徽省芜湖市芜湖县花桥镇鳄鱼湖农庄所养鳄鱼逃跑至附近农田。。据悉，溜出来的鳄鱼为散养的扬子鳄，比较温驯。初步预计有三四十条，具体数量未统计，其中最大的约1.8米长。图为网友拍摄到的农田中的鳄鱼。";
            //newsBean2.icon = Drawable.createFromStream(new URL("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584846640134&di=c939b2525473ff3cf9d4636abcafb96e&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F78%2F52%2F01200000123847134434529793168.jpg").openStream(), "image.jpg");
            newsBean2.news_url = "http://slide.news.sina.com.cn/s/slide_1_2841_101024.html#p=1";
            arrayList.add(newsBean2);

            NewsBean newsBean3 = new NewsBean();
            newsBean3.title = "暴雨过后 南京理工大学变“奇幻森林”";
            newsBean3.des = "近日，持续强降雨，导致地势低洼的南京理工大学出现严重积水。这一组几张照片，南理工恍若童话世界中。网友：泡在水中的南理工，也可以倔强地刷出颜值新高度。";
            //newsBean3.icon = Drawable.createFromStream(new URL("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584846640134&di=c939b2525473ff3cf9d4636abcafb96e&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F78%2F52%2F01200000123847134434529793168.jpg").openStream(), "image.jpg");
            newsBean3.news_url = "http://slide.news.sina.com.cn/s/slide_1_2841_101010.html#p=1";
            arrayList.add(newsBean3);
        }
        return arrayList;
    }
}