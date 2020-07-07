package com.example.graduation.fragment;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.example.graduation.Adapter.GarbageViewAdapter;
import com.example.graduation.Adapter.ListViewAdapter;
import com.example.graduation.Beans.UserBean;
import com.example.graduation.Post;
import com.example.graduation.R;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;

public class SecondFragment extends Fragment {
    private String name;
    private Button button,image_button;
    private List<Map<String, Object>> list1,list2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, null);
            button=view.findViewById(R.id.serach);
        image_button =view.findViewById(R.id.take_photo);

        return view;
    }
    @Override

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editTex =getActivity().findViewById(R.id.edittext);
                name = editTex.getText().toString();

                search() ;
                try {
                    Thread.currentThread().sleep(500);//阻断2秒

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    ListView listView = (ListView)getActivity().findViewById(R.id.listv);
                    if(list1!=null){
                    listView.setAdapter(new GarbageViewAdapter(getActivity(), list1));}
                    else {
                        new AlertDialog.Builder(getContext()).setTitle("提示").setMessage("无此类物品分类信息").setCancelable(true).show();
                    }
                }

                Log.d("MainActivity", "aaa");
            }
        });
        image_button.setOnClickListener(new View.OnClickListener() {
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
    private List<Map<String,Object>> parseJSONWithJSONObject(String jsonData){
        List<Map<String,Object>> list= new ArrayList<>();

        try{
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i=0; i< jsonArray.length(); i++){
                Map<String,Object> map =new HashMap<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("garbage_name");
                String cat = jsonObject.getString("garbage_cat");
                String other = jsonObject.getString("garbage_other");
                if(other==null)
                    other="暂未更新";
                map.put("name",name);
                map.put("cat",cat);
                map.put("other",other);
                list.add(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    private List<Map<String,Object>> parseimgJSONWithJSONObject(String jsonData){
        List<Map<String,Object>> list= new ArrayList<>();

        try{
            JSONObject json =  new JSONObject(jsonData);

            String channel = json.getString("newslist");

            JSONArray jsonArray = new JSONArray(channel);
            for (int i=0; i< jsonArray.length(); i++){
                Map<String,Object> map =new HashMap<>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String cat = jsonObject.getString("lajitype");
                String other = jsonObject.getString("lajitip");
                if(other==null)
                    other="暂未更新";
                map.put("name",name);
                switch (cat){
                    case "0":
                        cat = "可回收垃圾";
                        break;
                    case "1":
                        cat = "有害垃圾";
                        break;
                    case "2":
                        cat = "湿垃圾";
                        break;
                    case "3":
                        cat = "干垃圾";
                        break;
                    case "4":
                        cat = "待进一步识别";
                        break;

                }
                map.put("cat",cat);
                map.put("other",other);
                list.add(map);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("MainActivity", String.valueOf(list.get(0).get("cat")));
        return list;
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

                    final Bitmap photo = intent.getParcelableExtra("data");

                    //给头像设置你相机拍的照片

                   // searchimg(ForthFragment.bitmapToString(photo));

                    savebitmap(photo);
                    try {
                        Thread.currentThread().sleep(2000);//阻断2秒

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        ListView listView = (ListView)getActivity().findViewById(R.id.listv);
                        if(list2!=null){
                            listView.setAdapter(new GarbageViewAdapter(getActivity(), list2));}
                        else {
                            new AlertDialog.Builder(getContext()).setTitle("提示").setMessage("无此类物品分类信息").setCancelable(true).show();
                        }
                    }

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

                    Bitmap image = bundle.getParcelable("data");

                    //设置到ImageView上
                    searchimg(ForthFragment.bitmapToString(image));
                    savebitmap(image);
                    //也可以进行一些保存、压缩等操作后上传

                    try {
                        Thread.currentThread().sleep(2000);//阻断2秒

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        ListView listView = (ListView)getActivity().findViewById(R.id.listv);
                        if(list2!=null){
                            listView.setAdapter(new GarbageViewAdapter(getActivity(), list2));}
                        else {
                            new AlertDialog.Builder(getContext()).setTitle("提示").setMessage("无此类物品分类信息").setCancelable(true).show();
                        }
                    }


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
    public  void searchimg(final String img64)  {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    URL url = new URL("http://api.tianapi.com/txapi/imglajifenlei/index");


                    String body ="key=612894aece31a50fe7107174737b184e&img=data:image/jpeg;base64,"+ img64;
//                    String sr = sendPost("http://api.tianapi.com/txapi/imglajifenlei/index", body);
                    String sr = sendPost("https://www.tianapi.com/gethttp/test/", body);
                    Log.d("Main1Activity", String.valueOf(sr));
                    Log.d("Main1Activity", "111");
                    Log.d("Main1Activity", img64);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;

        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            System.out.println(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    private  final int REQUEST_EXTERNAL_STORAGE = 1;
    private  String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    public  void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
    /**
     * 在对sd卡进行读写操作之前调用这个方法
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to grant permissions
     */




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
            Toast.makeText(getActivity(),"开始扫描识别"+  Environment.getExternalStorageDirectory().toString()+"/mfw.png",Toast.LENGTH_SHORT).show();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
//                    URL url = new URL("http://api.tianapi.com/txapi/imglajifenlei/index");

                        String date = imageToBase64(Environment.getExternalStorageDirectory().toString()+"/mfw.png");
                        System.out.println(date);
                        String body ="key=612894aece31a50fe7107174737b184e&img=data:image/jpeg;base64,"+ date;
                         String sr = sendPost("http://api.tianapi.com/txapi/imglajifenlei/index", body);
                       //String sr = sendPost("https://www.tianapi.com/gethttp/test/", body);
                        Log.d("Main1Activity", String.valueOf(sr));
                        Log.d("Main1Activity", "111");
                        Log.d("Main10Activity", date);
                        list2 = parseimgJSONWithJSONObject(sr);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
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
//    public static String getImgStr(String imgFile) {
//        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
//        File file=new File(imgFile);
//        InputStream in = null;
//
//        byte[] data = null;
//        try {
//            in = new FileInputStream(file);
//            data = new byte[in.available()];
//            in.read(data);
//            in.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();		}
//
//        return encode;
//    }
//public static String encodeBase64File(String path) throws Exception {
//        File  file = new File(path);
//        FileInputStream inputFile = new FileInputStream(file);
//        byte[] buffer = new byte[(int)file.length()];inputFile.read(buffer);
//        inputFile.close();
//    Log.d("Main10Activity", "就这："+String.valueOf(buffer));
//    return android.util.Base64.encodeToString( buffer, Base64.NO_WRAP);}
    public static String imageToBase64(String path) throws IOException {

        if(TextUtils.isEmpty(path)){
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null,result1=null;
        try{
            is = new FileInputStream(path);
//创建一个字符流大小的数组。
            data = new byte[is.available()];
//写入数组
            is.read(data);
//用默认的编码格式进行编码
            result = Base64.encodeToString(data,Base64.NO_WRAP);
            result1 = URLEncoder.encode(result);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null !=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        String imgFilePath = "C:\\Users\\Administrator\\Desktop\\上传图片.png";//新生成的图片
//        OutputStream out = new FileOutputStream(imgFilePath);
//        out.write(data);
//        out.flush();
//        out.close();
//        outputStreamOps(result1);
//        sendto(result1);
        sendto(result1);
        return result1;
    }
//    private static void outputStreamOps(String data) {
//        byte[] buff=new byte[]{};
//        File file = new File("C:\\Users\\Administrator\\Desktop\\Base64编码.txt");
//        try{
//            OutputStream out  = new FileOutputStream(file,true);//创建一个向指定 File 对象表示的文件中写入数据的文件输出流。如果第二个参数为 true，则将字节写入文件末尾处，而不是写入文件开始处。创建一个新 FileDescriptor 对象来表示此文件连接。
//            buff=data.getBytes();
//            out.write(buff);
//            System.out.println("success");
//            out.close();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//
//    }
    //base64字符串转化成图片
    private static  void sendto(String data)
    {
        final String date =data;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.1.33:8080/getimg");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.connect();
                    String body = "date="+date;
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

    }
