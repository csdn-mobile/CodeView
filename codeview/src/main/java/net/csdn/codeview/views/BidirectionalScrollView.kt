package net.csdn.codeview.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.MeasureSpec.makeMeasureSpec
import android.widget.HorizontalScrollView
import androidx.recyclerview.widget.RecyclerView
import net.csdn.codeview.R
import kotlin.math.abs

/**
 * @class BidirectionalScrollView
 *
 * Combines vertical & horizontal scroll to implement bidirectional
 * scrolling behavior (like a map view, for example).
 *
 * @author Kirill Biakov
 */
class BidirectionalScrollView : HorizontalScrollView {

    private var downX: Float = 0f;
    private var moveX: Float = 0f;
    private var downY: Float = 0f;
    private var moveY: Float = 0f;

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private lateinit var codeContentRv: RecyclerView

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        codeContentRv = findViewById(R.id.rv_content)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        super.dispatchTouchEvent(event)
        // consumed by this ViewGroup.
        return true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev == null) {
            return true;
        }
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.rawX
                downY = ev.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                moveX = ev.rawX
                moveY = ev.rawY
                if (abs(moveX - downX) > 1) {
                    return false
                }
                if (abs(moveY - downY) > 10) {
                    return false
                }
            }
            else -> {}
        }
        super.onInterceptTouchEvent(ev)
        return true
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        // call recyclerview's and its onTouchEvent here
        // to support cross-direction scrolling like 'scroll' method does before. (eg: 45° left top to right down)

        // first, force child recyclerview handles vertical scrolling.
        codeContentRv.onTouchEvent(ev)
        // then handle horizontal scrolling here.
        super.onTouchEvent(ev)
        // finally mark this event is handled.
        return true
    }

    override fun measureChild(
        child: View,
        parentWidthMeasureSpec: Int,
        parentHeightMeasureSpec: Int
    ) {
        val zeroMeasureSpec = makeMeasureSpec(0)
        // let the child RecyclerView know the actual height.
        child.measure(zeroMeasureSpec, parentHeightMeasureSpec)
    }

    override fun measureChildWithMargins(
        child: View,
        parentWidthMeasureSpec: Int, widthUsed: Int,
        parentHeightMeasureSpec: Int, heightUsed: Int
    ) = with(child.layoutParams as MarginLayoutParams) {

        val widthMeasureSpec = makeMeasureSpec(leftMargin + rightMargin, MeasureSpec.UNSPECIFIED)

        /**
         * Let the child RecyclerView know the actual height again.
         * Because [RecyclerView.LayoutManager.mHeightMode] is determined by MeasureSpec mode in [RecyclerView.onMeasure].
         * If gives a [MeasureSpec.UNSPECIFIED] here, mHeightMode will be 0 and [LinearLayoutManager.LayoutState.mInfinite] will be true.
         * It will cause [LinearLayoutManager.fill] iterates all items in adapter instead of remaining space allowed,
         * which blocks main thread for a long time if there are many items in adapter,
         */
        val heightMode = MeasureSpec.getMode(parentHeightMeasureSpec)
        val parentHeight = MeasureSpec.getSize(parentHeightMeasureSpec)
        val heightMeasureSpec = makeMeasureSpec(parentHeight + topMargin + bottomMargin, heightMode)
        child.measure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun makeMeasureSpec(size: Int) = makeMeasureSpec(size, MeasureSpec.UNSPECIFIED)
}
