package diy;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by jh on 2016/9/1.
 */
public class TestView extends TextView implements ValueAnimator.AnimatorUpdateListener {

    private Paint mPaint1;
    private Paint mPaint2;
    private Scroller scroller;


    public TestView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        scroller = new Scroller(context);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
        mPaint1 = new Paint();
        mPaint1.setColor(Color.parseColor("#00ffff"));
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint2 = new Paint();
        mPaint2.setColor(Color.parseColor("#cc6600"));
     //   mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setStrokeMiter(200);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
    }


    private int measureHeight(int heightMeasureSpec) {
        int result = 0 ;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result,specSize);
            }
        }

        return result;
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0 ;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }else {
            result = 400;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result,specSize);
            }
        }
        return result;
    }

    int mViewWidth;
    int mTranslate;
    LinearGradient mLiner;
    Matrix matrixG;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0 ) {
            mViewWidth = getMeasuredWidth();

            if (mViewWidth > 0) {
                mPaint1 = getPaint();
                mLiner = new LinearGradient(0,0,mViewWidth,0,new int[]{Color.parseColor("#303F9F"),
                        Color.parseColor("#FF4081")},null, Shader.TileMode.CLAMP);
                mPaint1.setShader(mLiner);
                matrixG = new Matrix();
            }
        }
    }


    private boolean jg = true ;

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint2);

        super.onDraw(canvas);
        if (matrixG != null ) {
            matrixG.setTranslate(mTranslate,0);
            mLiner.setLocalMatrix(matrixG);
                mTranslate+=40;
            if (mTranslate > 400)
                mTranslate = -400;
            postInvalidateDelayed(100);
        }
        if (jg) {
           // animator();
            ObjectAnimator.ofFloat(this,"translationX",0,-100).setDuration(100).start();
            // smoothScrollTo(100,0);
            jg =false;
        }
    }

    final int startX = 0;
    final int deltax = 100;

    private void animator() {
        final ValueAnimator animator = ValueAnimator.ofInt(0,1).setDuration(1000);
        animator.start();
        animator.addUpdateListener(this);
    }

    private void smoothScrollTo(int destX , int destY) {
        int scrollX = getScrollX();
        int deltax = destX - scrollX;
        scroller.startScroll(scrollX,0,deltax,0,10000);
        invalidate();
    }

    int mlastX,mlastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int deltax = x-mlastX;
                int deltay = y-mlastY;
                Log.e("TGA","move , deltaX:"+deltax+"   deltaY:"+deltay);
                int translationX = (int) ViewHelper.getTranslationX(this)+deltax;
                int translationY = (int) ViewHelper.getTranslationY(this)+deltay;
                ViewHelper.setTranslationX(this,translationX);
                ViewHelper.setTranslationY(this,translationY);

                break;
        }
        mlastY = y;
        mlastX = x;
        return true;
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float fraction = valueAnimator.getAnimatedFraction();
        this.scrollTo(startX+(int) (deltax*fraction),0);
    }
}
