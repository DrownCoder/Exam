package nwsuaf.com.exam.activity;

import android.os.Bundle;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;

public class MyClassActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class);
        TopView();
    }
}
