package com.example.graduation.Adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.graduation.R;

import java.util.List;
import java.util.Map;

public class GarbageViewAdapter extends BaseAdapter  {

    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public GarbageViewAdapter(Context context, List<Map<String, Object>> data) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Zujian zujian = null;
        if (convertView == null) {
            zujian = new Zujian();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.garbage_item, null);

            zujian.title = (TextView) convertView.findViewById(R.id.tv_title);

            zujian.info = (TextView) convertView.findViewById(R.id.tv_des);
            convertView.setTag(zujian);
        } else {
            zujian = (Zujian) convertView.getTag();
        }
        //绑定数据

        zujian.title.setText("物品   :"+(String) data.get(position).get("name")+"    所属分类:    "+(String) data.get(position).get("cat"));
        Log.d("MainActivity", String.valueOf(data.size()));
        Log.d("MainActivity", (String)data.get(position).get("name"));
        zujian.info.setText("建议投放姿势："+(String) data.get(position).get("other"));
        return convertView;
    }


}

