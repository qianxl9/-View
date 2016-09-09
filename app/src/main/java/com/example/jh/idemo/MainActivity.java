package com.example.jh.idemo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import diy.Sclemenoy;
import diy.TopBar;

public class MainActivity extends AppCompatActivity {

    private TopBar mBar;
    private TextView mText;
    private Sclemenoy sclemenoy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
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
                Toast.makeText(MainActivity.this, "已点击左边按钮", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnRightClick() {
                Toast.makeText(MainActivity.this, "已点击右边按钮" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
