package com.study.b8a3;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.study.b8a3.main.BaseActivity;

import java.io.File;

public class StubViewActivity extends BaseActivity {
    Context context;

    public static String TAG = "---------->";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stub_view);
        context = getApplicationContext();
        printPath();
    }

//    public static void startActivity(Context context){
//        context.startActivity(new Intent(context, StubViewActivity.class));
//    }

    private void printPath() {

        logE("getDatabasePath():>",context.getDatabasePath("sample.db"));
        logE("getCacheDir():>",context.getCacheDir());
        logE("getFilesDir():>",context.getFilesDir());
        String[] strings = context.fileList();
        for (String path : strings) {//为空
            logE("fileList():--->",path);
        }
        logE("getDir(\"zhao\"):>",context.getDir("webview", context.MODE_PRIVATE).getAbsolutePath()+"/Web Data");
//        logE(TAG,"getCodeCacheDir():>",getCodeCacheDir()); //java.lang.NoSuchMethodError
        logE("getPackageCodePath():>",context.getPackageCodePath());
        logE("getPackageResourcePath():>",context.getPackageResourcePath());
        logE("getExternalFilesDir():>",context.getExternalFilesDir(null));
        File[] paths = context.getExternalFilesDirs(null);
        for (File path : paths) {
            logE("getExternalFilesDirs():--->",path.getPath());
        }
        logE("getExternalCacheDir():",context.getExternalCacheDir());
        paths = context.getExternalCacheDirs();
        for (File path : paths) {
            logE("getExternalCacheDirs():--->",path.getPath());
        }
        logE("getObbDir():>",context.getObbDir());
        paths = context.getObbDirs();
        for (File path : paths) {
            logE("getObbDirs():--->",path.getPath());
        }
        logE("getExternalStorageState():>",Environment.getExternalStorageState());
        logE("getExternalStorageDirectory():>",Environment.getExternalStorageDirectory());
        logE("getDownloadCacheDirectory():>",Environment.getDownloadCacheDirectory());
        logE("getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC):>",Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC));
        logE("getRootDirectory():>",Environment.getRootDirectory());
    }

    private void logE(String tag, Object msage) {
        StringBuffer b = new StringBuffer(tag);
        if (b.length() < 42) {
            for (int i = 0; i < 42 - tag.length(); i++) {
                b.append("-");
            }
            b.append(">");
        }
        tag = b.toString();
        
        Log.e(TAG, tag+msage);
    }
}
