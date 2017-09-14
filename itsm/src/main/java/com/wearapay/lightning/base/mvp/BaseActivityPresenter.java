package com.wearapay.lightning.base.mvp;

import android.content.Context;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

/**
 * Created by lyz on 2017/9/11.
 */

public class BaseActivityPresenter<T extends IBaseView> extends BasePresenter<T> {
  public BaseActivityPresenter(Context mContext) {
    super(mContext);
  }

  @Override public <T>LifecycleTransformer<T> bindToLifecycle() {
    return view.getUseActivity().bindToLifecycle();
  }

  @Override public <T>LifecycleTransformer<T> bindUntilLifecycle(FragmentEvent event) {
    ActivityEvent activityEvent;
    switch (event.name()) {
      case "DESTROY_VIEW":
      case "DETACH":
        activityEvent = ActivityEvent.DESTROY;
        break;
      default:
        activityEvent = ActivityEvent.valueOf(event.name());
        break;
    }

    return view.getUseActivity().bindUntilEvent(activityEvent);
  }
}
