package com.wearapay.lightning.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wearapay.lightning.R;
import com.wearapay.lightning.StatusBarCompat;
import com.wearapay.lightning.adapter.NoteRecyclerViewAdapter;
import com.wearapay.lightning.base.BaseSwipeBackActivity;
import com.wearapay.lightning.bean.IncidentDto;
import com.wearapay.lightning.uitls.FormatUtlis;
import java.util.ArrayList;
import java.util.List;

public class ItemDetailsActivity extends BaseSwipeBackActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.app_bar) AppBarLayout appBar;
  @BindView(R.id.tvItemStartTime) TextView tvItemEvent;
  @BindView(R.id.tvItemLevel) TextView tvItemLevel;
  @BindView(R.id.tvItemCount) TextView tvItemCount;
  @BindView(R.id.llName) LinearLayout llName;
  @BindView(R.id.tvItemPeople) TextView tvItemPeople;
  @BindView(R.id.tvItemAlarmTime) TextView tvItemAlarmTime;
  @BindView(R.id.tvItemDealTime) TextView tvItemDealTime;
  @BindView(R.id.tvItemCompleteTime) TextView tvItemCompleteTime;
  @BindView(R.id.tvContentTitle) TextView tvContentTitle;
  @BindView(R.id.tvContentIP) TextView tvContentIP;
  @BindView(R.id.tvContentServer) TextView tvContentServer;
  @BindView(R.id.tvItemPurpose) LinearLayout llMemory;
  @BindView(R.id.viewLine) View viewLine;
  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  @BindView(R.id.tvItemContinue) TextView tvItemContinue;
  private IncidentDto incidentDto;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item_details);
    StatusBarCompat.compat(this, 0x20000000);
    ButterKnife.bind(this);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(R.string.toolbar_alarm_details_title);
    initData();
    initView();
  }

  private void initData() {
    Intent intent = getIntent();
    incidentDto = (IncidentDto) intent.getSerializableExtra("IncidentDto");
  }

  private void initView() {

    if (incidentDto != null) {

      FormatUtlis.setText(tvItemEvent, incidentDto.getAlarmContent());
      FormatUtlis.setText(tvItemLevel,
          String.format(getString(R.string.item_alarm_level_item), incidentDto.getLevel()));
      FormatUtlis.setText(tvItemCount, incidentDto.getIncidentCount());
      FormatUtlis.setText(tvItemPeople, incidentDto.getHandlingName());
      FormatUtlis.setText(tvItemAlarmTime,
          FormatUtlis.chargeStringFormatTime(incidentDto.getAlarmCreationTime(),
              "yyyy-MM-dd HH:mm:ss", "MM/dd HH:mm"));
      FormatUtlis.setText(tvItemDealTime, FormatUtlis.chargeStringFormatTime(incidentDto.getHandlingStartTime(),
          "yyyy-MM-dd HH:mm:ss", "MM/dd HH:mm"));
      FormatUtlis.setText(tvItemCompleteTime,
          FormatUtlis.chargeStringFormatTime(incidentDto.getHandlingEndTime(),
              "yyyy-MM-dd HH:mm:ss", "MM/dd HH:mm"));
      tvContentTitle.setText(incidentDto.getBusinessName());
      tvContentIP.setText(incidentDto.getEntityIp());
      tvContentServer.setText(incidentDto.getEntityName());

      if (TextUtils.isEmpty(incidentDto.getHandlingEndTime())) {
        tvItemContinue.setVisibility(View.GONE);
      } else {
        FormatUtlis.setText(tvItemContinue, incidentDto.getAlarmDuration());
      }

      if (!TextUtils.isEmpty(incidentDto.getRemark())) {

        String remark = incidentDto.getRemark();
        remark = remark.replace("</br>", "\r\n");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        List<String> list = new ArrayList<>();
        list.add(remark);
        NoteRecyclerViewAdapter adapter = new NoteRecyclerViewAdapter(list);

        recyclerView.addItemDecoration(
            new DividerItemDecoration(ItemDetailsActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
      }
    }
  }
}
