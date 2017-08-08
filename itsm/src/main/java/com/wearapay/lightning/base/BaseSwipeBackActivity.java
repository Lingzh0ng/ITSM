package com.wearapay.lightning.base;

import android.os.Bundle;
import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation_swipeback.core.ISwipeBackActivity;
import me.yokeyword.fragmentation_swipeback.core.SwipeBackActivityDelegate;

/**
 * Created by lyz on 2017/8/8.
 */
public class BaseSwipeBackActivity extends BaseActivity implements ISwipeBackActivity {
  final SwipeBackActivityDelegate mDelegate = new SwipeBackActivityDelegate(this);

  public BaseSwipeBackActivity() {
  }

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.mDelegate.onCreate(savedInstanceState);
  }

  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    this.mDelegate.onPostCreate(savedInstanceState);
  }

  public SwipeBackLayout getSwipeBackLayout() {
    return this.mDelegate.getSwipeBackLayout();
  }

  public void setSwipeBackEnable(boolean enable) {
    this.mDelegate.setSwipeBackEnable(enable);
  }

  public boolean swipeBackPriority() {
    return this.mDelegate.swipeBackPriority();
  }
}
