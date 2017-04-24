package nwsuaf.com.exam.activity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
import nwsuaf.com.exam.adapter.FragmentAdapter;
import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.callback.ProblemCallback;
import nwsuaf.com.exam.customview.CustomDialog;
import nwsuaf.com.exam.entity.netmodel.CustomResponse;
import nwsuaf.com.exam.entity.netmodel.FAnswer;
import nwsuaf.com.exam.entity.netmodel.NetObject_Answer;
import nwsuaf.com.exam.entity.netmodel.NetObject_ProblemData;
import nwsuaf.com.exam.entity.netmodel.ProblemData;
import nwsuaf.com.exam.fragment.ExamDetailFragment;
import nwsuaf.com.exam.service.FAnswerService;
import nwsuaf.com.exam.util.FileUtils;
import nwsuaf.com.exam.util.GetUserInfo;
import nwsuaf.com.exam.util.InputUtil;
import nwsuaf.com.exam.util.KeyBoardUtils;
import nwsuaf.com.exam.util.OutputUtil;
import nwsuaf.com.exam.util.SPUtils;
import nwsuaf.com.exam.util.TimeUtils;
import okhttp3.Call;

public class ExamFinalActivity extends BaseActivity {
    private ViewPager mViewPager;
    //private ExamAdapter mAdapter;
    private FragmentAdapter mAdapter;
    private List<ProblemData> mData;
    //遮罩层
    private RelativeLayout mLayoutLoading;

    private List<FAnswer> mFAnswer;
    private List<Fragment> mFragments;

    private NetObject_ProblemData localdata;
    private CountDownTimer mTimer;
    private long mCurrentTime;
    private final long default_time = 180000;
    private FAnswerService answerService;
    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_final);
        TopView();
        initView();
        initData();
        initEvent();

        saveAnswertoLocal();
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
                mTimer.cancel();
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
                        startCountDownTimer(mCurrentTime);
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mIsServiceDestoryed.set(true);
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
        mFragments = new ArrayList<>();
        mFAnswer = new ArrayList<>();
        answerService = new FAnswerService(ExamFinalActivity.this);
        //getProblemDate();
        new GetDataTask().execute();
        //mAdapter = new ExamAdapter(ExamFinalActivity.this , mData ,mFAnswer);
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
    }

    /**
     * 联网获取题目
     */
    private void getProblemDate() {
        if (CheckNetData()) {
            new GetDataTask().execute();
        } else {
            getDataFromNet();
        }
    }

    /**
     * 检查本地是否存在联网获得的数据
     * ----程序异常结束二次进入->存在
     *
     * @return
     */
    private boolean CheckNetData() {
        localdata = new InputUtil<NetObject_ProblemData>().readObjectFromSdCard(AppConstants.LOCAL_DATA_BAK);
        if (localdata == null) {
            Log.e("checknetdata", "false");
            return false;
        }
        return true;
    }

    /**
     * 开始倒计时
     *
     * @param time
     */
    private void startCountDownTimer(long time) {
        mTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mCurrentTime = millisUntilFinished;
                setTitle(TimeUtils.formatTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                showConfirmDialog();
            }
        };
        mTimer.start();
    }

    /**
     * 创建答案和Fragment
     *
     * @param size
     */
    private void create(int size) {
        CreateAnswers(size);

        for (int i = 0; i < size; i++) {
            ExamDetailFragment fragment = new ExamDetailFragment();
            fragment.setData(mData.get(i));
            fragment.setAnswer(mFAnswer.get(i));
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
            showProgressDialog(ExamFinalActivity.this, "数据加载中……");
        } else {
            mLayoutLoading.setVisibility(View.GONE);
            dismissProgressDialog();
        }
    }

    private class SentToServerTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mTimer.cancel();
            showProgressDialog(ExamFinalActivity.this, "提交中……");
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                if (saveAnswerToLocal()) {
                    result = "答案保存成功";
                    sentAnswerToNet();
                } else {
                    result = "答案保存失败";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equals("答案保存成功")) {
                Toast.makeText(ExamFinalActivity.this, result, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ExamFinalActivity.this, result, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 获取数据
     * -本地
     * -联网
     */
    private class GetDataTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            if (CheckNetData()) {
                mData.clear();
                mData.addAll(localdata.getData());
                create(mData.size());
                mCurrentTime = Long.valueOf(String.valueOf(SPUtils.get(ExamFinalActivity.this
                        , "lasttime", default_time)));
            } else {
                getDataFromNet();
                return "NET";
            }
            return "LOCAL";
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("type", s);
            if(s.equals("LOCAL")){
                mAdapter.notifyDataSetChanged();
                setTitle(TimeUtils.formatTime(mCurrentTime));
                onLoading(false);
                startCountDownTimer(mCurrentTime);
            }
        }
    }

    /**
     * 联网获取数据
     */
    private void getDataFromNet() {
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
     * 提交答案到服务器
     */
    private void sentAnswerToNet() {
        String url = new StringBuffer(AppConstants.LOCAL_HOST)
                .append("/sentAnswerToNet").toString();
        File sdCardDir = Environment.getExternalStorageDirectory();//获取sd卡目录
        File sdFile = new File(sdCardDir, AppConstants.LOCAL_ANSWER_BAK);
        if (sdFile.exists()) {
            //提交文件
            /*OkHttpUtils
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
                            dismissProgressDialog();
                            finish();
                        }
                    });*/
            //暂时先用GET测试

            OkHttpUtils
                    .get()
                    .url(url)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(final String response, int id) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dismissProgressDialog();
                                    CustomResponse res = new Gson().fromJson(response, CustomResponse.class);
                                    if (res.getCode().equals(AppConstants.SUCCESS_SENTANSWER)) {
                                        ToastMsg(ExamFinalActivity.this, res.getMsg());
                                        answerService.deleteAll();
                                        FileUtils.delFile(AppConstants.LOCAL_DATA_BAK);
                                        finish();
                                    } else {
                                        ToastMsg(ExamFinalActivity.this, res.getMsg());
                                    }
                                }
                            }, 10000);
                        }
                    });
        } else {
            Toast.makeText(ExamFinalActivity.this, "答案为空，提交失败！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化Answer
     */
    private void CreateAnswers(int size) {
        if (answerService.getGroupData().size() <= 0 || !CheckNetData()) {
            if (mFAnswer.size() <= 0) {
                for (int i = 0; i < size; i++) {
                    FAnswer item = new FAnswer();
                    mFAnswer.add(item);
                }
            }
        } else {
            //如果是崩溃重新进入情况
            mFAnswer = answerService.getGroupData();
            Log.i("size", "" + answerService.getGroupData().size());
        }
    }

    /**
     * 备份答案到本地
     */
    private boolean saveAnswerToLocal() {
        getAnswer();
        NetObject_Answer netAnswer = new NetObject_Answer();
        netAnswer.setId(GetUserInfo.getPeo_id());
        netAnswer.setData(mFAnswer);
        //备份答案到本地
        return new OutputUtil<NetObject_Answer>()
                .writObjectIntoSDcard(AppConstants.LOCAL_ANSWER_BAK, netAnswer);
    }

    private void getAnswer() {
        mFAnswer.clear();
        for (Fragment fragment : mFragments) {
            mFAnswer.add(((ExamDetailFragment) fragment).getAnswer());
        }
    }

    /*private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.i("Mcache", "执行");
            getAnswer();
            answerService.inserAnswer(mFAnswer);
            SPUtils.put(ExamFinalActivity.this, "lasttime", mCurrentTime);
            handler.postDelayed(this, 10000);
        }
    };*/

    public class ServiceWorker implements Runnable {
        @Override
        public void run() {
            while (!mIsServiceDestoryed.get()) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("Mcache", "执行");
                getAnswer();
                answerService.inserAnswer(mFAnswer);
                SPUtils.put(ExamFinalActivity.this, "lasttime", mCurrentTime);
            }
        }
    }

    public void saveAnswertoLocal() {
        //handler.postDelayed(runnable, 10000);
        new Thread(new ServiceWorker()).start();
    }

    @Override
    protected void onDestroy() {
        /*handler.removeCallbacks(runnable);
        runnable = null;
        handler = null;*/
        mIsServiceDestoryed.set(true);
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        super.onDestroy();
    }
}
