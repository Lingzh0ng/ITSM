package com.wearapay.lightning.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.wearapay.lightning.R;
import com.wearapay.lightning.adapter.MemberRecyclerViewAdapter;
import com.wearapay.lightning.base.BaseFragment;
import com.wearapay.lightning.bean.UserConfDto;
import com.wearapay.lightning.net.ApiHelper;
import com.wearapay.lightning.net.BaseObserver;
import com.wearapay.lightning.ui.MemberDetailsActivity;
import io.reactivex.annotations.NonNull;
import java.util.ArrayList;
import java.util.List;

public class MemberFragment extends BaseFragment {

  @BindView(R.id.list) RecyclerView recyclerView;
  @BindView(R.id.refreshLayout) TwinklingRefreshLayout refreshLayout;
  @BindView(R.id.emptyView) FrameLayout emptyView;
  private List<UserConfDto> userConfDtos;
  private MemberRecyclerViewAdapter memberRecyclerViewAdapter;
  private Unbinder bind;

  public MemberFragment() {
  }

  // TODO: Customize parameter initialization
  @SuppressWarnings("unused") public static MemberFragment newInstance() {
    MemberFragment fragment = new MemberFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_item_member, container, false);
    userConfDtos = new ArrayList<>();
    // Set the adapter

    bind = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Context context = view.getContext();
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    recyclerView.addItemDecoration(
        new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    memberRecyclerViewAdapter = new MemberRecyclerViewAdapter(userConfDtos,
        new MemberRecyclerViewAdapter.OnListFragmentInteractionListener() {
          @Override public void onListFragmentInteraction(UserConfDto item) {
            Toast.makeText(getContext(), item.getName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), MemberDetailsActivity.class);
            intent.putExtra("UserConfDto", item);
            startActivity(intent);
          }
        });
    recyclerView.setAdapter(memberRecyclerViewAdapter);
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

  private void onFinishRefresh() {
    if (refreshLayout != null) {
      refreshLayout.finishRefreshing();
      refreshLayout.finishLoadmore();
    }
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
            }else {
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

  private void showEmpty() {
    emptyView.setVisibility(View.VISIBLE);
  }

  private void hideEmpty() {
    emptyView.setVisibility(View.GONE);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    //bind.unbind();
  }

  @Override public void onDetach() {
    super.onDetach();
  }
}
