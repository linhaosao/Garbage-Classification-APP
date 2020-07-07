package com.example.graduation;

import android.util.Log;
import androidx.annotation.NonNull;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

public class Post {

    public static String post (String urlStr, @NonNull Map<String, String> params){
        //依次获取用户名，密码与路径

        Log.d("MainActivity",urlStr);
        try {
            //获取网络上get方式提交的整个路径
            Log.d("MainActivity",urlStr+getPostParams(params));

            URL url = new URL(urlStr+getPostParams(params));
            //打开网络连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //设置提交方式
            conn.setRequestMethod("POST");
            //设置网络超时时间
            conn.setConnectTimeout(100000);
            //获取结果码
            int code = conn.getResponseCode();


            if (code == 200) {
                //用io流与web后台进行数据交互
                InputStream is = conn.getInputStream();
                //字节流转字符流
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                //读出每一行的数据
                String s = br.readLine();
                //返回读出的每一行的数据
                Log.d("MainActivity",s);
                return s;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;


    }
    public static String getPostParams(Map<String, String> params) {
        String paramsString = null;
        if (params != null) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Log.d("MainActivity", String.valueOf(paramsString));
                Map.Entry<String, String> next = iterator.next();
                String key = next.getKey();
                String value = next.getValue();
                if (paramsString == null) {
                    paramsString = "?"+key + "=" + value;
                } else {
                    paramsString += "&" + key + "=" + value;
                }
            }
        }
        return paramsString;
    }

    }


