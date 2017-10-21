package me.readhub.android.md.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.readhub.android.md.R;
import me.readhub.android.md.model.entity.News;
import me.readhub.android.md.model.entity.Pageable;
import me.readhub.android.md.presenter.contract.INewsListPresenter;
import me.readhub.android.md.presenter.implement.NewsListPresenter;
import me.readhub.android.md.ui.adapter.GapItemDecoration;
import me.readhub.android.md.ui.adapter.NewsListAdapter;
import me.readhub.android.md.ui.listener.FloatingTipButtonBehaviorListener;
import me.readhub.android.md.ui.util.ToastUtils;
import me.readhub.android.md.ui.view.INewsListView;
import me.readhub.android.md.ui.viewholder.LoadMoreFooter;

public class NewsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoadMoreFooter.OnLoadMoreListener, INewsListView {

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

    @BindView(R.id.btn_back_to_top_and_refresh)
    View btnBackToTopAndRefresh;

    private int tab;

    private LoadMoreFooter loadMoreFooter;
    private NewsListAdapter listAdapter;

    private INewsListPresenter newsListPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        tab = getArguments().getInt(EXTRA_TAB, TAB_NEWS);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new GapItemDecoration(getContext()));
        recyclerView.addOnScrollListener(new FloatingTipButtonBehaviorListener.ForRecyclerView(btnBackToTopAndRefresh));

        loadMoreFooter = new LoadMoreFooter(getContext(), recyclerView, this);

        listAdapter = new NewsListAdapter(getActivity());
        recyclerView.setAdapter(listAdapter);

        newsListPresenter = new NewsListPresenter(getActivity(), this, tab);

        refreshLayout.setColorSchemeResources(R.color.color_primary);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        newsListPresenter.refreshNewsListAsyncTask();
    }

    @Override
    public void onLoadMore() {
        newsListPresenter.loadMoreNewsListAsyncTask(listAdapter.getNewsList().get(listAdapter.getNewsList().size() - 1).getPublishDate().toInstant().toEpochMilli());
    }

    @OnClick(R.id.btn_back_to_top_and_refresh)
    void onBtnBackToTopAndRefreshClick() {
        recyclerView.scrollToPosition(0);
        if (!refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(true);
            onRefresh();
        }
    }

    @Override
    public void onRefreshOk(@NonNull Pageable<News> pageable) {
        listAdapter.getNewsList().clear();
        listAdapter.getNewsList().addAll(pageable.getDataList());
        listAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
        loadMoreFooter.setState(pageable.getDataList().isEmpty() ? LoadMoreFooter.STATE_DISABLED : LoadMoreFooter.STATE_ENDLESS);
    }

    @Override
    public void onRefreshError(@NonNull String message) {
        ToastUtils.with(getContext()).show(message);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreOk(@NonNull Pageable<News> pageable) {
        int startPosition = listAdapter.getItemCount();
        listAdapter.getNewsList().addAll(pageable.getDataList());
        listAdapter.notifyItemRangeInserted(startPosition, pageable.getDataList().size());
        loadMoreFooter.setState(pageable.getDataList().isEmpty() ? LoadMoreFooter.STATE_FINISHED : LoadMoreFooter.STATE_ENDLESS);
    }

    @Override
    public void onLoadMoreError(@NonNull String message) {
        ToastUtils.with(getContext()).show(message);
        loadMoreFooter.setState(LoadMoreFooter.STATE_FAILED);
    }

}
