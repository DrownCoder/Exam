package nwsuaf.com.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.service.ExamService;

public class ExamTypeActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout singleChoice,ll_id_multiplechoice,ll_id_judgechoice,ll_id_picturechoice;
    private TextView tv_id_single,tv_id_mult,tv_id_judge,tv_id_pic;
    private ImageView iv_id_leftbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        TopView();
        initViews();
        initViewSource();
        initEvents();
    }

    private void initViewSource() {
        ExamService problemService = new ExamService(this);
        int single = problemService.getTypeGroupData(1).size();
        int mult = problemService.getTypeGroupData(2).size();
        int judge = problemService.getTypeGroupData(3).size();
        int pic = problemService.getTypeGroupData(4).size();

        tv_id_single.setText(single + "道");
        tv_id_mult.setText(mult + "道");
        tv_id_judge.setText(judge + "道");
        tv_id_pic.setText(pic + "道");
    }

    private void initEvents() {
        iv_id_leftbtn.setImageResource(R.drawable.left);
        iv_id_leftbtn.setOnClickListener(this);
        singleChoice.setOnClickListener(this);
        ll_id_multiplechoice.setOnClickListener(this);
        ll_id_judgechoice.setOnClickListener(this);
        ll_id_picturechoice.setOnClickListener(this);
    }

    private void initViews() {
        iv_id_leftbtn = (ImageView) findViewById(R.id.iv_id_leftbtn);

        singleChoice = (LinearLayout) findViewById(R.id.ll_id_singlechoice);
        ll_id_multiplechoice = (LinearLayout) findViewById(R.id.ll_id_multiplechoice);
        ll_id_judgechoice = (LinearLayout) findViewById(R.id.ll_id_judgechoice);
        ll_id_picturechoice = (LinearLayout) findViewById(R.id.ll_id_picturechoice);

        tv_id_single= (TextView) findViewById(R.id.tv_id_single);
        tv_id_mult= (TextView) findViewById(R.id.tv_id_mult);
        tv_id_judge= (TextView) findViewById(R.id.tv_id_judge);
        tv_id_pic= (TextView) findViewById(R.id.tv_id_pic);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_id_leftbtn:
                finish();
                break;
            case R.id.ll_id_singlechoice:
                Intent intent1 = new Intent(this,ExamActivity.class);
                intent1.putExtra("type", AppConstants.TYPE_SINGLE);
                startActivity(intent1);
                break;
            case R.id.ll_id_multiplechoice:
                Intent intent2 = new Intent(this,ExamActivity.class);
                intent2.putExtra("type", AppConstants.TYPE_MULTIPLE);
                startActivity(intent2);
                break;
            case R.id.ll_id_judgechoice:
                Intent intent3 = new Intent(this,ExamActivity.class);
                intent3.putExtra("type", AppConstants.TYPE_JUDGE);
                startActivity(intent3);
                break;
            case R.id.ll_id_picturechoice:
                Intent intent4 = new Intent(this,ExamActivity.class);
                intent4.putExtra("type", AppConstants.TYPE_PIC);
                startActivity(intent4);
                break;
        }
    }
}
