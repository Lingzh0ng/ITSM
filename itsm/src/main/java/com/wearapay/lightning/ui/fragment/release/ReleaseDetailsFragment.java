package com.wearapay.lightning.ui.fragment.release;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.wearapay.lightning.R;
import com.wearapay.lightning.adapter.ReleaseLogRecyclerViewAdapter;
import com.wearapay.lightning.base.BaseFragment;
import java.util.ArrayList;
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
  @BindView(R.id.llContent) LinearLayout llContent;
  @BindView(R.id.tvItemIp) TextView tvItemIp;
  @BindView(R.id.list) RecyclerView recyclerView;
  @BindView(R.id.refreshLayout) TwinklingRefreshLayout refreshLayout;
  @BindView(R.id.scrollView) NestedScrollView scrollView;

  public static ReleaseDetailsFragment newInstance() {
    ReleaseDetailsFragment fragment = new ReleaseDetailsFragment();
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
    refreshLayout.setEnableLoadmore(false);
    refreshLayout.setEnableRefresh(false);
    List<String> stringList = new ArrayList<>();
    for (int i = 0; i < 15; i++) {
      stringList.add("测试数据" + i);
    }
    ReleaseLogRecyclerViewAdapter releaseLogRecyclerViewAdapter =
        new ReleaseLogRecyclerViewAdapter(stringList);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    linearLayoutManager.setSmoothScrollbarEnabled(true);
    linearLayoutManager.setAutoMeasureEnabled(true);
    recyclerView.setBackgroundColor(Color.BLACK);
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setHasFixedSize(true);
    recyclerView.setNestedScrollingEnabled(false);
    recyclerView.setAdapter(releaseLogRecyclerViewAdapter);
    //scrollView.fullScroll(ScrollView.FOCUS_UP);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }
}
