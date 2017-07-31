package com.wearapay.lightning.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.wearapay.lightning.LConsts;
import com.wearapay.lightning.R;
import com.wearapay.lightning.adapter.MemberRecyclerViewAdapter;
import com.wearapay.lightning.base.BaseListFragment;
import com.wearapay.lightning.bean.BIncidentRemark;
import com.wearapay.lightning.bean.DealStatus;
import com.wearapay.lightning.bean.IncidentDto;
import com.wearapay.lightning.bean.UserConfDto;
import com.wearapay.lightning.net.ApiHelper;
import com.wearapay.lightning.net.BaseObserver;
import com.wearapay.lightning.ui.MemberDetailsActivity;
import com.wearapay.lightning.uitls.RxBus;
import com.wearapay.lightning.uitls.ToastUtils;
import com.wearapay.lightning.uitls.event.UpdateEvent;
import io.reactivex.annotations.NonNull;
import java.util.ArrayList;
import java.util.List;

public class MemberFragment extends BaseListFragment {

  private List<UserConfDto> userConfDtos;
  private MemberRecyclerViewAdapter memberRecyclerViewAdapter;
  private IncidentDto incidentDto;
  private boolean select;
  private DealStatus dealStatus = DealStatus.DEAL_WAIT;

  public MemberFragment() {
  }

  // TODO: Customize parameter initialization
  @SuppressWarnings("unused") public static MemberFragment newInstance() {
    MemberFragment fragment = new MemberFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initData();
  }

  private void initData() {
    refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
      @Override public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
        hideEmpty();
        getAllMember();
      }

      @Override public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {

      }
    });
    refreshLayout.setEnableLoadmore(false);
    refreshLayout.startRefresh();
  }

  @Override protected void initView() {
    Context context = getContext();
    Bundle arguments = getArguments();
    select = arguments.getBoolean("select");
    if (select) {
      incidentDto = (IncidentDto) arguments.getSerializable("IncidentDto");
      dealStatus = (DealStatus) arguments.getSerializable("dealStatus");
    }
    userConfDtos = new ArrayList<>();
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    recyclerView.addItemDecoration(
        new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    memberRecyclerViewAdapter = new MemberRecyclerViewAdapter(userConfDtos,
        new MemberRecyclerViewAdapter.OnListFragmentInteractionListener() {
          @Override public void onListFragmentInteraction(UserConfDto item) {
            if (select) {
              ToastUtils.showLongSafe(incidentDto.getBusinessName());
              eventCompile(item.getId());
            } else {
              Intent intent = new Intent(getContext(), MemberDetailsActivity.class);
              intent.putExtra("UserConfDto", item);
              startActivity(intent);
            }
          }
        });
    recyclerView.setAdapter(memberRecyclerViewAdapter);
  }

  @Override public void fetchData() {

  }

  @Override protected int getLayout() {
    return R.layout.fragment_item_member;
  }

  private void eventCompile(String id) {
    showProgress();
    int status = LConsts.INCIDENT_OCCUR;
    switch (dealStatus) {
      case DEAL_WAIT:
        status = LConsts.INCIDENT_OCCUR;
        break;
      case DEAL_DOING:
        status = LConsts.INCIDENT_HANDLE;
        break;
    }
    wrap(ApiHelper.getInstance()
        .eventCompile(incidentDto.getId(), status, id, new BIncidentRemark())).subscribe(
        new BaseObserver<IncidentDto>(getActivity()) {
          @Override public void onNext(@NonNull IncidentDto incidentDto) {
            super.onNext(incidentDto);
            RxBus.getInstance().send(new UpdateEvent(true));
            hideProgress();
            getActivity().finish();
          }

          @Override protected void handlerError(Throwable e) {
            super.handlerError(e);
            ToastUtils.showLongSafe(R.string.e_general);
            hideProgress();
          }
        });
  }

  private void getAllMember() {
    wrap(ApiHelper.getInstance().getAllUser()).subscribe(
        new BaseObserver<List<UserConfDto>>(getActivity()) {
          @Override public void onNext(@NonNull List<UserConfDto> userConfDtos) {
            super.onNext(userConfDtos);
            onFinishRefresh();
            MemberFragment.this.userConfDtos.clear();
            MemberFragment.this.userConfDtos.addAll(userConfDtos);
            memberRecyclerViewAdapter.notifyDataSetChanged();
            if (userConfDtos.size() > 0) {
              hideEmpty();
            } else {
              showEmpty();
            }
          }

          @Override public void onError(@NonNull Throwable e) {
            super.onError(e);
            showEmpty();
            onFinishRefresh();
          }
        });
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    //bind.unbind();
  }

  @Override public void onDetach() {
    super.onDetach();
  }
}
