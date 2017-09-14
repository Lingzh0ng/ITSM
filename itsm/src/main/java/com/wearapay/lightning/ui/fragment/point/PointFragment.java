package com.wearapay.lightning.ui.fragment.point;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.wearapay.lightning.LConsts;
import com.wearapay.lightning.R;
import com.wearapay.lightning.adapter.PointItemAdapter;
import com.wearapay.lightning.base.BaseMvpFragment;
import com.wearapay.lightning.base.mvp.BasePresenter;
import com.wearapay.lightning.bean.BChartInfo;
import com.wearapay.lightning.ui.fragment.point.presenter.PointPresenter;
import com.wearapay.lightning.ui.fragment.point.view.IPointView;
import com.wearapay.lightning.uitls.ActivityUtils;
import java.util.ArrayList;
import java.util.List;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by lyz on 2017/9/12.
 */

public class PointFragment extends BaseMvpFragment implements IPointView {

  @BindView(R.id.llWLF) LinearLayout llWLF;
  @BindView(R.id.ivWZF) ImageView ivWZF;
  @BindView(R.id.ivWLF) ImageView ivWLF;
  @BindView(R.id.llWZF) LinearLayout llWZF;
  @BindView(R.id.rvPoint) RecyclerView rvPoint;
  @BindView(R.id.tvMonth) TextView tvMonth;
  @BindView(R.id.id) HorizontalScrollView id;
  @BindView(R.id.tv24Hour) TextView tv24Hour;
  @BindView(R.id.tvOneWeek) TextView tvOneWeek;
  @BindView(R.id.imageView) PhotoView imageView;
  @BindView(R.id.tvTitle) TextView tvTitle;
  private Unbinder unbinder;

  private List<BChartInfo> bChartInfos;
  private List<BChartInfo> showChartInfos;
  BChartInfo showBChartInfo;

  private static final String wlf = "万来付";
  private static final String wzf = "联通钥匙扣";
  private String current = wlf;

  //private String pic1 =
  //    "https://zabbix.wearapay.com/chart2.php?graphid=1681&period=86400&width=1950&height=1080";
  //private String pic2 =
  //    "http://img.blog.csdn.net/20170228153453035?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbGVuZ193ZW5fcm91/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center";
  private PointPresenter pointPresenter;
  private PointItemAdapter pointItemAdapter;

  public PointFragment() {
  }

  public static PointFragment newInstance() {
    PointFragment fragment = new PointFragment();
    return fragment;
  }

  @Override protected BasePresenter[] initPresenters() {
    pointPresenter = new PointPresenter(getActivity());
    return new BasePresenter[] { pointPresenter };
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View inflate = inflater.inflate(R.layout.fragment_point, container, false);
    unbinder = ButterKnife.bind(this, inflate);
    return inflate;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView();
    pointPresenter.getPointDate();
    imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

      @Override public void onPhotoTap(View view, float x, float y) {
        if (showBChartInfo != null) {
          Bundle bundle = new Bundle();
          bundle.putSerializable("BChartInfo", showBChartInfo);
          bundle.putString("title", showBChartInfo.getAppDisplay());
          ActivityUtils.startFragment(getActivity(), LConsts.FragmentType.PhotoView, bundle);
        }
      }
    });
  }

  private void initView() {
    showChartInfos = new ArrayList<>();
    pointItemAdapter = new PointItemAdapter(this, showChartInfos);
    rvPoint.setLayoutManager(new LinearLayoutManager(getActivity()));
    pointItemAdapter.setOnItemOnClickListener(new PointItemAdapter.OnItemOnClickListener() {
      @Override public void onItemClick(int position, BChartInfo countIncident) {
        if (countIncident != null) {
          Bundle bundle = new Bundle();
          bundle.putSerializable("BChartInfo", countIncident);
          bundle.putString("title", countIncident.getAppDisplay());
          ActivityUtils.startFragment(getActivity(), LConsts.FragmentType.PhotoView, bundle);
        }
      }
    });
    rvPoint.setAdapter(pointItemAdapter);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({ R.id.tv24Hour, R.id.tvOneWeek, R.id.tvMonth, R.id.llWLF, R.id.llWZF })
  public void onViewClicked(View view) {

    switch (view.getId()) {
      case R.id.llWLF:
        selectAppName(current, wlf);
        break;
      case R.id.llWZF:
        selectAppName(current, wzf);
        break;
      case R.id.imageView:
        //if (showBChartInfo != null) {
        //  Bundle bundle = new Bundle();
        //  bundle.putSerializable("BChartInfo", showBChartInfo);
        //  bundle.putString("title", showBChartInfo.getAppDisplay());
        //  ActivityUtils.startFragment(getActivity(), LConsts.FragmentType.PhotoView, bundle);
        //}
        break;
      case R.id.tv24Hour:
        selectAppPic(LConsts.DAY_TIME_MIN, R.id.tv24Hour);
        break;
      case R.id.tvOneWeek:
        selectAppPic(LConsts.WEEK_TIME_MIN, R.id.tvOneWeek);
        break;
      case R.id.tvMonth:
        selectAppPic(LConsts.MONTH_TIME_MIM, R.id.tvMonth);
        break;
    }
  }

  private void selectAppName(String currentAppName, String selectAppName) {
    current = selectAppName;
    if (!currentAppName.equals(selectAppName)) {
      showChartInfos.clear();
      updatePic(R.id.tv24Hour);
      updateIcon(selectAppName);
      for (int i = 0; i < bChartInfos.size(); i++) {
        if (bChartInfos.get(i).getPeriod() == LConsts.DAY_TIME_MIN && selectAppName.equals(
            bChartInfos.get(i).getServiceNameC())) {
          showBChartInfo = bChartInfos.get(i);
          showChartInfos.add(bChartInfos.get(i));
        }
      }
    }
    notifyDataSetChange();
  }

  private void notifyDataSetChange() {
    System.out.println(showChartInfos);
    pointItemAdapter.notifyDataSetChanged();
  }

  private void updateIcon(String selectAppName) {
    if (wlf.equals(selectAppName)) {
      ivWLF.setImageResource(R.drawable.icon_android_large);
      ivWZF.setImageResource(R.drawable.ic_wzf_mid);
    } else {
      ivWLF.setImageResource(R.drawable.icon_android_mid);
      ivWZF.setImageResource(R.drawable.ic_wzf_large);
    }
  }

  @Override public void displayPoint(List<BChartInfo> bChartInfos) {
    this.bChartInfos = bChartInfos;
    if (bChartInfos.size() > 0) {
      for (int i = 0; i < bChartInfos.size(); i++) {
        if (bChartInfos.get(i).getPeriod() == LConsts.DAY_TIME_MIN && wlf.equals(
            bChartInfos.get(i).getServiceNameC())) {
          showBChartInfo = bChartInfos.get(i);
          imageView.postDelayed(new Runnable() {
            @Override public void run() {
              //updatePic(R.id.tv24Hour);
              selectAppName("", wlf);
              //showChartInfos.clear();
              //showChartInfos.addAll(PointFragment.this.bChartInfos);
              //pointItemAdapter.notifyDataSetChanged();
            }
          }, 300);
          return;
        }
      }
    }
  }

  @Override public void displayFail() {

  }

  private void updatePic(int id) {
    //tvTitle.setText(showBChartInfo.getAppDisplay());
    //Glide.with(this)
    //    .load(showBChartInfo.getUrl())
    //    .asBitmap()
    //    .placeholder(R.drawable.loading)
    //    .error(R.drawable.error)
    //    .thumbnail(0.1f)
    //    .dontAnimate()
    //    .into(imageView);
    updateTab(id);
  }

  private void updateTab(int id) {
    tvMonth.setTextColor(getResources().getColor(R.color.black));
    tvOneWeek.setTextColor(getResources().getColor(R.color.black));
    tv24Hour.setTextColor(getResources().getColor(R.color.black));
    switch (id) {
      case R.id.tv24Hour:
        tv24Hour.setTextColor(getResources().getColor(R.color.light));
        break;
      case R.id.tvOneWeek:
        tvOneWeek.setTextColor(getResources().getColor(R.color.light));
        break;
      case R.id.tvMonth:
        tvMonth.setTextColor(getResources().getColor(R.color.light));
        break;
    }
  }

  private void selectAppPic(int time, int id) {
    showChartInfos.clear();
    updatePic(id);
    for (int i = 0; i < bChartInfos.size(); i++) {
      if (bChartInfos.get(i).getPeriod() == time && current.equals(
          bChartInfos.get(i).getServiceNameC())) {
        showBChartInfo = bChartInfos.get(i);
        showChartInfos.add(bChartInfos.get(i));
      }
    }
    notifyDataSetChange();
    //showMessage("图片不存在");
  }
}
