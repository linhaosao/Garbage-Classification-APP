package com.example.graduation.fragment;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.example.graduation.Adapter.GarbageViewAdapter;
import com.example.graduation.Beans.UserBean;
import com.example.graduation.MainActivity;
import com.example.graduation.Post;
import com.example.graduation.R;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ThirdFragment extends Fragment {
    private Button button,choose;
    private Button tion;
    private Button commit;
    private TextView tv,cat;
    private TextView posi,position;
    private String a,b,c,d,e;
    private AlertDialog alertDialog3;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, null);
        button=view.findViewById(R.id.seledate);
        tion =view.findViewById(R.id.tion);
        tv=view.findViewById(R.id.deee);
        posi=view.findViewById(R.id.posi);
        commit=view.findViewById(R.id.commit);
        position=view.findViewById(R.id.position);
        choose=view.findViewById(R.id.choose);
        cat=view.findViewById(R.id.cat);
        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = {"有害垃圾", "干垃圾", "湿垃圾", "可回收垃圾"};
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                alertBuilder.setTitle("选择主要分类");
                /**
                 *第一个参数:弹出框的消息集合，一般为字符串集合
                 * 第二个参数：默认被选中的，布尔类数组
                 * 第三个参数：勾选事件监听
                 */
                final HashMap<Integer,String> hashMap = new HashMap<>();
                alertBuilder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                        if (isChecked){
                            Toast.makeText(getActivity(), "选择" + items[i], Toast.LENGTH_SHORT).show();
                            hashMap.put(i,items[i]);
                        }else {
                            Toast.makeText(getActivity(), "取消选择" + items[i], Toast.LENGTH_SHORT).show();
                            hashMap.remove(i);
                        }
                    }
                });
                alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog3.dismiss();
                        String haschoosed="";
                        for (Map.Entry < Integer, String > entry: hashMap.entrySet()) {
                           haschoosed+=entry.getValue();
                        }

                        cat.setText(haschoosed);
                    }
                });

                alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog3.dismiss();
                    }
                });


                alertDialog3 = alertBuilder.create();
                alertDialog3.show();
            }});

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
//月
                int month = calendar.get(Calendar.MONTH)+1;
//日
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv.setText("预约日期："+String.format("%d-%d-%d",year,monthOfYear,dayOfMonth));
                    }
                },year,month,day);
                DatePicker datePicker = datePickerDialog.getDatePicker();
                datePicker.setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        tion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityPicker cityPicker = new CityPicker.Builder(getActivity())
                        .textSize(20)
                        .title("地址选择")

                        .titleBackgroundColor("#C7C7C7")


                        .confirTextColor("#000000")
                        .cancelTextColor("#000000")
                        .province("江苏省")
                        .city("常州市")
                        .district("天宁区")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(7)
                        .itemPadding(10)
                        .onlyShowProvinceAndCity(false)
                        .build();
                cityPicker.show();

                //监听方法，获取选择结果
                cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        //省份
                        String province = citySelected[0];
                        //城市
                        String city = citySelected[1];
                        //区县（如果设定了两级联动，那么该项返回空）
                        String district = citySelected[2];
                        //邮编
                        String code = citySelected[3];
                        posi.setText(province+city+district);
                    }


                    public void onCancel() {
                        Toast.makeText(getActivity(), "已取消", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 a =posi.getText().toString();
                 b =position.getText().toString();
                 c =tv.getText().toString();
                 d = MainActivity.mobilenumber;
                 e = cat.getText().toString();
               if(checkprovince(a)&&checkdetail(b)&&checkdate(c)&&checkcat(e)){
                   search();
                   insertcount();
                   Toast.makeText(getContext(), "提交预约积分+3，每天只有第一次加积分", Toast.LENGTH_SHORT).show();
                   new AlertDialog.Builder(getActivity()).setTitle("通知").setMessage("提交成功，等待管理员审核").setCancelable(true).show();
               }
            }
        });
    }
    private boolean checkprovince(String mobile) {
        if ("".equals(mobile)) {
            Log.e("tag", "mobile=" + mobile);
            new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("请选择省市区").setCancelable(true).show();
            return false;
        } else {
            Log.e("tag", "输入了地点");
//                requestVerifyCode(mobile);
            return true;
        }

    }
    private boolean checkcat(String mobile) {
        if ("".equals(mobile)) {
            Log.e("tag", "mobile=" + mobile);
            new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("请选择主要分类").setCancelable(true).show();
            return false;
        } else {
            Log.e("tag", "输入了地点");
//                requestVerifyCode(mobile);
            return true;
        }

    }
    private boolean checkdetail(String mobile) {
        if ("".equals(mobile)) {
            Log.e("tag", "mobile=" + mobile);
            new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("请输入详细地址").setCancelable(true).show();
            return false;
        }
        else {
            Log.e("tag", "输入了详细地址");
//                requestVerifyCode(mobile);
            return true;
        }

    }
    private boolean checkdate(String mobile) {
        if ("".equals(mobile)) {
            Log.e("tag", "mobile=" + mobile);
            new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("请选择日期").setCancelable(true).show();
            return false;
        }
        else {
            Log.e("tag", "输入了日期");
//                requestVerifyCode(mobile);
            return true;
        }

    }
    public  void search()  {
        Log.d("MainActivity", "插入");
        Thread thread = new Thread(new Runnable() {
            @RequiresApi (api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                Log.d("MainActivity", "插入");
                try  {
                    Map<String, String> dd = new HashMap();
                    dd.put("Order_phonenumber", d);
                    dd.put("Order_position", a);
                    dd.put("Order_detail", b);
                    dd.put("Order_date", c);
                    dd.put("Order_cat", e);
                    dd.put("Order_status", "未处理");
                    String urlStr = "http://192.168.1.33:8080/hello/orderinsert";
                    try {
                        URL url = new URL(urlStr+ Post.getPostParams(dd));
                        //打开网络连接
                        Log.d("MainActivity", String.valueOf(url));
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


                            Log.d("MainActivity", "插入");
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
    public  void insertcount()  {
        Log.d("MainActivity", "插入");
        Thread thread = new Thread(new Runnable() {
            @RequiresApi (api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                Log.d("MainActivity", "插入");
                try  {
                    Map<String, String> dd = new HashMap();
                    dd.put("Phonenumber", UserBean.userBean.getUser_phonenumber());
                    dd.put("Count_acquire", "提交预约");
                    dd.put("Count_number", "3");
                    Calendar calendar = Calendar.getInstance();
//获取系统的日期
//年
                    int year = calendar.get(Calendar.YEAR);
//月
                    int month = calendar.get(Calendar.MONTH)+1;
//日
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    Log.d("Main1Activity", year +String.valueOf(month)+ day);
                    String h= year+"-"+String.valueOf(month)+"-"+ day;
                    dd.put("Count_date", h);

                    String urlStr = "http://192.168.1.33:8080/hello/addcount";
                    try {
                        URL url = new URL(urlStr+ Post.getPostParams(dd));
                        //打开网络连接
                        Log.d("MainActivity", String.valueOf(url));
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


                            Log.d("MainActivity", "插入");
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