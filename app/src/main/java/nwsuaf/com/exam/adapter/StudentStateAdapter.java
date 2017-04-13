package nwsuaf.com.exam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.entity.netmodel.ClassInfo;
import nwsuaf.com.exam.entity.netmodel.StudentState;

/**
 * Created by dengzhaoxuan on 2017/4/13.
 */

public class StudentStateAdapter extends RecyclerView.Adapter<StudentStateAdapter.ViewHolder> {
    private Context mContext;
    private List<StudentState> mData;

    public StudentStateAdapter(Context context , List<StudentState> data) {
        this.mContext = context;
        this.mData = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.student_state_item, parent,
                false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(mData != null){
            StudentState item = mData.get(position);
            holder.tvStuName.setText(item.getName());
            holder.tvId.setText(String.format(mContext.getResources().
                            getString(R.string.stustate_id), item.getId()));
            if (item.isJoin()) {
                holder.tvJoin.setText(String.format(mContext.getResources().
                        getString(R.string.stustate_join), "已加入"));
                holder.tvJoin.setTextColor(mContext.getResources().getColor(R.color.my_wonderful_blue_color));
            }else{
                holder.tvJoin.setText(String.format(mContext.getResources().
                        getString(R.string.stustate_join), "未加入"));
                holder.tvJoin.setTextColor(mContext.getResources().getColor(R.color.lightred));
            }

            if (item.isSubmit()) {
                holder.tvSubmit.setText(String.format(mContext.getResources().
                        getString(R.string.stustate_join), "已提交"));
                holder.tvSubmit.setTextColor(mContext.getResources().getColor(R.color.my_wonderful_blue_color));
            }else{
                holder.tvSubmit.setText(String.format(mContext.getResources().
                        getString(R.string.stustate_join), "未提交"));
                holder.tvSubmit.setTextColor(mContext.getResources().getColor(R.color.lightred));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvStuName;
        private TextView tvId;
        private TextView tvSubmit;
        private TextView tvJoin;
        public ViewHolder(View view){
            super(view);
            tvStuName = (TextView) view.findViewById(R.id.tv_student_name);
            tvId = (TextView) view.findViewById(R.id.tv_student_id);
            tvJoin = (TextView) view.findViewById(R.id.tv_student_join);
            tvSubmit = (TextView) view.findViewById(R.id.tv_student_submit);
        }
    }
}
