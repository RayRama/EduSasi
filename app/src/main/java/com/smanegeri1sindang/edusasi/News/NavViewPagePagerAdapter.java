package com.smanegeri1sindang.edusasi.News;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class NavViewPagePagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments = new ArrayList<>();

    public NavViewPagePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
