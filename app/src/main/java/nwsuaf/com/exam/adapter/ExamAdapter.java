package nwsuaf.com.exam.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.entity.netmodel.Answer;
import nwsuaf.com.exam.entity.netmodel.ProblemData;

/**
 * Created by dengzhaoxuan on 2017/4/13.
 */

public class ExamAdapter extends PagerAdapter{
    //显示的数据
    private List<ProblemData> datas = null;
    private List<Answer> mAnswer;
    private LinkedList<View> mViewCache = null;
    private Context mContext ;
    private LayoutInflater mLayoutInflater = null;

    public ExamAdapter(Context context, List<ProblemData> mData , List<Answer> mAnswer) {
        mContext = context;
        datas = mData;
        this.mAnswer = mAnswer;
        this.mLayoutInflater = LayoutInflater.from(mContext) ;
        this.mViewCache = new LinkedList<>();
        if(datas == null){
            datas = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewHolder viewHolder = null;
        View convertView = null;
        List<String> mImgList = datas.get(position).getImgList();
        Answer answer = mAnswer.get(position);
        if(mViewCache.size() == 0){
            convertView = this.mLayoutInflater.inflate(R.layout.exam_final_layout ,
                    null ,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            convertView = mViewCache.removeFirst();
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Glide.with(mContext).load(mImgList.get(0)).crossFade().into(viewHolder.pic1);
        Glide.with(mContext).load(mImgList.get(1)).crossFade().into(viewHolder.pic2);
        Glide.with(mContext).load(mImgList.get(2)).crossFade().into(viewHolder.pic3);
        Glide.with(mContext).load(mImgList.get(3)).crossFade().into(viewHolder.pic4);

        if(answer.isEmpty()){
            viewHolder.ke.setText("");
            viewHolder.shu.setText("");
            viewHolder.zhong.setText("");
        }else{
            viewHolder.ke.setText(answer.getKe());
            viewHolder.shu.setText(answer.getShu());
            viewHolder.zhong.setText(answer.getZhong());
        }
        container.addView(convertView ,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT );

        return convertView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View contentView = (View) object;
        container.removeView(contentView);
        this.mViewCache.add(contentView);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    public class ViewHolder{
        private ImageView pic1;
        private ImageView pic2;
        private ImageView pic3;
        private ImageView pic4;
        private EditText ke;
        private EditText shu;
        private EditText zhong;

        public ViewHolder(View view) {
            pic1 = (ImageView) view.findViewById(R.id.iv_id_a);
            pic2 = (ImageView) view.findViewById(R.id.iv_id_b);
            pic3 = (ImageView) view.findViewById(R.id.iv_id_c);
            pic4 = (ImageView) view.findViewById(R.id.iv_id_d);
            ke = (EditText) view.findViewById(R.id.et_ke);
            shu = (EditText) view.findViewById(R.id.et_shu);
            zhong = (EditText) view.findViewById(R.id.et_zhong);
        }
    }
}
