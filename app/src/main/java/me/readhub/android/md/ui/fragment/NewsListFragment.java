package me.readhub.android.md.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.readhub.android.md.R;

public class NewsListFragment extends Fragment {

    public static final int TAB_NEWS = 0;
    public static final int TAB_TECHNEWS = 1;
    private static final String EXTRA_TAB = "tab";

    @NonNull
    public static NewsListFragment newInstance(int tab) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_TAB, tab);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler_view)
    HeaderAndFooterRecyclerView recyclerView;

    private int tab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        tab = getArguments().getInt(EXTRA_TAB, TAB_NEWS);
    }

}
