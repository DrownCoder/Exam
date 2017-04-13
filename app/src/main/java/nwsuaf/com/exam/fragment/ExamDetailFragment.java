package nwsuaf.com.exam.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.entity.netmodel.Answer;
import nwsuaf.com.exam.entity.netmodel.ProblemData;

/**
 * Created by dengzhaoxuan on 2017/4/13.
 */

public class ExamDetailFragment extends Fragment {
    private ImageView pic1;
    private ImageView pic2;
    private ImageView pic3;
    private ImageView pic4;
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
        initData();
        return view;
    }

    public void setData(ProblemData data) {
        this.mData = data;
    }

    private void initData() {
        if(mData!=null){
            List<String> mImgList = mData.getImgList();
            Glide.with(getActivity()).load(mImgList.get(0)).crossFade().into(pic1);
            Glide.with(getActivity()).load(mImgList.get(1)).crossFade().into(pic2);
            Glide.with(getActivity()).load(mImgList.get(2)).crossFade().into(pic3);
            Glide.with(getActivity()).load(mImgList.get(3)).crossFade().into(pic4);
        }
    }

    private void initView(View view) {
        pic1 = (ImageView) view.findViewById(R.id.iv_id_a);
        pic2 = (ImageView) view.findViewById(R.id.iv_id_b);
        pic3 = (ImageView) view.findViewById(R.id.iv_id_c);
        pic4 = (ImageView) view.findViewById(R.id.iv_id_d);
        ke = (EditText) view.findViewById(R.id.et_ke);
        shu = (EditText) view.findViewById(R.id.et_shu);
        zhong = (EditText) view.findViewById(R.id.et_zhong);
    }

    public Answer getAnswer(){
        Answer answer = new Answer();
        answer.setId(mData.getId());
        answer.setKe(ke.getText().toString());
        answer.setShu(shu.getText().toString());
        answer.setZhong(zhong.getText().toString());
        return answer;
    }
}
