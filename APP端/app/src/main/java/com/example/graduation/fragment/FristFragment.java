package com.example.graduation.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.example.graduation.Activity.FirstActivity;
import com.example.graduation.Adapter.ListViewAdapter;
import com.example.graduation.Beans.NewsBean;
import com.example.graduation.Beans.UserBean;
import com.example.graduation.Post;
import com.example.graduation.R;
import com.example.graduation.Util.NewsUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

public class FristFragment  extends Fragment{

    private List<NewsBean> list1=new ArrayList<>();
    private ListView listView;
    private String a,b,c,d;
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_frist , container, false);
        listView = (ListView)view.findViewById(R.id.lv);
        search();
        try {
            Thread.currentThread().sleep(1000);//阻断1秒

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            listView.setAdapter(new ListViewAdapter(getActivity(), list1));
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), FirstActivity.class);
                intent.putExtra("url", list1.get(position).news_url);
                insertcount();
                startActivity(intent);
            }
        });

        return view;
    }




//
//    public List<Map<String, Object>> getData(){
//
//        for (int i = 0; i < 10; i++) {
//            Map<String, Object> map=new HashMap<String, Object>();
//            map.put("image", R.drawable.ic_launcher_background);
//            map.put("title", "这是?个标题"+i);
//            map.put("info", "这是一个详细信息000000000000000000000000000000000000000000000000000000000000000000000000" + i);
//            list.add(map);
//        }
//        return list;
//    }
    public  void search()  {
        Thread thread = new Thread(new Runnable() {
            @RequiresApi (api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try  {
//                    Map<String, String> a = new HashMap();

                    String urlStr = "http://192.168.1.33:8080/hello/searchnews";
                    try {
                        URL url = new URL(urlStr);
                        //打开网络连接

                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        //设置提交方式
                        conn.setRequestMethod("POST");
                        //设置网络超时时间
                        conn.setConnectTimeout(100000);
                        //获取结果码
                        int code1 = conn.getResponseCode();


                        if (code1 == 200) {
                            //用io流与web后台进行数据交互
                            InputStream is = conn.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                            String strRead = null;
                            StringBuffer sbf = new StringBuffer();
                            while ((strRead = reader.readLine()) != null) {
                                sbf.append(strRead);
                                sbf.append("\r\n");
                            }

                            list1=parseJSONWithJSONObject(String.valueOf(sbf));

                            Log.d("MainActivity", String.valueOf(sbf));
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }});
        thread.start();
    }
    private List<NewsBean> parseJSONWithJSONObject(String jsonData){
        List<NewsBean> list= new ArrayList<>();

        try{
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i=0; i< jsonArray.length(); i++){

                NewsBean newsBean=new NewsBean();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String news_title = jsonObject.getString("news_title");
                String news_pic = jsonObject.getString("news_pic");
                String news_url = jsonObject.getString("news_url");
                String news_content = jsonObject.getString("news_content");
                if(news_pic==null)
                    news_pic="http://5b0988e595225.cdn.sohucs.com/images/20190405/c8f82f18b18f4824b4e8e9d33c9572f3.jpeg";
                newsBean.news_url=news_url;
                newsBean.title=news_title;
                newsBean.des=news_content;
//              newsBean.image=Drawable.createFromStream( new URL(news_pic).openStream(),"src");
                newsBean.image=news_pic;
                list.add(newsBean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public  void insertcount()  {
        Log.d("MainActivity", "插入");
        Thread thread = new Thread(new Runnable() {
            @RequiresApi (api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                Log.d("MainActivity", "插入");
                try  {
                    Map<String, String> dd = new HashMap();
                    dd.put("Phonenumber", UserBean.userBean.getUser_phonenumber());
                    dd.put("Count_acquire", "浏览新闻");
                    dd.put("Count_number", "2");
                    Calendar calendar = Calendar.getInstance();
//获取系统的日期
//年
                    int year = calendar.get(Calendar.YEAR);
//月
                    int month = calendar.get(Calendar.MONTH)+1;
//日
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    Log.d("Main1Activity", year +String.valueOf(month)+ day);
                    c= year+"-"+String.valueOf(month)+"-"+ day;
                    dd.put("Count_date", c);

                    String urlStr = "http://192.168.1.33:8080/hello/addcount";
                    try {
                        URL url = new URL(urlStr+ Post.getPostParams(dd));
                        //打开网络连接
                        Log.d("MainActivity", String.valueOf(url));
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        //设置提交方式
                        conn.setRequestMethod("POST");
                        //设置网络超时时间
                        conn.setConnectTimeout(100000);
                        //获取结果码
                        int code1 = conn.getResponseCode();


                        if (code1 == 200) {
                            //用io流与web后台进行数据交互
                            InputStream is = conn.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                            String strRead = null;
                            StringBuffer sbf = new StringBuffer();
                            while ((strRead = reader.readLine()) != null) {
                                sbf.append(strRead);
                                sbf.append("\r\n");
                            }


                            Log.d("MainActivity", "插入");
                            Log.d("MainActivity", String.valueOf(sbf));
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }});
        thread.start();
    }
}



