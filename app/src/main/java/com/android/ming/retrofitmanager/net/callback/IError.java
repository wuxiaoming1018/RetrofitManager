package com.android.ming.retrofitmanager.net.callback;

public interface IError {
    void onError(int code,String msg,String url);
}
