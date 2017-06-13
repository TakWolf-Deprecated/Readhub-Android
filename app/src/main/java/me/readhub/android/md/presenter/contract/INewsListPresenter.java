package me.readhub.android.md.presenter.contract;

public interface INewsListPresenter {

    void refreshNewsListAsyncTask();

    void loadMoreNewsListAsyncTask(long lastCursor);

}
