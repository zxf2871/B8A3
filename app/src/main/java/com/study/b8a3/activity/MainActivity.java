package com.study.b8a3.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.study.b8a3.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
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
            case R.id.app_bar_start_activity:
                Intent intent = new Intent(this, TestActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
