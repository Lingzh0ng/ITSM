package com.wearapay.lightning.ui.fragment.release;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wearapay.lightning.LConsts;
import com.wearapay.lightning.R;
import com.wearapay.lightning.adapter.FragmentReleaseAdapter;
import com.wearapay.lightning.base.BaseFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyz on 2017/7/31.
 */
public class ReleaseFragment extends BaseFragment {

  @BindView(R.id.tabs) TabLayout tabs;
  @BindView(R.id.vp) ViewPager vp;
  private int itemMyCount = 0;
  private int itemAllCount = 0;
  private FragmentReleaseAdapter fragmentReleaseAdapter;
  private List<ReleaseListFragment> listFragments;
  private LConsts.ReleaseEnvironment environment = LConsts.ReleaseEnvironment.SC;

  public int getItemMyCount() {
    return itemMyCount;
  }

  public void setItemMyCount(int itemMyCount) {
    this.itemMyCount = itemMyCount;
  }

  public int getItemAllCount() {
    return itemAllCount;
  }

  public void setItemAllCount(int itemAllCount) {
    this.itemAllCount = itemAllCount;
  }

  public static ReleaseFragment newInstance(LConsts.ReleaseEnvironment environment, int itemMyCount,
      int itemAllCount) {
    ReleaseFragment fragment = new ReleaseFragment();
    fragment.itemMyCount = itemMyCount;
    fragment.itemAllCount = itemAllCount;
    fragment.environment = environment;
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_blank, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView();
  }

  private void initView() {
    listFragments = new ArrayList<>();
    listFragments.add(ReleaseListFragment.newInstance(environment, 0, getItemMyCount()));
    listFragments.add(ReleaseListFragment.newInstance(environment, 1, getItemAllCount()));
    fragmentReleaseAdapter = new FragmentReleaseAdapter(getChildFragmentManager(), listFragments);
    vp.setAdapter(fragmentReleaseAdapter);
    tabs.setupWithViewPager(vp);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    vp.removeAllViews();
    FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
    fragmentTransaction.detach(listFragments.get(0));
    fragmentTransaction.detach(listFragments.get(1));
    fragmentTransaction.commitAllowingStateLoss();
  }
}
