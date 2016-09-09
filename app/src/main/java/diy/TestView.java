package diy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by jh on 2016/9/1.
 */
public class TestView extends TextView {

    private Paint mPaint1;
    private Paint mPaint2;


    public TestView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
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

    }
}
