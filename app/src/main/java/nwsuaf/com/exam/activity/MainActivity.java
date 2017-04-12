package nwsuaf.com.exam.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.app.AppConstants;


public class MainActivity extends Activity implements View.OnClickListener {
    private ImageView iv_mineexam,iv_id_exam;
    private RelativeLayout rl_id_errorlist,rl_id_examtype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvents();
    }

    private void initEvents() {
        iv_id_exam.setOnClickListener(this);
        iv_mineexam.setOnClickListener(this);
        rl_id_errorlist.setOnClickListener(this);
        rl_id_examtype.setOnClickListener(this);
    }

    private void initViews() {
        iv_id_exam = (ImageView) findViewById(R.id.iv_id_exam);
        iv_mineexam = (ImageView) findViewById(R.id.iv_mineexam);
        rl_id_errorlist = (RelativeLayout) findViewById(R.id.rl_id_errorlist);
        rl_id_examtype = (RelativeLayout) findViewById(R.id.rl_id_examtype);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_mineexam:
                Intent intent1 = new Intent(this,ExamActivity.class);
                intent1.putExtra("type", AppConstants.TYPE_MINEEXAM);
                startActivity(intent1);
                break;
            case R.id.rl_id_errorlist:
                Intent intent2 = new Intent(this,ExamActivity.class);
                intent2.putExtra("type", AppConstants.TYPE_SEE_ERRORLIST);
                startActivity(intent2);
                break;
            case R.id.rl_id_examtype:
                Intent intent3 = new Intent(this,ExamTypeActivity.class);
                startActivity(intent3);
                break;
            case R.id.iv_id_exam:
                Intent intent4 = new Intent(this,MainActivity2.class);
                startActivity(intent4);
                break;
        }
    }
}
