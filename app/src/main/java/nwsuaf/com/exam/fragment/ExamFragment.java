package nwsuaf.com.exam.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

import cn.iwgang.countdownview.CountdownView;
import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.ExamActivity;
import nwsuaf.com.exam.activity.LoginActivity;
import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.customview.CustomDialog;
import nwsuaf.com.exam.customview.SubmitButton;
import nwsuaf.com.exam.util.GetUserInfo;


public class ExamFragment extends Fragment implements View.OnClickListener {
    private SubmitButton bt_id_exam;
    private RelativeLayout rl_id_mineexam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exam_activity, container, false);
        initViews(view);
        initEvents();
        return view;
    }

    private void initEvents() {
        bt_id_exam.setOnClickListener(this);
        rl_id_mineexam.setOnClickListener(this);
    }

    private void initViews(View view) {
        bt_id_exam = (SubmitButton) view.findViewById(R.id.bt_id_exam);
        rl_id_mineexam = (RelativeLayout) view.findViewById(R.id.rl_id_mineexam);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_id_exam:
                if (GetUserInfo.isGet()) {
                    bt_id_exam.startAnimation();
                    new Handler().postDelayed(new Runnable() {

                        public void run() {
                            bt_id_exam.startAnimation();
                            //获取题目
                            GetNetProblem();
                        }

                    }, 1500);
                } else {
                    Toast.makeText(getContext(), "未登录，请先登录", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.rl_id_mineexam:
                if (GetUserInfo.isGet()) {//判断是否登录过
                    //显示用户信息
                    CustomDialog.Builder customBuilder = new
                            CustomDialog.Builder(getActivity());
                    customBuilder.setTitle("用户信息")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setContentView(LayoutInflater.from(getActivity()).inflate(R.layout.dialog_userinfo, null, false));
                    final CustomDialog dialog;
                    dialog = customBuilder.create();
                    dialog.show();
                    customBuilder.showUserInfo(GetUserInfo.getPeo_name(), GetUserInfo.getPeo_id(),
                            GetUserInfo.getClass_name());
                } else {//登录
                    Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intentLogin);
                }
                break;
        }
    }

    private void GetNetProblem() {
        //显示进度框
        CustomDialog.Builder customBuilder = new
                CustomDialog.Builder(getActivity());
        customBuilder.setTitle("等待发卷...")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setContentView(LayoutInflater.from(getActivity()).inflate(R.layout.dialog_progress, null, false));
        final CustomDialog dialog;
        dialog = customBuilder.create();
        dialog.show();
        Random random = new Random();
        int time = random.nextInt(90000);
        customBuilder.startUpdateTime(random.nextInt(time));
        customBuilder.dialog_time.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                //倒计时结束 获得数据
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), ExamActivity.class);
                intent.putExtra("type", AppConstants.TYPE_EXAM);
                startActivity(intent);
            }
        });
    }
}
