package com.example.jh.idemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jh on 2016/9/11.
 */
public class Mydapter extends ArrayAdapter<String> {

    public Mydapter(Context context, int textViewResourced, List<String> objects) {
        super(context,textViewResourced,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item,null);
        }else {
            view = convertView;
        }
        TextView textView = (TextView) view.findViewById(R.id.text_view);
        textView.setText(getItem(position));
        return view;
    }
}
