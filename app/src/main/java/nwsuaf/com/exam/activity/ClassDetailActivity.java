package nwsuaf.com.exam.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
import nwsuaf.com.exam.adapter.StudentStateAdapter;
import nwsuaf.com.exam.entity.netmodel.ClassInfo;
import nwsuaf.com.exam.entity.netmodel.StudentState;

public class ClassDetailActivity extends BaseActivity {
    private ClassInfo mClassInfo;
    private SwipeRefreshLayout mSwipe;
    private RecyclerView mRecyclerView;
    private StudentStateAdapter mAdapter;
    private List<StudentState> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);
        TopView();

        mClassInfo = (ClassInfo) getIntent().getSerializableExtra("classinfo");
        initViews();
        initEvents();
    }

    private void initEvents() {
        setLeftBack();
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                mAdapter.notifyDataSetChanged();
                mSwipe.setRefreshing(false);
            }
        });
    }

    private void initViews() {
        setTitle(mClassInfo.getClassname());
        setRightIcon(R.drawable.refresh);
        mSwipe = (SwipeRefreshLayout) findViewById(R.id.sw_refresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.rcy_myclass);
        /**
         * --------------------------假数据-------------------------------------------------
         */
        StudentState item1 = new StudentState();
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
        mData.add(item3);
        /**
         * --------------------------假数据-------------------------------------------------
         */
        mAdapter = new StudentStateAdapter(ClassDetailActivity.this, mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ClassDetailActivity.this));
        mRecyclerView.setAdapter(mAdapter);
    }
}
