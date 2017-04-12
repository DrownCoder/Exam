package nwsuaf.com.exam.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;

public class NullActivity extends BaseActivity {
    private ImageView iv_id_leftbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null);
        TopView();
        iv_id_leftbtn = (ImageView) findViewById(R.id.iv_id_leftbtn);
        iv_id_leftbtn.setImageResource(R.drawable.left);
        iv_id_leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
