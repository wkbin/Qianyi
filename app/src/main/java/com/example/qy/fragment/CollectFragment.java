package com.example.qy.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qy.R;
import com.example.qy.adapter.CollectAdapter;
import com.example.qy.bean.Collect;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 07 日 下午 2:41
 * Description: 收藏页面
 */
public class CollectFragment extends Fragment {
    private List<Collect> list;
    private RecyclerView rv_collect;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rv_collect = getActivity().findViewById(R.id.rv_collect);

        initData();

        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),2);

        CollectAdapter adapter = new CollectAdapter(getActivity(),list);
        rv_collect.setLayoutManager(layoutManager);


        rv_collect.setAdapter(adapter);
    }

    private void initData(){
        list = new ArrayList<>();

        Collect c1 = new Collect();
        c1.imageUrl = "https://img2.woyaogexing.com/2019/03/08/1b578f4855a34c9da59d85175c84f0bc!1080x1920.jpeg";
        c1.like = "9999";
        c1.title = "柠檬（∩△∩）";
        list.add(c1);

        Collect c2 = new Collect();
        c2.imageUrl = "https://img2.woyaogexing.com/2019/03/08/6f2caef98b1f4691821ed590bc53a9e3!1080x1920.jpeg";
        c2.like = "9999";
        c2.title = "草莓味";
        list.add(c2);

        Collect c3 = new Collect();
        c3.imageUrl = "https://img2.woyaogexing.com/2019/03/07/949aa1d489034aae8bf28299bdcd97e6!1080x1920.jpeg";
        c3.like = "9999";
        c3.title = "该换壁纸啦";
        list.add(c3);

        Collect c4 = new Collect();
        c4.imageUrl = "https://img2.woyaogexing.com/2019/03/07/0238f337f2a942998f1191001cfa04a1!1080x1920.jpeg";
        c4.like = "9999";
        c4.title = "音乐";
        list.add(c4);

        Collect c5 = new Collect();
        c5.imageUrl = "https://img2.woyaogexing.com/2019/03/07/36d35ddc9cb94c0fa5afd841b6866be8!1080x1920.jpeg";
        c5.like = "9999";
        c5.title = "月色真美 風也溫柔";
        list.add(c5);

        Collect c6 = new Collect();
        c6.imageUrl = "https://img2.woyaogexing.com/2019/03/08/1b578f4855a34c9da59d85175c84f0bc!1080x1920.jpeg";
        c6.like = "9999";
        c6.title = "柠檬（∩△∩）";
        list.add(c6);


        Collect c7 = new Collect();
        c7.imageUrl = "https://img2.woyaogexing.com/2019/03/08/6f2caef98b1f4691821ed590bc53a9e3!1080x1920.jpeg";
        c7.like = "9999";
        c7.title = "草莓味";
        list.add(c7);

        Collect c8 = new Collect();
        c8.imageUrl = "https://img2.woyaogexing.com/2019/03/07/949aa1d489034aae8bf28299bdcd97e6!1080x1920.jpeg";
        c8.like = "9999";
        c8.title = "该换壁纸啦";
        list.add(c8);

        Collect c9 = new Collect();
        c9.imageUrl = "https://img2.woyaogexing.com/2019/03/07/0238f337f2a942998f1191001cfa04a1!1080x1920.jpeg";
        c9.like = "9999";
        c9.title = "音乐";
        list.add(c9);

        Collect c10 = new Collect();
        c10.imageUrl = "https://img2.woyaogexing.com/2019/03/07/36d35ddc9cb94c0fa5afd841b6866be8!1080x1920.jpeg";
        c10.like = "9999";
        c10.title = "月色真美 風也溫柔";
        list.add(c10);

        Collect c11 = new Collect();
        c11.imageUrl = "https://img2.woyaogexing.com/2019/03/08/1b578f4855a34c9da59d85175c84f0bc!1080x1920.jpeg";
        c11.like = "9999";
        c11.title = "柠檬（∩△∩）";
        list.add(c11);


        Collect c12 = new Collect();
        c12.imageUrl = "https://img2.woyaogexing.com/2019/03/08/6f2caef98b1f4691821ed590bc53a9e3!1080x1920.jpeg";
        c12.like = "9999";
        c12.title = "草莓味";
        list.add(c12);

        Collect c13 = new Collect();
        c13.imageUrl = "https://img2.woyaogexing.com/2019/03/07/949aa1d489034aae8bf28299bdcd97e6!1080x1920.jpeg";
        c13.like = "9999";
        c13.title = "该换壁纸啦";
        list.add(c13);

        Collect c14 = new Collect();
        c14.imageUrl = "https://img2.woyaogexing.com/2019/03/07/0238f337f2a942998f1191001cfa04a1!1080x1920.jpeg";
        c14.like = "9999";
        c14.title = "音乐";
        list.add(c14);

        Collect c15 = new Collect();
        c15.imageUrl = "https://img2.woyaogexing.com/2019/03/07/36d35ddc9cb94c0fa5afd841b6866be8!1080x1920.jpeg";
        c15.like = "9999";
        c15.title = "月色真美 風也溫柔";
        list.add(c15);



    }
}
