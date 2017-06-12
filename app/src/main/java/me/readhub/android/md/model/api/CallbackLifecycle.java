package me.readhub.android.md.model.api;

import okhttp3.Headers;

public interface CallbackLifecycle<Data> {

    boolean onResultOk(int code, Headers headers, Data data);

    boolean onResultError(int code, Headers headers, ErrorResult error);

    boolean onCallCancel();

    boolean onCallException(Throwable t, ErrorResult error);

    void onFinish();

}
