package diy;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by jh on 2016/9/6.
 */
public class PathView extends View {



    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int mSize;
    private Paint discPaint;  //圆盘的笔
    private Paint pointerPaint; //指针的笔
    private Paint progressPaint;  //进度条的笔;
    private Paint writingProgressPaint; //进度条上文字的笔;
    private Paint scaleArcPaint; //画刻度弧的笔
    private Paint scalePaint; //画刻度的笔
    private Paint scaleTextPaint; //画刻度上文字的笔；

    private float progressRadius;
    private float sacleRadius;
    private final int PROGRESS_WIDTH = 17;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.getSize(heightMeasureSpec);
        widthMeasureSpec = MeasureSpec.getSize(widthMeasureSpec);
        mSize = Math.min(heightMeasureSpec,widthMeasureSpec);
        radius = mSize/2;
        setMeasuredDimension(mSize,mSize);
    }

    private int measureSpec(int mesasure) {
        int result = 175;
        int mode = MeasureSpec.getMode(mesasure);
        int size = MeasureSpec.getSize(mesasure);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = 200;
                result = Math.min(result,size);
                break;
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initPaint();
    }

    int put;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawProgress(canvas);
        drawSacleArc(canvas);
        drawWriting(canvas);
        drawPointer(canvas);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (put<=240)
               setNumble(put++);
                invalidate();
            }
        },100);
    }

    private float writingDistance;
    private float zhongx;

    private void drawProgress(Canvas canvas) {
        canvas.save();   //一定要先画边上的，最后画中间的衔接，不然一段是直角连接，一段就是圆角连接了。
        progressRadius = mSize-dp2px(10);
        writingDistance = 0+dp2px(10);
        zhongx = (progressRadius-writingDistance)/2;
        RectF rectF = new RectF(writingDistance,writingDistance,progressRadius,progressRadius);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setColor(Color.parseColor("#0099cc"));
        canvas.drawArc(rectF,150,120,false,progressPaint);
        progressPaint.setColor(Color.parseColor("#cccccc"));
        canvas.drawArc(rectF,330,60,false,progressPaint);
        progressPaint.setColor(Color.parseColor("#990033"));
        progressPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawArc(rectF,270,60,false,progressPaint);
        canvas.restore();
    }

    private int radius;
    private void drawWriting(Canvas canvas) {
        String writing1 ="正常";
        String writing2 = "预警";
        String writing3 = "警告";
        canvas.save();
        canvas.rotate(-60,radius,radius);
        canvas.drawText(writing1,zhongx,writingDistance+dp2px(5),writingProgressPaint);
        canvas.rotate(90,radius,radius);
        canvas.drawText(writing2,zhongx,writingDistance+dp2px(5),writingProgressPaint);
        canvas.rotate(60,radius,radius);
        canvas.drawText(writing3,zhongx,writingDistance+dp2px(5),writingProgressPaint);
        canvas.restore();
    }

    private int sacleArcLeft;
    private int sacleArcReight;
    private int startdegress = -120;
    private int pointerHigh;

    private void drawSacleArc(Canvas canvas) {
        sacleArcLeft =(int) writingDistance+dp2px(10);
        sacleArcReight = (int) progressRadius-dp2px(10);
        canvas.save();
        RectF rectF = new RectF(sacleArcLeft,sacleArcLeft,sacleArcReight,sacleArcReight);
        canvas.drawArc(rectF,150,240,false,scaleArcPaint);
        canvas.rotate(startdegress,radius,radius);
        int x = 0;
        for (int i = 0 ; i<= 40; i++) {
                int top = dp2px(10);
                if (i % 5==0) {
                    if (i==5||i==0) {
                        canvas.drawText(i+"",radius-dp2px(4),sacleArcLeft+top+dp2px(12),scaleTextPaint);
                        pointerHigh = sacleArcLeft+top+dp2px(17);
                    }
                    else
                        canvas.drawText(i+"",radius-dp2px(7),sacleArcLeft+top+dp2px(12),scaleTextPaint);
                    canvas.drawLine(radius,sacleArcLeft,radius,sacleArcLeft+top,scalePaint );
                }else {
                    top-=dp2px(4);
                    canvas.drawLine(radius,sacleArcLeft,radius,sacleArcLeft+top,scalePaint );
                }
            canvas.rotate(6,radius,radius);
        }
        canvas.restore();
    }

    private int centerRadius = dp2px(17);
    private int numble;

    private void setNumble(int x) {
        this.numble = x;
    }

    private void drawPointer(Canvas canvas) {
        canvas.save();
        canvas.rotate(startdegress,radius,radius);
        canvas.rotate(numble,radius,radius);
        RectF rectF = new RectF(radius-centerRadius/2,radius-centerRadius/2,radius+centerRadius/2,radius+centerRadius/2);
        Path path = new Path();
        path.addArc(rectF,0,180);
        path.moveTo(radius,pointerHigh);
        path.lineTo(radius-centerRadius/2,radius);
        path.lineTo(radius+centerRadius/2,radius);
        path.close();
        canvas.drawCircle(radius,radius,centerRadius,discPaint);
        canvas.drawPath(path,pointerPaint);
        canvas.drawCircle(radius,radius,centerRadius/2-dp2px(3),discPaint);
        canvas.restore();
    }

    
    /**
     * 将 dp 转换为 px
     * @param dp
     * @return
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private int sp2px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    private void initPaint() {
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(dp2px(PROGRESS_WIDTH));
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        writingProgressPaint = new Paint();
        writingProgressPaint.setAntiAlias(true);
        writingProgressPaint.setTextSize(40);
        writingProgressPaint.setTypeface(Typeface.DEFAULT_BOLD);
        writingProgressPaint.setColor(Color.parseColor("#000000"));

        scaleArcPaint = new Paint();
        scaleArcPaint.setAntiAlias(true);
        scaleArcPaint.setStrokeWidth(dp2px(3));
        scaleArcPaint.setStyle(Paint.Style.STROKE);

        scalePaint = new Paint();
        scalePaint.setStrokeWidth(dp2px(1));
        scalePaint.setAntiAlias(true);

        scaleTextPaint = new Paint();
        scaleTextPaint.setAntiAlias(true);
        scaleTextPaint.setTextSize(sp2px(15));

        pointerPaint = new Paint();
        pointerPaint.setAntiAlias(true);
        pointerPaint.setColor(Color.parseColor("#666699"));
        pointerPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        discPaint = new Paint();
        discPaint.setAntiAlias(true);
        discPaint.setColor(Color.parseColor("#333300"));
    }
}
