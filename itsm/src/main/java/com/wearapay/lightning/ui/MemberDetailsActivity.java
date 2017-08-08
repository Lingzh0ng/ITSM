package com.wearapay.lightning.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.wearapay.lightning.R;
import com.wearapay.lightning.StatusBarCompat;
import com.wearapay.lightning.base.BaseSwipeBackActivity;
import com.wearapay.lightning.bean.UserConfDto;
import com.wearapay.lightning.uitls.AppUtils;
import com.wearapay.lightning.uitls.ToastUtils;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_CONTACTS;

public class MemberDetailsActivity extends BaseSwipeBackActivity {

  private static final int REQUEST_CALL_PHONE = 1000;
  @BindView(R.id.tvPhoneNumber) TextView tvPhoneNumber;
  @BindView(R.id.ivMessage) ImageView ivMessage;
  @BindView(R.id.ivCall) ImageView ivCall;
  @BindView(R.id.tvEml) TextView tvEml;
  @BindView(R.id.ivEml) ImageView ivEml;
  @BindView(R.id.ivWeChat) ImageView ivWeChat;
  @BindView(R.id.toolbar_layout) CollapsingToolbarLayout toolbarLayout;
  @BindView(R.id.tvWechat) TextView tvWechat;

  private String phoneNumber;
  private String email;
  private String weChat;
  private UserConfDto userConfDto;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_member_details);
    StatusBarCompat.compat(this, 0x20000000);
    ButterKnife.bind(this);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Intent intent = getIntent();
    userConfDto = (UserConfDto) intent.getSerializableExtra("UserConfDto");
    if (userConfDto == null) {
      ToastUtils.showShortSafe(R.string.item_user_no_exist);
      finish();
    } else {
      System.out.println(userConfDto);
      getSupportActionBar().setTitle(userConfDto.getName());
      toolbarLayout.setTitle(userConfDto.getName());
      phoneNumber = userConfDto.getPhone();
      if (TextUtils.isEmpty(phoneNumber)) {
        tvPhoneNumber.setText(R.string.item_phone_no_exist);
      } else {
        tvPhoneNumber.setText(phoneNumber);
      }
      email = userConfDto.getEmail();
      //TODO Email 字段
      if (TextUtils.isEmpty(email)) {
        tvEml.setText(R.string.item_email_no_exist);
      } else {
        tvEml.setText(email);
      }
      weChat = userConfDto.getWeChatId();
      if (TextUtils.isEmpty(weChat)) {
        tvWechat.setText(R.string.item_wechat_no_exist);
      } else {
        tvWechat.setText(weChat);
      }
    }
  }

  @OnClick({ R.id.ivMessage, R.id.ivCall, R.id.ivEml, R.id.ivWeChat })
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.ivMessage:
        if (TextUtils.isEmpty(phoneNumber)) {
          ToastUtils.showShort(R.string.item_phone_no_exist);
        } else {
          doSendSMSTo(phoneNumber, "");
        }
        break;
      case R.id.ivCall:
        if (TextUtils.isEmpty(phoneNumber)) {
          ToastUtils.showShort(R.string.item_phone_no_exist);
        } else {
          doCallPhoneTo(phoneNumber);
        }
        break;
      case R.id.ivEml:
        if (TextUtils.isEmpty(phoneNumber)) {
          ToastUtils.showShort(R.string.item_email_no_exist);
        } else {
          doSendEmailTo(email, "");
        }
        break;
      case R.id.ivWeChat:
        if (TextUtils.isEmpty(weChat)) {
          ToastUtils.showShort(R.string.item_wechat_no_exist);
        } else {
          if (!AppUtils.isWeixinAvilible(this)) {
            ToastUtils.showShort(R.string.item_wechat_no_app);
          } else {
            copyWeChat();
            openWeChat();
          }
        }
        break;
    }
  }

  private void copyWeChat() {
    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    cm.setText(weChat);
    ToastUtils.showLongSafe(R.string.item_wechat_copy);
  }

  private void openWeChat() {
    Intent intent = new Intent();
    ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
    intent.setAction(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_LAUNCHER);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setComponent(cmp);
    startActivity(intent);
  }

  /**
   * 调起系统发短信功能
   */
  public void doSendSMSTo(String phoneNumber, String message) {
    if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
      Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
      intent.putExtra("sms_body", message);
      startActivity(intent);
    }
  }

  /**
   * 调起系统发短信功能
   */
  public void doSendEmailTo(String email, String message) {
    Intent data = new Intent(Intent.ACTION_SENDTO);
    data.setData(Uri.parse("mailto:" + email));
    data.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.item_alarm));
    data.putExtra(Intent.EXTRA_TEXT, message);
    startActivity(data);
  }

  /**
   * 调起系统打电话功能
   */
  public void doCallPhoneTo(String phoneNumber) {
    if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
      if (mayCallPhone()) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {
          // TODO: Consider calling
          //    ActivityCompat#requestPermissions
          // here to request the missing permissions, and then overriding
          //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
          //                                          int[] grantResults)
          // to handle the case where the user grants the permission. See the documentation
          // for ActivityCompat#requestPermissions for more details.
          return;
        }
        startActivity(intent);
      }
    }
  }

  private boolean mayCallPhone() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return true;
    }
    if (checkSelfPermission(CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
      return true;
    }
    if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
      Snackbar.make(ivCall, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
          .setAction(android.R.string.ok, new View.OnClickListener() {
            @Override @TargetApi(Build.VERSION_CODES.M) public void onClick(View v) {
              requestPermissions(new String[] { CALL_PHONE }, REQUEST_CALL_PHONE);
            }
          });
    } else {
      requestPermissions(new String[] { CALL_PHONE }, REQUEST_CALL_PHONE);
    }
    return false;
  }

  /**
   * Callback received when a permissions request has been completed.
   */
  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == REQUEST_CALL_PHONE) {
      if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        doCallPhoneTo(phoneNumber);
      }
    }
  }
}
