package com.zihuan.view.verlibrary

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView

class VerificationView : TextView {
    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    /**
     * 最大值
     */
    var maxNumber = 60

    /**
     * 倒计时延迟阈值
     */
    var cdThreshold = 1000L

    /**
     * 默认值
     */
    var defText = "发送验证码"

    /**
     * 倒计时格式
     */
    //var cdFormat=""
    /**
     * 倒计时监听
     */
    var callback: VerificationListener? = null

    /**
     * 访问条件
     */
    private lateinit var conditions: () -> Boolean

    fun conditions(action: () -> Boolean): VerificationView {
        conditions = action
        return this
    }

    private var innerMaxNumber = maxNumber


    private fun initView() {
        text = defText
        gravity = Gravity.CENTER
        setOnClickListener {
            if (conditions()) {
                mHandler.sendEmptyMessage(CDSTART)
            } else {
                callback?.apply {
                    onStartError()
                }
            }
        }
    }


    private fun verConditions(action: () -> Boolean): Boolean {
        return action()
    }

    //开始
    private val CDSTART = 1

    //倒计时中
    private val COUNTDOWNING = 2


    val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                CDSTART -> {
                    callback?.let {
                        it.onCDStart()
                    }
                    post(mRunnable)
                }
                COUNTDOWNING -> {
                    postDelayed(mRunnable, cdThreshold)
                }
            }
        }
    }

    private val mRunnable: Runnable = Runnable {
        if (innerMaxNumber > 0) {
            innerMaxNumber--
            text = "${innerMaxNumber}s"
            mHandler.sendEmptyMessage(COUNTDOWNING)
            callback?.let {
                it.onCountdown(innerMaxNumber)
            }
            if (isClickable) isClickable = false
        } else {
            innerMaxNumber = maxNumber
            text = defText
            callback?.let {
                it.onCDEnd()
            }
            isClickable = true
        }
    }

}