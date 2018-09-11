package com.uautogo.qidian.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by uuun on 2017/5/31.
 */

public class AccountVpAdapter extends FragmentPagerAdapter {
    List<Fragment> list;
    List<String> listName;
    public AccountVpAdapter(FragmentManager fm, List<Fragment> list, List<String> listName) {
        super(fm);
        this.list = list;
        this.listName = listName;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return listName.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return listName.get(position%listName.size());
    }

}
