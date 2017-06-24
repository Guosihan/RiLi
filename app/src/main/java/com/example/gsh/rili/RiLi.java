package com.example.gsh.rili;

import android.content.Context;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gsh on 2017/6/24.
 */

public class RiLi extends LinearLayout {
    private ImageView btnNext;
    private ImageView btnPre;
    private TextView textDate;
    private GridView grid;
    private Calendar calDate=Calendar.getInstance();
    public RiLi(Context context) {
        super(context);
    }

    public RiLi(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context);
    }



    public RiLi(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context);
    }
    private void initControl(Context context) {
        bindControl(context);
        bindConrolEvent();
        renderCalendar();
    }



    private void bindControl(Context context) {
        LayoutInflater inflater=LayoutInflater.from(context);
        inflater.inflate(R.layout.rili_view,this);
        btnNext= (ImageView) findViewById(R.id.btn_next);
        btnPre= (ImageView) findViewById(R.id.btn_pre);
        textDate= (TextView) findViewById(R.id.text_date);
        grid= (GridView) findViewById(R.id.rili_grid);
    }
    private void bindConrolEvent() {
        btnPre.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calDate.add(Calendar.MONTH,-1);
                renderCalendar();
            }
        });
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calDate.add(Calendar.MONTH,1);
                renderCalendar();
            }
        });
    }
    private void renderCalendar(){
        SimpleDateFormat sdf=new SimpleDateFormat("MMM yyyy");
        textDate.setText(sdf.format(calDate.getTime()));
        ArrayList<Date> cells=new ArrayList<>();
        Calendar calendar= (Calendar) calDate.clone();
        calendar.set(Calendar.DAY_OF_MONTH,1);
        int prevDays=calendar.get(Calendar.DAY_OF_WEEK)-1;
        calendar.add(Calendar.DAY_OF_MONTH,-prevDays);
        int maxCount=6*7;
        while (cells.size()<maxCount){
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }
        grid.setAdapter(new CalendarAdapter(getContext(),cells));
    }
    private class CalendarAdapter extends ArrayAdapter<Date>{
        LayoutInflater inflater;
        public CalendarAdapter(Context context,ArrayList<Date> days) {
            super(context, R.layout.calender_text_day,days);
            inflater=LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Date date=getItem(position);
            if(convertView ==null){
                convertView= inflater.inflate(R.layout.calender_text_day,parent,false);

            }
            int day=date.getDate();
            ((TextView)convertView).setText(String.valueOf(day));
            return convertView;
        }
    }
}
