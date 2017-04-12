package nwsuaf.com.exam.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
import nwsuaf.com.exam.adapter.HomePageAdapter;
import nwsuaf.com.exam.fragment.MineExamFragment;
import nwsuaf.com.exam.fragment.UserFragment;
import nwsuaf.com.exam.fragment.ExamFragment;
import nwsuaf.com.exam.util.ImageLoaderForLocal;

public class StudentActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private HomePageAdapter mAapter;
    private List<Fragment> fragmentList;
    private int moveimgheight = 0;
    private int moveimgwidth = 0;
    private ImageView top_id_moveimage;
    private RelativeLayout.LayoutParams layoutParams;
    private LinearLayout bottom_l1, bottom_l2, bottom_l3;
    private ImageView bottom_pic1, bottom_pic2, bottom_pic3;
    private TextView bottom_text1, bottom_text2, bottom_text3;
    private TextView top_id_threetext1, top_id_threetext2, top_id_threetext3;
    //保存当前选中tab
    private int tab_num = 1;

    private String top_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.wine_activity_main);

        TopView();

        /*
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);*/


        initView();
        initData();
        initEvent();
    }


    /**
     * 事件声明
     */
    private void initEvent() {
        bottom_l1.setOnClickListener(this);
        bottom_l2.setOnClickListener(this);
        bottom_l3.setOnClickListener(this);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                moveTabPic(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                resetView();
                setSeclect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    /*
    滚动条随着ViewPager滑动
     */
    private void moveTabPic(int position, float positionOffset, int positionOffsetPixels) {


        int moveX = (int) ((int) (moveimgwidth * position) + (((double) positionOffsetPixels / viewPager.getWidth()) * moveimgwidth));
        layoutParams.leftMargin = moveX;
        /*
         更改tab条长度和位置
        */
        if (positionOffset < 0.5) {
            layoutParams.width = (int) (((double) positionOffsetPixels / viewPager.getWidth()) * moveimgwidth) + moveimgwidth;
            layoutParams.height = moveimgheight - (int) (((double) positionOffsetPixels / viewPager.getWidth()) * moveimgheight);
        } else {
            layoutParams.width = 2 * moveimgwidth - (int) (((double) positionOffsetPixels / viewPager.getWidth()) * moveimgwidth);
            layoutParams.height = (int) (((double) positionOffsetPixels / viewPager.getWidth()) * moveimgheight);
        }
        Log.i("width", moveimgwidth + "");
        top_id_moveimage.setLayoutParams(layoutParams);
    }

    private void initData() {
        MineExamFragment one_oneFragment = new MineExamFragment();
        ExamFragment one_twoFragment = new ExamFragment();
        UserFragment one_threeFragment = new UserFragment();

        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(one_oneFragment);
        fragmentList.add(one_twoFragment);
        fragmentList.add(one_threeFragment);
        mAapter = new HomePageAdapter(this, getSupportFragmentManager(), (ArrayList<Fragment>) fragmentList);
        viewPager.setAdapter(mAapter);
    }

    /**
     * 声明组件
     */
    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.main_id_viewpager);
        viewPager.setOffscreenPageLimit(2);

        bottom_l1 = (LinearLayout) findViewById(R.id.bottom_id_tab1);
        bottom_l2 = (LinearLayout) findViewById(R.id.bottom_id_tab2);
        bottom_l3 = (LinearLayout) findViewById(R.id.bottom_id_tab3);


        bottom_pic1 = (ImageView) findViewById(R.id.bottom_id_pic1);
        bottom_pic2 = (ImageView) findViewById(R.id.bottom_id_pic2);
        bottom_pic3 = (ImageView) findViewById(R.id.bottom_id_pic3);

        bottom_text1 = (TextView) findViewById(R.id.bottom_id_text1);
        bottom_text2 = (TextView) findViewById(R.id.bottom_id_text2);
        bottom_text3 = (TextView) findViewById(R.id.bottom_id_text3);

        top_id_threetext1 = (TextView) findViewById(R.id.top_id_threetext1);
        top_id_threetext2 = (TextView) findViewById(R.id.top_id_threetext2);
        top_id_threetext3 = (TextView) findViewById(R.id.top_id_threetext3);

        top_id_moveimage = (ImageView) findViewById(R.id.top_id_moveimage);
        layoutParams = (RelativeLayout.LayoutParams) top_id_moveimage.getLayoutParams();
        moveimgwidth = layoutParams.width;
        moveimgheight = layoutParams.height;

    }


    @Override
    public void onClick(View v) {
        resetView();
        switch (v.getId()) {
            case R.id.bottom_id_tab1:
                setSeclect(0);
                break;
            case R.id.bottom_id_tab2:
                setSeclect(1);
                break;
            case R.id.bottom_id_tab3:
                setSeclect(2);
                break;
        }
    }

    /*
    设置当前选中项
     */
    private void setSeclect(int i) {
        // viewPager.setCurrentItem(i);
        switch (i) {
            case 0://点击第一个bottom tab
                viewPager.setCurrentItem(0);

                ImageLoaderForLocal.getInstance().loadDrawable(R.drawable.bottom_pic1_clicked, bottom_pic1);
                //bottom_pic1.setImageResource(R.drawable.bottom_pic1_clicked);
                bottom_text1.setTextColor(this.getResources().getColor(R.color.mainblue));
                break;
            case 1://点击第二个bottom tab
                viewPager.setCurrentItem(1);

                ImageLoaderForLocal.getInstance().loadDrawable(R.drawable.bottom_pic2_clicked, bottom_pic2);
                //bottom_pic2.setImageResource(R.drawable.bottom_pic2_clicked);
                bottom_text2.setTextColor(this.getResources().getColor(R.color.mainblue));
                break;
            case 2:
                viewPager.setCurrentItem(2);

                ImageLoaderForLocal.getInstance().loadDrawable(R.drawable.bottom_pic3_clicked, bottom_pic3);
                //bottom_pic4.setImageResource(R.drawable.b4_clicked);
                bottom_text3.setTextColor(this.getResources().getColor(R.color.mainblue));
                break;
        }
    }

    /*
    重置所有view
     */
    private void resetView() {
        ImageLoaderForLocal.getInstance().loadDrawable(R.drawable.bottom_pic1, bottom_pic1);
        ImageLoaderForLocal.getInstance().loadDrawable(R.drawable.bottom_pic2, bottom_pic2);
        ImageLoaderForLocal.getInstance().loadDrawable(R.drawable.bottom_pic3, bottom_pic3);
/*        bottom_pic1.setImageResource(R.drawable.b1);
        bottom_pic2.setImageResource(R.drawable.b2);
        bottom_pic4.setImageResource(R.drawable.b4);
        bottom_pic5.setImageResource(R.drawable.b5);*/

        bottom_text1.setTextColor(this.getResources().getColor(R.color.shallowblack));
        bottom_text2.setTextColor(this.getResources().getColor(R.color.shallowblack));
        bottom_text3.setTextColor(this.getResources().getColor(R.color.shallowblack));
    }
}
