package nwsuaf.com.exam.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
import nwsuaf.com.exam.adapter.FragmentAdapter;
import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.callback.ProblemCallback;
import nwsuaf.com.exam.customview.CustomDialog;
import nwsuaf.com.exam.entity.netmodel.Answer;
import nwsuaf.com.exam.entity.netmodel.NetObject_Answer;
import nwsuaf.com.exam.entity.netmodel.NetObject_Problem;
import nwsuaf.com.exam.entity.netmodel.NetObject_ProblemData;
import nwsuaf.com.exam.entity.netmodel.ProblemData;
import nwsuaf.com.exam.fragment.ExamDetailFragment;
import nwsuaf.com.exam.util.FileUtils;
import nwsuaf.com.exam.util.GetUserInfo;
import nwsuaf.com.exam.util.KeyBoardUtils;
import nwsuaf.com.exam.util.OutputUtil;
import nwsuaf.com.exam.util.TimeUtils;
import nwsuaf.com.exam.util.WebToolUtils;
import okhttp3.Call;

public class ExamFinalActivity extends BaseActivity {
    private ViewPager mViewPager;
    //private ExamAdapter mAdapter;
    private FragmentAdapter mAdapter;
    private List<ProblemData> mData;
    //遮罩层
    private RelativeLayout mLayoutLoading;

    private List<Answer> mAnswer;
    private List<Fragment> mFragments;
    private String[] mImgSource = new String[]{
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490091912314&di=043ca0ff0cdf5bfce360f287be22a538&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D3686266434%2C1600710035%26fm%3D214%26gp%3D0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490091910387&di=666ba858a654baa53d060f063ad65b9f&imgtype=0&src=http%3A%2F%2Fwww.benbenla.cn%2Fimages%2F20130107%2Fbenbenla-09c.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490091910387&di=7cccb906d6fa0d26776896c1c7d1f7eb&imgtype=0&src=http%3A%2F%2Fwww.benbenla.cn%2Fimages%2F20110910%2Fbenbenla-06c.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490091910387&di=04960dad8aa3b028c531a51ba3f47afc&imgtype=0&src=http%3A%2F%2Fimg.wallpaperlist.com%2Fuploads%2Fwallpaper%2Ffiles%2Fora%2Forange-lake-shore-in-sunset-wallpaper-1366x768-5346ca4997dfa.jpg",
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
                CustomDialog.Builder(ExamFinalActivity.this);
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
        setRightText("提交");
        mData = new ArrayList<>();
        mAnswer = new ArrayList<>();
        mFragments = new ArrayList<>();
        getProblemDate();
        //mAdapter = new ExamAdapter(ExamFinalActivity.this , mData ,mAnswer);
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
    }

    /**
     * 联网获取题目
     */
    private void getProblemDate() {
        String url = new StringBuffer(AppConstants.LOCAL_HOST)
                .append("/getProblemData").toString();
        OkHttpUtils
                .get()
                .url(url)
                .addParams("classname", GetUserInfo.getClass_name())
                .build()
                .execute(new ProblemCallback() {
                    @Override
                    public void onResponse(NetObject_ProblemData response, int id) {
                        NetObject_ProblemData res = response;
                        if (res.getCode().equals(AppConstants.SUCCESS_GETPROBLEM)) {
                            mData.clear();
                            mData.addAll(res.getData());
                            create(mData.size());
                            setTitle(TimeUtils.formatTime(res.getTime()));
                            mAdapter.notifyDataSetChanged();
                            onLoading(false);
                            startCountDownTimer(res.getTime());

                            //备份数据到本地
                            new OutputUtil<NetObject_ProblemData>()
                                    .writObjectIntoSDcard(AppConstants.LOCAL_DATA_BAK, res);
                        } else {
                            Toast.makeText(ExamFinalActivity.this, res.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 开始倒计时
     *
     * @param time
     */
    private void startCountDownTimer(long time) {
        CountDownTimer timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setTitle(TimeUtils.formatTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                showConfirmDialog();
            }
        };
        timer.start();
    }

    /**
     * 创建答案和Fragment
     *
     * @param size
     */
    private void create(int size) {
        for (int i = 0; i < size; i++) {
            Answer item = new Answer();
            mAnswer.add(item);
        }
        for (int i = 0; i < size; i++) {
            ExamDetailFragment fragment = new ExamDetailFragment();
            fragment.setData(mData.get(i));
            mFragments.add(fragment);
        }
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_final_problem);
        mLayoutLoading = (RelativeLayout) findViewById(R.id.rl_loading);
        onLoading(true);
    }

    /**
     * 显示加载UI
     *
     * @param isLoading
     */
    public void onLoading(boolean isLoading) {
        if (isLoading) {
            mLayoutLoading.setVisibility(View.VISIBLE);
            showProgressDialog(ExamFinalActivity.this);
        } else {
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
                if (saveAnswerToLocal()) {
                    result = "缓存成功";
                } else {
                    result = "缓存失败";
                }
                sentAnswerToNet();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result.equals("缓存成功")) {
                Toast.makeText(ExamFinalActivity.this, result, Toast.LENGTH_LONG).show();
/*                FileUtils.delFile("examcache.out");
                finish();*/
            } else {
                Toast.makeText(ExamFinalActivity.this, result, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 提交答案到服务器
     */
    private void sentAnswerToNet() {
        String url = new StringBuffer(AppConstants.LOCAL_HOST)
                .append("/sentAnswerToNet").toString();
        File sdCardDir = Environment.getExternalStorageDirectory();//获取sd卡目录
        File sdFile = new File(sdCardDir, AppConstants.LOCAL_ANSWER_BAK);
        if (FileUtils.isFileExist(AppConstants.LOCAL_ANSWER_BAK)) {
            OkHttpUtils
                    .postFile()
                    .url(url)
                    .file(sdFile)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }
                        @Override
                        public void onResponse(String response, int id) {
                            Toast.makeText(ExamFinalActivity.this, response, Toast.LENGTH_SHORT).show();
                            FileUtils.delFile("examcache.out");
                            finish();
                        }
                    });
        } else {
            Toast.makeText(ExamFinalActivity.this, "答案为空，提交失败！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 备份答案到本地
     */
    private boolean saveAnswerToLocal() {
        for (Fragment fragment : mFragments) {
            mAnswer.add(((ExamDetailFragment) fragment).getAnswer());
        }
        NetObject_Answer netAnswer = new NetObject_Answer();
        netAnswer.setId(GetUserInfo.getPeo_id());
        netAnswer.setData(mAnswer);
        //备份答案到本地
        return new OutputUtil<NetObject_Answer>()
                .writObjectIntoSDcard(AppConstants.LOCAL_ANSWER_BAK, netAnswer);
    }
}
