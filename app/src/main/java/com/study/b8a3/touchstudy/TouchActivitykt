package com.study.b8a3.touchstudy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import com.study.b8a3.R
import com.study.b8a3.main.BaseActivity

class TouchActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch)
    }

    companion object Factory {
        fun startTouchActivity(context: Context) {
            val intent = Intent(context, TouchActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        if(ev?.action == MotionEvent.ACTION_DOWN) {
            Log.e("touch----------", ": dispatchTouchEvent")
        }
        return super.dispatchTouchEvent(ev)
//        return false
//        return true
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event?.action == MotionEvent.ACTION_DOWN) {
            Log.e("touch----------", ": onTouchEvent")
        }
//        return super.onTouchEvent(event)
//        return false
        return true
    }
}
