package com.study.b8a3.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/8/22.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    private Context context;
    private List<T> list;
    private int layoutId;


    public CommonAdapter(Context context, int layoutId, List<T> list) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        if(list==null){
            return 0;
        }
        return list.size();
    }

    @Override
    public T getItem(int position) {
        if(list==null){
            return null;
        }
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(context,convertView,parent,layoutId,position);

         convert(viewHolder, getItem(position));
        return viewHolder.getConverterView();
    }

    protected abstract void convert(ViewHolder viewHolder, T t);

}
