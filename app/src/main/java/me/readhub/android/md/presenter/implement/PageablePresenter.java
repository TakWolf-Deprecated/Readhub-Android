package me.readhub.android.md.presenter.implement;

import android.app.Activity;
import android.support.annotation.NonNull;

import me.readhub.android.md.model.api.ErrorResult;
import me.readhub.android.md.model.api.DefaultCallback;
import me.readhub.android.md.model.entity.Pageable;
import me.readhub.android.md.presenter.contract.IPageablePresenter;
import me.readhub.android.md.ui.view.IPageableView;
import okhttp3.Headers;
import retrofit2.Call;

public class PageablePresenter<Data> implements IPageablePresenter<Data> {

    private final Activity activity;
    private final IPageableView<Data> pageableView;

    private Call<Pageable<Data>> refreshCall = null;
    private Call<Pageable<Data>> loadMoreCall = null;

    public PageablePresenter(@NonNull Activity activity, @NonNull IPageableView<Data> pageableView) {
        this.activity = activity;
        this.pageableView = pageableView;
    }

    public void cancelRefreshCall() {
        if (refreshCall != null) {
            if (!refreshCall.isCanceled()) {
                refreshCall.cancel();
            }
            refreshCall = null;
        }
    }

    public void cancelLoadMoreCall() {
        if (loadMoreCall != null) {
            if (!loadMoreCall.isCanceled()) {
                loadMoreCall.cancel();
            }
            loadMoreCall = null;
        }
    }

    @Override
    public void refreshAsyncTask(@NonNull Call<Pageable<Data>> call) {
        if (refreshCall == null) {
            refreshCall = call;
            call.enqueue(new DefaultCallback<Pageable<Data>>(activity) {

                @Override
                public boolean onResultOk(int code, Headers headers, Pageable<Data> pageable) {
                    cancelLoadMoreCall();
                    pageableView.onRefreshOk(pageable);
                    return false;
                }

                @Override
                public boolean onResultError(int code, Headers headers, ErrorResult error) {
                    pageableView.onRefreshError(error.getMessage());
                    return false;
                }

                @Override
                public boolean onCallException(Throwable t, ErrorResult error) {
                    pageableView.onRefreshError(error.getMessage());
                    return false;
                }

                @Override
                public boolean onCallCancel() {
                    return true;
                }

                @Override
                public void onFinish() {
                    refreshCall = null;
                }

            });
        }
    }

    @Override
    public void loadMoreAsyncTask(@NonNull Call<Pageable<Data>> call) {
        if (loadMoreCall == null) {
            loadMoreCall = call;
            call.enqueue(new DefaultCallback<Pageable<Data>>(activity) {

                @Override
                public boolean onResultOk(int code, Headers headers, Pageable<Data> pageable) {
                    pageableView.onLoadMoreOk(pageable);
                    return false;
                }

                @Override
                public boolean onResultError(int code, Headers headers, ErrorResult error) {
                    pageableView.onLoadMoreError(error.getMessage());
                    return false;
                }

                @Override
                public boolean onCallException(Throwable t, ErrorResult error) {
                    pageableView.onLoadMoreError(error.getMessage());
                    return false;
                }

                @Override
                public boolean onCallCancel() {
                    return true;
                }

                @Override
                public void onFinish() {
                    loadMoreCall = null;
                }

            });
        }
    }

}
