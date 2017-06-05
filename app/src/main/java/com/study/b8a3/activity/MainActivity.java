package com.study.b8a3.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.study.b8a3.R;
import com.study.b8a3.main.ActivityController;
import com.study.b8a3.main.BaseActivity;
import com.study.b8a3.view.ShimmerButton;
import com.study.b8a3.weexdemo.WEEXMaintActivity;

public class MainActivity extends BaseActivity {
    public static String TAG = ThirdActivity.class.getSimpleName();

    TextView mTextView;
    String mBackString;
    ShimmerButton mSimmerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Example of a call to a native method
        mTextView = (TextView) findViewById(R.id.sample_text);
        mBackString = (String) mTextView.getText();
        findViewById(R.id.btn_baidu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });
//        mSimmerButton = (ShimmerButton) findViewById(R.id.btn_simmer_show);
//        mSimmerButton.startSimmer();
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.app_bar_search:
                Toast.makeText(this, item.getTitle(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.app_bar_switch:
                Toast.makeText(this, item.getTitle(),Toast.LENGTH_SHORT).show();
                break;
            case R.id.app_bar_exit:
                ActivityController.finishAll();
                break;
            case R.id.app_bar_start_activity:
                Intent intent = new Intent(this, FirstActivity.class);
                //如果没有activity标准的activvity需要用到FALG_ACTIVITY_NEW_TASK
                //这种启动是显示的启动

//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("data", mBackString);
                //context new task不会传递参数
//                B8a3Application.sContext.startActivity(intent);
                startActivityForResult(intent, 101);
                break;

            case R.id.app_bar_start_second_activity:
                Intent intentSecondActivity = new Intent();
                intentSecondActivity.setAction("com.study.b8a3.intent.SECOND_ACTIVITY");
                //一般启动activity的时候需要指定category在manifest里面配置了default就不用指定了 android.intent.category.DEFAULT
                this.startActivity(intentSecondActivity);
                break;
            case R.id.app_bar_start_second_cat_activity:
                Intent secondActivityCatMy = new Intent();
                secondActivityCatMy.setAction("com.study.b8a3.intent.SECOND_ACTIVITY");
                secondActivityCatMy.addCategory("com.study.b8a3.category.SECOND_ACTIVITY_MY");
                this.startActivity(secondActivityCatMy);
                break;
            case R.id.app_bar_start_third_activity:
                Intent thirdActivity = new Intent();
                thirdActivity.setAction(Intent.ACTION_DIAL);
                thirdActivity.setData(Uri.parse("tel:10086"));
                this.startActivity(thirdActivity);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                mBackString = "Ok: " + data.getStringExtra("param");
                break;
            case RESULT_CANCELED:
                mBackString = "canceled: " + data.getStringExtra("param");
                break;
        }
        mTextView.setText(mBackString);

    }

    public void openLayoutActivity(View view) {
        LayoutActivity.startActivity(this);
    }

    public void openRecycleViewActivity(View view) {
        RecyclerViewActivity.openActivity(this);
    }

    public void btnOpenAnimation(View view) {
        AnimationActivity.startActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSimmerButton!=null){
            mSimmerButton.cancel();
        }
    }

    public void openShimmer(View view) {
        ShimmerActivity.startShimmerActivity(this);
    }

    public void btnOpenWeex(View view) {
        WEEXMaintActivity.startWEEXMainActivity(this);
    }
}
