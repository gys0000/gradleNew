package com.example.gradlenew.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import java.util.jar.Attributes

class TwoPager(context: Context, attributes: AttributeSet) : ViewGroup(context, attributes) {
    private val overScroller = OverScroller(context)
    private val viewConfiguration: ViewConfiguration = ViewConfiguration.get(context)
    private var maxVelocity: Int = 0//最大速度
    private var minVelocity: Int = 0//最小速度
    private var downX: Float = 0F
    private var downY: Float = 0F
    private var downScrollX: Float = 0F
    private var scrolling: Boolean = false
    private val velocityTracker = VelocityTracker.obtain()

    init {
        maxVelocity = viewConfiguration.scaledMaximumFlingVelocity
        minVelocity = viewConfiguration.scaledMinimumFlingVelocity
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * 布局
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        var childTop = 0
        var childRight = width
        var childBottom = height
        for (index in 0 until childCount) {
            var child = getChildAt(index)
            child.layout(childLeft, childTop, childRight, childBottom)
            childLeft += width
            childRight += width
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.actionMasked == MotionEvent.ACTION_DOWN) {
            //当点击按下的时候，该做什么样的准备
            //按下的时候，停止之前的滑动，速度也归零
            velocityTracker.clear()
        }
        velocityTracker.addMovement(ev)
        var result = false
        when (ev?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                scrolling = false
                downX = ev.x
                downY = ev.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                if (scrolling.not()) {
                    var dx = downX - ev.x
                    if (Math.abs(dx) > viewConfiguration.scaledPagingTouchSlop) {
                        scrolling = true
                        parent.requestDisallowInterceptTouchEvent(true)
                        result = true
                    }
                }
            }
        }
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                var dx = downX - event.x + downScrollX
                if (dx > width) {
                    dx = width.toFloat()
                } else {
                    dx = 0F
                }
                scrollTo(dx.toInt(), 0)
            }
            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000, maxVelocity.toFloat())
                var vx = velocityTracker.xVelocity
                var scrollX = scrollX
                var targetPage = 0
                if (Math.abs(vx) < minVelocity) {
                    targetPage = if (scrollX > width / 2) 1 else 0
                } else {
                    targetPage = if (vx < 0) 1 else 0
                }
                var scrollDiatance = if (targetPage == 1) width - scrollX else -scrollX
                overScroller.startScroll(getScrollX(), 0, scrollDiatance, 0)
                postInvalidateOnAnimation()
            }
        }
        return true
    }

    override fun computeScroll() {
        super.computeScroll()
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.currX, overScroller.currY)
            postInvalidateOnAnimation()//会调用draw,draw会调用computeScroll
        }
    }
}