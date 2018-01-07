package me.readhub.android.md.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;

import me.readhub.android.md.model.api.ApiClient;
import me.readhub.android.md.model.entity.News;
import me.readhub.android.md.model.entity.Pageable;
import me.readhub.android.md.presenter.contract.INewsListPresenter;
import me.readhub.android.md.presenter.contract.IPageablePresenter;
import me.readhub.android.md.ui.view.INewsListView;
import me.readhub.android.md.ui.viewholder.NewsListController;
import retrofit2.Call;

public class NewsListPresenter implements INewsListPresenter {

    private static final int PAGE_SIZE = 20;

    @NewsListController.Tab
    private final int tab;

    private final IPageablePresenter<News> pageablePresenter;

    public NewsListPresenter(@NonNull Activity activity, @NonNull INewsListView newsListView, @NewsListController.Tab int tab) {
        this.tab = tab;
        pageablePresenter = new PageablePresenter<>(activity, newsListView);
    }

    @Override
    public void refreshNewsListAsyncTask() {
        Call<Pageable<News>> call;
        if (tab == NewsListController.TAB_NEWS) {
            call = ApiClient.service.getNewsList(null, PAGE_SIZE);
        } else if (tab == NewsListController.TAB_TECHNEWS) {
            call = ApiClient.service.getTechNewsList(null, PAGE_SIZE);
        } else {
            throw new AssertionError("Unknown tab type.");
        }
        pageablePresenter.refreshAsyncTask(call);
    }

    @Override
    public void loadMoreNewsListAsyncTask(long lastCursor) {
        Call<Pageable<News>> call;
        if (tab == NewsListController.TAB_NEWS) {
            call = ApiClient.service.getNewsList(lastCursor, PAGE_SIZE);
        } else if (tab == NewsListController.TAB_TECHNEWS) {
            call = ApiClient.service.getTechNewsList(lastCursor, PAGE_SIZE);
        } else {
            throw new AssertionError("Unknown tab type.");
        }
        pageablePresenter.loadMoreAsyncTask(call);
    }

}
