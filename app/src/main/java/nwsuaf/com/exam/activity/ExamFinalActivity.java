package nwsuaf.com.exam.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
import nwsuaf.com.exam.adapter.ExamAdapter;
import nwsuaf.com.exam.entity.netmodel.ProblemData;

public class ExamFinalActivity extends BaseActivity {
    private ViewPager mViewPager;
    private ExamAdapter mAdapter;
    private List<ProblemData> mData;
    private TextView mTvTime;
    //遮罩层
    private RelativeLayout mLayoutLoading;


    private String[] mImgSource = new String[]{
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490091912314&di=043ca0ff0cdf5bfce360f287be22a538&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D3686266434%2C1600710035%26fm%3D214%26gp%3D0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490091910387&di=666ba858a654baa53d060f063ad65b9f&imgtype=0&src=http%3A%2F%2Fwww.benbenla.cn%2Fimages%2F20130107%2Fbenbenla-09c.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490091910387&di=7cccb906d6fa0d26776896c1c7d1f7eb&imgtype=0&src=http%3A%2F%2Fwww.benbenla.cn%2Fimages%2F20110910%2Fbenbenla-06c.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490091910387&di=04960dad8aa3b028c531a51ba3f47afc&imgtype=0&src=http%3A%2F%2Fimg.wallpaperlist.com%2Fuploads%2Fwallpaper%2Ffiles%2Fora%2Forange-lake-shore-in-sunset-wallpaper-1366x768-5346ca4997dfa.jpg"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_final);
        TopView();
        initView();
        initData();
    }

    private void initData() {
        setTitle("00:30:00");
        setRightText("提交");
        mData = new ArrayList<>();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i<20;i++){
                    ProblemData item1 = new ProblemData();
                    item1.setImgList(Arrays.asList(mImgSource));
                    mData.add(item1);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        onLoading(false);
                    }
                });
            }
        }, 2000);
        mAdapter = new ExamAdapter(ExamFinalActivity.this , mData);
        mViewPager.setAdapter(mAdapter);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_final_problem);
        mTvTime = (TextView) findViewById(R.id.tv_final_time);
        mLayoutLoading = (RelativeLayout) findViewById(R.id.rl_loading);
        onLoading(true);
    }

    public void onLoading(boolean isLoading){
        if(isLoading){
            mLayoutLoading.setVisibility(View.VISIBLE);
            showProgressDialog(ExamFinalActivity.this);
        }else{
            mLayoutLoading.setVisibility(View.GONE);
            dismissProgressDialog();
        }
    }
}
