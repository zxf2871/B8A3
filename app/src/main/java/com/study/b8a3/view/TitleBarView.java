package com.study.b8a3.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.study.b8a3.R;

/**
 * Created by B8A3 on 2017/4/10.
 */

public class TitleBarView extends LinearLayout {
    public TitleBarView(Context context) {
        super(context);
    }

    public TitleBarView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);
        Button leftButton = (Button) findViewById(R.id.btn_title_left);
        Button rightButton = (Button) findViewById(R.id.btn_title_right);

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)context).finish();
            }
        });

        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ((Button)view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public TitleBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
