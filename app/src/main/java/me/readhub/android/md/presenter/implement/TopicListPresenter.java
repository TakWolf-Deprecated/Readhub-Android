package me.readhub.android.md.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;

import me.readhub.android.md.model.api.ApiClient;
import me.readhub.android.md.model.entity.Topic;
import me.readhub.android.md.presenter.contract.IPageablePresenter;
import me.readhub.android.md.presenter.contract.ITopicListPresenter;
import me.readhub.android.md.ui.view.ITopicListView;

public class TopicListPresenter implements ITopicListPresenter {

    private static final int PAGE_SIZE = 20;

    private final IPageablePresenter<Topic> pageablePresenter;

    public TopicListPresenter(@NonNull Activity activity, @NonNull ITopicListView topicListView) {
        pageablePresenter = new PageablePresenter<>(activity, topicListView);
    }

    @Override
    public void refreshTopicListAsyncTask() {
        pageablePresenter.refreshAsyncTask(ApiClient.service.getTopicList(null, PAGE_SIZE));
    }

    @Override
    public void loadMoreTopicListAsyncTask(long lastCursor) {
        pageablePresenter.loadMoreAsyncTask(ApiClient.service.getTopicList(lastCursor, PAGE_SIZE));
    }

}
