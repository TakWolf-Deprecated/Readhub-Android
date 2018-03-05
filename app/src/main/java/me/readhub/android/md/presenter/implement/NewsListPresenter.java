package me.readhub.android.md.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;

import me.readhub.android.md.model.api.ApiClient;
import me.readhub.android.md.model.entity.News;
import me.readhub.android.md.presenter.contract.INewsListPresenter;
import me.readhub.android.md.presenter.contract.IPageablePresenter;
import me.readhub.android.md.ui.view.INewsListView;

public class NewsListPresenter implements INewsListPresenter {

    private static final int PAGE_SIZE = 20;

    @News.Type
    private final String type;

    private final IPageablePresenter<News> pageablePresenter;

    public NewsListPresenter(@NonNull Activity activity, @NonNull INewsListView newsListView, @News.Type String type) {
        this.type = type;
        pageablePresenter = new PageablePresenter<>(activity, newsListView);
    }

    @Override
    public void refreshNewsListAsyncTask() {
        pageablePresenter.refreshAsyncTask(ApiClient.service.getNewsList(type, null, PAGE_SIZE));
    }

    @Override
    public void loadMoreNewsListAsyncTask(long lastCursor) {
        pageablePresenter.loadMoreAsyncTask(ApiClient.service.getNewsList(type, lastCursor, PAGE_SIZE));
    }

}
