package com.wearapay.lightning.base.mvp;

import android.content.Context;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lyz54 on 2017/6/27.
 */

public abstract class BasePresenter<T> {
  protected Context mContext;
  protected T view;

  public BasePresenter(Context mContext) {
    this.mContext = mContext;
  }

  public void setView(T view) {
    this.view = view;
  }

  public void onDestroy() {
    mContext = null;
    view = null;
  }

  public abstract <T> LifecycleTransformer<T> bindToLifecycle();

  public abstract <T> LifecycleTransformer<T> bindUntilLifecycle(FragmentEvent event);

  protected <T> Observable<T> wrap(Observable<T> origin) {
    return origin.compose(bindToLifecycle())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  protected <T> Observable<T> wrapBindUntil(Observable<T> origin, FragmentEvent event) {
    return origin.compose(bindUntilLifecycle(event))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
