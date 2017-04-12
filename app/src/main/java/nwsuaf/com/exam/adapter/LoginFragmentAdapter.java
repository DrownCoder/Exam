package nwsuaf.com.exam.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by dengzhaoxuan on 2017/4/12.
 */

public class LoginFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mData;
    public LoginFragmentAdapter(FragmentManager fm , List<Fragment> data) {
        super(fm);
        mData = data;
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}
