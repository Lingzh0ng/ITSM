package com.wearapay.lightning.ui.fragment.event;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.wearapay.lightning.LConsts;
import com.wearapay.lightning.R;
import com.wearapay.lightning.adapter.MyItemRecyclerViewAdapter;
import com.wearapay.lightning.base.BaseListFragment;
import com.wearapay.lightning.base.mvp.BasePresenter;
import com.wearapay.lightning.bean.DealStatus;
import com.wearapay.lightning.bean.IncidentDto;
import com.wearapay.lightning.ui.EditInfoActivity;
import com.wearapay.lightning.ui.ItemDetailsActivity;
import com.wearapay.lightning.ui.fragment.event.presenter.ItemPresenter;
import com.wearapay.lightning.ui.fragment.event.view.IItemView;
import com.wearapay.lightning.uitls.ActivityUtils;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;

import static com.wearapay.lightning.bean.DealStatus.DEAL_WAIT;

public class ItemFragment extends BaseListFragment implements IItemView {

  private static final String ARG_COLUMN_COUNT = "column-count";
  private static final String ARG_POSITION = "position";
  private static final String ARG_ITEM_COUNT = "itemCount";
  private static final String ARG_STATUS = "status";
  private int position = 0;
  private int mColumnCount = 1;
  private DealStatus dealStatus = DEAL_WAIT;
  private MyItemRecyclerViewAdapter myItemRecyclerViewAdapter;
  private Observable<List<IncidentDto>> eventObservable;
  private int itemCount = 0;
  private ItemPresenter itemPresenter;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (getArguments() != null) {
      mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
      dealStatus = (DealStatus) getArguments().getSerializable(ARG_STATUS);
      position = getArguments().getInt(ARG_POSITION);
      itemCount = getArguments().getInt(ARG_ITEM_COUNT);
    }
    System.out.println("onCreate");
  }

  public int getItemCount() {
    return itemCount;
  }

  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }

  public List<IncidentDto> getIncidentDtos() {
    return incidentDtos;
  }

  public void setIncidentDtos(List<IncidentDto> incidentDtos) {
    this.incidentDtos = incidentDtos;
  }

  private List<IncidentDto> incidentDtos;

  public ItemFragment() {
  }

  @SuppressWarnings("unused")
  public static ItemFragment newInstance(int columnCount, int position, int itemCount,
      DealStatus status) {
    ItemFragment fragment = new ItemFragment();
    fragment.itemCount = itemCount;
    Bundle args = new Bundle();
    args.putInt(ARG_COLUMN_COUNT, columnCount);
    args.putInt(ARG_POSITION, position);
    args.putInt(ARG_ITEM_COUNT, itemCount);
    args.putSerializable(ARG_STATUS, status);
    fragment.setArguments(args);
    return fragment;
  }

  @Override protected BasePresenter[] initPresenters() {
    itemPresenter = new ItemPresenter(getActivity());
    return new BasePresenter[] { itemPresenter };
  }

  @Override protected void initView() {
    incidentDtos = new ArrayList<>();
    Context context = getContext();
    if (mColumnCount <= 1) {
      recyclerView.setLayoutManager(new LinearLayoutManager(context));
    } else {
      recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
    }
    myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(incidentDtos, dealStatus,
        new MyItemRecyclerViewAdapter.OnHomeItemClickListener() {
          @Override public void onItemClick(int position, IncidentDto item) {
            //ToastUtils.showShortSafe(item.getAlarmContent());
            Intent intent = new Intent(getActivity(), ItemDetailsActivity.class);
            intent.putExtra("IncidentDto", item);
            startActivityForResult(intent, 1);
          }

          @Override public void onYesButtonClick(int position, IncidentDto item) {
            Intent intent = new Intent(getActivity(), EditInfoActivity.class);
            intent.putExtra("onClickType", EditInfoActivity.CLAIM_TYPE);
            intent.putExtra("IncidentDto", item);
            startActivityForResult(intent, 1);
          }

          @Override public void onCloseButtonClick(int position, IncidentDto item) {
            Intent intent = new Intent(getActivity(), EditInfoActivity.class);
            intent.putExtra("onClickType", EditInfoActivity.CLOSE_TYPE);
            intent.putExtra("IncidentDto", item);
            startActivityForResult(intent, 1);
          }

          @Override public void onCompileButtonClick(int position, IncidentDto item) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("IncidentDto", item);
            bundle.putSerializable("dealStatus", dealStatus);
            bundle.putBoolean("select", true);
            bundle.putString("title", getString(R.string.toolbar_select_member));
            ActivityUtils.startFragment(getActivity(), LConsts.FragmentType.Member, bundle);
          }
        });
    recyclerView.setAdapter(myItemRecyclerViewAdapter);
    refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
      @Override public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
        hideEmpty();
        itemPresenter.getEventLists(dealStatus, position);
      }

      @Override public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {

      }
    });
    refreshLayout.setEnableLoadmore(false);
  }

  @Override public void fetchData() {
    refreshLayout.startRefresh();
  }

  @Override protected int getLayout() {
    return R.layout.fragment_item_list;
  }

  @Override public void onItemSuccess(List<IncidentDto> incidentDtos) {
    onFinishRefresh();
    ItemFragment.this.incidentDtos.clear();
    ItemFragment.this.incidentDtos.addAll(incidentDtos);
    myItemRecyclerViewAdapter.notifyDataSetChanged();
    if (incidentDtos.size() > 0) {
      hideEmpty();
    } else {
      showEmpty();
    }
  }

  @Override public void onItemFail() {
    ItemFragment.this.incidentDtos.clear();
    onFinishRefresh();
    showEmpty();
  }
}
