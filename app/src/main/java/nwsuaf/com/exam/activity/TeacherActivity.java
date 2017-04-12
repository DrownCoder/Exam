package nwsuaf.com.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
import nwsuaf.com.exam.customview.RippleView;

public class TeacherActivity extends BaseActivity {
    private RippleView mCreateClass;
    private RippleView mMyClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        setTitle("教师端");
        TopView();
        initViews();
        initEvents();
    }

    public void initViews() {
        mCreateClass = (RippleView) findViewById(R.id.rp_id_createclass);
        mMyClass = (RippleView) findViewById(R.id.rp_id_myclass);
    }

    public void initEvents() {
        mCreateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {

                    public void run() {
                        Intent intent = new Intent(TeacherActivity.this, CreateClassActivity.class);
                        startActivity(intent);
                    }

                }, 500);
            }
        });
        mMyClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {

                    public void run() {
                        Intent intent = new Intent(TeacherActivity.this, MyClassActivity.class);
                        startActivity(intent);
                    }

                }, 500);
            }
        });
    }
}
