package com.wearapay.lightning.ui.fragment.statistical;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.wearapay.lightning.LConsts;
import com.wearapay.lightning.R;
import com.wearapay.lightning.adapter.FragmentStatisticalItemAdapter;
import com.wearapay.lightning.base.BaseMvpFragment;
import com.wearapay.lightning.base.mvp.BasePresenter;
import com.wearapay.lightning.bean.BCountIncident;
import com.wearapay.lightning.bean.BCountIncidentByTime;
import com.wearapay.lightning.bean.BIncidentTime;
import com.wearapay.lightning.dialog.FixedDatePickerDialog;
import com.wearapay.lightning.ui.fragment.statistical.presenter.StatisticalPresenter;
import com.wearapay.lightning.ui.fragment.statistical.view.IStatisticalView;
import com.wearapay.lightning.uitls.AppUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lyz on 2017/7/24.
 */
public class StatisticalFragment extends BaseMvpFragment implements IStatisticalView {

  @BindView(R.id.tabs) TabLayout tabs;
  @BindView(R.id.vp) ViewPager vp;
  @BindView(R.id.tvMonth) TextView tvMonth;
  @BindView(R.id.tvYear) TextView tvYear;
  @BindView(R.id.tvSelf) TextView tvSelf;

  private int one;
  private int two;
  private int three;

  private List<BCountIncident> oneCountInfo = new ArrayList<>();//1级统计信息
  private List<BCountIncident> twoCountInfo = new ArrayList<>();//2级统计信息
  private List<BCountIncident> threeCountInfo = new ArrayList<>();//3级统计信息

  private Calendar startTimeCalendar = Calendar.getInstance();
  private Calendar endTimeCalendar = Calendar.getInstance();

  private List<StatisticalItemFragment> statisticalFragments;
  private BIncidentTime selfBIncidentTime;
  private DatePickerDialog startDatePickerDialog;
  private DatePickerDialog endDatePickerDialog;
  private StatisticalPresenter statisticalPresenter;

  public StatisticalFragment() {
    // Required empty public constructor
  }

  public static StatisticalFragment newInstance(String title) {
    StatisticalFragment fragment = new StatisticalFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_statistical, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    statisticalFragments = new ArrayList<>();

    initData(new BIncidentTime(System.currentTimeMillis()));

    vp.setAdapter(
        new FragmentStatisticalItemAdapter(getChildFragmentManager(), statisticalFragments));
    tabs.setupWithViewPager(vp);
  }

  private void initData(BIncidentTime bIncidentTime) {
    statisticalPresenter.getStatisticalInfo(bIncidentTime);
  }

  private void initView() {
    statisticalFragments.clear();
    statisticalFragments.add(StatisticalItemFragment.newInstance(one, oneCountInfo));
    statisticalFragments.add(StatisticalItemFragment.newInstance(two, twoCountInfo));
    statisticalFragments.add(StatisticalItemFragment.newInstance(three, threeCountInfo));
    selfBIncidentTime = new BIncidentTime();
    AppUtils.startOfDate(startTimeCalendar);
    startDatePickerDialog = getStartDatePickerDialog(startTimeCalendar, selfBIncidentTime);
    endDatePickerDialog = getEndDatePickerDialog(endTimeCalendar, selfBIncidentTime);
    vp.setAdapter(
        new FragmentStatisticalItemAdapter(getChildFragmentManager(), statisticalFragments));
    tabs.setupWithViewPager(vp);
    tabs.requestLayout();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }

  @OnClick({ R.id.tvMonth, R.id.tvYear, R.id.tvSelf }) public void onViewClicked(View view) {
    tvMonth.setTextColor(getResources().getColor(R.color.black));
    tvYear.setTextColor(getResources().getColor(R.color.black));
    tvSelf.setTextColor(getResources().getColor(R.color.black));
    long currentTimeMillis = System.currentTimeMillis();
    switch (view.getId()) {
      case R.id.tvMonth:
        tvMonth.setTextColor(getResources().getColor(R.color.light));
        initData(new BIncidentTime(currentTimeMillis));
        break;
      case R.id.tvYear:
        tvYear.setTextColor(getResources().getColor(R.color.light));
        initData(new BIncidentTime(currentTimeMillis - LConsts.YEAR_TIME, currentTimeMillis));
        break;
      case R.id.tvSelf:
        tvSelf.setTextColor(getResources().getColor(R.color.light));
        startDatePickerDialog.getDatePicker().setEnabled(false);
        startDatePickerDialog.show();
        break;
    }
  }

  private DatePickerDialog getStartDatePickerDialog(Calendar calendar,
      BIncidentTime bIncidentTime) {
    DatePickerDialog datePickerDialog =
        new FixedDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, calendar,
            (datePicker, i, i1, i2) -> {
              calendar.set(Calendar.YEAR, i);
              calendar.set(Calendar.MONTH, i1);
              calendar.set(Calendar.DAY_OF_MONTH, i2);
              bIncidentTime.setStartTime(calendar.getTime().getTime());
            });
    datePickerDialog.getDatePicker().setCalendarViewShown(false);
    datePickerDialog.setTitle(R.string.title_set_start_date);
    datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.button_yes_date),
        new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialogInterface, int i) {
            DatePicker datePicker = datePickerDialog.getDatePicker();
            calendar.set(Calendar.YEAR, datePicker.getYear());
            calendar.set(Calendar.MONTH, datePicker.getMonth());
            calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
            bIncidentTime.setStartTime(calendar.getTime().getTime());
            endDatePickerDialog.getDatePicker().setEnabled(false);
            endDatePickerDialog.show();
          }
        });
    datePickerDialog.getDatePicker().setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
    return datePickerDialog;
  }

  private DatePickerDialog getEndDatePickerDialog(Calendar calendar, BIncidentTime bIncidentTime) {
    DatePickerDialog datePickerDialog =
        new FixedDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, calendar,
            (datePicker, i, i1, i2) -> {
              calendar.set(Calendar.YEAR, i);
              calendar.set(Calendar.MONTH, i1);
              calendar.set(Calendar.DAY_OF_MONTH, i2);
              long endTime = calendar.getTime().getTime();
              long startTime = bIncidentTime.getStartTime();
              if (startTime > endTime) {
                bIncidentTime.setEndTime(startTime);
                bIncidentTime.setStartTime(endTime);
              } else {
                bIncidentTime.setEndTime(calendar.getTime().getTime());
              }
            });
    datePickerDialog.getDatePicker().setCalendarViewShown(false);
    datePickerDialog.setTitle(R.string.title_set_end_date);

    datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.button_yes_date),
        new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialogInterface, int i) {
            DatePicker datePicker = datePickerDialog.getDatePicker();
            calendar.set(Calendar.YEAR, datePicker.getYear());
            calendar.set(Calendar.MONTH, datePicker.getMonth());
            calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
            long endTime = calendar.getTime().getTime();
            long startTime = bIncidentTime.getStartTime();
            if (startTime > endTime) {
              bIncidentTime.setEndTime(startTime);
              bIncidentTime.setStartTime(endTime);
            } else {
              bIncidentTime.setEndTime(calendar.getTime().getTime());
            }
            System.out.println(selfBIncidentTime);
            initData(selfBIncidentTime);
          }
        });
    datePickerDialog.getDatePicker().setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);

    return datePickerDialog;
  }

  @Override public void displayStatistical(List<BCountIncidentByTime> bCountIncidentByTimes) {
    oneCountInfo.clear();
    twoCountInfo.clear();
    threeCountInfo.clear();
    if (bCountIncidentByTimes.size() < 2) {
      return;
    }
    for (BCountIncidentByTime time : bCountIncidentByTimes) {

      switch (time.getLevel()) {
        case 1:
          if (time.getCountInfo() != null) {
            oneCountInfo.addAll(time.getCountInfo());
          }
          one = oneCountInfo.size();
          break;
        case 2:
          if (time.getCountInfo() != null) {
            twoCountInfo.addAll(time.getCountInfo());
          }
          two = twoCountInfo.size();
          break;
        case 3:
          if (time.getCountInfo() != null) {
            threeCountInfo.addAll(time.getCountInfo());
          }
          three = threeCountInfo.size();
          break;
      }
    }
  }

  @Override public void refreshUI() {
    initView();
  }

  @Override public void dispalyFail() {
    showMessage(R.string.item_level_count_error);
  }

  @Override protected BasePresenter[] initPresenters() {
    statisticalPresenter = new StatisticalPresenter(getActivity());
    return new BasePresenter[] { statisticalPresenter };
  }
}
