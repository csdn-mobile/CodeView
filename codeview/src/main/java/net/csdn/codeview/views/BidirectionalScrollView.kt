package net.csdn.codeview.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.MeasureSpec.makeMeasureSpec
import android.widget.HorizontalScrollView
import androidx.recyclerview.widget.RecyclerView
import net.csdn.codeview.R

/**
 * @class BidirectionalScrollView
 *
 * Combines vertical & horizontal scroll to implement bidirectional
 * scrolling behavior (like a map view, for example).
 *
 * @author Kirill Biakov
 */
class BidirectionalScrollView : HorizontalScrollView {

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
