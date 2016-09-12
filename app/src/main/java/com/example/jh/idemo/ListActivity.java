package com.example.jh.idemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import diy.MyListView;

/**
 * Created by jh on 2016/9/11.
 */
public class ListActivity extends AppCompatActivity {

    private MyListView myListView;
    private List<String> items = new ArrayList<String>();
    private Mydapter mydapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        myListView =(MyListView) findViewById(R.id.list_view);
        mydapter = new Mydapter(this,R.layout.list_item,items);
        initList();
        myListView.setOnDeleteListener(new MyListView.OnDeleteListener() {
            @Override
            public void onDelete(final int selection, final ViewGroup viewGroup) {
                //ObjectAnimator.ofFloat(viewGroup,"scaleY",1,0).setDuration(1000).start();
                items.remove(selection);
                mydapter.notifyDataSetChanged();
            }
        });
        myListView.setAdapter(mydapter);
    }

    private void initList() {
        items.add("Content Item 1");
        items.add("Content Item 2");
        items.add("Content Item 3");
        items.add("Content Item 4");
        items.add("Content Item 5");
        items.add("Content Item 6");
        items.add("Content Item 7");
        items.add("Content Item 8");
        items.add("Content Item 9");
        items.add("Content Item 10");
        items.add("Content Item 11");
        items.add("Content Item 12");
        items.add("Content Item 13");
        items.add("Content Item 14");
        items.add("Content Item 15");
        items.add("Content Item 16");
        items.add("Content Item 17");
        items.add("Content Item 18");
        items.add("Content Item 19");
        items.add("Content Item 20");
    }


}
