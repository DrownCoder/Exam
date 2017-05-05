package nwsuaf.com.exam.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.callback.LoginCallback;
import nwsuaf.com.exam.entity.netmodel.NetObject_Peo;
import nwsuaf.com.exam.util.GetUserInfo;
import nwsuaf.com.exam.util.KeyBoardUtils;

public class CreateClassActivity extends BaseActivity {
    private EditText mCreateClassName;
    private Button mBtnCreate;
    private ImageView mClassPic;

    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        TopView();
        initViews();
        initEvents();
    }

    private void initEvents() {
        setLeftBack();
        mBtnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mCreateClassName.getText().toString();
                if (key == null || key.equals("")) {
                    Toast.makeText(CreateClassActivity.this, "您的输入为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mCreateClassName.setText("");
                createClass(key);
            }
        });
    }

    private void createClass(final String key) {
        String url = new StringBuffer(AppConstants.LOCAL_HOST)
                .append("/login").toString();
                /*.append("&stuid=")
                .append(URLDecoder.decode(tv_id_uid.getText().toString()))
                .append("&passwd=")
                .append(URLDecoder.decode(tv_id_passwd.getText().toString())).toString();*/
        OkHttpUtils
                .get()
                .url(url)
                .addParams("teacherid", GetUserInfo.getPeo_id())
                .addParams("classname", key)
                .build()
                .execute(new LoginCallback(){
                    @Override
                    public void onResponse(NetObject_Peo response, int id) {
                        NetObject_Peo res = (NetObject_Peo) response;
                        if (res.getCode().equals(AppConstants.SUCCESSLOGIN)) {
                            Toast.makeText(CreateClassActivity.this, "创建成功！", Toast.LENGTH_SHORT).show();
                            mBitmap = CodeUtils.createImage(key, 400, 400, null);
                            mClassPic.setImageBitmap(mBitmap);
                            KeyBoardUtils.closeKeybord(mCreateClassName,CreateClassActivity.this);
                        }else{
                            Toast.makeText(CreateClassActivity.this,res.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initViews() {
        mCreateClassName = (EditText) findViewById(R.id.et_classname);
        mBtnCreate = (Button) findViewById(R.id.bt_create);
        mClassPic = (ImageView) findViewById(R.id.iv_result);
    }

    @Override
    protected void onDestroy() {
        mBitmap = null;
        super.onDestroy();
    }
}
