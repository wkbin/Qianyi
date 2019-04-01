package com.example.qy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.bean.Calendar;
import com.example.qy.bean.Integral;
import com.example.qy.utils.CalendarAdapter;
import com.example.qy.utils.CalendarTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 30 日 下午 3:45
 * Description: 日历Adapter
 */
public class CalendarDetailedAdapter extends RecyclerView.Adapter<CalendarDetailedAdapter.ViewHolder> {
    private List<Calendar> list;
    private Context context;
    private int day;

    public CalendarDetailedAdapter(Context context,List<com.example.qy.bean.Calendar> list,int day){
        this.context = context;
        this.list = list;
        this.day = day;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.calendar_detailed_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Calendar c = list.get(i);
        viewHolder.tv_date.setText(c.year+"年"+" "+c.month+"月");
        List<Integer> days = new ArrayList<>();

        // 当月总天数
        int alldays = CalendarTool.getMonthLastDay(c.year,c.month);

        if (i == 0){
            // 当前月还剩多少天
            for (int j = day ; j<= alldays;j++){
                days.add(j);
            }
            int week = CalendarTool.getWeekdayOfMonth(c.year,c.month,day) ;
            if (day>7){
                int k = days.size();
                for (int j =  alldays-k,m = 1; m <=  week ; j--,m++){
                    days.add(0,j);
                }
            }
        }else{
            for (int m = 1;m <=alldays;m++){
                days.add(m);
            }
            int week = CalendarTool.getWeekdayOfMonth(c.year,c.month,1) ;
            for(int n = 1;n<week;n++){
                days.add(0,0);
            }
        }


//

//        Log.d("buy","week == "+week+",beforeday == ");
//        CalendarAdapter adapter = new CalendarAdapter(getContext(),list);
//        rc_calendar.setAdapter(adapter);

        CalendarAdapter adapter =new CalendarAdapter(context,days,c.year,c.month);
        viewHolder.rc_calendar_detailed.setLayoutManager(new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL));
        viewHolder.rc_calendar_detailed.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private RecyclerView rc_calendar_detailed;
        private TextView tv_date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rc_calendar_detailed = itemView.findViewById(R.id.rc_calendar_detailed);
            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }


}
