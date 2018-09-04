package me.readhub.android.md.model.api;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BackgroundCallback<Data> implements Callback<Data>, CallbackLifecycle<Data> {

    @Override
    public final void onResponse(Call<Data> call, Response<Data> response) {
        boolean interrupt;
        if (response.isSuccessful()) {
            interrupt = onResultOk(response.code(), response.headers(), response.body());
        } else {
            interrupt = onResultError(response.code(), response.headers(), ErrorResult.from(response));
        }
        if (!interrupt) {
            onFinish();
        }
    }

    @Override
    public final void onFailure(Call<Data> call, Throwable t) {
        boolean interrupt;
        if (call.isCanceled()) {
            interrupt = onCallCancel();
        } else {
            interrupt = onCallException(t, ErrorResult.from(t));
        }
        if (!interrupt) {
            onFinish();
        }
    }

    @Override
    public boolean onResultOk(int code, Headers headers, Data data) {
        return false;
    }

    @Override
    public boolean onResultError(int code, Headers headers, ErrorResult errorResult) {
        return false;
    }

    @Override
    public boolean onCallCancel() {
        return false;
    }

    @Override
    public boolean onCallException(Throwable t, ErrorResult errorResult) {
        return false;
    }

    @Override
    public void onFinish() {}

}
