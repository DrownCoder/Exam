package nwsuaf.com.exam.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
import nwsuaf.com.exam.adapter.ExamTypeChangeAdapter;
import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.customview.CustomDialog;
import nwsuaf.com.exam.customview.ProblemListPopupWindow;
import nwsuaf.com.exam.entity.answer;
import nwsuaf.com.exam.entity.examdate;
import nwsuaf.com.exam.entity.lcerror;
import nwsuaf.com.exam.entity.lcproblem;
import nwsuaf.com.exam.entity.netmodel.ImageUrlList;
import nwsuaf.com.exam.entity.netmodel.NetObject_Problem;
import nwsuaf.com.exam.service.AnswerService;
import nwsuaf.com.exam.service.ErrorService;
import nwsuaf.com.exam.service.ExamDateService;
import nwsuaf.com.exam.service.ExamService;
import nwsuaf.com.exam.util.FileUtils;
import nwsuaf.com.exam.util.GetUserInfo;
import nwsuaf.com.exam.util.GsonRequest;
import nwsuaf.com.exam.util.InputUtil;
import nwsuaf.com.exam.util.MeasureUtil;
import nwsuaf.com.exam.util.OutputUtil;
import nwsuaf.com.exam.util.TimeUtils;
import nwsuaf.com.exam.util.VolleyUtil;
import nwsuaf.com.exam.util.WebToolUtils;

public class ExamActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, ProblemListPopupWindow.onProblemItemClickListener {
    private ImageView iv_id_leftbtn;
    private ViewPager viewPager;
    private ExamTypeChangeAdapter mAdapter;
    private List<lcproblem> datas;
    private List<ImageUrlList> imgdatas;
    private RelativeLayout rl_id_problemlist;
    private RelativeLayout rl_id_root;
    //当前进度
    private TextView tv_progress;
    private TextView tv_total;

    private TextView tv_id_righttext;

    private ProblemListPopupWindow problem_list;

    //当前题号 = id+1;
    private int progress = 1;
    //我的答案
    private String manswer = "";
    private lcproblem problem;
    //保存答案
    private List<answer> answers;
    //是否更改答案
    //private boolean isChanged = false;

    //展示模式
    private int SHOW_TYPE;

    //耗时
    private long starttime;
    private long endtime;
    private String duration;
    //做题记录
    private examdate itemdate;

    private AnswerService answerService;

    private RequestQueue queue;
    private boolean isNetWork;

    private NetObject_Problem localdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainexam);
        TopView();
        chooseShowType();
        initViews();
        initDatas();
        initSource();
        initEvents();

        saveAnswertoLocal();
        /*cache = new CacheAnswerTask();
        cache.execute();*/
    }

    /**
     * 确定展示模式
     */
    private void chooseShowType() {
        SHOW_TYPE = getIntent().getIntExtra("type", 1);
    }

    private void initEvents() {
        rl_id_problemlist.setOnClickListener(this);

        viewPager.setOnPageChangeListener(this);

        iv_id_leftbtn.setOnClickListener(this);
        switch (SHOW_TYPE) {
            case AppConstants.TYPE_MINEEXAM:
            case AppConstants.TYPE_SEE_ERRORLIST:
            case AppConstants.TYPE_SINGLE:
            case AppConstants.TYPE_MULTIPLE:
            case AppConstants.TYPE_JUDGE:
            case AppConstants.TYPE_PIC:
            case AppConstants.TYPE_EXAM:
                /**
                 * 交卷
                 */
                tv_id_righttext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //做题时间
                        endtime = TimeUtils.getCurrentTimeInLong();
                        duration = TimeUtils.getLongFromTimeToTime(starttime, endtime);
                        //交卷
                        if (isNetWork) {
                            String url = new StringBuffer(AppConstants.LOCAL_HOST).
                                    append(AppConstants.WEBSERVER).
                                    append("/servlet/StuScoreOperationServlet")
                                    .append("?operation=3").toString();
                            new SentToServerTask().execute(url);
                        } else {
                            new JudgeAnswerTask().execute();
                        }

                    }
                });
                break;
            default:
                tv_id_righttext.setVisibility(View.GONE);
                break;

        }


        CheckDATA(datas);


    }

    private class JudgeAnswerTask extends AsyncTask<String, Void, String> {
        CustomDialog dialog;
        String time;

        @Override
        protected String doInBackground(String... strings) {
            int rightnum = 0;
            List<lcerror> errors = new ArrayList<>();
            time = TimeUtils.getCurrentTimeInID();
            for (answer item : answers) {
                //Log.e("answer",item.getAnswer()+(item.getIstrue()?"ture":"false"));
                if (!(item.getIsnull() == 1 ? true : false) && (item.getIstrue() == 1 ? true : false)) {
                    rightnum++;
                } else {
                    lcerror erroritem = new lcerror(null, new Long(item.getNum()), time);

                    errors.add(erroritem);
                }
            }
            if (errors.size() > 0) {
                //插入错题
                ErrorService errorService = new ErrorService(ExamActivity.this);
                errorService.addErrorListToDB(errors);
            }


            float rs = (float) rightnum / (float) answers.size();
            String result = String.valueOf((int) (rs * 100));


            //插入做题记录
            itemdate.setDate(TimeUtils.getCurrentTimeInID());
            itemdate.setTotalnum(datas.size());
            itemdate.setRight(rightnum);
            itemdate.setError(datas.size() - rightnum);
            itemdate.setIspass(rs >= 0.6 ? 1 : 0);
            itemdate.setTime(duration);
            ExamDateService dateService = new ExamDateService(ExamActivity.this);
            dateService.addExamDateToDB(itemdate);
            return result;
        }

        @Override
        protected void onPreExecute() {
            //显示进度框
            CustomDialog.Builder customBuilder = new
                    CustomDialog.Builder(ExamActivity.this);
            customBuilder.setTitle("提交中")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setContentView(LayoutInflater.from(ExamActivity.this).inflate(R.layout.dialog_progress, null, false));
            dialog = customBuilder.create();
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            answerService.deleteAll();
            dialog.dismiss();
            Intent intent = new Intent(ExamActivity.this, ResultActivity.class);
            intent.putExtra("result", s);
            intent.putExtra("time", time);
            intent.putExtra("duration", duration);
            intent.putExtra("type", SHOW_TYPE);
            startActivity(intent);
            finish();
        }
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        itemdate = new examdate();

        starttime = TimeUtils.getCurrentTimeInLong();
        ExamService problemService = new ExamService(this);
        ErrorService errorService = new ErrorService(this);
        answerService = new AnswerService(ExamActivity.this);

        switch (SHOW_TYPE) {
            case AppConstants.TYPE_MINEEXAM://自测
                datas = problemService.getTotalData();
                CreateAnswers();
                break;
            case AppConstants.TYPE_SEE_ERROR://查看错题
                String time = getIntent().getStringExtra("time");
                datas = errorService.getErrorData(time);
                break;
            case AppConstants.TYPE_SEE_EXAM://查看试卷
                datas = problemService.getTotalData();
                break;
            case AppConstants.TYPE_SEE_ERRORLIST://查看所有错题
                datas = errorService.getTotalData();
                CreateAnswers();
                break;
            case AppConstants.TYPE_SEE_EXAM_SINGLE://查看单选题
                datas = problemService.getTypeGroupData(AppConstants.PROBLEM_TYPE_SINGLE);
                break;
            case AppConstants.TYPE_SEE_EXAM_MULTI://查看多选题
                datas = problemService.getTypeGroupData(AppConstants.PROBLEM_TYPE_MULTI);
                break;
            case AppConstants.TYPE_SEE_EXAM_JUDGE://查看判断题
                datas = problemService.getTypeGroupData(AppConstants.PROBLEM_TYPE_JUDGE);
                break;
            case AppConstants.TYPE_SEE_EXAM_PIC://查看图片判断题
                datas = problemService.getTypeGroupData(AppConstants.PROBLEM_TYPE_PIC);
                break;
            case AppConstants.TYPE_SINGLE://单选题
                datas = problemService.getTypeGroupData(AppConstants.PROBLEM_TYPE_SINGLE);
                CreateAnswers();
                break;
            case AppConstants.TYPE_MULTIPLE://多选题
                datas = problemService.getTypeGroupData(AppConstants.PROBLEM_TYPE_MULTI);
                CreateAnswers();
                break;
            case AppConstants.TYPE_JUDGE://判断题
                datas = problemService.getTypeGroupData(AppConstants.PROBLEM_TYPE_JUDGE);
                CreateAnswers();
                break;
            case AppConstants.TYPE_PIC://图片选择题
                datas = problemService.getTypeGroupData(AppConstants.PROBLEM_TYPE_PIC);
                CreateAnswers();
                break;
            case AppConstants.TYPE_EXAM://网络考试
                isNetWork = true;
                if (!CheckNetData()) {
                    datas = new ArrayList<>();
                    getNetProblems();
                }else{
                    datas = localdata.getData();
                    imgdatas = localdata.getImg();
                    CreateAnswers();
                }
                break;
        }

        resetCurData(0);
    }

    /**
     * 检查本地是否存在联网获得的数据
     * ----程序异常结束二次进入->存在
     *
     * @return
     */
    private boolean CheckNetData() {
        localdata = new InputUtil<NetObject_Problem>().readObjectFromSdCard("examcache.out");
        if (localdata == null) {
            Log.e("checknetdata", "false");
            return false;
        }
        return true;
    }

    /**
     * 联网获取题目
     *
     * @return
     */
    private List<lcproblem> getNetProblems() {
        String url = new StringBuffer(AppConstants.LOCAL_HOST).append(AppConstants.WEBSERVER)
                .append("/servlet/GetProblemInfoServlet").toString();
        //String url = "http://192.168.139.1:8090/WineServer/GetPeoServlet";
        queue = VolleyUtil.getRequestQueue();
        GsonRequest<NetObject_Problem> gsonRequest = new GsonRequest<NetObject_Problem>(
                url, NetObject_Problem.class,
                new Response.Listener<NetObject_Problem>() {

                    @Override
                    public void onResponse(NetObject_Problem data) {
                        //data_tag = data.getData();
                        for (lcproblem item : data.getData()) {
                            Log.e("rightans", item.getAnswer());
                        }
                        datas = data.getData();
                        imgdatas = data.getImg();
                        CreateAnswers();
                        tv_total.setText(String.valueOf(datas.size()));
                        mAdapter = new ExamTypeChangeAdapter(answers, imgdatas, datas, ExamActivity.this, SHOW_TYPE);
                        viewPager.setAdapter(mAdapter);

                        //缓存数据到本地
                        new OutputUtil<NetObject_Problem>().writObjectIntoSDcard("examcache.out", data);
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(gsonRequest);
        return null;
    }

    private void CheckDATA(List<lcproblem> datas) {
        if (isNetWork) return;
        if (datas == null || datas.size() == 0) {
            Intent intent = new Intent(this, NullActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * 初始化Answer
     */
    private void CreateAnswers() {
        if (answerService.getGroupData().size() <= 0||!CheckNetData()) {
            initAnswers();
        } else {
            //如果是崩溃重新进入情况
            answers = answerService.getGroupData();
        }
    }

    private void initAnswers() {
        answers = new ArrayList<answer>();

        //初始防止adapter预加载为null
        for (int i = 0; i < datas.size(); i++) {
            answer answer = new answer();
            answer.setNum((long) (i + 1));
            answer.setIsnull(1);
            answers.add(answer);
        }
    }


    private void initSource() {
        iv_id_leftbtn.setImageResource(R.drawable.left);
        mAdapter = new ExamTypeChangeAdapter(answers, imgdatas, datas, this, SHOW_TYPE);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(0);
        tv_id_righttext.setText(R.string.tv_postanswer);

        tv_progress.setText(String.valueOf(progress));
        tv_total.setText(String.valueOf(datas.size()));
    }

    private void initViews() {
        iv_id_leftbtn = (ImageView) findViewById(R.id.iv_id_leftbtn);
        viewPager = (ViewPager) findViewById(R.id.id_vp_problem);
        rl_id_problemlist = (RelativeLayout) findViewById(R.id.rl_id_problemlist);
        rl_id_root = (RelativeLayout) findViewById(R.id.rl_id_root);
        tv_id_righttext = (TextView) findViewById(R.id.tv_id_righttext);

        tv_progress = (TextView) findViewById(R.id.tv_id_progress);
        tv_total = (TextView) findViewById(R.id.tv_id_total);
        iv_id_leftbtn = (ImageView) findViewById(R.id.iv_id_leftbtn);

    }


    /**
     * 底部栏消失，显示歌单
     */
    private void showProblemList() {
        //底部栏消失动画
        int height = MeasureUtil.dp2px(55, this);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, rl_id_problemlist.getX(), rl_id_problemlist.getX() + height);
        translateAnimation.setDuration(500);
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //show song list
                showProblemListDialog();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rl_id_problemlist.startAnimation(translateAnimation);

    }

    //底部栏显示动画
    private void showBottomLayout() {
        int height = MeasureUtil.dp2px(55, this);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, rl_id_problemlist.getX() + height, rl_id_problemlist.getX());
        translateAnimation.setDuration(1000);
        translateAnimation.setFillAfter(true);
        rl_id_problemlist.startAnimation(translateAnimation);
    }

    //显示歌单
    private void showProblemListDialog() {
        problem_list = new ProblemListPopupWindow(this, progress - 1, datas.size());
        problem_list.setCurrentIndex(progress - 1);
        problem_list.showAtLocation(rl_id_problemlist, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        problem_list.setOnProblemItemClickListener(this);

        problem_list.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                showBottomLayout();
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_id_problemlist:
                showProblemList();
                break;
            case R.id.ll_id_awa:
                judgeAnswer(1);
                break;
            case R.id.iv_id_leftbtn:
                finish();
                break;
        }
    }

    /**
     * 判断答案
     *
     * @param i
     */
    private void judgeAnswer(int i) {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //saveAnswer();
        //mAdapter.notifyDataSetChanged();
        mAdapter.multans = new int[]{0, 0, 0, 0, 0};
        String curanswer = answers.get(position).getAnswer() == null ? "" : answers.get(position).getAnswer();
        Log.e("ans", curanswer);
        resetArray(curanswer);
        Log.e("position", String.valueOf(position));
/*        for(int i =0;i<5;i++){
            Log.e("ans",mAdapter.)
        }*/
        resetCurData(position);
    }

    private void resetArray(String curanswer) {
        for (int i = 0; i < curanswer.length(); i++) {
            String s = String.valueOf(curanswer.charAt(i));
            switch (s) {
                case "A":
                    mAdapter.multans[0] = 1;
                    break;
                case "B":
                    mAdapter.multans[1] = 1;
                    break;
                case "C":
                    mAdapter.multans[2] = 1;
                    break;
                case "D":
                    mAdapter.multans[3] = 1;
                    break;
                case "E":
                    mAdapter.multans[4] = 1;
                    break;
            }
        }
    }

/*    *//**
     * 记录答案
     *//*
    private void saveAnswer() {
        answer answer = answers.get(progress - 1);
        setAnswer(answer);
        answers.set(progress - 1, answer);
    }*/


    /**
     * 重置当前页面数据
     */
    private void resetCurData(int position) {
        progress = position + 1;
        if (datas != null && datas.size() > 0) {
            problem = datas.get(position);
        }
        manswer = "";
        tv_progress.setText(String.valueOf(progress));
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 弹出窗口的gridview点击事件
     *
     * @param position
     */
    @Override
    public void onGridItemClicked(int position) {
        resetCurData(position);
        showBottomLayout();
        //先强制设定跳转到指定页面
        try {
            Field field = viewPager.getClass().getField("mCurItem");//参数mCurItem是系统自带的
            field.setAccessible(true);
            field.setInt(viewPager, position);

            Field mFirstLayout = viewPager.getClass().getDeclaredField("mFirstLayout");
            mFirstLayout.setAccessible(true);
            mFirstLayout.set(viewPager, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //然后调用下面的函数刷新数据
        mAdapter.notifyDataSetChanged();

        //再调用setCurrentItem()函数设置一次
        viewPager.setCurrentItem(position, false);
    }

    private class SentToServerTask extends AsyncTask<String, Void, String> {
        String url;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ExamActivity.this);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                url = params[0];
                int rightnum = 0;
                for (answer item : answers) {
                    //Log.e("answer",item.getAnswer());
                    if (!(item.getIsnull() == 1 ? true : false) && (item.getIstrue() == 1 ? true : false)) {
                        rightnum++;
                    }
                }
                //Log.e("Right",String.valueOf(rightnum));
                float rs = (float) rightnum / (float) answers.size();
                String res = String.valueOf((int) (rs * 100));
                List<NameValuePair> datas = new ArrayList<NameValuePair>();
                datas.add(new BasicNameValuePair("stuid", GetUserInfo.getPeo_id()));
                datas.add(new BasicNameValuePair("stuscore", res));

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
                Toast.makeText(ExamActivity.this, "上传成功！", Toast.LENGTH_LONG).show();
                FileUtils.delFile("examcache.out");
                answerService.deleteAll();
                finish();
            } else {
                Toast.makeText(ExamActivity.this, "服务器繁忙，请稍后重试！", Toast.LENGTH_LONG).show();
            }
        }
    }

    private Handler handler = new Handler();
    //private Thread saveThread;

    public void saveAnswertoLocal() {
        handler.postDelayed(runnable,10000);
        /*saveThread = new Thread() {
            @Override
            public void run() {
                Log.i("Mcache", "执行");
                answerService.inserAnswer(answers);
            }
        };
        saveThread.start();*/
    }
    /*private class CacheAnswerTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            //Looper.prepare();
            handler = new Handler();
            *//*Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Log.i("Mcache","执行");
                    answerService.inserAnswer(answers);
                    handler.postDelayed(this, 10000);
                }
            };*//*
            handler.postDelayed(runnable,10000);
            //Looper.loop();
            return null;
        }
    }*/

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        runnable = null;
        handler = null;
        //saveThread = null;
        //cache.cancel(true);
        super.onDestroy();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //saveThread.start();
            Log.i("Mcache","执行");
            answerService.inserAnswer(answers);
            handler.postDelayed(this, 10000);
        }
    };

}
