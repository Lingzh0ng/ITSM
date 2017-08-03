package com.wearapay.lightning.ui.fragment.release;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wearapay.lightning.LConsts;
import com.wearapay.lightning.R;
import com.wearapay.lightning.adapter.ReleaseLogRecyclerViewAdapter;
import com.wearapay.lightning.base.BaseFragment;
import com.wearapay.lightning.bean.BAppAutoDeploy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lyz on 2017/7/31.
 */
public class ReleaseLogFragment extends BaseFragment {

  @BindView(R.id.list) RecyclerView recyclerView;
  private ReleaseLogRecyclerViewAdapter releaseLogRecyclerViewAdapter;

  private BAppAutoDeploy appAutoDeploy;
  private LConsts.ReleaseEnvironment environment;
  private List<String> logList;

  public static ReleaseLogFragment newInstance(BAppAutoDeploy appAutoDeploy,
      LConsts.ReleaseEnvironment environment) {
    ReleaseLogFragment fragment = new ReleaseLogFragment();
    fragment.appAutoDeploy = appAutoDeploy;
    fragment.environment = environment;
    return fragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.release_log_fragment, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView();
  }

  private void initView() {
    logList = new ArrayList<>();

    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    releaseLogRecyclerViewAdapter = new ReleaseLogRecyclerViewAdapter(logList);
    recyclerView.setAdapter(releaseLogRecyclerViewAdapter);
    recyclerView.smoothScrollToPosition(logList.size());
  }

  public void upDateLog(String logs) {
    String[] split = logs.split("</br>");
    List<String> stringList = Arrays.asList(split);
    logList.clear();
    logList.add("发布日志");
    logList.addAll(stringList);
    //for (int i = a; i < a + 10; i++) {
    //  logList.add("测试日志 " + i);
    //}
    releaseLogRecyclerViewAdapter.notifyDataSetChanged();
    recyclerView.smoothScrollToPosition(logList.size());
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }
}
