package nwsuaf.com.exam.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nwsuaf.com.exam.fragment.ExamDetailFragment;

/**
 * Created by dengzhaoxuan on 2017/4/13.
 */

public class FragmentAdapter extends FragmentPagerAdapter{
    private List<Fragment> mFragmentList;
    public FragmentAdapter(FragmentManager fm , List<Fragment> fragments) {
        super(fm);
        if (fragments == null) {
            this.mFragmentList = new ArrayList<>();
        }else{
            this.mFragmentList = fragments;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ExamDetailFragment)object).clearViewCache();
        super.destroyItem(container, position, object);
    }
}
