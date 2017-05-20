package nwsuaf.com.exam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;


import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.TeacherActivity;
import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.callback.LoginCallback;
import nwsuaf.com.exam.customview.RippleView;
import nwsuaf.com.exam.entity.netmodel.NetObject_Peo;
import nwsuaf.com.exam.entity.netmodel.UserInfo;
import nwsuaf.com.exam.util.GetUserInfo;

public class TeacherLoginFragment extends Fragment {
    private EditText tv_id_uid,tv_id_passwd;
    private RippleView rp_id_login;

    private String mTeacherId;
    private String mTeacherPasswd;
    public TeacherLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teacher_login, container, false);
        initView(view);
        initEvent();
        return view;
    }

    private void initEvent() {
        rp_id_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkLoginInfo()){
                    /**
                     * 等接口
                     */
                    teaLoginToNet();
                }else{
                    Toast.makeText(getActivity(), "请填写完整登录信息", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * 检查登录信息
     *
     * @return
     */
    private boolean checkLoginInfo() {
        mTeacherId = tv_id_uid.getText().toString();
        mTeacherPasswd = tv_id_passwd.getText().toString();
        if (TextUtils.isEmpty(mTeacherId) || TextUtils.isEmpty(mTeacherPasswd)){
            return false;
        }
            return true;
    }

    private void teaLoginToNet() {
        String url = new StringBuffer(AppConstants.LOCAL_HOST)
                .append(AppConstants.WEBSERVER)
                .append("/login.do").toString();
                /*.append("&stuid=")
                .append(URLDecoder.decode(tv_id_uid.getText().toString()))
                .append("&passwd=")
                .append(URLDecoder.decode(tv_id_passwd.getText().toString())).toString();*/
        OkHttpUtils
                .get()
                .url(url)
                .addParams("username", mTeacherId)
                .addParams("psd", mTeacherPasswd)
                .addParams("isteacher","1")
                .build()
                .execute(new LoginCallback(){
                    @Override
                    public void onResponse(NetObject_Peo res, int id) {
                        if (res.getCode().equals(AppConstants.SUCCESSLOGIN)) {
                            Toast.makeText(getActivity(), "登录成功！", Toast.LENGTH_SHORT).show();
                            UserInfo info = res.getData();
                            GetUserInfo.setClass_name(info.getStuclass());
                            GetUserInfo.setPeo_id(info.getStuid());
                            GetUserInfo.setPeo_name(info.getStuname());
                            GetUserInfo.setIsGet(true);
                            Intent intent = new Intent(getActivity(), TeacherActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }else{
                            Toast.makeText(getActivity(),res.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initView(View view) {
        tv_id_uid = (EditText) view.findViewById(R.id.tv_id_uid);
        tv_id_passwd = (EditText) view.findViewById(R.id.tv_id_passwd);
        rp_id_login = (RippleView) view.findViewById(R.id.rp_id_login);
    }

}
