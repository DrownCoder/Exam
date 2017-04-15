package nwsuaf.com.exam.activity;

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
import nwsuaf.com.exam.adapter.StudentStateAdapter;
import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.callback.ClassDetailCallback;
import nwsuaf.com.exam.callback.ClassListCallback;
import nwsuaf.com.exam.entity.netmodel.ClassInfo;
import nwsuaf.com.exam.entity.netmodel.NetObject_ClassDetail;
import nwsuaf.com.exam.entity.netmodel.NetObject_ClassList;
import nwsuaf.com.exam.entity.netmodel.StudentState;
import nwsuaf.com.exam.util.GetUserInfo;

public class ClassDetailActivity extends BaseActivity {
    private ClassInfo mClassInfo;
    private SwipeRefreshLayout mSwipe;
    private RecyclerView mRecyclerView;
    private StudentStateAdapter mAdapter;
    private List<StudentState> mData;
    //遮罩层
    private RelativeLayout mLayoutLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);
        TopView();

        mClassInfo = (ClassInfo) getIntent().getSerializableExtra("classinfo");
        initViews();
        initDatas();
        initEvents();
    }
    /**
     * 显示加载UI
     * @param isLoading
     */
    public void onLoading(boolean isLoading){
        if(isLoading){
            mLayoutLoading.setVisibility(View.VISIBLE);
            showProgressDialog(ClassDetailActivity.this);
        }else{
            mLayoutLoading.setVisibility(View.GONE);
            dismissProgressDialog();
        }
    }
    private void initDatas() {
        mData = new ArrayList<>();
        mAdapter = new StudentStateAdapter(ClassDetailActivity.this, mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ClassDetailActivity.this));
        mRecyclerView.setAdapter(mAdapter);
        getClassDetail();
    }

    private void getClassDetail() {
        String url = new StringBuffer(AppConstants.LOCAL_HOST)
                .append("/getClassDetail").toString();
                /*.append("&stuid=")
                .append(URLDecoder.decode(tv_id_uid.getText().toString()))
                .append("&passwd=")
                .append(URLDecoder.decode(tv_id_passwd.getText().toString())).toString();*/
        OkHttpUtils
                .get()
                .url(url)
                .addParams("teacherid", GetUserInfo.getPeo_id())
                .build()
                .execute(new ClassDetailCallback(){
                    @Override
                    public void onResponse(NetObject_ClassDetail response, int id) {
                        NetObject_ClassDetail res = (NetObject_ClassDetail) response;
                        if (res.getCode().equals(AppConstants.SUCCESS_GETCLASSLIST)) {
                            mData.clear();
                            mData.addAll(res.getData());
                            mAdapter.notifyDataSetChanged();
                            onLoading(false);
                        }else{
                            Toast.makeText(ClassDetailActivity.this,res.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initEvents() {
        setLeftBack();
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                getClassDetail();
                mSwipe.setRefreshing(false);
            }
        });
    }

    private void initViews() {
        setTitle(mClassInfo.getClassname());
        setRightIcon(R.drawable.refresh);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.sw_refresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.rcy_myclass);
        mLayoutLoading = (RelativeLayout) findViewById(R.id.rl_loading);
        /**
         * --------------------------假数据-------------------------------------------------
         */
        /*StudentState item1 = new StudentState();
        item1.setId("2013013098");
        item1.setName("李达康");
        item1.setJoin(false);
        item1.setSubmit(false);
        StudentState item2 = new StudentState();
        item2.setId("2013013054");
        item2.setName("高玉良");
        item2.setJoin(true);
        item2.setSubmit(true);
        StudentState item3 = new StudentState();
        item3.setId("2013013100");
        item3.setName("张三");
        item3.setJoin(true);
        item3.setSubmit(false);
        mData = new ArrayList<>();
        mData.add(item1);
        mData.add(item2);
        mData.add(item3);*/
        /**
         * --------------------------假数据-------------------------------------------------
         */
    }
}
