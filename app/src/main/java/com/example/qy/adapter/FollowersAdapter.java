package com.example.qy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qy.R;
import com.example.qy.bean.Follwers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.ViewHolder> {
    public List<Follwers> lists;
    public Context context;
    public int type;
    public Map<String,Boolean> map = new HashMap();
    public Map<String,Map<String,Boolean>> map2 = new HashMap<>();   // 存状态

    public FollowersAdapter(Context context, List<Follwers> lists,int type){
        this.lists = lists;
        this.context = context;
        this.type = type;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civ_followers_icon;
        TextView tv_followers_name;
        TextView tv_signature;
        Button btn_followers_focus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            civ_followers_icon = itemView.findViewById(R.id.civ_followers_icon);
            tv_followers_name = itemView.findViewById(R.id.tv_followers_name);
            tv_signature = itemView.findViewById(R.id.tv_signature);
            btn_followers_focus = itemView.findViewById(R.id.btn_followers_focus);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.followers_item,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Follwers follwers = lists.get(i);
        Glide.with(context).load(follwers.icon).into(viewHolder.civ_followers_icon);
        viewHolder.tv_followers_name.setText(follwers.name);
        viewHolder.tv_signature.setText(follwers.signature);
        if (type == 1){
            if (follwers.together.equals("0")){
                viewHolder.btn_followers_focus.setText("已关注");
            }else{
                viewHolder.btn_followers_focus.setText("互相关注");
            }
            viewHolder.btn_followers_focus.setOnClickListener(v->{
                if (map.containsKey(follwers.loginId) && map.get(follwers.loginId)){
                    map.put(follwers.loginId,false);
                }else{
                    map.put(follwers.loginId,true);
                }
                if (follwers.together.equals("0")||follwers.together.equals("1")){
                    follwers.together = "2";
                    viewHolder.btn_followers_focus.setText("关注TA");
                    viewHolder.btn_followers_focus.setTextColor(Color.parseColor("#ffffff"));
                    viewHolder.btn_followers_focus.setBackgroundResource(R.drawable.shape_integral_task);
                }else{
                    follwers.together = "0";
                    viewHolder.btn_followers_focus.setText("已关注");
                    viewHolder.btn_followers_focus.setTextColor(Color.parseColor("#666666"));
                    viewHolder.btn_followers_focus.setBackgroundResource(R.drawable.shape_focus);
                }
            });
        }else if (type == 2){
            if (follwers.together.equals("0")){
                viewHolder.btn_followers_focus.setText("关注TA");
                viewHolder.btn_followers_focus.setTextColor(Color.parseColor("#ffffff"));
                viewHolder.btn_followers_focus.setBackgroundResource(R.drawable.shape_integral_task);
            }else{
                viewHolder.btn_followers_focus.setText("互相关注");
                viewHolder.btn_followers_focus.setTextColor(Color.parseColor("#666666"));
                viewHolder.btn_followers_focus.setBackgroundResource(R.drawable.shape_focus);
            }
            viewHolder.btn_followers_focus.setOnClickListener(v->{
                Map<String,Boolean> m = new HashMap<>();
                if (map2.containsKey(follwers.loginId) && map.get(follwers.loginId)){
                    m.put("state",false);
                    m.put("together",follwers.together.equals("1"));
                    map2.put(follwers.loginId,m);
                }else{
                    m.put("state",true);
                    m.put("together",follwers.together.equals("1"));
                    map2.put(follwers.loginId,m);
                }
                if (follwers.together.equals("1")){
                    viewHolder.btn_followers_focus.setText("关注TA");
                    viewHolder.btn_followers_focus.setTextColor(Color.parseColor("#ffffff"));
                    viewHolder.btn_followers_focus.setBackgroundResource(R.drawable.shape_integral_task);
                }else{

                    viewHolder.btn_followers_focus.setText("互相关注");
                    viewHolder.btn_followers_focus.setTextColor(Color.parseColor("#666666"));
                    viewHolder.btn_followers_focus.setBackgroundResource(R.drawable.shape_focus);
                }
            });

        }


    }

    public Map<String, Map<String, Boolean>> getMap2() {
        return map2;
    }

    public Map<String,Boolean> getMap(){
        return map;
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
}
