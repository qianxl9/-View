package com.example.jh.idemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import diy.Sclemenoy;
import diy.TopBar;

public class MainActivity extends AppCompatActivity {

    private TopBar mBar;
    private TextView mText;
    private Sclemenoy sclemenoy;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.test);
        StringBuilder cu = new StringBuilder();

        textView.setText(cu.toString());
        mBar = (TopBar) findViewById(R.id.bar_top_title);
        mText = (TextView) findViewById(R.id.text_master_title);
        mText.setTextColor(Color.parseColor("#EEC900"));
        sclemenoy = (Sclemenoy) findViewById(R.id.sclemenoy);
        sclemenoy.setOngetValueListener(new Sclemenoy.OngetValueListener() {
            @Override
            public void ongetValue(int value) {
                mText.setText(value +"");
            }
        });
        mBar.setOnLrClickListener(new TopBar.OnLRClickListener() {
            @Override
            public void OnleftClick() {
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                startActivity(intent);
            }

            @Override
            public void OnRightClick() {
                Toast.makeText(MainActivity.this, "已点击右边按钮" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static int[] histogram(int[] a,int m ) {
        int[] n = new int[m];
        int i = a.length;

        for (int x = 0 ; x < i ; x++ ) {
            if (a[x] < m) {
                n[a[x]]++;
            }
        }
        return n;
    }
}
