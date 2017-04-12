package nwsuaf.com.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.customview.DriverProgress;
import nwsuaf.com.exam.customview.WaterWaveProgress;
import nwsuaf.com.exam.entity.lcerror;
import nwsuaf.com.exam.entity.lcproblem;
import nwsuaf.com.exam.service.ErrorService;

public class ResultActivity extends BaseActivity implements View.OnClickListener {
    private DriverProgress dp_id_result;
    private RelativeLayout rl_id_seeerror,rl_id_seeexam,rl_id_retry;
    private String time;
    private String rs;
    private TextView tv_id_duration;
    private ImageView iv_id_leftbtn;
    private int SHOW_TYPE;
    private TextView tv_id_result;
    private TextView tv_id_ispass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rs_mine);

        TopView();

        Intent intent = getIntent();
        rs =  intent.getStringExtra("result");
        time = intent.getStringExtra("time");
        SHOW_TYPE = intent.getIntExtra("type",1);

        initViews();
        initViewSorce();
        initEvents();

    }

    private void initViewSorce() {
        String duration = getIntent().getStringExtra("duration");
        tv_id_duration.setText(duration);

        dp_id_result = (DriverProgress) findViewById(R.id.dp_id_result);
        dp_id_result.setProgress(Integer.valueOf(rs));
        dp_id_result.startAnimation();

        tv_id_result.setText(rs);
        if(Integer.valueOf(rs)>=60){
            tv_id_ispass.setText(R.string.tv_pass);
            tv_id_ispass.setTextColor(getResources().getColor(R.color.green));
        }else{
            tv_id_ispass.setText(R.string.tv_unpass);
            tv_id_ispass.setTextColor(getResources().getColor(R.color.lightred));
        }
    }

    private void initEvents() {
        iv_id_leftbtn.setImageResource(R.drawable.left);
        iv_id_leftbtn.setOnClickListener(this);

        rl_id_seeerror.setOnClickListener(this);
        rl_id_seeexam.setOnClickListener(this);
        rl_id_retry.setOnClickListener(this);
    }

    private void initViews() {
        iv_id_leftbtn = (ImageView) findViewById(R.id.iv_id_leftbtn);
        rl_id_seeerror = (RelativeLayout) findViewById(R.id.rl_id_seeerror);
        rl_id_seeexam = (RelativeLayout) findViewById(R.id.rl_id_seeexam);
        rl_id_retry = (RelativeLayout) findViewById(R.id.rl_id_retry);
        tv_id_duration = (TextView) findViewById(R.id.tv_id_duration);

        tv_id_result = (TextView) findViewById(R.id.tv_id_result);
        tv_id_ispass = (TextView) findViewById(R.id.tv_id_ispass);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_id_seeerror:
                //查看错题
                Intent intent1 = new Intent(ResultActivity.this,ExamActivity.class);
                intent1.putExtra("type", AppConstants.TYPE_SEE_ERROR);
                intent1.putExtra("time", time);
                startActivity(intent1);
                finish();
                break;
            case R.id.rl_id_seeexam:
                //查看试卷
                Intent intent2 = new Intent(ResultActivity.this,ExamActivity.class);
                intent2.putExtra("type", SHOW_TYPE+4);
                startActivity(intent2);
                finish();
                break;
            case R.id.rl_id_retry:
                //重新测试
                Intent intent3 = new Intent(ResultActivity.this,ExamActivity.class);
                intent3.putExtra("type", SHOW_TYPE);
                //intent2.putExtra("time",time);
                startActivity(intent3);
                finish();
                break;
            case R.id.iv_id_leftbtn:
                finish();
                break;
        }
    }
}
