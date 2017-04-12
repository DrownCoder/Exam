package nwsuaf.com.exam.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/17.
 */
public class HomePageAdapter extends FragmentStatePagerAdapter {//FragmentPagerAdapter

    //	private FragmentManager fm;
    private ArrayList<Fragment> fragments = null;
    private Context context;

    public HomePageAdapter(Context context, FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragments.get(arg0);
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return fragments.size();//hotIssuesList.size();
    }

}