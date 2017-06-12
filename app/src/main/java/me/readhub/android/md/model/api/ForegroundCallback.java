package me.readhub.android.md.model.api;

import android.app.Activity;
import android.support.annotation.NonNull;

import me.readhub.android.md.ui.util.ActivityUtils;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForegroundCallback<Data> implements Callback<Data>, CallbackLifecycle<Data> {

    private final Activity activity;

    public ForegroundCallback(@NonNull Activity activity) {
        this.activity = activity;
    }

    @NonNull
    protected final Activity getActivity() {
        return activity;
    }

    @Override
    public final void onResponse(Call<Data> call, Response<Data> response) {
        if (ActivityUtils.isAlive(activity)) {
            boolean interrupt;
            if (response.isSuccessful()) {
                interrupt = onResultOk(response.code(), response.headers(), response.body());
            } else {
                interrupt = onResultError(response.code(), response.headers(), ErrorResult.build(response));
            }
            if (!interrupt) {
                onFinish();
            }
        }
    }

    @Override
    public final void onFailure(Call<Data> call, Throwable t) {
        if (ActivityUtils.isAlive(activity)) {
            boolean interrupt;
            if (call.isCanceled()) {
                interrupt = onCallCancel();
            } else {
                interrupt = onCallException(t, ErrorResult.build(t));
            }
            if (!interrupt) {
                onFinish();
            }
        }
    }

    @Override
    public boolean onResultOk(int code, Headers headers, Data data) {
        return false;
    }

    @Override
    public boolean onResultError(int code, Headers headers, ErrorResult error) {
        return false;
    }

    @Override
    public boolean onCallCancel() {
        return false;
    }

    @Override
    public boolean onCallException(Throwable t, ErrorResult error) {
        return false;
    }

    @Override
    public void onFinish() {}

}
