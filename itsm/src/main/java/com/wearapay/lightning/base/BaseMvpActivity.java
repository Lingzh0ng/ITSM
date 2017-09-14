package com.wearapay.lightning.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.wearapay.lightning.BPProgressDialog;
import com.wearapay.lightning.HomeActivity;
import com.wearapay.lightning.base.mvp.BasePresenter;
import com.wearapay.lightning.base.mvp.IBaseView;
import com.wearapay.lightning.uitls.ToastUtils;

/**
 * Created by lyz on 2017/9/11.
 */

public abstract class BaseMvpActivity extends BaseActivity implements IBaseView {

  protected BPProgressDialog progressDialog;
  protected BasePresenter[] presenters;

  @Override public Context getUseContext() {
    return this;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (presenters == null) {
      presenters = initPresenters();
      if (presenters != null && presenters.length > 0) {
        for (int i = 0; i < presenters.length; i++) {
          System.out.println("presenters");
          presenters[i].setView(this);
        }
      }
    }
  }

  protected abstract BasePresenter[] initPresenters();

  @Override protected void onDestroy() {
    super.onDestroy();
    if (presenters != null && presenters.length > 0) {
      for (int i = 0; i < presenters.length; i++) {
        presenters[i].onDestroy();
      }
    }
  }

  @Override public void showProgress() {
    if (progressDialog == null) {
      progressDialog = new BPProgressDialog(this, "");
    }
    progressDialog.show();
  }

  @Override public void showProgress(String message) {
    if (progressDialog == null) {
      progressDialog = new BPProgressDialog(this, message);
    }
    progressDialog.show();
  }

  @Override public void showProgress(int messageResourceId) {
    if (progressDialog == null) {
      progressDialog = new BPProgressDialog(this, getString(messageResourceId));
    }
    progressDialog.show();
  }

  @Override public void hideProgress() {
    if (progressDialog != null) {
      progressDialog.dismiss();
    }
  }

  @Override public void showMessage(String message) {
    ToastUtils.showShort(message);
  }

  @Override public void showMessage(int messageId) {
    ToastUtils.showShort(messageId);
  }

  @Override public void showDiglog(String message) {

  }

  @Override public void processFail(Throwable t, String errorMessage) {

  }

  @Override public void navToHomePage() {
    Intent intent = new Intent(this, HomeActivity.class);
    startActivity(intent);
  }

  @Override public BaseFragment getUseFragment() {
    return null;
  }

  @Override public BaseActivity getUseActivity() {
    return this;
  }
}
