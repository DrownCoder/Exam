package nwsuaf.com.exam.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.zhy.http.okhttp.OkHttpUtils;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.MyUIActivity;
import nwsuaf.com.exam.activity.StudentActivity;
import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.callback.LoginCallback;
import nwsuaf.com.exam.customview.CustomDialog;
import nwsuaf.com.exam.customview.RippleView;
import nwsuaf.com.exam.entity.netmodel.NetObject_Peo;
import nwsuaf.com.exam.entity.netmodel.UserInfo;
import nwsuaf.com.exam.util.GetUserInfo;

public class StudentLoginFragment extends Fragment {
    private EditText tv_id_uid, tv_id_passwd;
    private RippleView rp_id_login;
    private RequestQueue queue;
    private TextView tv_classname;
    private ImageView mEntry;

    private String mStuId;
    private String mStuPasswd;
    private String mStuClass;
    private static final int REQUEST_CODE = 0x111;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_login, container, false);
        initView(view);
        initEvent();
        return view;
    }

    private void initEvent() {
        tv_classname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputTypeDialog();
            }
        });
        rp_id_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkLoginInfo()) {
                    /**
                     * 等接口
                     * 登录
                     */
                    stuLoginToNet();
                } else {
                    Toast.makeText(getActivity(), "请填写完整登录信息", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StudentActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    /**
     * 检查登录信息
     *
     * @return
     */
    private boolean checkLoginInfo() {
        mStuClass = tv_classname.getText().toString();
        mStuId = tv_id_uid.getText().toString();
        mStuPasswd = tv_id_passwd.getText().toString();
        if (TextUtils.isEmpty(mStuClass) || TextUtils.isEmpty(mStuId)
                || TextUtils.isEmpty(mStuPasswd) || mStuClass.equals("点我设置班级")) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private void stuLoginToNet() {
        String url = new StringBuffer(AppConstants.LOCAL_HOST)
                .append("/login").toString();
                /*.append("&stuid=")
                .append(URLDecoder.decode(tv_id_uid.getText().toString()))
                .append("&passwd=")
                .append(URLDecoder.decode(tv_id_passwd.getText().toString())).toString();*/
        OkHttpUtils
                .get()
                .url(url)
                .addParams("stuclass", mStuClass)
                .addParams("stuid", mStuId)
                .addParams("stupasswd", mStuPasswd)
                .build()
                .execute(new LoginCallback(){
                    @Override
                    public void onResponse(NetObject_Peo response, int id) {
                        NetObject_Peo res = (NetObject_Peo) response;
                        if (res.getCode().equals(AppConstants.SUCCESSLOGIN)) {
                            Toast.makeText(getActivity(), "登录成功！", Toast.LENGTH_SHORT).show();
                            UserInfo info = res.getData().get(0);
                            GetUserInfo.setClass_name(info.getStuclass());
                            GetUserInfo.setPeo_id(info.getStuid());
                            GetUserInfo.setPeo_name(info.getStuname());
                            GetUserInfo.setIsGet(true);
                            Intent intent = new Intent(getActivity(), StudentActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }else{
                            Toast.makeText(getActivity(),res.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 显示输入班级方式dialog
     */
    private void showInputTypeDialog() {
        CustomDialog.Builder customBuilder = new
                CustomDialog.Builder(getActivity());
        customBuilder.setTitle("请选择")
                .setNegativeButton("扫一扫", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intentReadUi = new Intent(getActivity(), MyUIActivity.class);
                        startActivityForResult(intentReadUi, REQUEST_CODE);
                    }
                })
                .setPositiveButton("手动输入", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        createInputDialog();
                    }
                })
                .setMessage("班级录入方式");
        CustomDialog dialog = customBuilder.create();
        dialog.show();
    }

    /**
     * 显示手动输入班级dialog
     */
    private void createInputDialog() {
        final EditText editText = new EditText(getActivity());
        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setTitle("请输入班级名称")
                .setContentView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setClassName(editText.getText().toString());
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    private void initView(View view) {
        tv_id_uid = (EditText) view.findViewById(R.id.tv_id_uid);
        tv_id_passwd = (EditText) view.findViewById(R.id.tv_id_passwd);
        rp_id_login = (RippleView) view.findViewById(R.id.rp_id_login);

        tv_classname = (TextView) view.findViewById(R.id.tv_classname);
        mEntry = (ImageView) view.findViewById(R.id.iv_id_quickentry);
    }

    public void setClassName(String str) {
        tv_classname.setText(str);
    }
}
