package nwsuaf.com.exam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import nwsuaf.com.exam.R;

/**
 * Created by Administrator on 2017/1/13.
 */
public class BottomGridViewAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    //当前位置坐标
    private int correntitem;
    //所以题目总量
    private int totalcount;
    private Context context;

    public BottomGridViewAdapter(Context context,int correntitem,int totoalcount){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.correntitem = correntitem;
        this.totalcount = totoalcount;
    }
    @Override
    public int getCount() {
        return totalcount;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        TextView item;
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.grid_item, null);

            // construct an item tag
            item =(TextView) convertView.findViewById(R.id.id_grid_item_text);
            convertView.setTag(item);
        } else
        {
            item = (TextView) convertView.getTag();
        }
        item.setTag(position);
        resetTextView(item);
        item.setText(String.valueOf(position+1));
        if(position==correntitem&&item.getTag().equals(position)){
            item.setBackgroundResource(R.drawable.circle_choice_select);
            item.setTextColor(context.getResources().getColor(R.color.mainblue));
        }
        return convertView;
    }

    private void resetTextView(TextView item) {
        item.setBackgroundResource(R.drawable.circle_choice);
        item.setTextColor(context.getResources().getColor(R.color.textblack));
    }
}
