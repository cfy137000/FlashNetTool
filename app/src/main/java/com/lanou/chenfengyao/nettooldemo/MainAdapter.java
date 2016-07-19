package com.lanou.chenfengyao.nettooldemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.chenfengyao.flashnet.image.ImageLoader;

import java.util.List;

/**
 * Created by ChenFengYao on 16/7/19.
 */
public class MainAdapter extends BaseAdapter {
    private List<String> data;
    private Context context;

    public MainAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText("这是:" + parent + "行");
        ImageLoader.getInstance(context).getImg(data.get(position), viewHolder.imageView,R.mipmap.ic_launcher);
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            imageView = (ImageView) itemView.findViewById(R.id.item_iv);
            textView = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }
}
