package com.example.qy.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.activity.fragment.search.adapter.CommodityAdapter;
import com.example.qy.adapter.ShoppingCartAdapter;
import com.example.qy.utils.ToastUtils;
import com.example.qy.whs.BaseActivity;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

public class ShoppingCartActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView rc_shopping_cart;
    private RecyclerView rc_guess;
    private RecyclerTouchListener onTouchListener;
    private boolean isComplete = false;

    private LinearLayout li_settlement;
    private TextView tv_management;
    private TextView tv_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        init("购物车");

        rc_shopping_cart = findViewById(R.id.rc_shopping_cart);
        rc_shopping_cart.setLayoutManager(new LinearLayoutManager(this));
        rc_shopping_cart.setAdapter(new ShoppingCartAdapter(this));

        onTouchListener = new RecyclerTouchListener(this, rc_shopping_cart);

        onTouchListener.setSwipeOptionViews(R.id.delete)
                .setSwipeable(R.id.rowFG, R.id.rowBG, (viewID,position)-> {

                    if (viewID == R.id.delete) {
                        // Do something
                        ToastUtils.showShort(ShoppingCartActivity.this,"delete "+position);
                    }

                });

        tv_management = findViewById(R.id.tv_management);
        tv_management.setOnClickListener(this);

        rc_guess = findViewById(R.id.rc_guess);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        rc_guess.setLayoutManager(manager);
        rc_guess.setAdapter(new CommodityAdapter(this));


        rc_guess.setNestedScrollingEnabled(false);
        rc_shopping_cart.setNestedScrollingEnabled(false);


        rc_guess.setHasFixedSize(true);
        rc_shopping_cart.setHasFixedSize(true);

        li_settlement = findViewById(R.id.li_settlement);
        tv_delete = findViewById(R.id.tv_delete);
    }

    @Override
    protected void onResume() {
        super.onResume();
        rc_shopping_cart.addOnItemTouchListener(onTouchListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        rc_shopping_cart.removeOnItemTouchListener(onTouchListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_management:
                if (isComplete){
                    isComplete = false;
                    tv_management.setText("管理");
                    li_settlement.setVisibility(View.VISIBLE);
                    tv_delete.setVisibility(View.GONE);
                }else{
                    isComplete = true;
                    tv_management.setText("完成");
                    tv_delete.setVisibility(View.VISIBLE);
                    li_settlement.setVisibility(View.GONE);
                }
                break;
        }
    }
}
