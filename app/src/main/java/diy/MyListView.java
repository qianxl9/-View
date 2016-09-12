package diy;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.jh.idemo.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by jh on 2016/9/11.
 */
public class MyListView extends ListView implements View.OnTouchListener,GestureDetector.OnGestureListener {

    private GestureDetector gestureDetector;
    private View deleteButton;
    private ViewGroup itemLayout;
    private int selectedItem;
    private boolean isShowButton ;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(context,this);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (isShowButton) {
            itemLayout.removeView(deleteButton);
            deleteButton = null ;
            isShowButton = false;
            return false;
        }else {
            Log.e("什么东西","   ");
          return  gestureDetector.onTouchEvent(motionEvent);
        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        if (!isShowButton) {
            selectedItem = pointToPosition((int) motionEvent.getX(),(int) motionEvent.getY());
            Log.e("MyListView","已执行到4。。。。。。");
        }

        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if (!isShowButton && Math.abs(v)>Math.abs(v1)) {
            deleteButton = LayoutInflater.from(getContext()).inflate(R.layout.delete_button,null);
            deleteButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemLayout.removeView(deleteButton);
                    deleteButton = null;
                    isShowButton = false;
                    if (listener !=null) {
                        listener.onDelete(selectedItem,itemLayout);
                    }
                }
            });
            itemLayout = (ViewGroup) getChildAt(selectedItem-getFirstVisiblePosition());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(200,200);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            itemLayout.addView(deleteButton,layoutParams);
            //ObjectAnimator.ofFloat(deleteButton,"translationX",0,deleteButton.getWidth()).setDuration(1).start();
            isShowButton = true;
            Log.e("MyListView","已执行到。。。。。。");
            ObjectAnimator.ofFloat(itemLayout,"translationX",0,deleteButton.getWidth()).setDuration(1000).start();
        }
        Log.e("MyListView","已执行到3。。。。。。");
        return false;
    }

    private OnDeleteListener listener;

    public void setOnDeleteListener(OnDeleteListener l) {
        listener = l;
    }

    public interface OnDeleteListener {
        void onDelete(int selection,ViewGroup viewGroup);
    }
}
