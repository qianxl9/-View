package diy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jh on 2016/9/3.
 */
public class CircleProgressView extends View {

    private Paint mPaint;

    private float mCirclexy;
    private float mRedius;

    private String mShowText;
    private float mTextSize;

    private RectF mArcR;
    private float mSweepAngle;

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initView();
    }

    private void initView() {
        float length = 0 ;
        if (getMeasuredHeight()>=getMeasuredWidth()) {
                length = getMeasuredWidth();
        }else {
                length = getMeasuredHeight();
        }

        mCirclexy = length/2;
        mRedius = (float) ((length*0.5)/2);

        mShowText = "这是一个自定义的";
        mTextSize = 50;

        mArcR = new RectF((float) (length*0.1),(float) (length*0.1),(float) (length*0.9),(float) (length*0.9));
        mSweepAngle =270;
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleProgressView(Context context) {
        super(context);
    }

    private static boolean zhi =true;

    @Override
    protected void onDraw(Canvas canvas) {
        
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#D1EEEE"));
        mPaint.setAntiAlias(true);
        canvas.drawCircle(mCirclexy,mCirclexy,mRedius,mPaint);
        canvas.save();

        canvas.restore();
        super.onDraw(canvas);

        if (mSweepAngle>90) {
            mPaint.reset();
            mPaint.setColor(Color.parseColor("#BFEFFF"));
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth((float) (getWidth()*0.1));
            canvas.drawArc(mArcR,0,mSweepAngle,false,mPaint);
            mSweepAngle --;
            postInvalidateDelayed(10);
        }else {
            mPaint.reset();
            mPaint.setColor(Color.parseColor("#BFEFFF"));
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth((float) (getWidth()*0.1));
            canvas.drawArc(mArcR,0,mSweepAngle,false,mPaint);
            canvas.translate(mCirclexy,mCirclexy);
            mPaint.reset();
            mPaint.setTextSize(mTextSize);
            mPaint.setColor(Color.parseColor("#757575"));
            mPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("这",0,"这".length(),0,0,mPaint);
        }

    }
}
