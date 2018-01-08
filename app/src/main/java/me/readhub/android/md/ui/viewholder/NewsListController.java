package me.readhub.android.md.ui.viewholder;

import android.app.Activity;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

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

public class NewsListController extends Controller implements SwipeRefreshLayout.OnRefreshListener, LoadMoreFooter.OnLoadMoreListener, INewsListView {

    public static final int TAB_NEWS = 0;
    public static final int TAB_TECHNEWS = 1;

    @IntDef({TAB_NEWS, TAB_TECHNEWS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Tab {}

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler_view)
    HeaderAndFooterRecyclerView recyclerView;

    @BindView(R.id.btn_back_to_top_and_refresh)
    View btnBackToTopAndRefresh;

    private final Activity activity;
    private final View contentView;

    private final LoadMoreFooter loadMoreFooter;
    private final NewsListAdapter listAdapter;

    private final INewsListPresenter newsListPresenter;

    public NewsListController(@NonNull Activity activity, @NonNull ViewPager viewPager, @Tab int tab) {
        this.activity = activity;
        contentView = LayoutInflater.from(activity).inflate(R.layout.controller_news_list, viewPager, false);
        ButterKnife.bind(this, contentView);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.addItemDecoration(new GapItemDecoration(activity));
        recyclerView.addOnScrollListener(new FloatingTipButtonBehaviorListener.ForRecyclerView(btnBackToTopAndRefresh));

        loadMoreFooter = new LoadMoreFooter(activity, recyclerView, this);

        listAdapter = new NewsListAdapter(activity);
        recyclerView.setAdapter(listAdapter);

        newsListPresenter = new NewsListPresenter(activity, this, tab);

        refreshLayout.setColorSchemeResources(R.color.color_primary);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
        onRefresh();
    }

    @NonNull
    @Override
    public View getContentView() {
        return contentView;
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
        ToastUtils.with(activity).show(message);
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
        ToastUtils.with(activity).show(message);
        loadMoreFooter.setState(LoadMoreFooter.STATE_FAILED);
    }

}
