package nwsuaf.com.exam.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rey.material.widget.CheckBox;

import java.util.HashMap;
import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.app.AppConstants;
import nwsuaf.com.exam.entity.answer;
import nwsuaf.com.exam.entity.lcproblem;
import nwsuaf.com.exam.entity.netmodel.ImageUrlList;

import static nwsuaf.com.exam.app.AppConstants.*;

/**
 * Created by Administrator on 2016/7/5.
 */
public class ExamTypeChangeAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    private Context mContext;
    private List<lcproblem> mdatas;
    private List<ImageUrlList> imgdatas;
    private List<answer> answers;
    private String manswer = "";
    public int[] multans = new int[]{0, 0, 0, 0, 0};
    //图片index---用于data.size-index == 图片题的始坐标
    private int index = 18;
    private String[] ans = new String[]{"A", "B", "C", "D", "E"};
    //布局容器
    private HashMap<Integer, View> viewLayoutHolder;

    //展示模式
    private int SHOW_TYPE;

    //checkbox监听器
    class OnCheckBoxListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()) {
                case R.id.cb_id_a:
                    setCheckedAnswer(b, 0);
                    break;
                case R.id.cb_id_b:
                    setCheckedAnswer(b, 1);
                    break;
                case R.id.cb_id_c:
                    setCheckedAnswer(b, 2);
                    break;
                case R.id.cb_id_d:
                    setCheckedAnswer(b, 3);
                    break;
                case R.id.cb_id_e:
                    setCheckedAnswer(b, 4);
                    break;
            }
            setAnswer(getMultAnswer(), Integer.valueOf(compoundButton.getTag().toString()));
        }


    }

    private void setCheckedAnswer(boolean b, int i) {
        if (b) {
            multans[i] = 1;
        } else {
            multans[i] = 0;
        }
    }

    private OnCheckBoxListener onCheckBoxListener;

/*    public interface onTextChangedListener {
        void notifyAnswer(String answer);
    }

    private onTextChangedListener onTextChangedListener;

    public void setOnTextChangedListener(ExamTypeChangeAdapter.onTextChangedListener onTextChangedListener) {
        this.onTextChangedListener = onTextChangedListener;
    }*/

    public ExamTypeChangeAdapter(List<answer> answers, List<lcproblem> datas, Context mContext, int SHOW_TYPE) {
        this.mContext = mContext;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.mdatas = datas;
        this.answers = answers;
        this.SHOW_TYPE = SHOW_TYPE;
        viewLayoutHolder = new HashMap<>();

        onCheckBoxListener = new OnCheckBoxListener();
    }

    public ExamTypeChangeAdapter(List<answer> answers, List<ImageUrlList> imgdatas, List<lcproblem> datas, Context mContext, int SHOW_TYPE) {
        this.mContext = mContext;
        this.layoutInflater = LayoutInflater.from(mContext);
        this.mdatas = datas;
        this.answers = answers;
        this.SHOW_TYPE = SHOW_TYPE;
        this.imgdatas = imgdatas;
        viewLayoutHolder = new HashMap<>();
        onCheckBoxListener = new OnCheckBoxListener();
    }


    @Override
    public int getCount() {
        return mdatas.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View contentView = (View) object;
        container.removeView(contentView);
        //int type = mdatas.get(position).getType() == 1 ? 0 : 1;
        int type = mdatas.get(position).getType();
        viewLayoutHolder.put(type, contentView);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View convertView = null;

        if (mdatas == null || mdatas.size() == 0) {
            return convertView;
        }
        switch (SHOW_TYPE) {
            case AppConstants.TYPE_MINEEXAM:
            case AppConstants.TYPE_SEE_ERRORLIST:
            case AppConstants.TYPE_SINGLE:
            case AppConstants.TYPE_MULTIPLE:
            case AppConstants.TYPE_JUDGE:
            case AppConstants.TYPE_PIC:
            case AppConstants.TYPE_EXAM:
                convertView = getViewForMineExam(position);
                break;
            default:
                convertView = getViewForShow(position);
        }

        container.addView(convertView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return convertView;
    }

    /**
     * 展示界面（不可点击，显示答案）
     *
     * @param position
     * @return
     */
    private View getViewForShow(int position) {
        View convertView = null;
        ViewHolder viewHolder = null;
        lcproblem data = mdatas.get(position);
        //题型判断，1---图片判断题     0---选择题
        //type = data.getType() == 1 ? 0 : 1;
        if (viewLayoutHolder.size() < 3) {
            switch (data.getType()) {
                case PROBLEM_TYPE_PIC://图片判断题
                    convertView = layoutInflater.inflate(R.layout.activity_picturechoice, null, false);
                    viewHolder = new ViewHolder(convertView, 1);
                    convertView.setTag(viewHolder);
                    break;
                case PROBLEM_TYPE_MULTI://多选题
                    convertView = layoutInflater.inflate(R.layout.activity_multichoice, null, false);
                    viewHolder = new ViewHolder(convertView, 2);
                    convertView.setTag(viewHolder);
                    break;
                default: //选择题
                    convertView = layoutInflater.inflate(R.layout.activity_choice, null, false);
                    viewHolder = new ViewHolder(convertView);
                    convertView.setTag(viewHolder);
                    break;
            }

        } else {
            convertView = viewLayoutHolder.remove(data.getType());
            viewHolder = (ViewHolder) convertView.getTag();
        }
        resetUI(data, viewHolder);
        switch (data.getType()) {
            case PROBLEM_TYPE_PIC://图片判断题
                /*viewHolder.et_id_pictureanswer.setTag(position);
                viewHolder.tv_id_pictitle.setText(data.getTitle());
                if (viewHolder.et_id_pictureanswer.getTag().equals(position)) {
                    viewHolder.et_id_pictureanswer.setText(data.getAnswer());
                }*/
                initImage(imgdatas.get(index++).getUrllist(), viewHolder);
                setSelectAnswer(position, viewHolder, data);
                // initEventForPic(viewHolder, position);
                break;
            case PROBLEM_TYPE_JUDGE://判断题
                viewHolder.tv_id_awa.setTag(position);
                viewHolder.tv_id_awb.setTag(position);
                viewHolder.problemtype.setText(data.getTypename());
                viewHolder.title.setText(data.getTitle());
                viewHolder.answera.setText(R.string.tv_true);
                viewHolder.answerb.setText(R.string.tv_false);
                viewHolder.ll_id_awc.setVisibility(View.GONE);
                viewHolder.ll_id_awd.setVisibility(View.GONE);
                switch (data.getAnswer()) {
                    case "A":
                        if (viewHolder.tv_id_awa.getTag().equals(position)) {
                            setSelected(viewHolder.tv_id_awa);
                        }
                        break;
                    case "B":
                        if (viewHolder.tv_id_awb.getTag().equals(position)) {
                            setSelected(viewHolder.tv_id_awb);
                        }
                        break;
                }
                //initEventForjudge(viewHolder,position,data);
                break;
            case PROBLEM_TYPE_MULTI:
                if (data.getE().length() >= 0) {
                    viewHolder.ll_id_awe.setVisibility(View.VISIBLE);
                    viewHolder.cb_id_e.setTag(position);
                    viewHolder.answere.setText(data.getE());
                }
                viewHolder.cb_id_a.setTag(position);
                viewHolder.cb_id_b.setTag(position);
                viewHolder.cb_id_c.setTag(position);
                viewHolder.cb_id_d.setTag(position);

                viewHolder.problemtype.setText(data.getTypename());
                viewHolder.title.setText(data.getTitle());
                viewHolder.answera.setText(data.getA());
                viewHolder.answerb.setText(data.getB());
                viewHolder.answerc.setText(data.getC());
                viewHolder.answerd.setText(data.getD());
                String manswer = data.getAnswer();
                for (int i = 0; i < manswer.length(); i++) {
                    String s = String.valueOf(manswer.charAt(i));
                    switch (s) {
                        case "A":
                            if (viewHolder.cb_id_a.getTag().equals(position)) {
                                viewHolder.cb_id_a.setChecked(true);
                            }
                            break;
                        case "B":
                            if (viewHolder.cb_id_b.getTag().equals(position)) {
                                viewHolder.cb_id_b.setChecked(true);
                            }
                            break;
                        case "C":
                            if (viewHolder.cb_id_c.getTag().equals(position)) {
                                viewHolder.cb_id_c.setChecked(true);
                            }
                            break;
                        case "D":
                            if (viewHolder.cb_id_d.getTag().equals(position)) {
                                viewHolder.cb_id_d.setChecked(true);
                            }
                            break;
                        case "E":
                            if (viewHolder.cb_id_e.getTag().equals(position)) {
                                viewHolder.cb_id_e.setChecked(true);
                            }
                            break;
                    }
                }
                break;
            default://选择题
                setSelectAnswer(position, viewHolder, data);

                //initEventForSelect(viewHolder, position, data);

        }
        return convertView;
    }

    private void setSelectAnswer(int position, ViewHolder viewHolder, lcproblem data) {
        viewHolder.tv_id_awa.setTag(position);
        viewHolder.tv_id_awb.setTag(position);
        viewHolder.tv_id_awc.setTag(position);
        viewHolder.tv_id_awd.setTag(position);

        viewHolder.problemtype.setText(data.getTypename());
        viewHolder.title.setText(data.getTitle());
        viewHolder.answera.setText(data.getA());
        viewHolder.answerb.setText(data.getB());
        viewHolder.answerc.setText(data.getC());
        viewHolder.answerd.setText(data.getD());
        String answer = data.getAnswer();
        for (int i = 0; i < answer.length(); i++) {
            String s = String.valueOf(answer.charAt(i));
            switch (s) {
                case "A":
                    if (viewHolder.tv_id_awa.getTag().equals(position)) {
                        setSelected(viewHolder.tv_id_awa);
                    }
                    break;
                case "B":
                    if (viewHolder.tv_id_awb.getTag().equals(position)) {
                        setSelected(viewHolder.tv_id_awb);
                    }
                    break;
                case "C":
                    if (viewHolder.tv_id_awc.getTag().equals(position)) {
                        setSelected(viewHolder.tv_id_awc);
                    }
                    break;
                case "D":
                    if (viewHolder.tv_id_awd.getTag().equals(position)) {
                        setSelected(viewHolder.tv_id_awd);
                    }
                    break;
            }
        }
    }

    /**
     * 自测题型界面（可以点击）
     *
     * @param position
     * @return
     */
    @NonNull
    private View getViewForMineExam(int position) {
        //Log.e("postion",String.valueOf(position));
        View convertView = null;
        ViewHolder viewHolder = null;
        lcproblem data = mdatas.get(position);
        answer answer = answers.get(position);
        //multans = new int[]{0,0,0,0,0};

        //题型判断，1---图片判断题     0---选择题
        //type = data.getType() == 1 ? 0 : 1;
        if (viewLayoutHolder.size() <= 3) {
            switch (data.getType()) {
                case PROBLEM_TYPE_PIC://图片判断题
                    convertView = layoutInflater.inflate(R.layout.activity_picturechoice, null, false);
                    viewHolder = new ViewHolder(convertView, 1);
                    convertView.setTag(viewHolder);
                    break;
                case PROBLEM_TYPE_MULTI://多选题
                    convertView = layoutInflater.inflate(R.layout.activity_multichoice, null, false);
                    viewHolder = new ViewHolder(convertView, 2);
                    convertView.setTag(viewHolder);
                    break;
                default: //选择题
                    convertView = layoutInflater.inflate(R.layout.activity_choice, null, false);
                    viewHolder = new ViewHolder(convertView);
                    convertView.setTag(viewHolder);
                    break;
            }

        } else {
            convertView = viewLayoutHolder.remove(data.getType());
            viewHolder = (ViewHolder) convertView.getTag();
        }
        resetUI(data, viewHolder);
        switch (data.getType()) {
            case PROBLEM_TYPE_PIC://图片判断题
                /*viewHolder.et_id_pictureanswer.setTag(position);
                viewHolder.tv_id_pictitle.setText(data.getTitle());
                if (viewHolder.et_id_pictureanswer.getTag().equals(position)) {
                    viewHolder.et_id_pictureanswer.setText(answer.getAnswer());
                }*/

                initImage(imgdatas.get(position - index).getUrllist(), viewHolder);
                setSelected(position, viewHolder, data, answer);
                //initEventForPic(viewHolder, position);
                initEventForSelect(viewHolder, position, data);
                break;
            case PROBLEM_TYPE_JUDGE://判断题
                viewHolder.tv_id_awa.setTag(position);
                viewHolder.tv_id_awb.setTag(position);
                viewHolder.problemtype.setText(data.getTypename());
                viewHolder.title.setText(data.getTitle());
                viewHolder.answera.setText(R.string.tv_true);
                viewHolder.answerb.setText(R.string.tv_false);
                viewHolder.ll_id_awc.setVisibility(View.GONE);
                viewHolder.ll_id_awd.setVisibility(View.GONE);
                if (!(answer.getIsnull()==1?true:false)) {
                    switch (answer.getAnswer()) {
                        case "A":
                            if (viewHolder.tv_id_awa.getTag().equals(position)) {
                                setSelected(viewHolder.tv_id_awa);
                            }
                            break;
                        case "B":
                            if (viewHolder.tv_id_awb.getTag().equals(position)) {
                                setSelected(viewHolder.tv_id_awb);
                            }
                            break;
                    }
                }
                initEventForjudge(viewHolder, position, data);
                break;
            case PROBLEM_TYPE_MULTI://多选题
                if (data.getE().length() >= 0) {
                    viewHolder.ll_id_awe.setVisibility(View.VISIBLE);
                    viewHolder.cb_id_e.setTag(position);
                    viewHolder.answere.setText(data.getE());
                }
                viewHolder.cb_id_a.setTag(position);
                viewHolder.cb_id_b.setTag(position);
                viewHolder.cb_id_c.setTag(position);
                viewHolder.cb_id_d.setTag(position);

                viewHolder.problemtype.setText(data.getTypename());
                viewHolder.title.setText(data.getTitle());
                viewHolder.answera.setText(data.getA());
                viewHolder.answerb.setText(data.getB());
                viewHolder.answerc.setText(data.getC());
                viewHolder.answerd.setText(data.getD());
                //String manswer = data.getAnswer();
                if (!(answer.getIsnull()==1?true:false)) {
                    for (int i = 0; i < answer.getAnswer().length(); i++) {
                        String s = String.valueOf(answer.getAnswer().charAt(i));
                        switch (s) {
                            case "A":
                                if (viewHolder.cb_id_a.getTag().equals(position)) {
                                    viewHolder.cb_id_a.setChecked(true);
                                }
                                break;
                            case "B":
                                if (viewHolder.cb_id_b.getTag().equals(position)) {
                                    viewHolder.cb_id_b.setChecked(true);
                                }
                                break;
                            case "C":
                                if (viewHolder.cb_id_c.getTag().equals(position)) {
                                    viewHolder.cb_id_c.setChecked(true);
                                }
                                break;
                            case "D":
                                if (viewHolder.cb_id_d.getTag().equals(position)) {
                                    viewHolder.cb_id_d.setChecked(true);
                                }
                                break;
                            case "E":
                                if (viewHolder.cb_id_e.getTag().equals(position)) {
                                    viewHolder.cb_id_e.setChecked(true);
                                }
                                break;
                        }
                    }
                }
                initEventForMulti(viewHolder);
                break;
            default://选择题
                setSelected(position, viewHolder, data, answer);
                //setSelectAnswer(position, viewHolder, data);
                initEventForSelect(viewHolder, position, data);
                break;

        }
        return convertView;
    }

    private void setSelected(int position, ViewHolder viewHolder, lcproblem data, answer answer) {
        viewHolder.tv_id_awa.setTag(position);
        viewHolder.tv_id_awb.setTag(position);
        viewHolder.tv_id_awc.setTag(position);
        viewHolder.tv_id_awd.setTag(position);

        viewHolder.problemtype.setText(data.getTypename());
        viewHolder.title.setText(data.getTitle());
        viewHolder.answera.setText(data.getA());
        viewHolder.answerb.setText(data.getB());
        viewHolder.answerc.setText(data.getC());
        viewHolder.answerd.setText(data.getD());
        if (!(answer.getIsnull()==1?true:false)) {
            switch (answer.getAnswer()) {
                case "A":
                    if (viewHolder.tv_id_awa.getTag().equals(position)) {
                        setSelected(viewHolder.tv_id_awa);
                    }
                    break;
                case "B":
                    if (viewHolder.tv_id_awb.getTag().equals(position)) {
                        setSelected(viewHolder.tv_id_awb);
                    }
                    break;
                case "C":
                    if (viewHolder.tv_id_awc.getTag().equals(position)) {
                        setSelected(viewHolder.tv_id_awc);
                    }
                    break;
                case "D":
                    if (viewHolder.tv_id_awd.getTag().equals(position)) {
                        setSelected(viewHolder.tv_id_awd);
                    }
                    break;
            }
        }
    }

    /**
     * 显示图片
     */
    private void initImage(List<String> imageUrlList, ViewHolder viewHolder) {
        Glide.with(mContext).load(imageUrlList.get(0)).crossFade().into(viewHolder.iv_id_a);
        Glide.with(mContext).load(imageUrlList.get(1)).crossFade().into(viewHolder.iv_id_b);
        Glide.with(mContext).load(imageUrlList.get(2)).crossFade().into(viewHolder.iv_id_c);
        Glide.with(mContext).load(imageUrlList.get(3)).crossFade().into(viewHolder.iv_id_d);
        /*ImageLoader.getInstance().displayImage(imageUrlList.get(0), viewHolder.iv_id_a);
        ImageLoader.getInstance().displayImage(imageUrlList.get(1), viewHolder.iv_id_b);
        ImageLoader.getInstance().displayImage(imageUrlList.get(2), viewHolder.iv_id_c);
        ImageLoader.getInstance().displayImage(imageUrlList.get(3), viewHolder.iv_id_d);*/
    }

    /**
     * 多选题事件声明
     */
    private void initEventForMulti(ViewHolder viewHolder) {
        viewHolder.cb_id_a.setOnCheckedChangeListener(onCheckBoxListener);
        viewHolder.cb_id_b.setOnCheckedChangeListener(onCheckBoxListener);
        viewHolder.cb_id_c.setOnCheckedChangeListener(onCheckBoxListener);
        viewHolder.cb_id_d.setOnCheckedChangeListener(onCheckBoxListener);
        viewHolder.cb_id_e.setOnCheckedChangeListener(onCheckBoxListener);
    }

    /*private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            resetUI(data, viewHolder);
            setAnswer("A", position);
            setSelected(viewHolder.tv_id_awa);
        }
    };*/
    /**
     * 选择题的事件声明
     *
     * @param viewHolder
     * @param position
     * @param data
     */
    private void initEventForSelect(final ViewHolder viewHolder, final int position, final lcproblem data) {
        viewHolder.ll_id_awa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetUI(data, viewHolder);
                setAnswer("A", position);
                setSelected(viewHolder.tv_id_awa);
            }
        });
        viewHolder.ll_id_awb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetUI(data, viewHolder);
                setAnswer("B", position);
                setSelected(viewHolder.tv_id_awb);
            }
        });
        viewHolder.ll_id_awc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetUI(data, viewHolder);
                setAnswer("C", position);
                setSelected(viewHolder.tv_id_awc);
            }
        });
        viewHolder.ll_id_awd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetUI(data, viewHolder);
                setAnswer("D", position);
                setSelected(viewHolder.tv_id_awd);
            }
        });
    }

    /**
     * 判断题的事件声明
     *
     * @param viewHolder
     * @param data
     */

    private void initEventForjudge(final ViewHolder viewHolder, final int position, final lcproblem data) {
        viewHolder.ll_id_awa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetUI(data, viewHolder);
                setAnswer("A", position);
                setSelected(viewHolder.tv_id_awa);
            }
        });
        viewHolder.ll_id_awb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetUI(data, viewHolder);
                setAnswer("B", position);
                setSelected(viewHolder.tv_id_awb);
            }
        });
    }

    /**
     * 设置选中状态
     */
    private void setSelected(TextView view) {
        view.setBackgroundResource(R.drawable.circle_choice_selected);
        view.setTextColor(mContext.getResources().getColor(R.color.white));
    }


    /**
     * 重置UI
     */
    private void resetUI(lcproblem data, ViewHolder viewHolder) {
        switch (data.getType()) {
            case PROBLEM_TYPE_PIC://图片判断题
                resetTextView(viewHolder.tv_id_awa);
                resetTextView(viewHolder.tv_id_awb);
                resetTextView(viewHolder.tv_id_awc);
                resetTextView(viewHolder.tv_id_awd);
                //viewHolder.et_id_pictureanswer.setText("");
                break;
            case PROBLEM_TYPE_JUDGE://判断题
                resetTextView(viewHolder.tv_id_awa);
                resetTextView(viewHolder.tv_id_awb);
                break;
            case PROBLEM_TYPE_MULTI://多选题
                viewHolder.cb_id_a.setOnCheckedChangeListener(null);
                viewHolder.cb_id_b.setOnCheckedChangeListener(null);
                viewHolder.cb_id_c.setOnCheckedChangeListener(null);
                viewHolder.cb_id_d.setOnCheckedChangeListener(null);
                viewHolder.cb_id_e.setOnCheckedChangeListener(null);

                viewHolder.cb_id_a.setChecked(false);
                viewHolder.cb_id_b.setChecked(false);
                viewHolder.cb_id_c.setChecked(false);
                viewHolder.cb_id_d.setChecked(false);
                viewHolder.cb_id_e.setChecked(false);
                break;
            default://选择题
                resetTextView(viewHolder.tv_id_awa);
                resetTextView(viewHolder.tv_id_awb);
                resetTextView(viewHolder.tv_id_awc);
                resetTextView(viewHolder.tv_id_awd);

        }
    }

    /**
     * 重置圆形文本选项
     *
     * @param view
     */
    private void resetTextView(TextView view) {
        view.setTextColor(mContext.getResources().getColor(R.color.textblack));
        view.setBackgroundResource(R.drawable.circle_choice);
    }

    /**
     * 图片类型的事件声明
     * @param viewHolder
     * @param answer
     * @param position
     */
    /*private void initEventForPic(final ViewHolder viewHolder, final int position) {
        viewHolder.et_id_pictureanswer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {

                    // 此处为得到焦点时的处理内容

                } else {

                    // 此处为失去焦点时的处理内容
                    manswer = ((EditText) view).getText().toString();
                    setAnswer(manswer, position);
                    //answers.get(position).setAnswer(manswer);
                    ///onTextChangedListener.notifyAnswer(manswer);
                }
            }
        });
    }*/


    /**
     * 将答案填入数据
     *
     * @param manswer
     * @param position
     */
    private void setAnswer(String manswer, int position) {
        //Log.e("postiont",String.valueOf(position));
        answer answer = answers.get(position);
        answer.setNum((long)(position+1));
        if (manswer.equals("")) {//判断是否作答
            answer.setIsnull(1);
        } else {
            answer.setIsnull(0);
        }
        answer.setAnswer(manswer);
        if (manswer.equals(mdatas.get(position).getAnswer())) {
            answer.setIstrue(1);
        } else {
            answer.setIstrue(0);
        }
    }

    /**
     * 获取多选答案
     *
     * @return
     */
    private String getMultAnswer() {
        manswer = "";
        for (int i = 0; i < multans.length; i++) {
            if (multans[i] == 1) {
                manswer += ans[i];
            }
        }
        Log.e("manswer", manswer);
        return manswer;
    }

    class ViewHolder {
        TextView problemtype;
        TextView title;
        TextView answera;
        TextView answerb;
        TextView answerc;
        TextView answerd;
        TextView answere;
        TextView tv_id_pictitle;

        TextView tv_id_awa, tv_id_awb, tv_id_awc, tv_id_awd;

        RelativeLayout ll_id_awa, ll_id_awb, ll_id_awc, ll_id_awd, ll_id_awe;

        CheckBox cb_id_a, cb_id_b, cb_id_c, cb_id_d, cb_id_e;

        ImageView iv_id_a, iv_id_b, iv_id_c, iv_id_d;

        public ViewHolder(View view) {

            tv_id_awa = (TextView) view.findViewById(R.id.tv_id_awa);
            tv_id_awb = (TextView) view.findViewById(R.id.tv_id_awb);
            tv_id_awc = (TextView) view.findViewById(R.id.tv_id_awc);
            tv_id_awd = (TextView) view.findViewById(R.id.tv_id_awd);

            initview(view);

        }

        private void initview(View view) {
            problemtype = (TextView) view.findViewById(R.id.tv_id_problemtype);
            title = (TextView) view.findViewById(R.id.tv_id_title);
            answera = (TextView) view.findViewById(R.id.tv_id_answera);
            answerb = (TextView) view.findViewById(R.id.tv_id_answerb);
            answerc = (TextView) view.findViewById(R.id.tv_id_answerc);
            answerd = (TextView) view.findViewById(R.id.tv_id_answerd);
            ll_id_awa = (RelativeLayout) view.findViewById(R.id.ll_id_awa);
            ll_id_awb = (RelativeLayout) view.findViewById(R.id.ll_id_awb);
            ll_id_awc = (RelativeLayout) view.findViewById(R.id.ll_id_awc);
            ll_id_awd = (RelativeLayout) view.findViewById(R.id.ll_id_awd);
        }

        public ViewHolder(View view, int n) {
            switch (n) {
                case 1:
                    initview(view);

                    tv_id_awa = (TextView) view.findViewById(R.id.tv_id_awa);
                    tv_id_awb = (TextView) view.findViewById(R.id.tv_id_awb);
                    tv_id_awc = (TextView) view.findViewById(R.id.tv_id_awc);
                    tv_id_awd = (TextView) view.findViewById(R.id.tv_id_awd);

                    iv_id_a = (ImageView) view.findViewById(R.id.iv_id_a);
                    iv_id_b = (ImageView) view.findViewById(R.id.iv_id_b);
                    iv_id_c = (ImageView) view.findViewById(R.id.iv_id_c);
                    iv_id_d = (ImageView) view.findViewById(R.id.iv_id_d);
                    break;
                case 2:
                    cb_id_a = (CheckBox) view.findViewById(R.id.cb_id_a);
                    cb_id_b = (CheckBox) view.findViewById(R.id.cb_id_b);
                    cb_id_c = (CheckBox) view.findViewById(R.id.cb_id_c);
                    cb_id_d = (CheckBox) view.findViewById(R.id.cb_id_d);
                    cb_id_e = (CheckBox) view.findViewById(R.id.cb_id_e);

                    answere = (TextView) view.findViewById(R.id.tv_id_answere);
                    ll_id_awe = (RelativeLayout) view.findViewById(R.id.ll_id_awe);
                    initview(view);
                    break;
            }

        }
    }
}
