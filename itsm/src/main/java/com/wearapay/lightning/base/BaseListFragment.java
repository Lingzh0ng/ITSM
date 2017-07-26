package com.wearapay.lightning.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.wearapay.lightning.R;

/**
 * Created by lyz on 2017/7/24.
 */
public abstract class BaseListFragment extends BaseFragment {

  protected RecyclerView recyclerView;
  protected TwinklingRefreshLayout refreshLayout;
  protected FrameLayout emptyView;

  protected boolean isViewInitiated;
  protected boolean isVisibleToUser;
  protected boolean isDataInitiated;

  protected abstract void initView();

  @Override public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    this.isVisibleToUser = isVisibleToUser;
    onLazyLoad(false);
  }

  protected void onLazyLoad(boolean forceUpdate) {
    if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
      fetchData();
      isDataInitiated = true;
    }
  }

  public abstract void fetchData();

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(getLayout(), container, false);
    System.out.println("onCreateView");
    recyclerView = (RecyclerView) view.findViewById(R.id.list);
    refreshLayout = (TwinklingRefreshLayout) view.findViewById(R.id.refreshLayout);
    emptyView = (FrameLayout) view.findViewById(R.id.emptyView);
    return view;
  }

  protected abstract int getLayout();

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    this.isViewInitiated = true;
    initView();
    onLazyLoad(false);
  }

  protected void onFinishRefresh() {
    if (refreshLayout != null) {
      refreshLayout.finishRefreshing();
      refreshLayout.finishLoadmore();
    }
  }

  protected void showEmpty() {
    emptyView.setVisibility(View.VISIBLE);
  }

  protected void hideEmpty() {
    emptyView.setVisibility(View.GONE);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }
}
