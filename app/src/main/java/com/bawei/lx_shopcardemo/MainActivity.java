package com.bawei.lx_shopcardemo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView thirdRecyclerview;
    /**
     * 全选
     */
    private TextView thirdAllselect;
    /**
     * 总价：
     */
    private TextView thirdTotalprice;
    /**
     * 共0件商品
     */
    private TextView thirdTotalnum;
    /**
     * 去结算
     */
    private TextView thirdSubmit;
    private LinearLayout thirdPayLinear;
    private ThirdFragmentAdapter adapter;
    private LinearLayout mThirdPayLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        // 1 选中 2 未选中
        thirdAllselect.setTag(1);
        showData();
    }

    private void initView() {
        thirdRecyclerview = (RecyclerView) findViewById(R.id.third_recyclerview);
        thirdAllselect = (TextView) findViewById(R.id.third_allselect);
        thirdTotalprice = (TextView) findViewById(R.id.third_totalprice);
        thirdTotalnum = (TextView) findViewById(R.id.third_totalnum);
        thirdSubmit = (TextView) findViewById(R.id.third_submit);
        thirdPayLinear = (LinearLayout) findViewById(R.id.third_pay_linear);

        thirdAllselect.setOnClickListener(this);
        thirdTotalprice.setOnClickListener(this);
        thirdTotalnum.setOnClickListener(this);
        thirdSubmit.setOnClickListener(this);
    }

    //存放购物车中所有的商品
    private List<MyBean.OrderDataBean.CartlistBean> mAllOrderList = new ArrayList<>();

    private void showData() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new ThirdFragmentAdapter(this);
        thirdRecyclerview.setAdapter(adapter);
        ;
        thirdRecyclerview.setLayoutManager(linearLayoutManager);


        try {
            InputStream inputStream = getAssets().open("shop.json");
            String data = StringUtils.convertStreamToString(inputStream);
            Gson gson = new Gson();
            MyBean shopBean = gson.fromJson(data, MyBean.class);


            for (int i = 0; i < shopBean.getOrderData().size(); i++) {
                int length = shopBean.getOrderData().get(i).getCartlist().size();
                for (int j = 0; j < length; j++) {
                    mAllOrderList.add(shopBean.getOrderData().get(i).getCartlist().get(j));
                }
            }
            setFirstState(mAllOrderList);

            adapter.setData(mAllOrderList);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //删除数据回调
        adapter.setOnDeleteClickListener(new ThirdFragmentAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(View view, int position, int cartid) {


            }
        });


        //
        adapter.setOnRefershListener(new ThirdFragmentAdapter.OnRefershListener() {
            @Override
            public void onRefersh(boolean isSelect, List<MyBean.OrderDataBean.CartlistBean> list) {

                //标记底部 全选按钮
                if (isSelect) {
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_selected);
                    thirdAllselect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                } else {
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_unselected);
                    thirdAllselect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                }

                //总价
                float mTotlaPrice = 0f;
                int mTotalNum = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isSelect()) {
                        mTotlaPrice += list.get(i).getPrice() * list.get(i).getCount();
                        mTotalNum += list.get(i).getCount();
                    }
                }
                System.out.println("mTotlaPrice = " + mTotlaPrice);

                thirdTotalprice.setText("总价 : " + mTotlaPrice);

                thirdTotalnum.setText("共" + mTotalNum + "件商品");
            }
        });


    }

    /**
     * 标记第一条数据 isfirst 1 显示商户名称 2 隐藏
     *
     * @param list
     */
    public static void setFirstState(List<MyBean.OrderDataBean.CartlistBean> list) {

        if (list.size() > 0) {
            list.get(0).setIsFirst(1);
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).getShopId() == list.get(i - 1).getShopId()) {
                    list.get(i).setIsFirst(2);
                } else {
                    list.get(i).setIsFirst(1);
                }
            }
        }

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.third_allselect:
                // 全选
                int state = (Integer) thirdAllselect.getTag();

                adapter.setUnSelected(state);
                if (state == 1) {
                    thirdAllselect.setTag(2);
                } else {
                    thirdAllselect.setTag(1);
                }
                break;
            case R.id.third_totalprice:
                break;
            case R.id.third_totalnum:
                break;
            case R.id.third_submit:
                Toast.makeText(this, "没钱赶紧滚", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
