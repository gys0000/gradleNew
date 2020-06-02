package com.example.gradlenew

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        tv_btn.postDelayed({
//            tv_btn.setText("2019-12",-200f)
//        },100)
//
//        tv_btn.setOnClickListener {
//            tv_btn.setText("2018-09",500f)
//        }
//        btn_pre.setOnClickListener {
//            tv_btn.setText("2018-09",-100f)
//        }
    }

}
