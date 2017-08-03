package com.wearapay.lightning;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.wearapay.lightning.net.ApiHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyz on 2017/6/27.
 */
public class App extends Application {

  public static App app;

  private List<Activity> activityList = new ArrayList<>();
  private int activityStatus = 0;

  @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH) @Override public void onCreate() {
    super.onCreate();
    app = this;
    ApiHelper.getInstance().init(this);
    registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
      @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activityList.add(activity);
      }

      @Override public void onActivityStarted(Activity activity) {
        activityStatus++;
      }

      @Override public void onActivityResumed(Activity activity) {

      }

      @Override public void onActivityPaused(Activity activity) {

      }

      @Override public void onActivityStopped(Activity activity) {
        activityStatus--;
      }

      @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

      }

      @Override public void onActivityDestroyed(Activity activity) {
        activityList.remove(activity);
      }
    });
    TwinklingRefreshLayout.setDefaultHeader(SinaRefreshView.class.getName());

    //final Thread.UncaughtExceptionHandler defaultExceptionHandler =
    //    Thread.getDefaultUncaughtExceptionHandler();
    //Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
    //  @Override public void uncaughtException(Thread thread, Throwable throwable) {
    //    defaultExceptionHandler.uncaughtException(thread, throwable);
    //  }
    //});
  }

  public List<Activity> getActivityList() {
    return activityList;
  }

  public Activity getCurrentActivity() {
    return activityList.get(activityList.size() - 1);
  }

  public boolean isAppHide() {
    return activityStatus == 1;
  }

  public void exitApp() {
    for (int i = 0; i < activityList.size(); i++) {
      activityList.get(i).finish();
    }
  }
}