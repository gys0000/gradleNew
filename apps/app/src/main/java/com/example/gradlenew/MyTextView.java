package com.example.gradlenew;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class MyTextView extends View {

    private float fraction;
    private int w;
    private int h;
    private String text = "";
    private String oldText = "";
    private float from;
    private TextPaint paint;
    private ObjectAnimator animator;

    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xff000000);
        paint.setTextSize(30);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("MyTextView", "onMeasure---->");
        w = MeasureSpec.getSize(widthMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("MyTextView", "onSizeChanged---->");
        this.w = w;
        this.h = h;
    }

    public void setText(String text, float from) {
        this.text = text;
        int textHalf = text.length() * 15 / 2;
        this.from = from;
        Log.e("MyTextView", "setText---->" + textHalf + ":" + w / 2f + ":" + oldText);
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(this, "fraction", from, w / 2f - textHalf);
        } else {
            animator.cancel();
            animator = null;
            animator = ObjectAnimator.ofFloat(this, "fraction", from, w / 2f - textHalf);
        }
        animator.setDuration(2000);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                oldText = text;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(text, fraction, h / 2 + 10, paint);
        Log.e("MyTextView", "+++++---->" + fraction + ":" + text + ":" + oldText + ":" + from);
        if (!oldText.equals("")) {
            if (from > 0) {
                Log.e("MyTextView", "---->" + fraction + ":" + text + ":" + oldText);
                canvas.drawText(oldText, fraction -300, h / 2 + 10, paint);
            } else {
                canvas.drawText(oldText, fraction + 200, h / 2 + 10, paint);
            }
        }
    }

    public float getFraction() {
        return fraction;
    }

    public void setFraction(float fraction) {
        this.fraction = fraction;
        invalidate();
    }

}
