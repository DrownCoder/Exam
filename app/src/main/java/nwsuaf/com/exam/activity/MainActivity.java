package nwsuaf.com.exam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
import nwsuaf.com.exam.adapter.LoginFragmentAdapter;
import nwsuaf.com.exam.fragment.StudentLoginFragment;
import nwsuaf.com.exam.fragment.TeacherLoginFragment;


public class MainActivity extends BaseActivity {
    private ViewPager mViewpager;
    //tab
    private ImageView mTabTeacherIv;
    private ImageView mTabStudentIv;
    private TextView mTabTeacherTv;
    private TextView mTabStudentTv;
    private RelativeLayout mTeacherLayout;
    private RelativeLayout mStudentLayout;
    private static final int REQUEST_CODE = 0x111;

    private StudentLoginFragment stuFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("登录");
        TopView();
        initViews();
        initEvents();
    }

    private void initEvents() {
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    setStudentTab();
                }else{
                    setTeacherTab();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTeacherTab() {
        mTabTeacherTv.setTextColor(getResources().getColor(R.color.white));
        mTabStudentTv.setTextColor(getResources().getColor(R.color.textblacklight));
        mTabTeacherIv.setImageResource(R.drawable.teacher_login_focused);
        mTabStudentIv.setImageResource(R.drawable.student_lognin);
        mTeacherLayout.setBackgroundColor(getResources().getColor(R.color.bohegreen));
        mStudentLayout.setBackgroundColor(getResources().getColor(R.color.contentwhite));
    }

    private void setStudentTab() {
        mTabTeacherTv.setTextColor(getResources().getColor(R.color.textblacklight));
        mTabStudentTv.setTextColor(getResources().getColor(R.color.white));
        mTabTeacherIv.setImageResource(R.drawable.teacher_login);
        mTabStudentIv.setImageResource(R.drawable.student_login_focused);
        mTeacherLayout.setBackgroundColor(getResources().getColor(R.color.contentwhite));
        mStudentLayout.setBackgroundColor(getResources().getColor(R.color.bohegreen));
    }

    private void initViews() {
        mTabStudentIv = (ImageView) findViewById(R.id.iv_id_student);
        mTabStudentTv = (TextView) findViewById(R.id.tv_id_student);
        mTabTeacherIv = (ImageView) findViewById(R.id.iv_id_teacher);
        mTabTeacherTv = (TextView) findViewById(R.id.tv_id_teacher);
        mTeacherLayout = (RelativeLayout) findViewById(R.id.rl_id_teacher);
        mStudentLayout = (RelativeLayout) findViewById(R.id.rl_id_student);

        mViewpager = (ViewPager) findViewById(R.id.vp_login);
        stuFragment = new StudentLoginFragment();
        TeacherLoginFragment teaFragment = new TeacherLoginFragment();
        List<Fragment> list = new ArrayList<>();
        list.add(stuFragment);
        list.add(teaFragment);
        mViewpager.setAdapter(new LoginFragmentAdapter(getSupportFragmentManager(), list));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        /**
         * 处理二维码扫描结果
         */
        if (resultCode == RESULT_OK) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    //stuFragment.setClassName(result);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
