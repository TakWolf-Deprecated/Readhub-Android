package me.readhub.android.md.model.api;

import android.support.annotation.NonNull;

import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Response;

public class ErrorResult {

    public static ErrorResult build(@NonNull Response response) {
        ErrorResult errorResult = new ErrorResult();
        switch (response.code()) {
            case 400:
                errorResult.setMessage("请求参数有误");
                break;
            case 403:
                errorResult.setMessage("请求被拒绝");
                break;
            case 404:
                errorResult.setMessage("资源未找到");
                break;
            case 405:
                errorResult.setMessage("请求方式不被允许");
                break;
            case 408:
                errorResult.setMessage("请求超时");
                break;
            case 422:
                errorResult.setMessage("请求语义错误");
                break;
            case 500:
                errorResult.setMessage("服务器逻辑错误");
                break;
            case 502:
                errorResult.setMessage("服务器网关错误");
                break;
            case 504:
                errorResult.setMessage("服务器网关超时");
                break;
            default:
                errorResult.setMessage("未知响应：" + response.message());
                break;
        }
        return errorResult;
    }

    public static ErrorResult build(@NonNull Throwable t) {
        ErrorResult errorResult = new ErrorResult();
        if (t instanceof UnknownHostException || t instanceof ConnectException) {
            errorResult.setMessage("网络无法连接");
        } else if (t instanceof NoRouteToHostException) {
            errorResult.setMessage("无法访问网络");
        } else if (t instanceof SocketTimeoutException) {
            errorResult.setMessage("网络访问超时");
        } else if (t instanceof JsonSyntaxException) {
            errorResult.setMessage("响应数据格式错误");
        } else {
            errorResult.setMessage("未知错误：" + t.getLocalizedMessage());
        }
        return errorResult;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
