package com.wearapay.lightning.uitls;

import android.text.TextUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lyz on 2017/7/17.
 */
public class FormatUtlis {

  public static long getLongFormatTime(String date, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    Date dt2 = null;
    try {
      dt2 = sdf.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return dt2.getTime();
  }

  public static String getStringFormatTime(Long date, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
  }

  public static String chargeStringFormatTime(String date, String oldFormat, String newFormat) {
    if (TextUtils.isEmpty(date)) return date;
    long longFormatTime = getLongFormatTime(date, oldFormat);
    return getStringFormatTime(longFormatTime, newFormat);
  }
}
