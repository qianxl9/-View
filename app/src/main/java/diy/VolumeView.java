package diy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jh on 2016/9/3.
 */
public class VolumeView extends View {

    private Paint mPaint;
    private double mFixedwith;
    private double mrandomHeith;
    private int mViewW;
    private int mViewH;
    private double minterval;
    private int mRectCount = 10;

    public VolumeView(Context context) {
        super(context);
    }

    public VolumeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VolumeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initView();
    }

    private void initView() {

        mViewH = getMeasuredHeight();
        mViewW = getMeasuredWidth();
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#48D1CC"));
        mFixedwith =  mViewW*0.7/10;
        minterval = mViewW*0.1/10;
    }

    private float shek;


    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        for (int i = 0 ; i<mRectCount ; i++) {
            //   mPaint.reset();
            mPaint.setColor(Color.parseColor("#5CACEE"));
            mrandomHeith = Math.random()*mViewH;
            shek = (float) (Math.random()*(mrandomHeith-20));
            canvas.drawRect((float) (minterval+mFixedwith*i),
                    (float) mrandomHeith,
                    (float) (mFixedwith*(i+1)),
                    mViewH,mPaint);

            // mPaint.reset();
            mPaint.setColor(Color.parseColor("#b9d3ee"));
            canvas.drawRect((float) (minterval+mFixedwith*i),
                    (float) shek,
                    (float) (mFixedwith*(i+1)),
                    shek+20,mPaint);
        }
        postInvalidateDelayed(200);
    }
}
