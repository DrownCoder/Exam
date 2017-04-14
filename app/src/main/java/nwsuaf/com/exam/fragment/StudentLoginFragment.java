package nwsuaf.com.exam.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.lang.ref.WeakReference;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.ExamFinalActivity;
import nwsuaf.com.exam.activity.MyUIActivity;
import nwsuaf.com.exam.activity.StudentActivity;
import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.customview.CustomDialog;
import nwsuaf.com.exam.customview.RippleView;
import nwsuaf.com.exam.entity.netmodel.NetObject_Peo;
import nwsuaf.com.exam.util.GetUserInfo;
import nwsuaf.com.exam.util.GsonRequest;
import nwsuaf.com.exam.util.VolleyUtil;

public class StudentLoginFragment extends Fragment {
    private EditText tv_id_uid, tv_id_passwd;
    private RippleView rp_id_login;
    private RequestQueue queue;
    private TextView tv_classname;
    private ImageView mEntry;

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
                /**
                 * 等接口
                 */
                //getNetWorkDatas();
                Intent intent = new Intent(getActivity(), ExamFinalActivity.class);
                startActivity(intent);
                getActivity().finish();
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
                mListener, mErrorListener);
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
                if (returncode == AppConstants.DONTEXIST) {
                    Toast.makeText(act, "用户不存在！", Toast.LENGTH_SHORT).show();
                } else if (returncode == AppConstants.WRONGPASSWORD) {
                    Toast.makeText(act, "密码错误！", Toast.LENGTH_SHORT).show();
                } else if (returncode == AppConstants.SUCCESSLOGIN) {
                    GetUserInfo.setIsGet(true);
                    GetUserInfo.setPeo_name(data.getData().get(0).getName());
                    GetUserInfo.setPeo_id(data.getData().get(0).getStuid());
                    GetUserInfo.setClass_name(data.getData().get(0).getStuclass() + "");
                    Toast.makeText(act, "登录成功！", Toast.LENGTH_SHORT).show();
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

    mListener mListener = new mListener(getActivity());

    mErrorListener mErrorListener = new mErrorListener(getActivity());

    public void setClassName(String str) {
        tv_classname.setText(str);
    }
}
