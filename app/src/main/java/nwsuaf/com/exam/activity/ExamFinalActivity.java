package nwsuaf.com.exam.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
import nwsuaf.com.exam.adapter.FragmentAdapter;
import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.customview.CustomDialog;
import nwsuaf.com.exam.entity.netmodel.Answer;
import nwsuaf.com.exam.entity.netmodel.ProblemData;
import nwsuaf.com.exam.fragment.ExamDetailFragment;
import nwsuaf.com.exam.util.FileUtils;
import nwsuaf.com.exam.util.GetUserInfo;
import nwsuaf.com.exam.util.KeyBoardUtils;
import nwsuaf.com.exam.util.WebToolUtils;

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
                //确认提交？
                showConfirmDialog();
            }
        });
    }

    /**
     * 确认提交Dialog
     */
    private void showConfirmDialog() {
        CustomDialog.Builder customBuilder = new
                CustomDialog.Builder(this);
        customBuilder.setTitle("交卷")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //交卷
                        new SentToServerTask().execute();
                    }
                })
                .setMessage("确认提交试卷？");
        CustomDialog dialog = customBuilder.create();
        dialog.show();
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
     * 创建答案和Fragment
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

    /**
     * 显示加载UI
     * @param isLoading
     */
    public void onLoading(boolean isLoading){
        if(isLoading){
            mLayoutLoading.setVisibility(View.VISIBLE);
            showProgressDialog(ExamFinalActivity.this);
        }else{
            mLayoutLoading.setVisibility(View.GONE);
            dismissProgressDialog();
        }
    }

    private class SentToServerTask extends AsyncTask<String, Void, String> {
        String url;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ExamFinalActivity.this);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                url = params[0];
                for (Fragment fragment : mFragments) {
                    mAnswer.add(((ExamDetailFragment)fragment).getAnswer());
                }
                List<NameValuePair> datas = new ArrayList<NameValuePair>();
                datas.add(new BasicNameValuePair("stuid", GetUserInfo.getPeo_id()));

                result = WebToolUtils.HttpSentMessage(datas, url);
            } catch (Exception e) {
                e.printStackTrace();
                //Toast.makeText(ExamActivity.this,"服务器繁忙，请重新提交！", Toast.LENGTH_LONG).show();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result.equals(String.valueOf(AppConstants.INSERTSUCCESS))) {
                Toast.makeText(ExamFinalActivity.this, "上传成功！", Toast.LENGTH_LONG).show();
                FileUtils.delFile("examcache.out");
                finish();
            } else {
                Toast.makeText(ExamFinalActivity.this, "服务器繁忙，请稍后重试！", Toast.LENGTH_LONG).show();
            }
        }
    }
}
