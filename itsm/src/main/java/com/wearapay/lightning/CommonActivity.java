package com.wearapay.lightning;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wearapay.lightning.base.BaseFragment;
import com.wearapay.lightning.base.BaseSwipeBackActivity;
import com.wearapay.lightning.base.mvp.BasePresenter;
import com.wearapay.lightning.uitls.ActivityUtils;
import java.io.Serializable;

/**
 * Created by lyz on 2017/7/27.
 */
public class CommonActivity extends BaseSwipeBackActivity {
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.contentFrame) FrameLayout contentFrame;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_common);
    StatusBarCompat.compat(this, 0x20000000);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    boolean booleanExtra = getIntent().getBooleanExtra(LConsts.CAN_BACK, true);
    if (booleanExtra) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    String title = getIntent().getExtras().getString("title");
    if (!TextUtils.isEmpty(title)) {
      getSupportActionBar().setTitle(title);
    }

    showFragment();
  }

  @Override protected BasePresenter[] initPresenters() {
    return new BasePresenter[0];
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void showFragment() {
    Serializable serializable = getIntent().getSerializableExtra(LConsts.FRAGMENT_TYPE);
    if (serializable == null) {
      Log.e("CommonActivity", LConsts.FRAGMENT_TYPE + " is null ");
      finish();
      return;
    }

    try {
      LConsts.FragmentType fragmentType = (LConsts.FragmentType) serializable;
      Class<BaseFragment> fragmentClass = fragmentType.getClazz();
      Fragment fragment = fragmentClass.newInstance();
      Bundle bundle = getIntent().getExtras();
      fragment.setArguments(bundle);
      ActivityUtils.replaceFragment(getSupportFragmentManager(), fragment, R.id.contentFrame);
    } catch (Exception e) {
      e.printStackTrace();
      finish();
    }
  }

  @Override public void finish() {
    if (isTaskRoot()) {
      startActivity(new Intent(this, HomeActivity.class));
      Log.e("CommonActivity", "start HomeActivity");
    }
    super.finish();
  }
}
