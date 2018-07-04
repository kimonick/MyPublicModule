package com.kimonik.utilsmodule.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * * ====================================================================
 * name:            FragmentVPAdapter
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/10
 * description： viewpager的fragment适配器
 * history：
 * * ====================================================================
 */

public class FragmentVPAdapter extends FragmentPagerAdapter {
    private List<Fragment>  list;

    public FragmentVPAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
