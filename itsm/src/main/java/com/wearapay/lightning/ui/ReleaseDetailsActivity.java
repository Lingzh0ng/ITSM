package com.wearapay.lightning.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wearapay.lightning.LConsts;
import com.wearapay.lightning.R;
import com.wearapay.lightning.base.BaseActivity;
import com.wearapay.lightning.bean.BAppAutoDeploy;
import com.wearapay.lightning.ui.fragment.release.ReleaseDetailsFragment;
import com.wearapay.lightning.ui.fragment.release.ReleaseLogFragment;

/**
 * Created by lyz on 2017/7/31.
 */
public class ReleaseDetailsActivity extends BaseActivity {

  @BindView(R.id.contentFrame) FrameLayout contentFrame;
  @BindView(R.id.logFrame) FrameLayout logFrame;
  private BAppAutoDeploy bAppAutoDeploy;
  private LConsts.ReleaseEnvironment environment;
  private ReleaseDetailsFragment detailsFragment;
  private ReleaseLogFragment logFragment;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_release_details);
    ButterKnife.bind(this);

    initData();

    initFragment();
  }

  private void initData() {
    Intent intent = getIntent();
    bAppAutoDeploy = (BAppAutoDeploy) intent.getSerializableExtra("BAppAutoDeploy");
    environment = (LConsts.ReleaseEnvironment) intent.getSerializableExtra("environment");
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
}
