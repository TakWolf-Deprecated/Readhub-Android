package me.readhub.android.md.model.api;

import android.app.Activity;
import android.support.annotation.NonNull;

import me.readhub.android.md.ui.util.ToastUtils;
import okhttp3.Headers;

public class DefaultCallback<Data> extends ForegroundCallback<Data> {

    public DefaultCallback(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    public boolean onResultError(int code, Headers headers, ErrorResult error) {
        toastError(error);
        return false;
    }

    @Override
    public boolean onCallException(Throwable t, ErrorResult error) {
        toastError(error);
        return false;
    }

    protected void toastError(@NonNull ErrorResult error) {
        ToastUtils.with(getActivity()).show(error.getMessage());
    }

}
