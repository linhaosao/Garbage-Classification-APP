package com.example.graduatetest.PagesController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.graduatetest.Entity.News;
import com.example.graduatetest.Serviceimpl.Newsimpl;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Random;

@Controller
@ResponseBody
public class Login {
    public static final String APPKEY = "371cd8f48fcf90bd";// 你的appkey
    public static final String URL = "https://api.jisuapi.com/news/search";
    public static final String keyword = "垃圾";// utf8 关键字
    @Autowired
    private Newsimpl newsimpl ;

    @RequestMapping(value = "yanzhengma")

    public String Login(String PhoneNumber) throws IOException {

        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://utf8.api.smschinese.cn");
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");//在头文件中设置转码
        String code =getRandomCode();
        NameValuePair[] data ={ new NameValuePair("Uid", "542685395@qq.com"),
                                new NameValuePair("Key", "d41d8cd98f00b204e980"),
                                new NameValuePair("smsMob",PhoneNumber),
                                new NameValuePair("smsText","验证码"+code)
        };

        post.setRequestBody(data);

        client.executeMethod(post);
        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        System.out.println("statusCode:"+statusCode);
        for(Header h : headers)
        {
            System.out.println(h.toString());
        }
        String result = new String(post.getResponseBodyAsString().getBytes("utf-8"));
        System.out.println(result); //打印返回消息状态

        System.out.println("验证码"+code);
        post.releaseConnection();
        return "验证码"+code;
    }
    public static String getRandomCode(){
        Random random = new Random();
        StringBuffer result= new StringBuffer();
        for (int i=0;i<6;i++){
            result.append(random.nextInt(10));
        }
        return result.toString();
    }
    @RequestMapping(value = "news")
    @ResponseBody
    public  String Get() throws Exception {

        String result = null;
        String url = URL + "?keyword=" + URLEncoder.encode(keyword, "utf-8") + "&appkey=" + APPKEY;
        HttpClient client = new HttpClient();

        GetMethod method = new GetMethod(url);
         client.executeMethod(method);
        result = method.getResponseBodyAsString();



        outputStreamOps1(result);
        System.out.println(result); //打印返回消息状态

            JSONObject json =  JSONObject.parseObject(result);

                String channel = json.getString("result");
                 JSONObject json1 =  JSONObject.parseObject(channel);

                JSONArray list = json1.getJSONArray("list");
                for (int i = 0; i < list.size(); i++) {

                    JSONObject obj = (JSONObject) list.get(i);
                    String title = obj.getString("title");
                    String time = obj.getString("time");
                    String src = obj.getString("src");
                    String category = obj.getString("category");
                    String pic = obj.getString("pic");
                    String content = obj.getString("content");
                    String url1 = obj.getString("url");
                    String weburl = obj.getString("weburl");

                    if(pic==null){
                        pic = "https://img.zcool.cn/community/017114575e640a0000018c1b8f300d.jpg";
                    }


                    News news =new News(0,src,pic,url1,title);
                    if(news!=null) {

                        System.out.println(news.getNews_title());
                        System.out.println(news.getNews_pic());
                        System.out.println(news.getNews_url());
                        System.out.println(news.getNews_content());

                    }
                    try{
                        newsimpl.add(news);
                    }catch(Exception e){
                        System.out.println("exception");
                    }finally {
                        continue;
                    }




    }


            return result;

    }
    @RequestMapping("/img")
    @ResponseBody
    public String searchimg(String date) {




        System.out.println(date);
        outputStreamOps(date);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://api.tianapi.com/txapi/imglajifenlei/index";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("key", "612894aece31a50fe7107174737b184e");
        map.add("img","data:image/png;base64,"+date);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        System.out.println(response.getBody());
        return response.getBody();
    }
    public static String getImgStr(String imgFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        File file=new File(imgFile);
        InputStream in = null;

        byte[] data = null;
        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();

        } catch (IOException e) {
            e.printStackTrace();		}
        Base64.Encoder encoder = Base64.getEncoder();
        String encode = encoder.encodeToString(data);
        return encode;
    }
    private static void outputStreamOps(String data) {
        byte[] buff=new byte[]{};
        File file = new File("C:\\Users\\Administrator\\Desktop\\图片BASE64.txt");
        try{
            OutputStream out  = new FileOutputStream(file,true);//创建一个向指定 File 对象表示的文件中写入数据的文件输出流。如果第二个参数为 true，则将字节写入文件末尾处，而不是写入文件开始处。创建一个新 FileDescriptor 对象来表示此文件连接。
            buff=data.getBytes();
            out.write(buff);
            System.out.println("success");
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    @RequestMapping("/getimg")
    @ResponseBody
    public void sear(String date) throws IOException {
        outputStreamOps(date);
        GenerateImage(date);
    }
    //base64字符串转化成图片
    public static void  GenerateImage(String imgStr) throws IOException { //对字节数组字符串进行Base64解码并生成图片
        byte[] decode = Base64.getDecoder().decode(imgStr);
        FileOutputStream fileOutputStream = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\图片.png"));
        fileOutputStream.write(decode);

    }
    private static void outputStreamOps1(String data) {
        byte[] buff=new byte[]{};
        File file = new File("C:\\Users\\Administrator\\Desktop\\爬取新闻数据.txt");
        try{
            OutputStream out  = new FileOutputStream(file,true);//创建一个向指定 File 对象表示的文件中写入数据的文件输出流。如果第二个参数为 true，则将字节写入文件末尾处，而不是写入文件开始处。创建一个新 FileDescriptor 对象来表示此文件连接。
            buff=data.getBytes();
            out.write(buff);
            System.out.println("success");
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    }



