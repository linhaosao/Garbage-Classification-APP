package com.example.graduatetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Base64;
import java.util.Scanner;


@SpringBootApplication
public class GraduateTestApplication {
    public static void main(String[] args) {
//        SpringApplication.run(GraduateTestApplication.class, args);
        SpringApplication.run(GraduateTestApplication.class, args);
        Scanner s = new Scanner(System.in);
        String imgFile = s.nextLine();
        String date = getImgStr(imgFile);
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
        File file = new File("C:\\Users\\Administrator\\Desktop\\test.txt");
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

