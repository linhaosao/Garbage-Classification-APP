package com.example.graduation.Adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.RequiresApi;
import com.example.graduation.Beans.NewsBean;
import com.example.graduation.Beans.OrderBean;
import com.example.graduation.Post;
import com.example.graduation.R;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderViewAdapter extends BaseAdapter  {

    private List<OrderBean> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private byte[] data1;
    private Bitmap bitmap;
    private Handler handler = null;
    private ImageView getImg;
    private Runnable runnable;
    private Zujian zujian;
    private String name;
    public OrderViewAdapter(Context context, List<OrderBean> data) {
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
        public Button  button;
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
        trustAllCerts[0] = new TrustAllManager();
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    static class TrustAllManager implements X509TrustManager {
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
            convertView = layoutInflater.inflate(R.layout.order_item, null);
            zujian.status = (TextView) convertView.findViewById(R.id.tv_title);
            zujian.position = (TextView) convertView.findViewById(R.id.tv_des);
            zujian.cat = (TextView) convertView.findViewById(R.id.tv_cat);
            zujian.button =convertView.findViewById(R.id.delthis);
            convertView.setTag(zujian);
        } else {
            zujian = (Zujian) convertView.getTag();
        }
        zujian.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name =data.get(position).getOrder_id();
                search();
                data.remove(position);//将集合中的数据删除
                OrderViewAdapter.this.notifyDataSetChanged();//更新适配器
            }
        });


        zujian.position.setText("地址："+data.get(position).getOrder_position()+data.get(position).getOrder_detail());
        zujian.status.setText("日期:"+(String) data.get(position).getOrder_date()+"处理状态"+data.get(position).getOrder_status());
        zujian.cat.setText("主要分类:"+data.get(position).getOrder_cat());
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
    public  void search()  {
        Thread thread = new Thread(new Runnable() {
            @RequiresApi (api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try  {
                    Map<String, String> a = new HashMap();
                    a.put("name", name);
                    String urlStr = "http://192.168.1.33:8080/hello/getgarbage";
                    try {
                        URL url = new URL(urlStr+ Post.getPostParams(a));
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

