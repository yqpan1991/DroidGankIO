package com.edus.gankio.net;

public interface DataCallback<T> {
    void onReceived(T data);
    void onException(Throwable throwable);
}
