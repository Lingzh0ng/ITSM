package com.wearapay.lightning.base;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.MotionEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.wearapay.lightning.BPProgressDialog;
import com.wearapay.lightning.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.SupportHelper;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by lyz on 2017/7/18.
 */
public class BaseActivity extends RxAppCompatActivity implements ISupportActivity {

  final SupportActivityDelegate mDelegate = new SupportActivityDelegate(this);

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

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        supportFinishAfterTransition();
        overridePendingTransition(0, R.anim.right_to_left);
        break;
    }
    return super.onOptionsItemSelected(item);
  }


  public SupportActivityDelegate getSupportDelegate() {
    return this.mDelegate;
  }

  public ExtraTransaction extraTransaction() {
    return this.mDelegate.extraTransaction();
  }

  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.mDelegate.onCreate(savedInstanceState);
    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
  }

  protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    this.mDelegate.onPostCreate(savedInstanceState);
  }

  protected void onDestroy() {
    this.mDelegate.onDestroy();
    super.onDestroy();
    //overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
  }

  public boolean dispatchTouchEvent(MotionEvent ev) {
    return this.mDelegate.dispatchTouchEvent(ev) || super.dispatchTouchEvent(ev);
  }

  public void onBackPressed() {
    this.mDelegate.onBackPressed();
    overridePendingTransition(0, R.anim.right_to_left);
  }

  public void onBackPressedSupport() {
    this.mDelegate.onBackPressedSupport();
  }

  public FragmentAnimator getFragmentAnimator() {
    return this.mDelegate.getFragmentAnimator();
  }

  public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
    this.mDelegate.setFragmentAnimator(fragmentAnimator);
  }

  public FragmentAnimator onCreateFragmentAnimator() {
    return this.mDelegate.onCreateFragmentAnimator();
  }

  public void loadRootFragment(int containerId, @NonNull ISupportFragment toFragment) {
    this.mDelegate.loadRootFragment(containerId, toFragment);
  }

  public void loadRootFragment(int containerId, ISupportFragment toFragment, boolean addToBackStack,
      boolean allowAnimation) {
    this.mDelegate.loadRootFragment(containerId, toFragment, addToBackStack, allowAnimation);
  }

  public void loadMultipleRootFragment(int containerId, int showPosition,
      ISupportFragment... toFragments) {
    this.mDelegate.loadMultipleRootFragment(containerId, showPosition, toFragments);
  }

  public void showHideFragment(ISupportFragment showFragment) {
    this.mDelegate.showHideFragment(showFragment);
  }

  public void showHideFragment(ISupportFragment showFragment, ISupportFragment hideFragment) {
    this.mDelegate.showHideFragment(showFragment, hideFragment);
  }

  public void start(ISupportFragment toFragment) {
    this.mDelegate.start(toFragment);
  }

  public void start(ISupportFragment toFragment, int launchMode) {
    this.mDelegate.start(toFragment, launchMode);
  }

  public void startForResult(ISupportFragment toFragment, int requestCode) {
    this.mDelegate.startForResult(toFragment, requestCode);
  }

  public void startWithPop(ISupportFragment toFragment) {
    this.mDelegate.startWithPop(toFragment);
  }

  public void replaceFragment(ISupportFragment toFragment, boolean addToBackStack) {
    this.mDelegate.replaceFragment(toFragment, addToBackStack);
  }

  public void pop() {
    this.mDelegate.pop();
  }

  public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment) {
    this.mDelegate.popTo(targetFragmentClass, includeTargetFragment);
  }

  public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment,
      Runnable afterPopTransactionRunnable) {
    this.mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable);
  }

  public void popTo(Class<?> targetFragmentClass, boolean includeTargetFragment,
      Runnable afterPopTransactionRunnable, int popAnim) {
    this.mDelegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable,
        popAnim);
  }

  public void setDefaultFragmentBackground(@DrawableRes int backgroundRes) {
    this.mDelegate.setDefaultFragmentBackground(backgroundRes);
  }

  public ISupportFragment getTopFragment() {
    return SupportHelper.getTopFragment(this.getSupportFragmentManager());
  }

  public <T extends ISupportFragment> T findFragment(Class<T> fragmentClass) {
    return SupportHelper.findFragment(this.getSupportFragmentManager(), fragmentClass);
  }
}
