package nwsuaf.com.exam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import nwsuaf.com.exam.R;

/**
 * Created by dengzhaoxuan on 2017/4/17.
 */

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mPicList;
    public PicAdapter(Context context, List<String> piclist) {
        this.mContext = context;
        this.mPicList = piclist;
        if (piclist == null) {
            this.mPicList = new ArrayList<>();
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_pic,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(mPicList!=null){
            Glide.with(mContext).load(mPicList.get(position)).crossFade().into(holder.img);
        }
    }

    @Override
    public int getItemCount() {
        return mPicList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        public ViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.iv_pic);
        }
    }
}
