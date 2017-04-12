package nwsuaf.com.exam.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.base.BaseActivity;
import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.customview.RippleView;
import nwsuaf.com.exam.entity.netmodel.NetObject_Peo;
import nwsuaf.com.exam.util.GetUserInfo;
import nwsuaf.com.exam.util.GsonRequest;
import nwsuaf.com.exam.util.VolleyUtil;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity{
    private EditText tv_id_uid,tv_id_passwd;
    private RippleView rp_id_login;

    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
    }

    private void initEvent() {
        rp_id_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNetWorkDatas();
            }
        });
    }

    private void initView() {
        tv_id_uid = (EditText) findViewById(R.id.tv_id_uid);
        tv_id_passwd = (EditText) findViewById(R.id.tv_id_passwd);
        rp_id_login = (RippleView) findViewById(R.id.rp_id_login);
    }

    private void getNetWorkDatas() {
        String url = new StringBuffer(AppConstants.LOCAL_HOST).append(AppConstants.WEBSERVER)
                .append("/servlet/StudentInfoOperationServlet")
                .append("?operation=2")
                .append("&stuid=")
                .append(tv_id_uid.getText().toString())
                .append("&passwd=")
                .append(tv_id_passwd.getText().toString()).toString();
        //String url = "http://192.168.139.1:8090/WineServer/GetPeoServlet";
        queue = VolleyUtil.getRequestQueue();
        GsonRequest<NetObject_Peo> gsonRequest = new GsonRequest<NetObject_Peo>(
                url, NetObject_Peo.class,
                mListener,mErrorListener);
        queue.add(gsonRequest);
    }

    private static class mListener implements Response.Listener<NetObject_Peo> {
        private final WeakReference<Activity> activityWeakReference;
        //private final WeakReference<VolleyCallback> callbackWeakReference;

        public mListener(Activity activity) {
            activityWeakReference = new WeakReference<Activity>(activity);
            //callbackWeakReference = new WeakReference<VolleyCallback>(callback);
        }
        @Override
        public void onResponse(NetObject_Peo data) {
            Activity act = activityWeakReference.get();
            //VolleyCallback vc = callbackWeakReference.get();
            if (act != null) {
                int returncode = data.getReturncode();
                if(returncode == AppConstants.DONTEXIST){
                    Toast.makeText(act,"用户不存在！",Toast.LENGTH_SHORT).show();
                }
                else if(returncode == AppConstants.WRONGPASSWORD){
                    Toast.makeText(act,"密码错误！",Toast.LENGTH_SHORT).show();
                }
                else if(returncode == AppConstants.SUCCESSLOGIN){
                    GetUserInfo.setIsGet(true);
                    GetUserInfo.setPeo_name(data.getData().get(0).getName());
                    GetUserInfo.setPeo_id(data.getData().get(0).getStuid());
                    GetUserInfo.setClass_name(data.getData().get(0).getStuclass() + "");
                    Toast.makeText(act,"登录成功！",Toast.LENGTH_SHORT).show();
                    act.finish();
                }
            }
        }
    }
    private static class mErrorListener implements Response.ErrorListener {
        private final WeakReference<Activity> activityWeakReference;
        //private final WeakReference<VolleyCallback> callbackWeakReference;

        public mErrorListener(Activity activity) {
            activityWeakReference = new WeakReference<Activity>(activity);
            //callbackWeakReference = new WeakReference<VolleyCallback>(callback);
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    }
    mListener mListener = new mListener(this);

    mErrorListener mErrorListener = new mErrorListener(this);

    @Override
    protected void onDestroy() {
        mListener = null;
        mErrorListener = null;
        super.onDestroy();
    }
}

