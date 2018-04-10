package me.readhub.android.md.ui.holder;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.takwolf.android.hfrecyclerview.HeaderAndFooterRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.readhub.android.md.R;
import me.readhub.android.md.model.entity.Pageable;
import me.readhub.android.md.model.entity.Topic;
import me.readhub.android.md.presenter.contract.ITopicListPresenter;
import me.readhub.android.md.presenter.implement.TopicListPresenter;
import me.readhub.android.md.ui.adapter.GapItemDecoration;
import me.readhub.android.md.ui.adapter.TopicListAdapter;
import me.readhub.android.md.ui.listener.FloatingTipButtonBehaviorListener;
import me.readhub.android.md.ui.util.ToastUtils;
import me.readhub.android.md.ui.view.ITopicListView;

public class TopicListController extends Controller implements SwipeRefreshLayout.OnRefreshListener, LoadMoreFooter.OnLoadMoreListener, ITopicListView {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler_view)
    HeaderAndFooterRecyclerView recyclerView;

    @BindView(R.id.btn_back_to_top_and_refresh)
    View btnBackToTopAndRefresh;

    private final Activity activity;
    private final View contentView;

    private final LoadMoreFooter loadMoreFooter;
    private final TopicListAdapter listAdapter;

    private final ITopicListPresenter topicListPresenter;

    public TopicListController(@NonNull Activity activity, @NonNull ViewPager viewPager) {
        this.activity = activity;
        contentView = LayoutInflater.from(activity).inflate(R.layout.controller_topic_list, viewPager, false);
        ButterKnife.bind(this, contentView);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.addItemDecoration(new GapItemDecoration(activity));
        recyclerView.addOnScrollListener(new FloatingTipButtonBehaviorListener.ForRecyclerView(btnBackToTopAndRefresh));

        loadMoreFooter = new LoadMoreFooter(activity, recyclerView, this);

        listAdapter = new TopicListAdapter(activity);
        recyclerView.setAdapter(listAdapter);

        topicListPresenter = new TopicListPresenter(activity, this);

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
        topicListPresenter.refreshTopicListAsyncTask();
    }

    @Override
    public void onLoadMore() {
        topicListPresenter.loadMoreTopicListAsyncTask(listAdapter.getTopicList().get(listAdapter.getTopicList().size() - 1).getOrder());
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
    public void onRefreshOk(@NonNull Pageable<Topic> pageable) {
        listAdapter.getTopicList().clear();
        listAdapter.getTopicList().addAll(pageable.getDataList());
        listAdapter.clearExpandStates();
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
    public void onLoadMoreOk(@NonNull Pageable<Topic> pageable) {
        int startPosition = listAdapter.getItemCount();
        listAdapter.getTopicList().addAll(pageable.getDataList());
        listAdapter.notifyItemRangeInserted(startPosition, pageable.getDataList().size());
        loadMoreFooter.setState(pageable.getDataList().isEmpty() ? LoadMoreFooter.STATE_FINISHED : LoadMoreFooter.STATE_ENDLESS);
    }

    @Override
    public void onLoadMoreError(@NonNull String message) {
        ToastUtils.with(activity).show(message);
        loadMoreFooter.setState(LoadMoreFooter.STATE_FAILED);
    }

}
