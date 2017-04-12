package com.study.b8a3.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Administrator on 2015/8/22.
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private int position;
    private View converterView;
    private Context context;

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.position = position;
        this.mViews = new SparseArray<View>();
        converterView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.context = context;
        converterView.setTag(this);
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int postion) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, postion);
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.position = postion;
            return viewHolder;
        }

    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = converterView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConverterView() {
        return converterView;
    }

    public ViewHolder setText( int viewId, String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolder setCheckBox(int itemCheckBoxId,boolean checked){
        CheckBox cb = getView(itemCheckBoxId);
        cb.setChecked(checked);
        return this;

    }

    public ViewHolder setImageResource(int itemImageId, int imageResouceId) {
        ImageView iv = getView(itemImageId);
        iv.setImageResource(imageResouceId);
        return this;
    }
    public ViewHolder setImageBitMap(int itemImageId, Bitmap bitMap) {
        ImageView iv = getView(itemImageId);
        iv.setImageBitmap(bitMap);
        return this;
    }


    public ViewHolder setImageBy1(String path, int itemImageId){
        ImageView imgV = getView(itemImageId);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        //使用获取到的的insamplesize 再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        imgV.setImageBitmap(bitmap);
        return this;
    }
}
