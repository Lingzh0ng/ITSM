package com.wearapay.lightning.ui.fragment.release;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Window;
import android.view.WindowManager;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.wearapay.lightning.LConsts;
import com.wearapay.lightning.R;
import com.wearapay.lightning.adapter.ReleaseItemRecyclerViewAdapter;
import com.wearapay.lightning.base.BaseListFragment;
import com.wearapay.lightning.base.mvp.BasePresenter;
import com.wearapay.lightning.bean.BAppAutoDeploy;
import com.wearapay.lightning.ui.ReleaseDetailsActivity;
import com.wearapay.lightning.ui.fragment.release.presenter.ReleaseListPresenter;
import com.wearapay.lightning.ui.fragment.release.view.IReleaseListView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyz on 2017/7/31.
 */
public class ReleaseListFragment extends BaseListFragment implements IReleaseListView {

  private int itemCount;
  private int position;
  private ArrayList<BAppAutoDeploy> incidentDtos;
  private ReleaseItemRecyclerViewAdapter releaseItemRecyclerViewAdapter;
  private LConsts.ReleaseEnvironment environment = LConsts.ReleaseEnvironment.SC;
  private ReleaseListPresenter releaseListPresenter;

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public int getItemCount() {
    return itemCount;
  }

  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }

  public static ReleaseListFragment newInstance(LConsts.ReleaseEnvironment environment,
      int position, int itemCount) {
    ReleaseListFragment fragment = new ReleaseListFragment();
    fragment.setItemCount(itemCount);
    fragment.setPosition(position);
    fragment.environment = environment;
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override protected void initView() {
    WindowManager windowManager = getActivity().getWindowManager();
    Window window = getActivity().getWindow();
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    incidentDtos = new ArrayList<>();
    Context context = getContext();
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    releaseItemRecyclerViewAdapter =
        new ReleaseItemRecyclerViewAdapter(getActivity(), incidentDtos, position,
            new ReleaseItemRecyclerViewAdapter.OnReleaseItemClickListener() {
              @Override public void onItemClick(int position, BAppAutoDeploy item) {

              }

              @Override public void onReleaseButtonClick(int position, BAppAutoDeploy item) {
                //ToastUtils.showLongSafe("onReleaseButtonClick");
                Intent intent = new Intent(getActivity(), ReleaseDetailsActivity.class);
                intent.putExtra("BAppAutoDeploy", item);
                intent.putExtra("environment", environment);
                startActivity(intent);
              }

              @Override public void onCheckButtonClick(int position, BAppAutoDeploy item) {

              }
            });

    recyclerView.setAdapter(releaseItemRecyclerViewAdapter);

    refreshLayout.setEnableLoadmore(false);

    refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
      @Override public void onRefresh(TwinklingRefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        hideEmpty();
        getReleaseLists();
      }
    });
  }

  private void getReleaseLists() {
    releaseListPresenter.getReleaseLists(environment, position);
  }

  @Override public void fetchData() {
    refreshLayout.startRefresh();
  }

  @Override protected int getLayout() {
    return R.layout.item_release;
  }

  @Override protected BasePresenter[] initPresenters() {
    releaseListPresenter = new ReleaseListPresenter(getActivity());
    return new BasePresenter[] { releaseListPresenter };
  }

  @Override public void displayReleaseList(List<BAppAutoDeploy> bAppAutoDeploys) {
    onFinishRefresh();
    if (bAppAutoDeploys == null || bAppAutoDeploys.size() == 0) {
      showEmpty();
    } else {
      incidentDtos.clear();
      incidentDtos.addAll(bAppAutoDeploys);
      releaseItemRecyclerViewAdapter.notifyDataSetChanged();
    }
  }

  @Override public void getReleaseListFail() {
    onFinishRefresh();
    showEmpty();
  }
}
