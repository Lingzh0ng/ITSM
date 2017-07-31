package com.wearapay.lightning.ui.fragment.release;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.wearapay.lightning.R;
import com.wearapay.lightning.adapter.ReleaseItemRecyclerViewAdapter;
import com.wearapay.lightning.base.BaseListFragment;
import com.wearapay.lightning.bean.IncidentDto;
import com.wearapay.lightning.ui.ReleaseDetailsActivity;
import com.wearapay.lightning.uitls.ToastUtils;
import java.util.ArrayList;

/**
 * Created by lyz on 2017/7/31.
 */
public class ReleaseListFragment extends BaseListFragment {

  private int itemCount;
  private int position;
  private ArrayList<IncidentDto> incidentDtos;
  private ReleaseItemRecyclerViewAdapter releaseItemRecyclerViewAdapter;

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

  public static ReleaseListFragment newInstance(String title, int position, int itemCount) {
    ReleaseListFragment fragment = new ReleaseListFragment();
    fragment.setItemCount(itemCount);
    fragment.setPosition(position);
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override protected void initView() {
    incidentDtos = new ArrayList<>();
    Context context = getContext();
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    releaseItemRecyclerViewAdapter = new ReleaseItemRecyclerViewAdapter(incidentDtos, position,
        new ReleaseItemRecyclerViewAdapter.OnReleaseItemClickListener() {
          @Override public void onItemClick(int position, IncidentDto item) {

          }

          @Override public void onReleaseButtonClick(int position, IncidentDto item) {
            ToastUtils.showLongSafe("onReleaseButtonClick");
            Intent intent = new Intent(getActivity(), ReleaseDetailsActivity.class);
            startActivity(intent);
          }

          @Override public void onCheckButtonClick(int position, IncidentDto item) {

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
    incidentDtos.clear();
    for (int i = 0; i < itemCount; i++) {
      incidentDtos.add(new IncidentDto());
    }
    releaseItemRecyclerViewAdapter.notifyDataSetChanged();
    refreshLayout.finishRefreshing();
  }

  @Override public void fetchData() {
    refreshLayout.startRefresh();
  }

  @Override protected int getLayout() {
    return R.layout.item_release;
  }
}
