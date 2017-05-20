package nwsuaf.com.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
import nwsuaf.com.exam.adapter.MyClassAdapter;
import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.callback.ClassListCallback;
import nwsuaf.com.exam.entity.netmodel.ClassInfo;
import nwsuaf.com.exam.entity.netmodel.NetObject_ClassList;
import nwsuaf.com.exam.util.GetUserInfo;

public class MyClassActivity extends BaseActivity {
    private SwipeRefreshLayout mSwipe;
    private RecyclerView mRecyclerView;
    private MyClassAdapter mAdapter;
    private List<ClassInfo> mData;
    //遮罩层
    private RelativeLayout mLayoutLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class);
        TopView();
        setTitle("我的班级");
        initViews();
        initDatas();
        initEvents();
    }

    private void initDatas() {
        mData = new ArrayList<>();
        mAdapter = new MyClassAdapter(MyClassActivity.this, mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyClassActivity.this));
        mRecyclerView.setAdapter(mAdapter);
        getClassListInfo();
    }

    private void getClassListInfo() {
        String url = new StringBuffer(AppConstants.LOCAL_HOST)
                .append(AppConstants.WEBSERVER)
                .append("/read")
                .append("/group.do").toString();
                /*.append("&stuid=")
                .append(URLDecoder.decode(tv_id_uid.getText().toString()))
                .append("&passwd=")
                .append(URLDecoder.decode(tv_id_passwd.getText().toString())).toString();*/
        OkHttpUtils
                .get()
                .url(url)
                .addParams("username", GetUserInfo.getPeo_id())
                .build()
                .execute(new ClassListCallback(){
                    @Override
                    public void onResponse(NetObject_ClassList res, int id) {
                        if (res.getCode().equals(AppConstants.SUCCESS_GETCLASSLIST)) {
                            mData.clear();
                            mData.addAll(res.getData());
                            mAdapter.notifyDataSetChanged();
                            onLoading(false);
                            mSwipe.setRefreshing(false);
                        }else{
                            Toast.makeText(MyClassActivity.this,res.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    /**
     * 显示加载UI
     * @param isLoading
     */
    public void onLoading(boolean isLoading){
        if(isLoading){
            mLayoutLoading.setVisibility(View.VISIBLE);
            showProgressDialog(MyClassActivity.this,"数据加载中……");
        }else{
            mLayoutLoading.setVisibility(View.GONE);
            dismissProgressDialog();
        }
    }
    private void initEvents() {
        setLeftBack();
        setRightClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //右键点击刷新
            }
        });
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                getClassListInfo();
            }
        });

        mAdapter.setItemClickListener(new MyClassAdapter.ItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Intent intent = new Intent(MyClassActivity.this, ClassDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("classinfo",mData.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        setRightClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipe.setRefreshing(true);
                getClassListInfo();
            }
        });
    }

    private void initViews() {
        setRightIcon(R.drawable.refresh);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.sw_refresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.rcy_myclass);
        mLayoutLoading = (RelativeLayout) findViewById(R.id.rl_loading);
        onLoading(true);
/*
*//**
 * --------------------------假数据-------------------------------------------------
 *//*
        ClassInfo item1 = new ClassInfo();
        item1.setClassname("软件131班");
        item1.setOnline(30);
        item1.setSubmit(16);
        item1.setUnsubmit(14);
        ClassInfo item2 = new ClassInfo();
        item2.setClassname("软件132班");
        item2.setOnline(31);
        item2.setSubmit(12);
        item2.setUnsubmit(16);
        ClassInfo item3 = new ClassInfo();
        item3.setClassname("软件133班");
        item3.setOnline(29);
        item3.setSubmit(10);
        item3.setUnsubmit(19);
        mData = new ArrayList<>();
        mData.add(item1);
        mData.add(item2);
        mData.add(item3);
        *//**
         * --------------------------假数据-------------------------------------------------
         */
    }
}
