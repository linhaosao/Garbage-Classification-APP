package com.example.graduation.fragment;
import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.graduation.Activity.FirstActivity;
import com.example.graduation.Adapter.CountVIewAdapter;
import com.example.graduation.Adapter.ListViewAdapter;
import com.example.graduation.Adapter.OrderViewAdapter;


import com.example.graduation.Beans.CountBean;
import com.example.graduation.Beans.NewsBean;
import com.example.graduation.Beans.OrderBean;

import com.example.graduation.Beans.UserBean;

import com.example.graduation.MainActivity;
import com.example.graduation.Post;
import com.example.graduation.R;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

import static android.app.Activity.RESULT_OK;

public class ForthFragment extends Fragment {
    private  ImageView blurImageView;
    private  ImageView avatarImageView;
    private Button button2,button3;
    private Dialog dialog;
    private Dialog dialog1,dialog2,dialog3;
    private LinearLayout btn;
    private LinearLayout update,order,count,logout;
    private View view;
    private View view1,view2,view4;
    private EditText editText;
    private ListView listView;
    private RatingBar ratingBar_Small;
    private List<OrderBean> list1=new ArrayList<>();
    private List<CountBean> list2=new ArrayList<CountBean>();
    private  Bitmap photo;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forth, null);
        blurImageView= view.findViewById(R.id.iv_blur);
        avatarImageView = view.findViewById(R.id.iv_avatar);
        btn=view.findViewById(R.id.ohiyon);
        update =view.findViewById(R.id.update);
        order  = view.findViewById(R.id.order);
        count =view.findViewById(R.id.count);
        logout =view.findViewById(R.id.logout);
        Log.d("MainActivity",UserBean.userBean.getUser_name());
        TextView textView =view.findViewById(R.id.user_name);
        textView.setText(UserBean.userBean.getUser_name());
        TextView textView1 =view.findViewById(R.id.user_val);
        textView1.setText(UserBean.userBean.getUser_phonenumber());
        if(UserBean.userBean.getUser_av()!="null");
        avatarImageView.setImageBitmap(stringToBitmap(UserBean.userBean.getUser_av()));
        Log.d("MainActivity",UserBean.userBean.getUser_av());
        getstatus();
        try {
            Thread.currentThread().sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!list1.isEmpty())
        {if(list1.get(0).getOrder_status().length()>5)
            Toast.makeText(getContext(), "管理员已审核，快去查看吧", Toast.LENGTH_SHORT).show();}
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        blurImageView.setBackgroundResource(R.drawable.ic_launcher_background);
        blurImageView.getBackground().setAlpha(10);
        avatarImageView.setBackgroundResource(R.mipmap.appav);
        avatarImageView.getBackground().setAlpha(60);
        View v=getActivity().getLayoutInflater().inflate(R.layout.dialog, null);
        avatarImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new AlertDialog.Builder(getContext())

                        .setPositiveButton("相机", new DialogInterface.OnClickListener() {

                            @Override

                            public void onClick(DialogInterface dialog, int which) {

                                //动态权限：点击相机时获取相机权限

                                DongTaiShare();

                                //从相机获取图片

                                getPicFromCamera();

                            }

                        }).setNegativeButton("相册", new DialogInterface.OnClickListener() {

                            @Override

                            public void onClick(DialogInterface dialog, int which) {

                                //从相册获取图片

                                getPicFromAlbm();

                            }

                        }).create().show();
            }
        });

        dialog=new Dialog(getContext());
        button2=v.findViewById(R.id.button2);
        dialog.setTitle("评价");
        dialog.setContentView(v);
        ratingBar_Small = (RatingBar)v.findViewById(R.id.ratingBar1);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String a =String.valueOf(ratingBar_Small.getNumStars());
                String b = UserBean.userBean.getUser_phonenumber();
                Calendar calendar = Calendar.getInstance();
//获取系统的日期
//年
                int year = calendar.get(Calendar.YEAR);
//月
                int month = calendar.get(Calendar.MONTH)+1;
//日
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                Log.d("Main1Activity", year +String.valueOf(month)+ day);
                Log.d("MainActivity", "分数"+a);
                String c= year+"-"+String.valueOf(month)+"-"+ day;
                insertrate(a,b,c);
                dialog.dismiss();
            }
        });
        view1=getActivity().getLayoutInflater().inflate(R.layout.dialog1, null);
        dialog1=new Dialog(getContext());
        button3=view1.findViewById(R.id.button2);
        dialog1.setTitle("修改名称");
        dialog1.setContentView(view1);
        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog1.show();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();

                editText = view1.findViewById(R.id.checkBox1);
                String a = editText.getText().toString();
                Log.d("MainActivity",a+"666");
                TextView textView =view.findViewById(R.id.user_name);
                textView.setText(a);
                UserBean.userBean.setUser_name(a);
                Log.d("Main1Activity",UserBean.userBean.getUser_name() );
                update();
                dialog1.dismiss();

            }
        });
        view2=getActivity().getLayoutInflater().inflate(R.layout.my_order, null);
        dialog2=new Dialog(getContext());


        order.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                view2= getActivity().getLayoutInflater().inflate(R.layout.my_order, null);
                listView = (ListView)view2.findViewById(R.id.listv);

                search();

                try {
                    Thread.currentThread().sleep(1000);//阻断1秒

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    listView.setAdapter(new OrderViewAdapter(getActivity(), list1));
                }


                dialog2.setTitle("订单列表");
                dialog2.setContentView(view2);
                dialog2.show();
            }
        });
        view4=getActivity().getLayoutInflater().inflate(R.layout.my_count, null);
        dialog3=new Dialog(getContext());
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);

                startActivity(intent);
            }
            });

        count.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                listView = (ListView)view4.findViewById(R.id.listv);

                searchcount();

                try {
                    Thread.currentThread().sleep(1000);//阻断1秒

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {

                    listView.setAdapter(new CountVIewAdapter(getActivity(), list2));
                    Log.d("MainActivity",list2.get(0).getCount_acquire());
                }


                dialog3.setTitle("积分详情");
                dialog3.setContentView(view4);
                dialog3.show();
            }
        });
    }
    private void DongTaiShare() {

        if (Build.VERSION.SDK_INT >= 23) {

            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS, Manifest.permission.CAMERA};

            ActivityCompat.requestPermissions(getActivity(), mPermissionList, 123);

        }

    }



    //调用系统相机

    private void getPicFromCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, 1);

    }



    //调用相册

    private void getPicFromAlbm() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        photoPickerIntent.setType("image/*");

        startActivityForResult(photoPickerIntent, 2);



    }


    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        switch (requestCode) {

            // 调用相机后返回

            case 1:

                if (resultCode == RESULT_OK) {

                     photo = intent.getParcelableExtra("data");

                    //给头像设置你相机拍的照片
                    Log.d("Main1Activity",bitmapToString(photo) );
                    UserBean.userBean.setUser_av(bitmapToString(photo));
                    Log.d("Main3Activity",UserBean.userBean.getUser_av());
                    update();
                    avatarImageView.setImageBitmap(ForthFragment.stringToBitmap(UserBean.userBean.getUser_av()));



                }

                break;

            //调用相册后返回

            case 2:

                if (resultCode == RESULT_OK) {

                    Uri uri = intent.getData();

                    cropPhoto(uri);//裁剪图片

                }

                break;

            //调用剪裁后返回

            case 3:

                Bundle bundle = intent.getExtras();

                if (bundle != null) {

                    //在这里获得了剪裁后的Bitmap对象，可以用于上传

                    photo = bundle.getParcelable("data");
                    Log.d("Main1Activity",bitmapToString(photo) );
                    //设置到ImageView上
                    UserBean.userBean.setUser_av(bitmapToString(photo));
                    update();
                    avatarImageView.setImageBitmap(photo);

                    //也可以进行一些保存、压缩等操作后上传



                    /*

                     *这里可以做上传文件的额操作

                     */

                }

                break;

        }

    }
    private void cropPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.setDataAndType(uri, "image/*");

        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 1);

        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 300);

        intent.putExtra("outputY", 300);

        intent.putExtra("return-data", true);

        startActivityForResult(intent, 3);

    }
    public  void search()  {
        Thread thread = new Thread(new Runnable() {
            @RequiresApi (api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try  {
                    Map<String, String> a = new HashMap();
                    a.put("name", UserBean.userBean.getUser_phonenumber());
                    String urlStr = "http://192.168.1.33:8080/hello/searchorder";
                    try {
                        URL url = new URL(urlStr+ Post.getPostParams(a));
                        //打开网络连接

                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        Log.d("MainActivity", urlStr+ Post.getPostParams(a));
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

                            Log.d("MainActivity", "获取数据"+list1.get(0).getOrder_position());
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
    public  void searchcount()  {
        Thread thread = new Thread(new Runnable() {
            @RequiresApi (api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try  {
                    Map<String, String> a = new HashMap();
                    a.put("Phonenumber", UserBean.userBean.getUser_phonenumber());
                    String urlStr = "http://192.168.1.33:8080/hello/getcount";
                    try {
                        URL url = new URL(urlStr+ Post.getPostParams(a));
                        //打开网络连接

                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        Log.d("MainActivity", urlStr+ Post.getPostParams(a));
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
                            Log.d("MainActivity", "获取数据"+sbf);
                            list2=parseJSONWithJSONObject1(String.valueOf(sbf));
                            Log.d("MainActivity", "获取数据111"+list2.get(0).getPhonenumber());

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
    private List<OrderBean> parseJSONWithJSONObject(String jsonData){
        List<OrderBean> list= new ArrayList<>();

        try{
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i=0; i< jsonArray.length(); i++){


                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String order_phonenumber = jsonObject.getString("order_phonenumber");
                String order_detail = jsonObject.getString("order_detail");
                String order_position = jsonObject.getString("order_position");
                String order_date = jsonObject.getString("order_date");
                String order_status = jsonObject.getString("order_status");
                String order_cat = jsonObject.getString("order_cat");
                String order_id = jsonObject.getString("order_id");
                OrderBean orderBean =new OrderBean(order_id, order_phonenumber,order_position, order_detail, order_date, order_status, order_cat);

                list.add(orderBean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    private List<CountBean> parseJSONWithJSONObject1(String jsonData){
        List<CountBean> list= new ArrayList<>();

        try{
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i=0; i< jsonArray.length(); i++){


                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String order_phonenumber = jsonObject.getString("count_id");
                String order_detail = jsonObject.getString("phonenumber");
                String order_position = jsonObject.getString("count_acquire");
                String order_date = jsonObject.getString("count_number");
                String order_status = jsonObject.getString("count_date");


                CountBean CountBean =new CountBean(order_phonenumber,order_detail, order_position , order_date, order_status);
                Log.d("MainActivity", "获取数据111"+CountBean.getCount_number());
                list.add(CountBean);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
//            a.put("User_phonenumber", UserBean.userBean.getUser_phonenumber());
//                    a.put("User_av",UserBean.userBean.getUser_av());
//                    a.put("User_name",UserBean.userBean.getUser_name());
//    String urlStr = "http://192.168.1.33:8080/hello/updateuser";
    public  void update()  {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.1.33:8080/hello/updateuser");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.connect();
                    Log.d("MainActivity", UserBean.userBean.getUser_av());
                    String body = "User_phonenumber="+UserBean.userBean.getUser_phonenumber()+"&User_name="+UserBean.userBean.getUser_name()+"&User_av="+UserBean.userBean.getUser_av();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(body);
                    writer.close();

                    int responseCode = connection.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK){
                        InputStream inputStream = connection.getInputStream();
                        byte[] bytes = new byte[0];
                        bytes = new byte[inputStream.available()];
                        inputStream.read(bytes);
                        String str = new String(bytes);
                      //将流转换为字符串。
                        Log.d("kwwl","result============="+str);
                        Log.d("MainActivity","传输成功");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
    /**
     * Base64字符串转换成图片
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToString(Bitmap bitmap) {

        String result = null;
        String result1 = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();


                result = Base64.encodeToString( bitmapBytes,Base64.URL_SAFE);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**
     * base64转为bitmap
     * @param base64Data
     * @return
     */
    public static Bitmap stringToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.URL_SAFE);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private void savebitmap(Bitmap bitmap)
    {
        //因为xml用的是背景，所以这里也是获得背景

        //创建文件，因为不存在2级目录，所以不用判断exist，要保存png，这里后缀就是png，要保存jpg，后缀就用jpg
        File file=new File( Environment.getExternalStorageDirectory().toString() +"/mfw.png");
        try {
            //文件输出流
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            //压缩图片，如果要保存png，就用Bitmap.CompressFormat.PNG，要保存jpg就用Bitmap.CompressFormat.JPEG,质量是100%，表示不压缩
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
            //写入，这里会卡顿，因为图片较大
            fileOutputStream.flush();
            //记得要关闭写入流
            fileOutputStream.close();
            //成功的提示，写入成功后，请在对应目录中找保存的图片
            Toast.makeText(getActivity(),"写入成功！目录"+  Environment.getExternalStorageDirectory().toString()+"/mfw.png",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //失败的提示
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            //失败的提示
            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }
    /**
     * 将图片转换成Base64编码的字符串
     */

    public  void insertrate(String a,String b,String c)  {
        final String Rating_bar =a;
        final String Rating_phonenumer =b;
        final String Rating_date = c;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.1.33:8080/hello/addrating");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.connect();
                    Log.d("Main2Activity", Rating_date);
                    String body = "Rating_bar="+Rating_bar+"&Rating_phonenumer="+Rating_phonenumer+"&Rating_date="+Rating_date;
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(body);
                    writer.close();

                    int responseCode = connection.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK){
                        InputStream inputStream = connection.getInputStream();
                        byte[] bytes = new byte[0];
                        bytes = new byte[inputStream.available()];
                        inputStream.read(bytes);
                        String str = new String(bytes);
                        //将流转换为字符串。
                        Log.d("kwwl","result============="+str);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public  void getstatus()  {
        Thread thread = new Thread(new Runnable() {
            @RequiresApi (api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                try  {
                    Map<String, String> a = new HashMap();
                    a.put("name", UserBean.userBean.getUser_phonenumber());
                    String urlStr = "http://192.168.1.33:8080/hello/searchorder";
                    try {
                        URL url = new URL(urlStr+ Post.getPostParams(a));
                        //打开网络连接

                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        Log.d("MainActivity", urlStr+ Post.getPostParams(a));
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

                            Log.d("MainActivity", "获取数据"+list1.get(0).getOrder_status());
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




