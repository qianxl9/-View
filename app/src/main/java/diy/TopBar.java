package diy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Dimension;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jh.idemo.R;

/**
 * Created by jh on 2016/9/2.
 */
public class TopBar extends RelativeLayout {

    private String mTitleText;
    private float mTitleTextSize;
    private int mTitleTextColor;
    private String mleftText;
    private Drawable mleftBackgound;
    private int mleftColor;
    private String mRightText;
    private Drawable mRightBackgound;
    private int mRightColor;

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs);
        setView(context);
    }


    private void getAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.TopBar);
        mTitleText = ta.getString(R.styleable.TopBar_title);
        mTitleTextSize = ta.getDimension(R.styleable.TopBar_titleTextSize,10);
        mTitleTextColor = ta.getColor(R.styleable.TopBar_titleTextColor,0);
        mleftBackgound = ta.getDrawable(R.styleable.TopBar_leftBackground);
        mleftColor = ta.getColor(R.styleable.TopBar_leftTextColor,0);
        mleftText = ta.getString(R.styleable.TopBar_leftText);
        mRightColor = ta.getColor(R.styleable.TopBar_rightTextColor,0);
        mRightText = ta.getString(R.styleable.TopBar_rightText);
        mRightBackgound = ta.getDrawable(R.styleable.TopBar_rightBackground);
        ta.recycle();
    }

    private Button mleftButton;
    private Button mRightButton;
    private TextView mTitle;
    private LayoutParams mleftLP;
    private LayoutParams mRightLp;
    private LayoutParams mTitleLP;
    private OnLRClickListener lrClickListener;


    public interface OnLRClickListener {
        void OnleftClick();

        void OnRightClick();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int ibt=widthMeasureSpec;
        super.onMeasure(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void setOnLrClickListener(OnLRClickListener listener) {
        this.lrClickListener = listener;
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0 ;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }else {
            result = 150;
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

    public void setView(Context context) {
        mleftButton = new Button(context);
        mRightButton = new Button(context);
        mTitle = new TextView(context);

        mTitle.setText(mTitleText);
        mTitle.setTextColor(mTitleTextColor);
        mTitle.setTextSize(mTitleTextSize);
        mTitle.setGravity(Gravity.CENTER);
        mTitleLP = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT);
        mTitleLP.addRule(RelativeLayout.CENTER_IN_PARENT);

        mTitle.setLayoutParams(mTitleLP);

        mleftLP = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT);
        mleftLP.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        mleftButton.setText(mleftText);
        mleftButton.setTextColor(mleftColor);
        mleftButton.setBackground(mleftBackgound);
        mleftButton.setLayoutParams(mleftLP);

        mRightLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT);
        mRightLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mRightButton.setText(mRightText);
        mRightButton.setTextColor(mRightColor);
        mRightButton.setBackground(mRightBackgound);
        mRightButton.setLayoutParams(mRightLp);


        addView(mTitle);
        addView(mRightButton);
        addView(mleftButton);
        setOnclik(mRightButton,mleftButton);
    }

    private void setOnclik(Button mRightButton, Button mleftButton) {
            mRightButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lrClickListener !=null) {
                        lrClickListener.OnRightClick();
                    }
                }
            });

            mleftButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lrClickListener != null) {
                        lrClickListener.OnleftClick();
                    }
                }
            });
    }


}
