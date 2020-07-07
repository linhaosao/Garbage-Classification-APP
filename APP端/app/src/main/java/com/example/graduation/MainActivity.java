package com.example.graduation;





import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import com.example.graduation.Beans.UserBean;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity  extends Activity implements View.OnClickListener{
    public static String mobilenumber;
    private int yzm;
    private String comenumber;
    private Context mContext;
    private Button getyanzhengma1, login_btn;
    private EditText mobile_login, yanzhengma;
    private int countSeconds = 60;//倒计时秒数
    private Handler mCountHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (countSeconds > 0) {
                --countSeconds;
                getyanzhengma1.setText("(" + countSeconds + ")后获取验证码");
                mCountHandler.sendEmptyMessageDelayed(0, 1000);
            } else {
                countSeconds = 60;
                getyanzhengma1.setText("请重新获取验证码");
                yzm =666;
            }
        }
    };
    private Handler CountHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Map<String, String> a = new HashMap();
            a.put("PhoneNumber", comenumber);
            String urlStr = "http://192.168.1.33:8080/yanzhengma";
            try {
                URL url = new URL(urlStr+Post.getPostParams(a));
                //打开网络连接
                Log.d("MainActivity",urlStr+Post.getPostParams(a));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                //设置提交方式
                conn.setRequestMethod("POST");
                //设置网络超时时间
                conn.setConnectTimeout(100000);
                //获取结果码
                int code1 = conn.getResponseCode();

                Log.d("MainActivity", code1 +"code");
                if (code1 == 200) {
                    //用io流与web后台进行数据交互
                    InputStream is = conn.getInputStream();
                    //字节流转字符流
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    //读出每一行的数据
                    String s = br.readLine();
                    Pattern p = Pattern.compile("[^0-9]");
                    Matcher m = p.matcher(s);
                    yzm = Integer.parseInt(m.replaceAll("").trim());
                    //返回读出的每一行的数据
                    Log.d("MainActivity",s);

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        initEvent();
    }
    private void initView(){
        mobile_login =  findViewById(R.id.mobile_login);
        getyanzhengma1 =  findViewById(R.id.getyanzhengma1);
        yanzhengma =  findViewById(R.id.yanzhengma);
        login_btn =  findViewById(R.id.login_btn);

    }
    private void initEvent() {
        getyanzhengma1.setOnClickListener( this);
        login_btn.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getyanzhengma1:
                if (countSeconds == 60) {
                    String mobile = mobile_login.getText().toString();
                    Log.e("tag", "mobile==" + mobile);

                    if(getMobiile(mobile))
                    {    startCountBack();
                        comenumber = mobile_login.getText().toString();
                        login();

                        Log.e("tag","开始计时");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "不能重复发送验证码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_btn:
                if(yzm==666){
                    new AlertDialog.Builder(mContext).setTitle("提示").setMessage("验证码已过期").setCancelable(true).show();
                     break;}
                if(confirm(yanzhengma.getText().toString())) {
                    new AlertDialog.Builder(mContext).setTitle("提示").setMessage("登录成功").setCancelable(true).show();
                    mobilenumber = comenumber;
                    Intent it =new Intent();
                    signin();
                    try {
                        Thread.currentThread().sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getuser();
                    it.setClass(MainActivity.this,SelectActivity.class);
                    MainActivity.this.startActivity(it);
                }else {
                    new AlertDialog.Builder(mContext).setTitle("提示").setMessage("登录失败").setCancelable(true).show();
                }
                break;
            default:
                break;
        }
    }
    //获取验证码信息，判断是否有手机号码
    private boolean getMobiile(String mobile) {
        if ("".equals(mobile)) {
            Log.e("tag", "mobile=" + mobile);
            new AlertDialog.Builder(mContext).setTitle("提示").setMessage("手机号码不能为空").setCancelable(true).show();
        } else if (isMobileNO(mobile) == false) {
            new AlertDialog.Builder(mContext).setTitle("提示").setMessage("请输入正确的手机号码").setCancelable(true).show();
        } else {
            Log.e("tag", "输入了正确的手机号");
//                requestVerifyCode(mobile);
            return true;
        }
        return false;
    }
    //使用正则表达式判断电话号码
    public static boolean isMobileNO(String tel) {
        Pattern p = Pattern.compile("^(13[0-9]|15([0-3]|[5-9])|14[5,7,9]|17[1,3,5,6,7,8]|18[0-9])\\d{8}$");
        Matcher m = p.matcher(tel);

        return m.matches();
    }
    public  void login()  {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    Map<String, String> a = new HashMap();
                    a.put("PhoneNumber", comenumber);
                    String urlStr = "http://192.168.1.33:8080/yanzhengma";
                    try {
                        URL url = new URL(urlStr+Post.getPostParams(a));
                        //打开网络连接
                        Log.d("MainActivity",urlStr+Post.getPostParams(a));
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        //设置提交方式
                        conn.setRequestMethod("POST");
                        //设置网络超时时间
                        conn.setConnectTimeout(100000);
                        //获取结果码
                        int code1 = conn.getResponseCode();

                        Log.d("MainActivity", code1 +"code");
                        if (code1 == 200) {
                            //用io流与web后台进行数据交互
                            InputStream is = conn.getInputStream();
                            //字节流转字符流
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
                            //读出每一行的数据
                            String s = br.readLine();
                            Pattern p = Pattern.compile("[^0-9]");
                            Matcher m = p.matcher(s);
                            yzm = Integer.parseInt(m.replaceAll("").trim());
                            //返回读出的每一行的数据
                            Log.d("MainActivity",s);

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
    public  void signin()  {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    Map<String, String> a = new HashMap();
                    a.put("User_phonenumber", mobilenumber);
                    String urlStr = "http://192.168.1.33:8080/hello/userinsert";
                    try {
                        URL url = new URL(urlStr+Post.getPostParams(a));
                        //打开网络连接
                        Log.d("MainActivity",urlStr+Post.getPostParams(a));
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        //设置提交方式
                        conn.setRequestMethod("POST");
                        //设置网络超时时间
                        conn.setConnectTimeout(100000);
                        //获取结果码
                        int code1 = conn.getResponseCode();

                        Log.d("MainActivity", code1 +"code");
                        if (code1 == 200) {
                            //用io流与web后台进行数据交互
                            InputStream is = conn.getInputStream();
                            //字节流转字符流
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
                            //读出每一行的数据
                            String s = br.readLine();

                            Log.d("MainActivity",s);

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
    public  void getuser()  {
        Thread thread = new Thread(new Runnable() {
            @RequiresApi (api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try  {
//                    Map<String, String> a = new HashMap();
                    Map<String, String> a = new HashMap();
                    a.put("phonenumber", mobilenumber);
                    String urlStr = "http://192.168.1.33:8080/hello/selectuser";
                    try {
                        URL url = new URL(urlStr+Post.getPostParams(a));
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
                            Log.d("MainActivity", urlStr+Post.getPostParams(a));
                            Log.d("MainActivity", "返回内容"+String.valueOf(sbf));


                            JSONObject jsonObject = new JSONObject(String.valueOf(sbf));
                            String av=null;
                            String phonephone =null;
                            String  User_name =null;


                            av=jsonObject.getString("user_av");
                            phonephone=jsonObject.getString("user_phonenumber");
                            User_name=jsonObject.getString("user_name");


                                UserBean.userBean.setUser_name(User_name);
                                UserBean.userBean.setUser_av(av);
                                UserBean.userBean.setUser_phonenumber(phonephone);
                            if(UserBean.userBean.getUser_name()=="null")
                                UserBean.userBean.setUser_name("新用户");
                            if(UserBean.userBean.getUser_av()==null )
                                UserBean.userBean.setUser_av("nopic");
                            Log.d("MainActivity", User_name);
                            Log.d("MainActivity", UserBean.userBean.getUser_phonenumber());
                            Log.d("MainActivity", String.valueOf(UserBean.userBean.getUser_av()=="null"));
                            Log.d("MainActivity", UserBean.userBean.getUser_av());
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

                                                public boolean confirm(String Code) {
                                                    return yzm == Integer.parseInt(Code);


                                                }

                                                private void startCountBack() {
                                                    ((Activity) mContext).runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            getyanzhengma1.setText(countSeconds + "");
                                                            mCountHandler.sendEmptyMessage(0);
                                                        }
                                                    });
                                                }

//    class MyThread extends Thread {
//
//
//
//        @Override
//        public void run() {
//            Map <String,String> a = new HashMap();
//            a.put("PhoneNumber",comenumber);
//            String code =Post.post("http://192.168.1.38:8080/yanzhengma",a);
//            Pattern p = Pattern.compile("[^0-9]");
//            Matcher m = p.matcher(code);
//            yzm =Integer.parseInt(m.replaceAll("").trim());
//
//        }
//    }

}