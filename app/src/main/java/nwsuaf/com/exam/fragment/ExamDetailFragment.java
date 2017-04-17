package nwsuaf.com.exam.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.adapter.PicAdapter;
import nwsuaf.com.exam.entity.netmodel.Answer;
import nwsuaf.com.exam.entity.netmodel.ProblemData;
import nwsuaf.com.exam.util.TimeUtils;

/**
 * Created by dengzhaoxuan on 2017/4/13.
 */

public class ExamDetailFragment extends Fragment {
    private RecyclerView mRcyPicList;
    private EditText ke;
    private EditText shu;
    private EditText zhong;

    private ProblemData mData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.exam_final_layout, container, false);
        initView(view);
        return view;
    }

    public void setData(ProblemData data) {
        this.mData = data;
    }


    private void initView(View view) {
        mRcyPicList = (RecyclerView) view.findViewById(R.id.rcy_piclist);
        mRcyPicList.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRcyPicList.setAdapter(new PicAdapter(getActivity(),mData.getImgList()));
        ke = (EditText) view.findViewById(R.id.et_ke);
        shu = (EditText) view.findViewById(R.id.et_shu);
        zhong = (EditText) view.findViewById(R.id.et_zhong);
    }

    public Answer getAnswer(){
        Answer answer = new Answer();
        String anske = "";
        String ansshu = "";
        String anszhong = "";
        if(ke != null && !TextUtils.isEmpty(ke.getText().toString())){
            anske = ke.getText().toString();
        }
        if(shu != null && !TextUtils.isEmpty(shu.getText().toString())){
            ansshu = ke.getText().toString();
        }
        if(zhong != null && !TextUtils.isEmpty(zhong.getText().toString())){
            anszhong = zhong.getText().toString();
        }
        answer.setId(mData.getId());
        answer.setKe(anske);
        answer.setShu(ansshu);
        answer.setZhong(anszhong);
        return answer;
    }
}
