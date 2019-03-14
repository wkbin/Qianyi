package com.example.qy.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.qy.R;
import com.example.qy.adapter.HomeAdapter;
import com.example.qy.bean.UserInfo;
import com.example.qy.bean.Video;
import com.example.qy.ui.ViewPagerLayoutManager;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.whs.MyApplication;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class HomeFragment extends Fragment {
    private String TAG = "HomeFragment";
    private RecyclerView rv_home;
    private List<Video> list;
    private ImageView iv_home,iv_search;
    private ViewPagerLayoutManager viewPagerLayoutManager;
    HomeAdapter adapter;

    private boolean isShowFragment;

    private int user_id;


    // 旋转动画
    private RotateAnimation animation;
    // 音符移动动画
    private TranslateAnimation ta,ta1;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x333){

                viewPagerLayoutManager = new ViewPagerLayoutManager(getActivity(),OrientationHelper.VERTICAL);
                rv_home.setLayoutManager(viewPagerLayoutManager);
//                Collections.shuffle(list);
                adapter = new HomeAdapter(getActivity(),list,user_id,getFragmentManager());
                rv_home.setAdapter(adapter);


                adapter.setOnItemClickListener(postion->{

                        View itemView = rv_home.getChildAt(0);
                        PLVideoTextureView plVideoView = itemView.findViewById(R.id.PLvv_play);
                        if (plVideoView.isPlaying()){
                            pausePlay();
                        }else{
                            startPlay();
                        }
                });

                viewPagerLayoutManager.setOnViewPagerListener(new ViewPagerLayoutManager.OnViewPagerListener() {
                    @Override
                    public void onInitComplete() {
                        startPlay();
                    }

                    @Override
                    public void onPageRelease(boolean isNext, int position) {
                        Log.e(TAG,"释放位置:"+position +" 下一页:"+isNext);
                        // 要释放的视频
                        pausePlay(isNext?0:1);
                    }

                    @Override
                    public void onPageSelected(int position, boolean isBottom) {
                        Log.e(TAG,"选中位置:"+position+"  是否是滑动到底部:"+isBottom);
                        startPlay();
                    }
                });

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView(){

        int magnify = 10000;
        int toDegrees = 360;
        int duration = 4000;    //旋转速度
        toDegrees *= magnify;
        duration *= magnify;
        animation = new RotateAnimation(0,toDegrees,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(duration);
        LinearInterpolator lir = new LinearInterpolator();
        animation.setInterpolator(lir);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);



        // 音符动画  以自身为坐标点   参数： x轴的起始点,结束点   y轴的起始点,结束点


        Random rand = new Random();
        int xDelta = -(rand.nextInt(100)+100);
        ta = new TranslateAnimation(0,xDelta,0,-200);
        // 设置动画时长
        ta.setRepeatCount(Animation.INFINITE);
        ta.setRepeatMode(Animation.RESTART);
        ta.setDuration(3000);

        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        // 音符动画  以自身为坐标点   参数： x轴的起始点,结束点   y轴的起始点,结束点
//        ta1 = new TranslateAnimation(0,-50,0,-250);
//        // 设置动画时长
//        ta1.setRepeatCount(Animation.INFINITE);
//        ta1.setRepeatMode(Animation.RESTART);
//        ta1.setDuration(3000);




        rv_home = getActivity().findViewById(R.id.rv_home);
        list = new ArrayList<>();

        UserInfo userInfo = ((MyApplication)getActivity().getApplication()).getUserInfo();
        if (userInfo != null){
            user_id = userInfo.loginId;
        }

        String url = HttpQYUtils.getSelectVideo(user_id,0);

        Log.d("666","url = "+url);
        HttpUtils.sendOkHttpRequest(url , new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                try {
                    JSONObject object = new JSONObject(responseText);
                    JSONObject dataObject = object.getJSONObject("data");
                    JSONArray jsonArray = dataObject.getJSONArray("videolist");
                    if (jsonArray != null){
                        for(int i = 0 ; i < jsonArray.length();i ++){
                            Video v = new Video();
                            JSONObject video = jsonArray.getJSONObject(i);
                            v.id = video.getString("id");
                            v.videoPath = video.getString("videoPath");
                            v.coverPath = video.getString("coverPath");
                            v.countLike = video.getJSONObject("likes").getInt("countLike");
                            v.liked = video.getJSONObject("likes").getBoolean("liked");
                            v.createdate = video.getString("createdate");
                            v.videoDesc = video.getString("videoDesc");
                            v.musicId = video.getString("musicId");
                            v.type = video.getString("type");
                            v.addressId = video.getString("addressId");
                            v.countComments = video.getString("countComments");
                            list.add(v);
                        }

                        Message msg = new Message();
                        msg.what = 0x333;
                        handler.sendMessage(msg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    public void startPlay(){
        View itemView = rv_home.getChildAt(0);
        if (itemView == null) return;
        PLVideoTextureView plVideoView = itemView.findViewById(R.id.PLvv_play);
        de.hdodenhof.circleimageview.CircleImageView civ_tx = itemView.findViewById(R.id.civ_dp);
        ImageView iv_yficon = itemView.findViewById(R.id.iv_yficon);
//        ImageView iv_yficon1 = itemView.findViewById(R.id.iv_yficon1);
        ImageView iv_pause = itemView.findViewById(R.id.iv_pause);
        iv_pause.setVisibility(View.GONE);
        civ_tx.startAnimation(animation);

        iv_yficon.startAnimation(ta);

//        iv_yficon1.startAnimation(ta1);

        if (!plVideoView.isPlaying())
            plVideoView.start();
    }
    public void pausePlay(int index){
        View itemView = rv_home.getChildAt(index);
        PLVideoTextureView  plVideoView = itemView.findViewById(R.id.PLvv_play);
        if (plVideoView.isPlaying()){
            plVideoView.pause();
//            plVideoView.stopPlayback();
        }
    }
    public void pausePlay(){
        View itemView = rv_home.getChildAt(0);
        if (itemView == null) return;
        PLVideoTextureView  plVideoView = itemView.findViewById(R.id.PLvv_play);
        ImageView iv_pause = itemView.findViewById(R.id.iv_pause);
        iv_pause.setVisibility(View.VISIBLE);
        if (plVideoView.isPlaying()){
            plVideoView.pause();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pausePlay();
    }

    @Override
    public void onStart() {
        super.onStart();
        UserInfo userInfo = ((MyApplication)getActivity().getApplication()).getUserInfo();
        if (userInfo != null) {

            if (user_id != userInfo.loginId){
                user_id = userInfo.loginId;
                if (adapter != null){
                    adapter.setUser_id(user_id);
                }

            }
        }



        Log.d("666","onStart");
        if (!isShowFragment) return;
        View itemView = rv_home.getChildAt(0);
        if (itemView == null)
            return;
//        PLVideoTextureView  plVideoView = itemView.findViewById(R.id.PLvv_play);
//        if (!plVideoView.isPlaying())


            startPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("666","onStop");
        pausePlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        View itemView = rv_home.getChildAt(0);
        PLVideoTextureView  plVideoView = itemView.findViewById(R.id.PLvv_play);
        plVideoView.stopPlayback();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            // fragment隐藏了
            pausePlay();
            isShowFragment = false;
            Log.d("666","fragment隐藏了");
        }else{
            // fragment显示了
            startPlay();
            isShowFragment = true;
            Log.d("666","fragment显示了");
        }
    }
}
