package com.main.x_base.domain.request;

import android.annotation.SuppressLint;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Create by KunMinX at 2022/6/14
 */
public class AsyncTask {

  @SuppressLint("CheckResult")
  public static <T> Observable<T> doIO(Action<T> start) {
    return Observable.create(start::onEmit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
  }

  @SuppressLint("CheckResult")
  public static <T> Observable<T> doCalculate(Action<T> start) {
    return Observable.create(start::onEmit)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread());
  }

  public interface Action<T> {
    void onEmit(ObservableEmitter<T> emitter);
  }

  public interface Observer<T> extends io.reactivex.rxjava3.core.Observer<T> {
    default void onSubscribe(@NonNull Disposable d) {
    }

    void onNext(@NonNull T t);

    default void onError(@NonNull Throwable e) {
    }

    default void onComplete() {
    }
  }
}
