package com.zihuan.view.verlibrary

interface VerificationListener {
    //倒计时开始
    fun onCDStart()

    //倒计时结束
    fun onCDEnd()

    //倒计时
    fun onCountdown(number: Int)

    //启动失败
    fun onStartError(){

    }
}