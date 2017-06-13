package me.readhub.android.md.ui.fragment;

import android.support.annotation.NonNull;

import me.readhub.android.md.ui.base.TitledFragment;

public class TopicListFragment extends TitledFragment {

    @NonNull
    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }


    @Override
    public int getPageTitleId() {
        return 0;
    }

}
