package zhonghuass.ssml.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter{
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    private List<Fragment> mFragmentList;

    public void setFragments(ArrayList<Fragment> fragments) {
        mFragmentList = fragments;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragmentList.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

}
