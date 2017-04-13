package nwsuaf.com.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
import nwsuaf.com.exam.adapter.MyClassAdapter;
import nwsuaf.com.exam.entity.netmodel.ClassInfo;

public class MyClassActivity extends BaseActivity {
    private SwipeRefreshLayout mSwipe;
    private RecyclerView mRecyclerView;
    private MyClassAdapter mAdapter;
    private List<ClassInfo> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class);
        TopView();
        setTitle("我的班级");
        initViews();
        initEvents();
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
                mAdapter.notifyDataSetChanged();
                mSwipe.setRefreshing(false);
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
    }

    private void initViews() {
        setRightIcon(R.drawable.refresh);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.sw_refresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.rcy_myclass);

/**
 * --------------------------假数据-------------------------------------------------
 */
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
        /**
         * --------------------------假数据-------------------------------------------------
         */
        mAdapter = new MyClassAdapter(MyClassActivity.this, mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyClassActivity.this));
        mRecyclerView.setAdapter(mAdapter);
    }
}
