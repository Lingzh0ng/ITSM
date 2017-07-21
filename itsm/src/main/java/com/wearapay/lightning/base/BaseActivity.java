package com.wearapay.lightning.base;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.wearapay.lightning.BPProgressDialog;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lyz on 2017/7/18.
 */
public class BaseActivity extends RxAppCompatActivity {
  protected <T> Observable<T> wrap(Observable<T> origin) {
    return origin.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(this.<T>bindToLifecycle());
  }

  protected BPProgressDialog progressDialog;

  public void showProgress() {
    if (progressDialog == null) {
      progressDialog = new BPProgressDialog(this, "");
    }
    progressDialog.show();
  }

  public void showProgress(String message) {
    if (progressDialog == null) {
      progressDialog = new BPProgressDialog(this, message);
    }
    progressDialog.show();
  }

  public void showProgress(int messageResourceId) {
    if (progressDialog == null) {
      progressDialog = new BPProgressDialog(this, getString(messageResourceId));
    }
    progressDialog.show();
  }

  public void hideProgress() {
    if (progressDialog != null) {
      progressDialog.dismiss();
    }
  }
}
