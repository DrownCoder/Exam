package nwsuaf.com.exam.customview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.adapter.BottomGridViewAdapter;
import nwsuaf.com.exam.util.MeasureUtil;

/**
 * Created by Administrator on 2016/7/5.
 */
public class ProblemListPopupWindow extends PopupWindow{
    private View contentView;
    private Context context;
    private BottomGridViewAdapter gridViewAdapter;
    //当前位置
    private int currentitem;
    //总共题量
    private int totalcount;
    private GridView problemlist;

    private TextView tv_id_progress,tv_id_total;

    public interface onProblemItemClickListener{
        void onGridItemClicked(int position);
    }

    private onProblemItemClickListener onProblemItemClickListener;

    public void setOnProblemItemClickListener(onProblemItemClickListener onProblemItemClickListener) {
        this.onProblemItemClickListener = onProblemItemClickListener;
    }



    public ProblemListPopupWindow(Context context, int currentitem,int totalcount){
        this.currentitem = currentitem;
        this.totalcount = totalcount;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popupwindow_layout, null);
        this.context = context;
        this.setContentView(contentView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(MeasureUtil.getWindowHeight(context) * 1 / 2);
        //this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popwin_anim_style);
        setBackgroundLight();

        setOutsideDismiss(contentView);

        //实例化一个ColorDrawable颜色为半透明
        /*ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背�?
        this.setBackgroundDrawable(dw);*/
        initViews(contentView);
        initViewSource();
        initEvents();
    }

    /*
    点击外部消失窗口
     */
    private void setOutsideDismiss(View view) {
        this.setOutsideTouchable(true);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isShowing()) {
                    dissmissWindow();
                }
                return false;
            }
        });
    }


    //设置背景半透明
    private void setBackgroundLight() {
        WindowManager.LayoutParams params=((Activity)context).getWindow().getAttributes();
        params.alpha=1.0f;
        ((Activity)context).getWindow().setAttributes(params);
    }
    //恢复背景
    private void setBackgroundreset(){
        WindowManager.LayoutParams params=((Activity)context).getWindow().getAttributes();
        params.alpha=1.0f;
        ((Activity)context).getWindow().setAttributes(params);
    }

    private void initViewSource() {
        gridViewAdapter = new BottomGridViewAdapter(context,currentitem,totalcount);
        problemlist.setAdapter(gridViewAdapter);
        tv_id_total.setText(String.valueOf(totalcount));
    }

    private void initEvents() {
/*        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmissWindow();
            }
        });*/
        problemlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dissmissWindow();

                if(onProblemItemClickListener!=null){
                    onProblemItemClickListener.onGridItemClicked(position);
                }
            }
        });
    }


    //消失窗口
    private void dissmissWindow(){
        setBackgroundreset();

        dismiss();

/*        if(listener != null){
            listener.onCloseClicked(isUpdateList);
        }*/
    }

    private void initViews(View view) {
        problemlist = (GridView) view.findViewById(R.id.gl_id_popop_problemlist);
        tv_id_progress = (TextView) view.findViewById(R.id.tv_id_progress);
        tv_id_total = (TextView) view.findViewById(R.id.tv_id_total);
    }

    /**
     * 设置当前题目
     */
    public void setCurrentIndex(int index){
        problemlist.setSelection(index);
        tv_id_progress.setText(String.valueOf(index + 1));
    }

}
