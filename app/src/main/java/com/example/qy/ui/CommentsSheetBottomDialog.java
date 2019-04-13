package com.example.qy.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.qy.R;
import com.example.qy.activity.LoginActivity;
import com.example.qy.adapter.CommentAdapter;
import com.example.qy.bean.Comment;
import com.example.qy.bean.SecondComment;
import com.example.qy.bean.UserInfo;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.utils.ToastUtils;
import com.example.qy.whs.MyApplication;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Author: 王克斌
 * Date: 2019 年 03 月 12 日 下午 9:33
 * Description: 评论对话框
 */
public class CommentsSheetBottomDialog extends BottomSheetDialogFragment {
    private TextView tv_input_comments;
    private TextView tv_title;
    private ImageView iv_close;
    private RecyclerView rc_first_comments;
    private int user_id;
    private String video_id;
    private List<Comment> list;
    public int total;
    private UserInfo userInfo;
    private CommentAdapter adapter;
    private LinearLayout li_wpl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //给dialog设置主题为透明背景 不然会有默认的白色背景
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomBottomSheetDialogTheme);
    }

    /**
     * 如果想要点击外部消失的话 重写此方法
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        //设置点击外部可消失
        dialog.setCanceledOnTouchOutside(true);
        //设置使软键盘弹出的时候dialog不会被顶起
        Window win = dialog.getWindow();
        WindowManager.LayoutParams params = win.getAttributes();
        win.setSoftInputMode(params.SOFT_INPUT_ADJUST_NOTHING);
        dialog.getWindow().setDimAmount(0f);// 去除遮罩
        //这里设置dialog的进出动画
        win.setWindowAnimations(R.style.DialogBottomAnim);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 在这里将view的高度设置为精确高度，即可屏蔽向上滑动不占全屏的手势。
        //如果不设置高度的话 会默认向上滑动时dialog覆盖全屏
        View view = inflater.inflate(R.layout.bottom_dialog_comments, container, false);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (getScreenHeight(getActivity())/2)+getScreenHeight(getActivity())/6));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        userInfo = ((MyApplication)getActivity().getApplication()).getUserInfo();
        user_id = getArguments().getInt("user_id");
        video_id = getArguments().getString("video_id");
        iv_close = view.findViewById(R.id.iv_close);
        tv_input_comments = view.findViewById(R.id.tv_input_comments);
        tv_title = view.findViewById(R.id.tv_title);
        li_wpl = view.findViewById(R.id.li_wpl);
        tv_input_comments.setOnClickListener(v-> {
                if (userInfo == null){
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                    return;
                }
                //弹出评论输入框
                InputDialog inputDialog = new InputDialog(getActivity());

                Window window = inputDialog.getWindow();
                WindowManager.LayoutParams params = window.getAttributes();
                //设置软键盘通常是可见的
                window.setSoftInputMode(params.SOFT_INPUT_STATE_VISIBLE);

                inputDialog.show();






                inputDialog.setOnClickListener(content -> {
                    if (TextUtils.isEmpty(content) || content.equals("")){
                        ToastUtils.showShort(getActivity(),"您还没有输入文字");
                        return;
                    }
                    HttpUtils.sendOkHttpRequest(HttpQYUtils.getInsertComments(user_id, Integer.parseInt(video_id), content), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            getActivity().runOnUiThread(()->{
                                ToastUtils.showShort(getContext(),"连接断开");
                            });
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseText = response.body().string();
                            try {
                                JSONObject object = new JSONObject(responseText);
                                String msg = object.getString("msg");

                                getActivity().runOnUiThread(()->{
                                    ToastUtils.showShort(getContext(),msg);
                                });
                                if (object.getBoolean("isSuc")){
                                    MyApplication application = (MyApplication) getActivity().getApplication();;
                                    int user_id = application.getUserInfo().loginId;
                                    Comment comment = new Comment();
                                    comment.id = object.getJSONObject("data").getInt("id");
                                    if (user_id == 0){
                                        return;
                                    }
                                    comment.fromUid = user_id;
                                    comment.icon = userInfo.icon;
                                    comment.name = userInfo.nickname;
                                    comment.videoId = Integer.parseInt(video_id);
                                    comment.content = content;
                                    comment.giveLike = false;
                                    getActivity().runOnUiThread(()->{
                                        tv_title.setText(total+1+" 条评论");
                                        if (list == null){
                                            list = new ArrayList<>();
                                        }
                                        list.add(0,comment);
                                        if (adapter == null){
                                            li_wpl.setVisibility(View.GONE);
                                            rc_first_comments.setVisibility(View.VISIBLE);
                                            adapter = new CommentAdapter(getActivity(),list);
                                            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                                            rc_first_comments.setLayoutManager(manager);
                                            rc_first_comments.setAdapter(adapter);
                                        }else{
                                            adapter.notifyDataSetChanged();
                                        }
                                        inputDialog.dismiss();
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                });
        });
        iv_close.setOnClickListener(v->{
            dismiss();
        });

        rc_first_comments = view.findViewById(R.id.rc_first_comments);

        HttpUtils.sendOkHttpRequest(HttpQYUtils.getCommentsContentWithReply(user_id,Integer.parseInt(video_id), 0), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                getActivity().runOnUiThread(()->{
//                    ToastUtils.showShort(getContext(),"连接断开");
//                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseText);
                    if (jsonObject.getBoolean("isSuc")){
                        list = new ArrayList<>();
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        total = dataObject.getInt("total");
                        JSONArray commentsArray = dataObject.getJSONArray("commentslist");
                        for (int i = 0 ; i < commentsArray.length() ; i++){
                            JSONObject commentObject = commentsArray.getJSONObject(i);
                            Comment comment = new Comment();
                            comment.id = commentObject.getInt("id");
                            comment.videoId = commentObject.getInt("videoId");
                            comment.content = commentObject.getString("content");
                            comment.giveLike = commentObject.getBoolean("giveLike");
                            comment.date = commentObject.getString("createdate");
                            comment.fromUid = commentObject.getInt("fromUid");
                            if(!commentObject.isNull("userInfo")){
                                comment.name = commentObject.getJSONObject("userInfo").getString("infoNickname");
                                comment.icon = commentObject.getJSONObject("userInfo").getString("infoIcon");
                            }
                            comment.likeCounts = commentObject.getInt("likeCounts");
                            if (!commentObject.isNull("reply")){
                                JSONObject replyObject = commentObject.getJSONObject("reply");
                                if (!replyObject.isNull("replyList")){
                                    JSONArray replyList = replyObject.getJSONArray("replyList");
                                    comment.secondList = new ArrayList<>();
                                    for (int j = 0 ; j < replyList.length() ; j++){
                                        JSONObject comment2Object = replyList.getJSONObject(j);
                                        JSONObject replyObject2 = comment2Object.getJSONObject("reply");
                                        SecondComment comment2 = new SecondComment();
//                                        comment2.giveLike = replyObject.getBoolean("giveLike");
                                        comment2.giveLike = false;
                                        comment2.comentsId = replyObject2.getInt("comentsId");
                                        comment2.id = replyObject2.getInt("id");
                                        if (replyObject2.isNull("toUid")) continue;
                                        comment2.toUid = replyObject2.getInt("toUid");
                                        comment2.content = replyObject2.getString("content");
                                        comment2.fromUid = replyObject2.getInt("fromUid");
                                        comment2.date = replyObject2.getString("createdate");
                                        comment2.likeCounts = replyObject2.getInt("likeCounts");
                                        comment2.type = replyObject2.getString("type");
                                        comment2.toName = comment2Object.getString("nickname");
                                        comment2.name = comment2Object.getJSONObject("replyUserInfo").getString("infoNickname");
                                        comment2.infoIcon = comment2Object.getJSONObject("replyUserInfo").getString("infoIcon");
                                        comment.secondList.add(comment2);
                                    }
                                }
                            }

                            list.add(comment);
                        }

                        getActivity().runOnUiThread(()->{
                            tv_title.setText(total+" 条评论");
                            if (list.size() == 0||total == 0){
                                li_wpl.setVisibility(View.VISIBLE);
                                rc_first_comments.setVisibility(View.GONE);
                            }else{
                                li_wpl.setVisibility(View.GONE);
                                rc_first_comments.setVisibility(View.VISIBLE);

                                adapter = new CommentAdapter(getActivity(),list);
                                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                                rc_first_comments.setLayoutManager(manager);
                                rc_first_comments.setAdapter(adapter);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * 得到屏幕的高
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }
}
