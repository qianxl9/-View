package diy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by jh on 2016/9/4.
 */
public class Sclemenoy extends View {

    private Paint textPaint;  //写数字的笔；
    private Paint mLinePaint; //画横线的笔
    private Paint mVertioalPaint; //画中间横的笔
    private Paint mValuePaint; //画刻度的笔

    private VelocityTracker velocityTracker;
    private Scroller scroller;
    private int defuleValue = 150;
    private int movex,lastMovex=0;
    private int lastx;
    private boolean isLeft=false;
    private int mWith,starty;

    public Sclemenoy(Context context) {
        super(context);
        scroller = new Scroller(context);
    }

    public Sclemenoy(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    public Sclemenoy(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       setMeasuredDimension(measureHeight(widthMeasureSpec),measureHeight(heightMeasureSpec));
    }

    private void initSize() {
        mWith = getMeasuredWidth();
        starty = getMeasuredHeight()/2;
    }

    private int measureHeight(int measureHeight) {
        int mode = MeasureSpec.getMode(measureHeight);
        int size = MeasureSpec.getSize(measureHeight);
        defuleValue = 150;
        switch (mode) {
            case MeasureSpec.AT_MOST:
                int result =Math.min(defuleValue,size);
                return result;
            case MeasureSpec.EXACTLY:
                return size;
        }
        return size;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initPaint();
        initSize();
    }

    private boolean isDrawText = true ;

    private OngetValueListener listener;

    public interface OngetValueListener {
        void ongetValue(int value);
    }

    public void setOngetValueListener(OngetValueListener ongetValueListener) {
        this.listener = ongetValueListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0,starty,mWith,starty,mLinePaint); //画横线
        canvas.drawLine(mWith/2,0,mWith/2,starty,mVertioalPaint); //画中间的坐标。

        if (listener != null) {
            listener.ongetValue((-lastMovex+(mWith/2+7))/13);
        }

        for (int start = 0; start < mWith ; start ++) { //得到起始的值，然后按屏幕的宽绘制余下的内容；
            int Top = starty - 10;
            //因为向左滑得到滑动距离负的，所以要取反。
            if ((-lastMovex+start) % 130 == 0 ) {
                Top-=20;
                isDrawText = true;
            }else {
                isDrawText = false;
            }

            if ((-lastMovex+start) >=0 && (-lastMovex + start) <= 5000*13 ) {
                if ((-lastMovex+start) % 13 == 0) {
                    canvas.drawLine(start,starty,start,Top,mValuePaint);
                }
                if (isDrawText) {
                    canvas.drawText((-lastMovex + start)/13+"",start,Top-8,textPaint);
                }
            }
        }
        super.onDraw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker  = VelocityTracker.obtain();  //创建速度追踪的对象。
        }
        velocityTracker.addMovement(event); //添加移动事件的对象。
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastx = (int)event.getX();
                break;
 
            case MotionEvent.ACTION_MOVE:
                movex = (int) event.getX()-lastx;
                lastMovex +=movex;
                if (movex < 0 ) { //表示向左滑，大坐标滑到小坐标，最后呢一减一定是负的。
                    isLeft = true;  //为了计算滑动距离处方便判断，因其得出算出的都是正值。
                    if ((-lastMovex+mWith/2) > 5000*13) { //因刻度标识在中间。
                        lastMovex -=movex;
                        return true;
                    }
                }else {
                    isLeft = false;
                    if ((lastMovex+mWith/2)>= mWith) {  //同上，同时也不能再重绘视图。
                        lastMovex-=movex;
                        return true;
                    }
                }
                invalidate();
                break;

            case MotionEvent.ACTION_UP:

                velocityTracker.computeCurrentVelocity(1000);//设置计量单位，毫秒. 就是多少毫秒收集一次移动的像素。
                float currentVelocityx = velocityTracker.getXVelocity(); //分别获取X ，Y 轴的速度
                float currentVelocityy = velocityTracker.getYVelocity();

                if ( Math.abs(currentVelocityx) < 800) {
                    return true;
                }
                //此处把X的距离取反是为了方便统一。fling()方法就是惯性滑动的处理，和动画。

                scroller.fling(130,starty,(int)(-Math.abs(currentVelocityx)),(int)(Math.abs(currentVelocityy)),0,1080,0,1920);

                velocityTracker.recycle();
                velocityTracker = null ;
                break;
        }
        lastx =(int) event.getX();
        return true;
    }

    @Override
    public void computeScroll() {  //计算滚动的距离
        if (scroller.computeScrollOffset()) {
            int currX = scroller.getCurrX();
            if (isLeft) {
                lastMovex-=currX;
            }else {
                lastMovex+=currX;
            }
            if ((lastMovex+mWith/2) >= mWith) {
                lastMovex-=currX;
                return;
            }
            if ((-lastMovex+mWith/2) >= 5000*13) {
                lastMovex+=currX;
                return;
            }
            invalidate();
        }
    }

    private void initPaint() {
        mLinePaint = new Paint();
        textPaint = new Paint();
        mValuePaint = new Paint();
        mVertioalPaint = new Paint();

        mValuePaint.setColor(0xFF999999);
        mValuePaint.setStyle(Paint.Style.STROKE);
        mValuePaint.setAntiAlias(true);
        mValuePaint.setStrokeWidth(2);

        textPaint.setColor(0xFF999999);
        textPaint.setTextSize(30);
        textPaint.setStrokeWidth(2);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);

        mLinePaint.setColor(0xFF999999);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(2);
        mLinePaint.setAntiAlias(true);

        mVertioalPaint.setColor(0xFFFE7F1A);
        mVertioalPaint.setAntiAlias(true);
        mVertioalPaint.setStrokeWidth(3);
        mVertioalPaint.setStyle(Paint.Style.STROKE);
    }
}
