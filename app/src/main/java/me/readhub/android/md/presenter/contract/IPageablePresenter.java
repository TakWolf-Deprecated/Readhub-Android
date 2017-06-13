package me.readhub.android.md.presenter.contract;

import android.support.annotation.NonNull;

import me.readhub.android.md.model.entity.Pageable;
import retrofit2.Call;

public interface IPageablePresenter<Data> {

    void refreshAsyncTask(@NonNull Call<Pageable<Data>> call);

    void loadMoreAsyncTask(@NonNull Call<Pageable<Data>> call);

}
