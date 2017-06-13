package me.readhub.android.md.presenter.contract;

public interface ITopicListPresenter {

    void refreshTopicListAsyncTask();

    void loadMoreTopicListAsyncTask(long lastCursor);

}
