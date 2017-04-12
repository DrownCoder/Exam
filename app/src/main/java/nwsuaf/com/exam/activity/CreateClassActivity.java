package nwsuaf.com.exam.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CodeUtils;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
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
                mBitmap = CodeUtils.createImage(key, 400, 400, null);
                mClassPic.setImageBitmap(mBitmap);
                KeyBoardUtils.closeKeybord(mCreateClassName,CreateClassActivity.this);
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
