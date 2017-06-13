package me.readhub.android.md.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.readhub.android.md.ui.base.TitledFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private final List<TitledFragment> fragmentList = new ArrayList<>();
    private final Context context;

    public MainPagerAdapter(@NonNull Context context, @NonNull FragmentManager manager) {
        super(manager);
        this.context = context;

        // 初始化
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(fragmentList.get(position).getPageTitleId());
    }

}
