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
import me.readhub.android.md.R;
import me.readhub.android.md.model.entity.Pageable;
import me.readhub.android.md.model.entity.Topic;
import me.readhub.android.md.presenter.contract.ITopicListPresenter;
import me.readhub.android.md.presenter.implement.TopicListPresenter;
import me.readhub.android.md.ui.adapter.TopicListAdapter;
import me.readhub.android.md.ui.util.ToastUtils;
import me.readhub.android.md.ui.view.ITopicListView;
import me.readhub.android.md.ui.viewholder.LoadMoreFooter;

public class TopicListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoadMoreFooter.OnLoadMoreListener, ITopicListView {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler_view)
    HeaderAndFooterRecyclerView recyclerView;

    private LoadMoreFooter loadMoreFooter;
    private TopicListAdapter listAdapter;

    private ITopicListPresenter topicListPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadMoreFooter = new LoadMoreFooter(getContext(), recyclerView, this);

        listAdapter = new TopicListAdapter(getActivity());
        recyclerView.setAdapter(listAdapter);

        topicListPresenter = new TopicListPresenter(getActivity(), this);

        refreshLayout.setColorSchemeResources(R.color.color_primary);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        topicListPresenter.refreshTopicListAsyncTask();
    }

    @Override
    public void onLoadMore() {
        topicListPresenter.loadMoreTopicListAsyncTask(listAdapter.getTopicList().get(listAdapter.getTopicList().size() - 1).getOrder());
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
        ToastUtils.with(getContext()).show(message);
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
        ToastUtils.with(getContext()).show(message);
        loadMoreFooter.setState(LoadMoreFooter.STATE_FAILED);
    }

}
