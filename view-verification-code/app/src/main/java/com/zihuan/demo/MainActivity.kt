package com.zihuan.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.zihuan.view.verlibrary.SimpleVerListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvTest.conditions { etCode.text.toString().isNotEmpty() }.callback =
            object : SimpleVerListener() {
                override fun onCDStart() {
                    Log.e("倒计时中", "开始")
                }

                override fun onCountdown(number: Int) {
                    Log.e("倒计时中", "$number")
                }

                override fun onStartError() {
                    Log.e("倒计时中", "条件错误")

                }
            }
    }


}
