package com.example.graduation.Adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.graduation.Beans.NewsBean;
import com.example.graduation.R;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

public class ListViewAdapter extends BaseAdapter  {

    private List<NewsBean> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private byte[] data1;
    private Bitmap bitmap;
    private Handler handler = null;
    private ImageView getImg;
    private Runnable runnable;
    private Zujian zujian;
    public ListViewAdapter(Context context, List<NewsBean> data) {
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
        public ImageView image;
        public TextView title;

        public TextView info;
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
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            zujian.image = (ImageView) convertView.findViewById(R.id.iv_icon);
            zujian.title = (TextView) convertView.findViewById(R.id.tv_title);

            zujian.info = (TextView) convertView.findViewById(R.id.tv_des);
            getImg = (ImageView) convertView.findViewById(R.id.iv_icon);
            convertView.setTag(zujian);
        } else {
            zujian = (Zujian) convertView.getTag();
        }
        //绑定数据
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                try {
                    URL url = new URL(data.get(position).image);
                    Log.d("Main1Activity", data.get(position).image);
                    Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
                    return bitmap;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                zujian.image.setImageBitmap(bitmap);
            }
        }.execute();





//        zujian.image.setBackgroundResource((Integer) data.get(position).image);
//        zujian.image.setImageDrawable(data.get(position).image);
        zujian.title.setText((String) data.get(position).des);
        zujian.info.setText((String) data.get(position).title);
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

