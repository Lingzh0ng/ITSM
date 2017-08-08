package com.wearapay.lightning.base;

import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation_swipeback.core.ISwipeBackFragment;
import me.yokeyword.fragmentation_swipeback.core.SwipeBackFragmentDelegate;

/**
 * Created by lyz on 2017/8/8.
 */
public class BaseSwipeBackFragment extends BaseFragment implements ISwipeBackFragment {
  final SwipeBackFragmentDelegate mDelegate = new SwipeBackFragmentDelegate(this);

  public BaseSwipeBackFragment() {
  }

  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.mDelegate.onCreate(savedInstanceState);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    this.mDelegate.onViewCreated(view, savedInstanceState);
  }

  public View attachToSwipeBack(View view) {
    return this.mDelegate.attachToSwipeBack(view);
  }

  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    this.mDelegate.onHiddenChanged(hidden);
  }

  public SwipeBackLayout getSwipeBackLayout() {
    return this.mDelegate.getSwipeBackLayout();
  }

  public void setSwipeBackEnable(boolean enable) {
    this.mDelegate.setSwipeBackEnable(enable);
  }

  public void setParallaxOffset(@FloatRange(
      from = 0.0D,
      to = 1.0D) float offset) {
    this.mDelegate.setParallaxOffset(offset);
  }

  public void onDestroyView() {
    this.mDelegate.onDestroyView();
    super.onDestroyView();
  }
}
