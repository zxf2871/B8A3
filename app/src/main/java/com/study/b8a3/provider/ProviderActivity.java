package com.study.b8a3.provider;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.study.b8a3.R;

public class ProviderActivity extends AppCompatActivity {

    private Button buttonTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        buttonTest = (Button)findViewById(R.id.bt_test);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriString = "content://com.study.b8a3.provider.b8a3provider";
                Uri uri = Uri.parse(uriString);
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                String providerType = getContentResolver().getType(uri);

                ContentValues values = new ContentValues();
                values.put("id", 1);
                values.put("name", "will");
                Uri insertReturn =getContentResolver().insert(uri, values);
                int deleteReturn =  getContentResolver().delete(uri, "a:c", new String[]{"a","b","c"});
                int updateReturn =  getContentResolver().update(uri, values,"a:c", new String[]{"a","b","c"});
            }
        });

    }

    public static void startProviderActivity(Context context){
        Intent intent = new Intent(context, ProviderActivity.class);
        context.startActivity(intent);
    }


}
