package com.wearapay.lightning;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.wearapay.lightning.base.BaseActivity;
import com.wearapay.lightning.bean.BIncidentCount;
import com.wearapay.lightning.bean.DealStatus;
import com.wearapay.lightning.net.ApiHelper;
import com.wearapay.lightning.net.BaseObserver;
import com.wearapay.lightning.ui.fragment.BlankFragment;
import com.wearapay.lightning.ui.fragment.MemberFragment;
import com.wearapay.lightning.ui.fragment.SettingFragment;
import com.wearapay.lightning.ui.fragment.release.ReleaseFragment;
import com.wearapay.lightning.ui.fragment.statistical.StatisticalFragment;
import com.wearapay.lightning.uitls.RxBus;
import com.wearapay.lightning.uitls.ToastUtils;
import com.wearapay.lightning.uitls.event.UpdateEvent;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HomeActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener,
    BlankFragment.OnFragmentInteractionListener {

  private Toolbar toolbar;
  private FragmentManager supportFragmentManager;
  private Menu menu;
  private NavigationView navigationView;
  private BPProgressDialog progressDialog;
  private Disposable subscribe;
  private BIncidentCount bIncidentCount;
  private View headerView;
  private int currentem = R.id.nav_deal;
  private TextView tvCompany;
  private TextView tvEmail;
  private ImageView ivHeader;
  private DrawerLayout drawer;

  private boolean showFragment;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main2);
    StatusBarCompat.compat(this, 0x20000000);

    toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setBackgroundColor(0x0f00ff);
    setSupportActionBar(toolbar);

    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close) {
          @Override public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            System.out.println("drawer open");
            showFragment = false;
            getUserEvent();
          }
        };
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    navigationView = (NavigationView) findViewById(R.id.nav_view);
    menu = navigationView.getMenu();
    //menu.findItem(R.id.nav_release).setVisible(false);
    menu.findItem(R.id.nav_change).setVisible(false);
    navigationView.setNavigationItemSelectedListener(this);

    headerView = navigationView.getHeaderView(0);
    if (headerView != null) {
      //headerView.setOnClickListener(new View.OnClickListener() {
      //  @Override public void onClick(View v) {
      //    ApiHelper.getInstance()
      //        .getTimeList()
      //        .subscribeOn(Schedulers.io())
      //        .observeOn(AndroidSchedulers.mainThread())
      //        .subscribe(new Consumer<ResponseBody>() {
      //          @Override public void accept(@NonNull ResponseBody responseBody) throws Exception {
      //            System.out.println(responseBody);
      //          }
      //        }, new Consumer<Throwable>() {
      //          @Override public void accept(@NonNull Throwable throwable) throws Exception {
      //            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
      //          }
      //        });
      //  }
      //});
      tvCompany = (TextView) headerView.findViewById(R.id.tvCompany);
      tvEmail = (TextView) headerView.findViewById(R.id.tvEmail);
      ivHeader = (ImageView) headerView.findViewById(R.id.imageView);
      tvEmail.setText(ApiHelper.getInstance().getEmail());
    }

    //navigationView.setLis

    supportFragmentManager = getSupportFragmentManager();
    showFragment = true;
    getUserEvent();

    initRxBus();
  }

  private void initRxBus() {
    subscribe = RxBus.getInstance()
        .toObserverable(UpdateEvent.class)
        .subscribe(new Consumer<UpdateEvent>() {
          @Override public void accept(@NonNull UpdateEvent o) throws Exception {
            System.out.println(o);
            showFragment = true;
            getUserEvent();
            if (o.isLogin()) {
              String email = ApiHelper.getInstance().getEmail();
              tvEmail.setText(email + "@" + getString(R.string.title_email_suffix));
              headerView.setOnClickListener(null);
            }
          }
        });
  }

  @Override protected void onStart() {
    super.onStart();
    String email = ApiHelper.getInstance().getEmail();
    tvEmail.setText(email + "@" + getString(R.string.title_email_suffix));
    headerView.setOnClickListener(null);
    //if (ApiHelper.getInstance().loginStatus()) {
    //
    //} else {
    //  tvEmail.setText(R.string.item_header_email_no_login);
    //  headerView.setOnClickListener(new View.OnClickListener() {
    //    @Override public void onClick(View v) {
    //      startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    //    }
    //  });
    //}
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (subscribe != null) {
      subscribe.dispose();
    }
  }

  private String getFormat(String str, int i, int j) {
    if (str.contains("       ")) {
      str = str.split("       ")[0];
    }
    return String.format(str + "       %d/%d", i, j);
  }

  private long touchTime;

  @Override public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      long currentTime = System.currentTimeMillis();
      if ((currentTime - touchTime) >= 3000) {
        ToastUtils.showShortSafe(R.string.title_activity_exit);
        touchTime = currentTime;
      } else {
        ((App) getApplication()).exitApp();
      }
    }
  }

  //@Override public boolean onCreateOptionsMenu(Menu menu) {
  //  // Inflate the menu; this adds items to the action bar if it is present.
  //  getMenuInflater().inflate(R.menu.main2, menu);
  //  return true;
  //}
  //
  //@Override public boolean onOptionsItemSelected(MenuItem item) {
  //  // Handle action bar item clicks here. The action bar will
  //  // automatically handle clicks on the Home/Up button, so long
  //  // as you specify a parent activity in AndroidManifest.xml.
  //  int id = item.getItemId();
  //
  //  //noinspection SimplifiableIfStatement
  //  if (id == R.id.action_settings) {
  //    return true;
  //  }
  //
  //  return super.onOptionsItemSelected(item);
  //}

  private Fragment blankFragment;

  @SuppressWarnings("StatementWithEmptyBody") @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();
    currentem = id;
    String title = item.getTitle().toString().split("     ")[0];
    toolbar.setTitle(title);
    if (bIncidentCount == null) {
      getUserEvent();
      return true;
    }
    FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

    if (id == R.id.nav_deal) {//未处理
      //item.setTitle(getFormat(title, 1, 2));
      blankFragment =
          BlankFragment.newInstance(title, DealStatus.DEAL_WAIT, bIncidentCount.getActivityUser(),
              bIncidentCount.getActivityAll());
    } else if (id == R.id.nav_dealing) {//处理中
      //item.setTitle(getFormat(title, 0, 2));
      blankFragment =
          BlankFragment.newInstance(title, DealStatus.DEAL_DOING, bIncidentCount.getAckUser(),
              bIncidentCount.getAckAll());
    } else if (id == R.id.nav_dealed) {//已处理
      //item.setTitle(getFormat(title, 0, 2));
      blankFragment = BlankFragment.newInstance(title, DealStatus.DEAL_COMPLETE,
          bIncidentCount.getResolvedUser(), bIncidentCount.getResolvedAll());
    } else if (id == R.id.nav_people) {//成员
      blankFragment = MemberFragment.newInstance();
    } else if (id == R.id.nav_set) {//设置
      blankFragment = SettingFragment.newInstance();
    } else if (id == R.id.nav_statistical) {//统计
      blankFragment = StatisticalFragment.newInstance("11");
    } else if (id == R.id.nav_release) {//发布
      blankFragment = ReleaseFragment.newInstance(3, 10);
    } else if (id == R.id.nav_change) {//变更
      return true;
    }
    fragmentTransaction.replace(R.id.fl, blankFragment);
    fragmentTransaction.commitAllowingStateLoss();
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override public void onFragmentInteraction(Uri uri) {

  }

  public void getUserEvent(MenuItem item) {
    showProgress();
    wrap(ApiHelper.getInstance().getEventCount()).subscribe(
        new BaseObserver<BIncidentCount>(HomeActivity.this) {
          @Override public void onNext(@NonNull BIncidentCount bIncidentCount) {
            super.onNext(bIncidentCount);
            hideProgress();
            displayNav(bIncidentCount);
            if (item != null) {
              onNavigationItemSelected(item);
            }
          }

          @Override public void onError(@NonNull Throwable e) {
            hideProgress();
            super.onError(e);
            //bIncidentCount = new BIncidentCount();
            //displayNav(bIncidentCount);
          }
        });
  }

  public void getUserEvent() {
    getUserEvent(null);
  }

  private void displayNav(BIncidentCount bIncidentCount) {
    this.bIncidentCount = bIncidentCount;
    menu.getItem(0)
        .setTitle(getFormat(menu.getItem(0).toString(), bIncidentCount.getActivityUser(),
            bIncidentCount.getActivityAll()));
    menu.getItem(1)
        .setTitle(getFormat(menu.getItem(1).toString(), bIncidentCount.getAckUser(),
            bIncidentCount.getAckAll()));
    menu.getItem(2)
        .setTitle(getFormat(menu.getItem(2).toString(), bIncidentCount.getResolvedUser(),
            bIncidentCount.getResolvedAll()));
    if (showFragment) displayFragmentPager();
  }

  private void displayFragmentPager() {
    showFragment = false;
    FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
    Fragment blankFragment =
        BlankFragment.newInstance(menu.getItem(0).toString().split("   ")[0], DealStatus.DEAL_WAIT,
            bIncidentCount.getActivityUser(), bIncidentCount.getActivityAll());
    int id = currentem;
    String title = menu.findItem(id).toString().split("   ")[0];
    if (id == R.id.nav_deal) {//未处理
      //item.setTitle(getFormat(title, 1, 2));
      blankFragment =
          BlankFragment.newInstance(title, DealStatus.DEAL_WAIT, bIncidentCount.getActivityUser(),
              bIncidentCount.getActivityAll());
    } else if (id == R.id.nav_dealing) {//处理中
      //item.setTitle(getFormat(title, 0, 2));
      blankFragment =
          BlankFragment.newInstance(title, DealStatus.DEAL_DOING, bIncidentCount.getAckUser(),
              bIncidentCount.getAckAll());
    } else if (id == R.id.nav_dealed) {//已处理
      //item.setTitle(getFormat(title, 0, 2));
      blankFragment = BlankFragment.newInstance(title, DealStatus.DEAL_COMPLETE,
          bIncidentCount.getResolvedUser(), bIncidentCount.getResolvedAll());
    } else if (id == R.id.nav_people) {//成员
      blankFragment = MemberFragment.newInstance();
    } else if (id == R.id.nav_set) {//设置
      blankFragment = SettingFragment.newInstance();
    }
    fragmentTransaction.replace(R.id.fl, blankFragment);
    fragmentTransaction.commitAllowingStateLoss();
  }
}
