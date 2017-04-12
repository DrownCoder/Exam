package nwsuaf.com.exam.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.ExamActivity;
import nwsuaf.com.exam.activity.ExamTypeActivity;
import nwsuaf.com.exam.activity.RecordActivity;
import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.customview.RippleView;


public class MineExamFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout rl_id_examtype,rl_id_mineexam;
    private RippleView rp_id_errorlist;
    private RippleView rp_id_record;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_exam_layout, container, false);
        initViews(view);
        initEvents();
        return view;
    }

    private void initViews(View view) {
        rl_id_examtype = (RelativeLayout) view.findViewById(R.id.rl_id_examtype);
        rp_id_errorlist = (RippleView) view.findViewById(R.id.rp_id_errorlist);
        rl_id_mineexam = (RelativeLayout) view.findViewById(R.id.rl_id_mineexam);
        rp_id_record = (RippleView) view.findViewById(R.id.rp_id_record);
    }

    private void initEvents() {
        rp_id_errorlist.setOnClickListener(this);
        rl_id_mineexam.setOnClickListener(this);
        rl_id_examtype.setOnClickListener(this);
        rp_id_record.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_id_mineexam:
                Intent intent1 = new Intent(getActivity(),ExamActivity.class);
                intent1.putExtra("type", AppConstants.TYPE_MINEEXAM);
                startActivity(intent1);
                break;
            case R.id.rp_id_errorlist:
                new Handler().postDelayed(new Runnable() {

                    public void run() {

                        //execute the task
                        Intent intent2 = new Intent(getActivity(), ExamActivity.class);
                        intent2.putExtra("type", AppConstants.TYPE_SEE_ERRORLIST);
                        startActivity(intent2);
                    }

                }, 500);
                break;
            case R.id.rl_id_examtype:
                Intent intent3 = new Intent(getActivity(),ExamTypeActivity.class);
                startActivity(intent3);
                break;
            case R.id.rp_id_record:
                new Handler().postDelayed(new Runnable() {

                    public void run() {
                        //execute the task
                        Intent intent4 = new Intent(getActivity(),RecordActivity.class);
                        startActivity(intent4);
                    }

                }, 500);
                break;

        }
    }
}
