package com.example.graduation.Activity;

import android.content.*;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.*;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.graduation.Beans.NewsBean;
import com.example.graduation.R;
import com.example.graduation.Util.NewsUtils;

import java.io.IOException;
import java.util.ArrayList;

public class FirstActivity  extends  AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       final String a= (String) getIntent().getSerializableExtra("url");
        setContentView(R.layout.web_content);

        WebView webview = findViewById(R.id.webview);

        webview.setWebViewClient(new WebViewClient());//非常非常重要的单webviewclient 让他不打开浏览器
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        webview.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        webview.getSettings().setSupportZoom(true);//是否可以缩放，默认true
        webview.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        webview.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        webview.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        webview.getSettings().setAppCacheEnabled(true);//是否使用缓存
        webview.getSettings().setDomStorageEnabled(true);//DOM Storage
        webview.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36");

        final WebSettings webSettings=webview.getSettings();
        webSettings.setJavaScriptEnabled(true);//运行客户端运行服务器端js权限
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder builder=new AlertDialog.Builder(FirstActivity.this);
                builder.setTitle("再错你要被我打！！");
                builder.setMessage(message);
                builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                builder.create().show();
                return (true);
            }
        });
        webview.loadUrl(a);
        Toast.makeText(getApplicationContext(), "浏览新闻积分+2，每天只有第一次加积分", Toast.LENGTH_SHORT).show();
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                          // 将文本内容放到系统剪贴板里。
                                          cm.setText(a);

                                          Toast.makeText(getApplicationContext(), "页面链接已复制，快去分享吧", Toast.LENGTH_SHORT).show();
                                      }
                                  });
//        MediaPlayer mediaPlayer = new MediaPlayer();
//
//        Uri uri = Uri
//
//                .parse("http://www.php.cn/asset/1.mp3");
//
//        try {
//
//            mediaPlayer.setDataSource(getApplicationContext(), uri);
//
//        } catch (IllegalArgumentException e) {
//
//            // TODO Auto-generated catch block
//
//            e.printStackTrace();
//
//        } catch (SecurityException e) {
//
//            // TODO Auto-generated catch block
//
//            e.printStackTrace();
//
//        } catch (IllegalStateException e) {
//
//            // TODO Auto-generated catch block
//
//            e.printStackTrace();
//
//        } catch (IOException e) {
//
//            // TODO Auto-generated catch block
//
//            e.printStackTrace();
//
//        }
//
//
//
//        try {
//
//            mediaPlayer.prepare();
//
//        } catch (IllegalStateException e) {
//
//            // TODO Auto-generated catch block
//
//            e.printStackTrace();
//
//        } catch (IOException e) {
//
//            // TODO Auto-generated catch block
//
//            e.printStackTrace();
//
//        }
//
//        mediaPlayer.start();


    }

    }




