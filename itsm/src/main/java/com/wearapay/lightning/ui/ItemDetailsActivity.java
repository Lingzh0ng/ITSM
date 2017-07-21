package com.wearapay.lightning.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wearapay.lightning.R;
import com.wearapay.lightning.adapter.NoteRecyclerViewAdapter;
import com.wearapay.lightning.base.BaseActivity;
import com.wearapay.lightning.bean.IncidentDto;
import com.wearapay.lightning.uitls.FormatUtlis;
import java.util.ArrayList;
import java.util.List;

public class ItemDetailsActivity extends BaseActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.app_bar) AppBarLayout appBar;
  @BindView(R.id.tvItemEvent) TextView tvItemEvent;
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
  @BindView(R.id.llMemory) LinearLayout llMemory;
  @BindView(R.id.viewLine) View viewLine;
  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  @BindView(R.id.tvItemContinue) TextView tvItemContinue;
  private IncidentDto incidentDto;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item_details);
    //StatusBarCompat.compat(this, 0x20000000);
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

      setText(tvItemEvent, incidentDto.getAlarmContent());
      setText(tvItemLevel,
          String.format(getString(R.string.item_alarm_level_item), incidentDto.getLevel()));
      setText(tvItemCount, incidentDto.getIncidentCount());
      setText(tvItemPeople, incidentDto.getHandlingName());
      setText(tvItemAlarmTime,
          FormatUtlis.chargeStringFormatTime(incidentDto.getAlarmCreationTime(),
              "yyyy-MM-dd hh:mm:ss", "MM/dd hh:mm"));
      setText(tvItemDealTime, FormatUtlis.chargeStringFormatTime(incidentDto.getHandlingStartTime(),
          "yyyy-MM-dd hh:mm:ss", "MM/dd hh:mm"));
      setText(tvItemCompleteTime,
          FormatUtlis.chargeStringFormatTime(incidentDto.getHandlingEndTime(),
              "yyyy-MM-dd hh:mm:ss", "MM/dd hh:mm"));
      tvContentTitle.setText(incidentDto.getBusinessName());
      tvContentIP.setText(incidentDto.getEntityIp());
      tvContentServer.setText(incidentDto.getEntityName());

      if (TextUtils.isEmpty(incidentDto.getHandlingEndTime())) {
        tvItemContinue.setVisibility(View.GONE);
      } else {
        setText(tvItemContinue, incidentDto.getAlarmDuration());
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
        //for (int i = 0; i < 3; i++) {
        //  list.add("sfafahkj按计划分开就爱好是否看见啊好烦客家话   \n" + i);
        //}
        NoteRecyclerViewAdapter adapter = new NoteRecyclerViewAdapter(list);

        recyclerView.addItemDecoration(
            new DividerItemDecoration(ItemDetailsActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
      }
    }
  }

  private void setText(TextView textView, String str) {
    textView.setText(String.format(textView.getText().toString(), str));
  }

  private void setText(TextView textView, int str) {
    textView.setText(String.format(textView.getText().toString(), str));
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}