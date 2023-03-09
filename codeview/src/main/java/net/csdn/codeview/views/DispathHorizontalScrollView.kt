package net.csdn.codeview.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.HorizontalScrollView
import kotlin.math.abs

/**
 * @class DispathHorizontalScrollView
 * @author KG
 */
class DispathHorizontalScrollView : HorizontalScrollView {
    private val TAG = "HorizontalScrollView"
    private var startX: Float = 0f
    private var startY: Float = 0f
    private var endX: Float = 0f
    private var endY: Float = 0f

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.rawX
                startY = ev.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                endX = ev.rawX
                endY = ev.rawY
                return if (abs(endX - startX) > abs(endY - startY)) {// 左右滑动
                    startX = endX
                    startY = endY
                    Log.i(TAG, "===左右====startX:$startX====startY:$startY====endX:$endX====endY:$endY")
                    false
                } else {
                    startX = endX
                    startY = endY
                    Log.i(TAG, "===上下====startX:$startX====startY:$startY====endX:$endX====endY:$endY")
                    super.dispatchTouchEvent(ev)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

//    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
//        var result = false
//        when {
//            ev?.action == MotionEvent.ACTION_DOWN -> {
//                mOffsetX = 0.0F
//                mOffsetY = 0.0F
//                mLastPosX = ev.x
//                mLastPosY = ev.y
//                result = super.onInterceptTouchEvent(ev)
//            }
//            ev != null -> {
//                val thisPosX: Float = ev.x
//                val thisPosY: Float = ev.y
//                Log.i(TAG, "onInterceptTouchEvent: thisposx,y：($thisPosX,$thisPosY）")
//                Log.i(TAG, "onInterceptTouchEvent: 初始mOffsetX，Y：($mOffsetX,$mOffsetY）")
//                mOffsetX += abs(thisPosX - mLastPosX)
//                mOffsetY += abs(thisPosY - mLastPosY)
//                Log.i(TAG, "onInterceptTouchEvent: 偏移后 mOffsetX，Y：($mOffsetX,$mOffsetY）")
//                Log.i(TAG, "onInterceptTouchEvent: 初始mLastPosX，Y：($mLastPosX,$mLastPosY）")
//                mLastPosY = thisPosY
//                mLastPosX = thisPosX
//                Log.i(TAG, "onInterceptTouchEvent: 之后mLastPosX，Y：($mLastPosX,$mLastPosY）")
//                result = if (mOffsetX < 3 && mOffsetY < 3) {
//                    //传给子控件
//                    false
//                } else {
//                    mOffsetY < mOffsetX
//                }
//            }
//            else -> {
//                result = super.onInterceptTouchEvent(ev)
//            }
//        }
//        return result
//    }
}
