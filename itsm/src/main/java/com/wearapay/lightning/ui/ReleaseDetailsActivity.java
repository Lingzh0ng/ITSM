package com.wearapay.lightning.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.wearapay.lightning.LConsts;
import com.wearapay.lightning.R;
import com.wearapay.lightning.base.BaseActivity;
import com.wearapay.lightning.bean.BAppAutoDeploy;
import com.wearapay.lightning.bean.BReleaseStatus;
import com.wearapay.lightning.net.ApiHelper;
import com.wearapay.lightning.net.BaseObserver;
import com.wearapay.lightning.ui.fragment.release.ReleaseDetailsFragment;
import com.wearapay.lightning.ui.fragment.release.ReleaseLogFragment;
import com.wearapay.lightning.uitls.RxBus;
import com.wearapay.lightning.uitls.ToastUtils;
import com.wearapay.lightning.uitls.event.UpdateEvent;
import com.wearapay.lightning.weight.VerticalTextView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

/**
 * Created by lyz on 2017/7/31.
 */
public class ReleaseDetailsActivity extends BaseActivity {

  @BindView(R.id.contentFrame) FrameLayout contentFrame;
  @BindView(R.id.logFrame) FrameLayout logFrame;
  @BindView(R.id.ivBack) ImageView ivBack;
  @BindView(R.id.vtTitle) VerticalTextView vtTitle;
  @BindView(R.id.btnRelease) ImageButton btnRelease;
  @BindView(R.id.btnSingleRelease) ImageButton btnSingleRelease;
  @BindView(R.id.btnVerify) ImageButton btnVerify;
  private BAppAutoDeploy bAppAutoDeploy;
  private LConsts.ReleaseEnvironment environment;
  private ReleaseDetailsFragment detailsFragment;
  private ReleaseLogFragment logFragment;

  private boolean ongoing = false;
  private String changeNo;
  private Observable<String> appDeploy;
  private Observable<BAppAutoDeploy> getDeployStatus;
  private Observable<String> getDeployFinishStatus;
  private Observable<BAppAutoDeploy> bAppAutoDeployObservable;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_release_details);
    ButterKnife.bind(this);

    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    initView();

    initData();

    initFragment();
  }

  private void initView() {
    btnSingleRelease.setEnabled(false);
    btnVerify.setEnabled(false);
  }

  private void initData() {
    Intent intent = getIntent();
    bAppAutoDeploy = (BAppAutoDeploy) intent.getSerializableExtra("BAppAutoDeploy");
    environment = (LConsts.ReleaseEnvironment) intent.getSerializableExtra("environment");
    changeNo = bAppAutoDeploy.getChangeNo();
    if (environment == LConsts.ReleaseEnvironment.SC) {
      appDeploy = ApiHelper.getInstance().appDeploy(changeNo);
      getDeployStatus = ApiHelper.getInstance().getDeployStatus(changeNo);
      getDeployFinishStatus = ApiHelper.getInstance().getDeployFinishStatus(changeNo);
      vtTitle.setText("生产发布");
    } else {
      appDeploy = ApiHelper.getInstance().appZSCDeploy(changeNo);
      getDeployStatus = ApiHelper.getInstance().getZSCDeployStatus(changeNo);
      getDeployFinishStatus = ApiHelper.getInstance().getZSCDeployFinishStatus(changeNo);
      vtTitle.setText("准生产发布");
    }
  }

  private void initFragment() {
    detailsFragment = ReleaseDetailsFragment.newInstance(bAppAutoDeploy, environment);
    logFragment = ReleaseLogFragment.newInstance(bAppAutoDeploy, environment);

    FragmentManager supportFragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.contentFrame, detailsFragment);
    fragmentTransaction.replace(R.id.logFrame, logFragment);
    fragmentTransaction.commitAllowingStateLoss();
  }

  @OnClick({ R.id.ivBack, R.id.btnRelease, R.id.btnSingleRelease, R.id.btnVerify })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.ivBack:
        onBackPressed();
        break;
      case R.id.btnRelease:
        if (ongoing) {
          doStop();
        } else {
          doRelease();
        }
        break;
      //case R.id.btnStop:
      //  doStop();
      //  break;
      case R.id.btnSingleRelease:
        doSingleRelease();
        break;
      case R.id.btnVerify:
        doVerify();
        break;
    }
  }

  private void doVerify() {
    showProgress();
    wrap(getDeployFinishStatus).subscribe(new BaseObserver<String>(ReleaseDetailsActivity.this) {
      @Override public void onNext(@NonNull String s) {
        super.onNext(s);
        hideProgress();
        if ("更新完成".equals(s)) {
          updateResult(s);
        } else {
          ToastUtils.showLongSafe(s);
        }
      }

      @Override public void onError(@NonNull Throwable e) {
        hideProgress();
        super.onError(e);
        ToastUtils.showLongSafe(e.getMessage());
      }
    });
  }

  private void updateResult(@NonNull String s) {
    ToastUtils.showLongSafe(s);
    btnSingleRelease.setEnabled(false);
    btnVerify.setEnabled(true);
    ongoing = false;
    if (disposable != null) {
      disposable.dispose();
    }
    RxBus.getInstance().send(new UpdateEvent(true));
  }

  private void doSingleRelease() {

  }

  private void doStop() {
    if (true) {
      ToastUtils.showLongSafe("暂不支持暂停");
      return;
    }
    btnRelease.setImageResource(R.drawable.stop);
    ongoing = false;
  }

  private void doRelease() {
    if (environment == LConsts.ReleaseEnvironment.SC) {
      long threadTimeMillis = SystemClock.currentThreadTimeMillis();
      if (threadTimeMillis < bAppAutoDeploy.getStartTime()) {
        ToastUtils.showShort("未到指定发布时间");
        return;
      }
    }
    showProgress();
    btnRelease.setImageResource(R.drawable.push);
    wrap(appDeploy).subscribe(new BaseObserver<String>(ReleaseDetailsActivity.this) {
      @Override public void onNext(@NonNull String ppResultBean) {
        super.onNext(ppResultBean);
        hideProgress();
        //btnRelease.setEnabled(false);
        //btnVerify.setEnabled(true);
        startUpdateReleaseInfo();
        ongoing = true;
      }

      @Override public void onError(@NonNull Throwable e) {
        super.onError(e);
        hideProgress();
      }
    });
    //wrap(ApiHelper.getInstance().getEventCount()).subscribe(
    //    new BaseObserver<BIncidentCount>(ReleaseDetailsActivity.this) {
    //      @Override public void onNext(@NonNull BIncidentCount bIncidentCount) {
    //        super.onNext(bIncidentCount);
    //        hideProgress();
    //        //btnRelease.setEnabled(false);
    //        btnVerify.setEnabled(true);
    //        startUpdateReleaseInfo();
    //        ongoing = true;
    //      }
    //
    //      @Override public void onError(@NonNull Throwable e) {
    //        super.onError(e);
    //        hideProgress();
    //      }
    //    });
  }

  private Disposable disposable;
  private static final int interval_number = 5;
  private BReleaseStatus currentStatus;

  private void startUpdateReleaseInfo() {
    bAppAutoDeployObservable = Observable.interval(interval_number, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMap(new Function<Long, Observable<BAppAutoDeploy>>() {
          @Override public Observable<BAppAutoDeploy> apply(@NonNull Long aLong) throws Exception {
            return getDeployStatus.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
          }
        });
    bAppAutoDeployObservable.subscribe(
        new BaseObserver<BAppAutoDeploy>(ReleaseDetailsActivity.this) {

          @Override public void onSubscribe(@NonNull Disposable d) {
            super.onSubscribe(d);
            disposable = d;
          }

          @Override public void onNext(@NonNull BAppAutoDeploy appAutoDeploy) {
            super.onNext(appAutoDeploy);
            currentStatus = appAutoDeploy.getStatus();
            switch (currentStatus) {
              case OK://正常结束
                updateResult("发布成功");
                break;
              case NG://异常结束
                updateResult("发布失败");
                break;
              case ON://执行中
                break;
              default:
                break;
            }
            notifyFragment(appAutoDeploy);
          }

          @Override public void onError(@NonNull Throwable e) {
            super.onError(e);
          }
        });
  }

  private void notifyFragment(BAppAutoDeploy appAutoDeploy) {
    if (detailsFragment != null) {
      detailsFragment.onUpdateStatus(appAutoDeploy.getDetaillog());
    }
    if (logFragment != null) {
      logFragment.upDateLog(appAutoDeploy.getDeployLog());
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (disposable != null) {
      disposable.dispose();
    }
  }

  @Override public void onBackPressed() {
    if (ongoing) {
      //ToastUtils.setGravity(Gravity.CENTER, 0, 0);
      ToastUtils.showLongSafe("发布中...");
    } else {
      super.onBackPressed();
    }
  }
}
