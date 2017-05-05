package nwsuaf.com.exam.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
import uk.co.senab.photoview.PhotoView;

public class ImageDetailActivity extends BaseActivity {
    private PhotoView mPhotoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_detail);

        TopView();
        initViews();
        initDatas();
    }

    private void initDatas() {
        String url = getIntent().getStringExtra("url");
        Glide.with(this).load(url)
                .error(R.drawable.pic_error)
                .crossFade()
                .into(mPhotoView);
    }

    @Override
    protected void onDestroy() {
        mPhotoView = null;
        super.onDestroy();
    }

    private void initViews() {
        mPhotoView = (PhotoView) findViewById(R.id.pv_touch);
    }
}
