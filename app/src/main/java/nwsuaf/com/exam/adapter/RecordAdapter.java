package nwsuaf.com.exam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.entity.examdate;
import nwsuaf.com.exam.util.TimeUtils;

/**
 * Created by dengzhaoxuan on 2017/2/22.
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder>{
    private Context context;
    private List<examdate> datas;

    public RecordAdapter(Context context, List<examdate> datas){
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(
                context).inflate(R.layout.recordlist_item_layout, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(datas != null){
            examdate item = datas.get(position);

            holder.tv_card_date.setText(String.format(context.getResources().
                    getString(R.string.card_date),
                    TimeUtils.formatStringToDate(item.getDate())));
            holder.tv_card_total.setText(String.format(context.getResources().
                            getString(R.string.card_total),
                    String.valueOf(item.getTotalnum())));
            holder.tv_card_right.setText(String.format(context.getResources().
                            getString(R.string.card_right),
                    String.valueOf(item.getRight())));
            holder.tv_card_error.setText(String.format(context.getResources().
                            getString(R.string.card_error),
                    String.valueOf(item.getError())));
            holder.tv_card_time.setText(String.format(context.getResources().
                            getString(R.string.card_time),
                    String.valueOf(item.getTime())));
            holder.iv_id_onpic.setImageResource(item.getIspass()==1? R.drawable.pass_on: R.drawable.unpass_on);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_card_date,tv_card_total,tv_card_right,tv_card_error,tv_card_time;
        ImageView iv_id_onpic;
        public ViewHolder(View itemView) {
            super(itemView);

            tv_card_date = (TextView) itemView.findViewById(R.id.tv_card_date);
            tv_card_total = (TextView) itemView.findViewById(R.id.tv_card_total);
            tv_card_right = (TextView) itemView.findViewById(R.id.tv_card_right);
            tv_card_error = (TextView) itemView.findViewById(R.id.tv_card_error);
            tv_card_time = (TextView) itemView.findViewById(R.id.tv_card_time);

            iv_id_onpic = (ImageView) itemView.findViewById(R.id.iv_id_onpic);
        }
    }
}
