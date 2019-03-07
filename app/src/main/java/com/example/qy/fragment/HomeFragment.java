package com.example.qy.fragment;

import android.content.Context;
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
import android.widget.ImageView;

import com.example.qy.R;
import com.example.qy.adapter.HomeAdapter;
import com.example.qy.ui.ViewPagerLayoutManager;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.jaeger.library.StatusBarUtil;
import com.pili.pldroid.player.widget.PLVideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class HomeFragment extends Fragment {
    private String TAG = "HomeFragment";
    private RecyclerView rv_home;
    private List<String> list;
    private ImageView iv_home,iv_search;
    private ViewPagerLayoutManager viewPagerLayoutManager;

    private boolean isShowFragment;

    private int index;  // 当前的视频id
    private int destructionIndex = 2;   // 需要销毁的视频id

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x333){

                viewPagerLayoutManager = new ViewPagerLayoutManager(getActivity(),OrientationHelper.VERTICAL);
                rv_home.setLayoutManager(viewPagerLayoutManager);
                HomeAdapter adapter = new HomeAdapter(getActivity(),list);
                rv_home.setAdapter(adapter);


                adapter.setOnItemClickListener(postion->{

                        View itemView = rv_home.getChildAt(0);
                        PLVideoView plVideoView = itemView.findViewById(R.id.PLvv_play);
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
                        index = position;
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
//        iv_home = getActivity().findViewById(R.id.iv_home);
//        iv_search = getActivity().findViewById(R.id.iv_search);
//        iv_home.setBackgroundResource(R.mipmap.ic_home);
//        iv_search.setBackgroundResource(R.mipmap.ic_search);
        rv_home = getActivity().findViewById(R.id.rv_home);

        list = new ArrayList<>();
        HttpUtils.sendOkHttpRequest(HttpQYUtils.getVideos(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                try {
                    JSONObject object = new JSONObject(responseText);
                    JSONArray jsonArray = object.getJSONArray("data");
                    for(int i = jsonArray.length() -1 ; i > 0;i --){
                        list.add("http://192.168.10.6:8080/QianYi/views/videos/" + jsonArray.getString(i));
//                        Log.d("777","jsonArray == "+"http://192.168.10.6:8080/QianYi/views/videos/"+jsonArray.getString(i));
                    }
                    Message msg = new Message();
                    msg.what = 0x333;
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    public void startPlay(){
        View itemView = rv_home.getChildAt(0);
        PLVideoView plVideoView = itemView.findViewById(R.id.PLvv_play);

//        int aspectRatio = plVideoView.getDisplayAspectRatio();
//        int height = plVideoView.getHeight();
//        int width = plVideoView.getWidth();
//        Log.d("HomeAdapter","高宽比 == "+aspectRatio+",高 = "+height+"，宽 = "+width);

        if (!plVideoView.isPlaying())
            plVideoView.start();
    }
    public void pausePlay(int index){
        View itemView = rv_home.getChildAt(index);
        PLVideoView plVideoView = itemView.findViewById(R.id.PLvv_play);
        if (plVideoView.isPlaying()){

//            if (index > destructionIndex){
//                destructionIndex += 2;
//                plVideoView.pause();
//
//            }else{
                plVideoView.stopPlayback();
//            }
        }

    }
    public void pausePlay(){
        View itemView = rv_home.getChildAt(0);
        if (itemView == null) return;
        PLVideoView plVideoView = itemView.findViewById(R.id.PLvv_play);

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
        Log.d("666","onStart");
        if (!isShowFragment) return;
        View itemView = rv_home.getChildAt(0);
        if (itemView == null)
            return;
        PLVideoView plVideoView = itemView.findViewById(R.id.PLvv_play);
        if (!plVideoView.isPlaying())
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
        PLVideoView plVideoView = itemView.findViewById(R.id.PLvv_play);
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
