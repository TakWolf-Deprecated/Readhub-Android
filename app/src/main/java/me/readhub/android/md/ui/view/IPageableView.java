package me.readhub.android.md.ui.view;

import android.support.annotation.NonNull;

import me.readhub.android.md.model.entity.Pageable;

public interface IPageableView<Data> {

    void onRefreshOk(@NonNull Pageable<Data> pageable);

    void onRefreshError(@NonNull String message);

    void onLoadMoreOk(@NonNull Pageable<Data> pageable);

    void onLoadMoreError(@NonNull String message);

}
