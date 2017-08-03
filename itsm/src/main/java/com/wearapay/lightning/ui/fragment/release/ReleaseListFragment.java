package com.wearapay.lightning.ui.fragment.release;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.wearapay.lightning.LConsts;
import com.wearapay.lightning.R;
import com.wearapay.lightning.adapter.ReleaseItemRecyclerViewAdapter;
import com.wearapay.lightning.base.BaseListFragment;
import com.wearapay.lightning.bean.BAppAutoDeploy;
import com.wearapay.lightning.net.ApiHelper;
import com.wearapay.lightning.net.BaseObserver;
import com.wearapay.lightning.ui.ReleaseDetailsActivity;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyz on 2017/7/31.
 */
public class ReleaseListFragment extends BaseListFragment {

  private int itemCount;
  private int position;
  private ArrayList<BAppAutoDeploy> incidentDtos;
  private ReleaseItemRecyclerViewAdapter releaseItemRecyclerViewAdapter;
  private LConsts.ReleaseEnvironment environment = LConsts.ReleaseEnvironment.SC;

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
    incidentDtos = new ArrayList<>();
    Context context = getContext();
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    releaseItemRecyclerViewAdapter = new ReleaseItemRecyclerViewAdapter(getActivity(),incidentDtos, position,
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
    Observable<List<BAppAutoDeploy>> observable;
    if (environment == LConsts.ReleaseEnvironment.SC) {
      if (position == 0) {
        observable = ApiHelper.getInstance().getDeployUser();
      } else {
        observable = ApiHelper.getInstance().getDeployAll();
      }
    } else {
      if (position == 0) {
        observable = ApiHelper.getInstance().getZSCDeployUser();
      } else {
        observable = ApiHelper.getInstance().getZSCDeployAll();
      }
    }
    wrap(observable).subscribe(new BaseObserver<List<BAppAutoDeploy>>(getActivity()) {
      @Override public void onNext(@NonNull List<BAppAutoDeploy> bAppAutoDeploys) {
        super.onNext(bAppAutoDeploys);
        onFinishRefresh();
        if (bAppAutoDeploys == null || bAppAutoDeploys.size() == 0) {
          showEmpty();
        } else {
          incidentDtos.clear();
          incidentDtos.addAll(bAppAutoDeploys);
          releaseItemRecyclerViewAdapter.notifyDataSetChanged();
        }
      }

      @Override public void onError(@NonNull Throwable e) {
        super.onError(e);
        onFinishRefresh();
        showEmpty();
      }
    });
  }

  @Override public void fetchData() {
    refreshLayout.startRefresh();
  }

  @Override protected int getLayout() {
    return R.layout.item_release;
  }
}
