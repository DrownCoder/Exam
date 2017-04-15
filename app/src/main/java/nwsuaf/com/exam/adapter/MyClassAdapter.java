package nwsuaf.com.exam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.entity.netmodel.ClassInfo;

/**
 * Created by dengzhaoxuan on 2017/4/13.
 */

public class MyClassAdapter extends RecyclerView.Adapter<MyClassAdapter.ViewHolder> {
    private Context mContext;
    private List<ClassInfo> mData;
    private View.OnClickListener onClickListener;
    public interface ItemClickListener{
        void OnItemClick(int position);
    }
    public ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public MyClassAdapter(Context context , List<ClassInfo> data) {
        this.mContext = context;
        if(data == null){
            mData = new ArrayList<>();
        }else{
            this.mData = data;
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.class_item, parent,
                false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(mData != null){
            ClassInfo item = mData.get(position);
            holder.tvClassName.setText(item.getClassname());
            holder.tvOnline.setText(String.valueOf(item.getOnline()));
            holder.tvSubmit.setText(String.valueOf(item.getSubmit()));
            holder.tvUnSubmit.setText(String.valueOf(item.getUnsubmit()));
            holder.rooter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.OnItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rooter;
        private TextView tvClassName;
        private TextView tvOnline;
        private TextView tvSubmit;
        private TextView tvUnSubmit;
        public ViewHolder(View view){
            super(view);
            rooter = (RelativeLayout) view.findViewById(R.id.rl_rooter);
            tvClassName = (TextView) view.findViewById(R.id.tv_class_name);
            tvOnline = (TextView) view.findViewById(R.id.tv_join);
            tvSubmit = (TextView) view.findViewById(R.id.tv_submit);
            tvUnSubmit = (TextView) view.findViewById(R.id.tv_unsubmit);
        }
    }
}
