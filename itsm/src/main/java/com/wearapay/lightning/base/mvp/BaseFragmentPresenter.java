package com.wearapay.lightning.base.mvp;

import android.content.Context;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * Created by lyz on 2017/9/11.
 */

public class BaseFragmentPresenter<T extends IBaseView> extends BasePresenter<T> {
  public BaseFragmentPresenter(Context mContext) {
    super(mContext);
  }

  @Override public <T> LifecycleTransformer<T> bindToLifecycle() {
    return view.getUseFragment().bindToLifecycle();
  }

  @Override public <T> LifecycleTransformer<T> bindUntilLifecycle(FragmentEvent event) {
    return view.getUseFragment().bindUntilEvent(event);
  }
}
