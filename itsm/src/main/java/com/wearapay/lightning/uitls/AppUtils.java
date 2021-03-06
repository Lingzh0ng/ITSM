package com.wearapay.lightning.uitls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lyz on 2017/7/26.
 */
public class AppUtils {
  public static boolean isWeixinAvilible(Context context) {
    final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
    List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
    if (pinfo != null) {
      for (int i = 0; i < pinfo.size(); i++) {
        String pn = pinfo.get(i).packageName;
        if (pn.equals("com.tencent.mm")) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Get 00:00:00 time of the give date.
   *
   * @param date give date.
   * @return 00:00:00 of the date.
   */
  public static Calendar startOfDate(Calendar date) {
    date.set(Calendar.HOUR_OF_DAY, 0);
    date.set(Calendar.MINUTE, 0);
    date.set(Calendar.SECOND, 0);
    date.set(Calendar.MILLISECOND, 0);
    return date;
  }

  public static Calendar endOfDate(Calendar date) {
    date.add(Calendar.DATE, -1);
    date = startOfDate(date);
    date.add(Calendar.MILLISECOND, -1);
    return date;
  }

  /**
   * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
   */
  public static int dip2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  /**
   * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
   */
  public static int px2dip(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }

  public static void startActvity(Activity activity, Intent intent) {

  }
}
