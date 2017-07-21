package com.wearapay.lightning.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.wearapay.lightning.R;
import com.wearapay.lightning.StatusBarCompat;
import com.wearapay.lightning.base.BaseActivity;
import com.wearapay.lightning.bean.BIncidentRemark;
import com.wearapay.lightning.bean.IncidentDto;
import com.wearapay.lightning.net.ApiHelper;
import com.wearapay.lightning.net.BaseObserver;
import com.wearapay.lightning.uitls.RxBus;
import com.wearapay.lightning.uitls.ToastUtils;
import com.wearapay.lightning.uitls.event.UpdateEvent;
import io.reactivex.annotations.NonNull;

public class EditInfoActivity extends BaseActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.pbClaim) ProgressBar pbClaim;
  @BindView(R.id.editInfo) EditText editInfo;
  @BindView(R.id.btnItemClaim) Button btnItemClaim;
  @BindView(R.id.llClaimForm) LinearLayout llClaimForm;
  @BindView(R.id.sl) ScrollView sl;
  private int currentType = CLOSE_TYPE;

  public static final int CLAIM_TYPE = 2;
  public static final int CLOSE_TYPE = 3;
  private IncidentDto incidentDto;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_info);
    StatusBarCompat.compat(this, 0x20000000);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    currentType = intent.getIntExtra("onClickType", CLOSE_TYPE);
    incidentDto = (IncidentDto) intent.getSerializableExtra("IncidentDto");
    if (incidentDto == null) {
      ToastUtils.showShortSafe(R.string.item_event_no_exist);
      finish();
    }
    if (CLAIM_TYPE == currentType) {
      getSupportActionBar().setTitle(R.string.item_yes_alarm);
      btnItemClaim.setText(R.string.item_yes);
    } else {
      getSupportActionBar().setTitle(R.string.item_close_alarm);
      btnItemClaim.setText(R.string.item_close);
    }
    //Serializable serializableExtra = intent.getSerializableExtra("");
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void updateEvenStatus(int status, BIncidentRemark note) {
    showProgress();
    wrap(ApiHelper.getInstance().updateEventStatus(incidentDto.getId(), status, note)).subscribe(
        new BaseObserver<IncidentDto>(EditInfoActivity.this) {
          @Override public void onNext(@NonNull IncidentDto responseBody) {
            hideProgress();
            super.onNext(responseBody);
            ToastUtils.showShortSafe("success");
            RxBus.getInstance().send(new UpdateEvent(true));
            finish();
          }

          @Override public void onError(@NonNull Throwable e) {
            hideProgress();
            super.onError(e);
            ToastUtils.showShortSafe("failure");
            pbClaim.setVisibility(View.GONE);
            sl.setVisibility(View.VISIBLE);
          }
        });
  }

  @OnClick(R.id.btnItemClaim) public void onClick() {
    String trim = editInfo.getText().toString().trim();
    pbClaim.setVisibility(View.VISIBLE);
    sl.setVisibility(View.GONE);
    BIncidentRemark remark = new BIncidentRemark();
    remark.setRemark(trim);
    updateEvenStatus(currentType, remark);
  }

}
