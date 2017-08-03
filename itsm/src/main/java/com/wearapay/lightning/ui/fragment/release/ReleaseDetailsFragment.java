package com.wearapay.lightning.ui.fragment.release;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.wearapay.lightning.LConsts;
import com.wearapay.lightning.R;
import com.wearapay.lightning.adapter.ReleaseAppItem2RecyclerViewAdapter;
import com.wearapay.lightning.adapter.ReleaseStatusRecyclerViewAdapter;
import com.wearapay.lightning.base.BaseFragment;
import com.wearapay.lightning.bean.BAppAutoDeploy;
import com.wearapay.lightning.bean.BAutoDeploy;
import com.wearapay.lightning.bean.BAutoDeployInfo;
import com.wearapay.lightning.uitls.FormatUtlis;
import java.util.List;

/**
 * Created by lyz on 2017/7/31.
 */
public class ReleaseDetailsFragment extends BaseFragment {
  @BindView(R.id.tvItemPurpose) TextView tvItemPurpose;
  @BindView(R.id.tvItemNumber) TextView tvItemNumber;
  @BindView(R.id.tvItemPeople) TextView tvItemPeople;
  @BindView(R.id.tvItemName) TextView tvItemName;
  @BindView(R.id.tvItemAppName) TextView tvItemAppName;
  @BindView(R.id.tvItemStartTime) TextView tvItemStartTime;
  @BindView(R.id.tvItemEndTime) TextView tvItemEndTime;
  @BindView(R.id.tvItemIp) TextView tvItemIp;
  @BindView(R.id.list) RecyclerView recyclerView;
  @BindView(R.id.refreshLayout) TwinklingRefreshLayout refreshLayout;
  @BindView(R.id.scrollView) NestedScrollView scrollView;
  @BindView(R.id.rvApp) RecyclerView rvApp;
  private ReleaseStatusRecyclerViewAdapter releaseStatusRecyclerViewAdapter;
  private BAppAutoDeploy appAutoDeploy;
  private LConsts.ReleaseEnvironment environment;
  private List<BAutoDeploy> detaillog;
  private String ip = "";
  private String name = "";
  private boolean isShowIp = true;

  public static ReleaseDetailsFragment newInstance(BAppAutoDeploy appAutoDeploy,
      LConsts.ReleaseEnvironment environment) {
    ReleaseDetailsFragment fragment = new ReleaseDetailsFragment();
    fragment.appAutoDeploy = appAutoDeploy;
    fragment.environment = environment;
    return fragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.release_details_fragment, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView();
  }

  private void initView() {
    tvItemPurpose.setText(appAutoDeploy.getPurpose());
    tvItemNumber.setText(appAutoDeploy.getChangeNo());
    tvItemPeople.setText(appAutoDeploy.getProposer());
    tvItemStartTime.setText(
        FormatUtlis.getStringFormatTime(appAutoDeploy.getStartTime(), "MM/dd HH:mm"));
    tvItemEndTime.setText(
        FormatUtlis.getStringFormatTime(appAutoDeploy.getEndTime(), "MM/dd HH:mm"));
    tvItemName.setText(appAutoDeploy.getBusinessName());
    List<BAutoDeployInfo> displayInfo = appAutoDeploy.getDisplayInfo();
    for (int i = 0; i < displayInfo.size(); i++) {
      ip += displayInfo.get(i).getIp();
      if (i == displayInfo.size() - 1) {
      } else {
        ip += displayInfo.get(i).getIp() + ";";
      }

      name += displayInfo.get(i).getScriptName();
      if (i == displayInfo.size() - 1) {
      } else {
        name += displayInfo.get(i).getScriptName() + ";";
      }
    }
    tvItemIp.setText(ip);

    ReleaseAppItem2RecyclerViewAdapter releaseAppItemRecyclerViewAdapter =
        new ReleaseAppItem2RecyclerViewAdapter(getActivity(), displayInfo);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
    gridLayoutManager.setSmoothScrollbarEnabled(true);
    gridLayoutManager.setAutoMeasureEnabled(true);
    rvApp.setLayoutManager(gridLayoutManager);
    rvApp.setHasFixedSize(true);
    rvApp.setNestedScrollingEnabled(false);
    rvApp.setAdapter(releaseAppItemRecyclerViewAdapter);
    rvApp.setFocusable(false);

    refreshLayout.setEnableLoadmore(false);
    refreshLayout.setEnableRefresh(false);

    //List<String> stringList = new ArrayList<>();
    //for (int i = 0; i < 15; i++) {
    //  stringList.add("测试数据" + i);
    //}

    detaillog = appAutoDeploy.getDetaillog();

    releaseStatusRecyclerViewAdapter =
        new ReleaseStatusRecyclerViewAdapter(getActivity(), detaillog);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    linearLayoutManager.setSmoothScrollbarEnabled(true);
    linearLayoutManager.setAutoMeasureEnabled(true);
    recyclerView.setBackgroundColor(getResources().getColor(R.color.black));
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setHasFixedSize(true);
    recyclerView.setNestedScrollingEnabled(false);
    recyclerView.setAdapter(releaseStatusRecyclerViewAdapter);

    recyclerView.setFocusable(false);
  }

  public void onUpdateStatus(List<BAutoDeploy> list) {
    detaillog.clear();
    detaillog.addAll(list);
    releaseStatusRecyclerViewAdapter.notifyDataSetChanged();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }

  @OnClick(R.id.tvItemIp) public void onViewClicked() {
    if (isShowIp) {
      tvItemIp.setText(name);
    } else {
      tvItemIp.setText(ip);
    }
    isShowIp = !isShowIp;
  }
}
