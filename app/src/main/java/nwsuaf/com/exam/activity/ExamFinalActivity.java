package nwsuaf.com.exam.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
import nwsuaf.com.exam.adapter.FragmentAdapter;
import nwsuaf.com.exam.entity.netmodel.Answer;
import nwsuaf.com.exam.entity.netmodel.ProblemData;
import nwsuaf.com.exam.fragment.ExamDetailFragment;
import nwsuaf.com.exam.util.KeyBoardUtils;

public class ExamFinalActivity extends BaseActivity {
    private ViewPager mViewPager;
    //private ExamAdapter mAdapter;
    private FragmentAdapter mAdapter;
    private List<ProblemData> mData;
    private TextView mTvTime;
    //遮罩层
    private RelativeLayout mLayoutLoading;

    private List<Answer> mAnswer;
    private List<Fragment> mFragments;
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
        initEvent();
    }

    private void initEvent() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                KeyBoardUtils.closeKeybord(ExamFinalActivity.this);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setRightClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initData() {
        setTitle("00:30:00");
        setRightText("提交");
        mData = new ArrayList<>();
        mAnswer = new ArrayList<>();
        mFragments = new ArrayList<>();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i = 0;i<20;i++){
                    ProblemData item1 = new ProblemData();
                    item1.setImgList(Arrays.asList(mImgSource));
                    mData.add(item1);
                }
                create(mData.size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        onLoading(false);
                    }
                });
            }
        }, 2000);
        //mAdapter = new ExamAdapter(ExamFinalActivity.this , mData ,mAnswer);
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
    }

    /**
     * 创建答案
     * @param size
     */
    private void create(int size) {
        for(int i = 0;i<size;i++) {
            Answer item = new Answer();
            mAnswer.add(item);
        }
        for(int i = 0;i<size;i++) {
            ExamDetailFragment fragment = new ExamDetailFragment();
            fragment.setData(mData.get(i));
            mFragments.add(fragment);
        }
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
