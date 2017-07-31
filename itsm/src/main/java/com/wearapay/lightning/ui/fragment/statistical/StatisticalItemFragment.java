package com.wearapay.lightning.ui.fragment.statistical;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.wearapay.lightning.R;
import com.wearapay.lightning.adapter.StatisticalItemAdapter;
import com.wearapay.lightning.base.BaseListFragment;
import com.wearapay.lightning.bean.BCountIncident;
import java.util.List;

public class StatisticalItemFragment extends BaseListFragment {

  private int mColumnCount = 1;

  private List<BCountIncident> bCountIncidents;
  private StatisticalItemAdapter statisticalItemAdapter;

  public StatisticalItemFragment() {
  }

  public int getItemCount() {
    return mColumnCount;
  }

  @SuppressWarnings("unused") public static StatisticalItemFragment newInstance(int columnCount,
      List<BCountIncident> bCountIncidents) {
    StatisticalItemFragment fragment = new StatisticalItemFragment();
    fragment.mColumnCount = columnCount;
    fragment.bCountIncidents = bCountIncidents;
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected void initView() {
    Context context = getContext();
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    statisticalItemAdapter = new StatisticalItemAdapter(bCountIncidents);
    statisticalItemAdapter.setOnItemOnClickListener(
        new StatisticalItemAdapter.OnItemOnClickListener() {
          @Override public void onItemClick(int position, BCountIncident countIncident) {
            //ToastUtils.showLongSafe("onclick");
          }
        });
    recyclerView.setAdapter(statisticalItemAdapter);
    refreshLayout.setEnableRefresh(false);
    refreshLayout.setEnableLoadmore(false);
    if (bCountIncidents == null || bCountIncidents.size() == 0) {
      showEmpty();
    } else {
      hideEmpty();
    }
  }

  @Override public void fetchData() {
    statisticalItemAdapter.notifyDataSetChanged();
  }

  @Override protected int getLayout() {
    return R.layout.item_statistical;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override public void onDetach() {
    super.onDetach();
  }
}
