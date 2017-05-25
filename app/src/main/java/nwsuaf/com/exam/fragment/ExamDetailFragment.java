package nwsuaf.com.exam.fragment;

import android.content.Intent;
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
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.activity.ExamFinalActivity;
import nwsuaf.com.exam.activity.ImageDetailActivity;
import nwsuaf.com.exam.adapter.PicAdapter;
import nwsuaf.com.exam.entity.netmodel.FAnswer;
import nwsuaf.com.exam.entity.netmodel.ProblemData;

/**
 * Created by dengzhaoxuan on 2017/4/13.
 */

public class ExamDetailFragment extends Fragment {
    private RecyclerView mRcyPicList;
    private PicAdapter mAdapter;
    private EditText ke;
    private EditText shu;
    private EditText zhong;

    private ProblemData mData;
    private FAnswer mAnswer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.exam_final_layout, container, false);
        initView(view);
        //initEvents();
        return view;
    }

    private void initEvents() {
        mAdapter.setmOnItemClickListener(new PicAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), ImageDetailActivity.class);
                intent.putExtra("url", mData.getImgList().get(position));
                startActivity(intent);
            }
        });
    }

    public void setData(ProblemData data) {
        this.mData = data;
    }
    public void setAnswer(FAnswer answer){
        this.mAnswer = answer;
    }
    private void initView(View view) {
        mRcyPicList = (RecyclerView) view.findViewById(R.id.rcy_piclist);
        mRcyPicList.setLayoutManager(new GridLayoutManager(getActivity(),1));
        mAdapter = new PicAdapter(getActivity(), mData.getImgList());
        mRcyPicList.setAdapter(mAdapter);
        ke = (EditText) view.findViewById(R.id.et_ke);
        shu = (EditText) view.findViewById(R.id.et_shu);
        zhong = (EditText) view.findViewById(R.id.et_zhong);

        if(mAnswer!=null){
            ke.setText(TextUtils.isEmpty(mAnswer.getKe())?"":mAnswer.getKe());
            shu.setText(TextUtils.isEmpty(mAnswer.getShu())?"":mAnswer.getShu());
            zhong.setText(TextUtils.isEmpty(mAnswer.getZhong())?"":mAnswer.getZhong());
        }
    }

    public FAnswer getAnswer(){
        FAnswer FAnswer = new FAnswer();
        String anske = "";
        String ansshu = "";
        String anszhong = "";
        if(ke != null && !TextUtils.isEmpty(ke.getText().toString())){
            anske = ke.getText().toString();
        }
        if(shu != null && !TextUtils.isEmpty(shu.getText().toString())){
            ansshu = shu.getText().toString();
        }
        if(zhong != null && !TextUtils.isEmpty(zhong.getText().toString())){
            anszhong = zhong.getText().toString();
        }
        FAnswer.setId(mData.getId());
        FAnswer.setKe(anske);
        FAnswer.setShu(ansshu);
        FAnswer.setZhong(anszhong);
        return FAnswer;
    }

    public void clearViewCache(){
        for(int i = 0;i<mData.getImgList().size();i++) {
            ImageView view = (ImageView) ((RelativeLayout) mRcyPicList.getChildAt(i)).getChildAt(0);
            Glide.clear(view);
        }
    }
}
