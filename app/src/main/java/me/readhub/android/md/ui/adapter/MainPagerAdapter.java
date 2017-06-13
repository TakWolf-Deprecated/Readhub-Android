package me.readhub.android.md.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.readhub.android.md.R;
import me.readhub.android.md.ui.fragment.NewsListFragment;
import me.readhub.android.md.ui.fragment.TopicListFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private static final int[] titleIds = {
        R.string.tab_topic,
        R.string.tab_news,
        R.string.tab_technews
    };

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final Context context;

    public MainPagerAdapter(@NonNull Context context, @NonNull FragmentManager manager) {
        super(manager);
        this.context = context;

        fragmentList.add(new TopicListFragment());
        fragmentList.add(NewsListFragment.newInstance(NewsListFragment.TAB_NEWS));
        fragmentList.add(NewsListFragment.newInstance(NewsListFragment.TAB_TECHNEWS));
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
        return context.getString(titleIds[position]);
    }

}
