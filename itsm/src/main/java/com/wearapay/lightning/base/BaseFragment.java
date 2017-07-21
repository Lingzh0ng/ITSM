package com.wearapay.lightning.base;

import com.trello.rxlifecycle2.components.support.RxFragment;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lyz on 2017/7/18.
 */
public class BaseFragment extends RxFragment {
  protected <T> Observable<T> wrap(Observable<T> origin) {
    return origin.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(bindToLifecycle());
  }
}
