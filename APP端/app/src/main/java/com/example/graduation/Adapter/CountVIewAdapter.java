package com.example.graduation.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.graduation.Beans.CountBean;
import com.example.graduation.Beans.NewsBean;
import com.example.graduation.Beans.OrderBean;
import com.example.graduation.R;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

public class CountVIewAdapter extends BaseAdapter {

    private List<CountBean> data;
    private CountBean count;
    private LayoutInflater layoutInflater;
    private Context context;
    private byte[] data1;
    private Bitmap bitmap;
    private Handler handler = null;
    private ImageView getImg;
    private Runnable runnable;
    private Zujian zujian;
    public CountVIewAdapter(Context context, List<CountBean> data) {
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 组件集合，对应list.xml中的控件
     *
     * @author Administrator
     */
    public final class Zujian {

        public TextView position;
        public TextView cat;
        public TextView status;
        public Button button;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * 获得某一位置的数据
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    /**
     * 获得唯一标识
     */
    @Override
    public long getItemId(int position) {
        return position;
    }
    static {
        try {
            trustAllHttpsCertificates();
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String urlHostName, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }
    private static void trustAllHttpsCertificates() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[1];
        trustAllCerts[0] = new OrderViewAdapter.TrustAllManager();
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    private static class TrustAllManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
        }
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        zujian = new Zujian();
        if (convertView == null) {

            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.count_item, null);
            zujian.status = (TextView) convertView.findViewById(R.id.tv_title);
            zujian.position = (TextView) convertView.findViewById(R.id.tv_des);
            zujian.cat = (TextView) convertView.findViewById(R.id.tv_cat);
//            zujian.button =convertView.findViewById(R.id.delthis);
            convertView.setTag(zujian);
        } else {
            zujian = (Zujian) convertView.getTag();
        }
//        zujian.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                data.remove(position);//将集合中的数据删除
//               CountVIewAdapter.this.notifyDataSetChanged();//更新适配器
//
//            }
//        });

        Log.d("MainActivity", "获取数据111"+data.get(position).getCount_acquire());
        zujian.position.setText("获取途径："+data.get(position).getCount_acquire());
        zujian.status.setText("获取日期:"+(String) data.get(position).getCount_date());
        zujian.cat.setText("分数:"+data.get(position).getCount_number());
        return convertView;
    }
    public static byte[] readInputStream(InputStream inputStream)throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
    public static byte[] GetUserHead(String urlpath) throws IOException {
        URL url = new URL(urlpath);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET"); // 设置请求方法为GET
        conn.setReadTimeout(5 * 1000); // 设置请求过时时间为5秒
        InputStream inputStream = conn.getInputStream(); // 通过输入流获得图片数据
        byte[] data =readInputStream(inputStream); // 获得图片的二进制数据
        return data;

    }





}
