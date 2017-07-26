package com.wearapay.lightning.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import java.util.Calendar;

/**
 * Created by leo on 3/4/16.
 */
public class FixedDatePickerDialog extends DatePickerDialog {
  public FixedDatePickerDialog(Context context, OnDateSetListener callBack, int year,
      int monthOfYear, int dayOfMonth) {
    super(context, callBack, year, monthOfYear, dayOfMonth);
  }

  public FixedDatePickerDialog(Context context, int theme, OnDateSetListener listener, int year,
      int monthOfYear, int dayOfMonth) {
    super(context, theme, listener, year, monthOfYear, dayOfMonth);
  }

  public FixedDatePickerDialog(Context context, int theme, Calendar calendar,
      OnDateSetListener listener) {
    super(context, theme, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH));
  }

  @Override public void show() {
    super.show();
    // added for MeiZu issue
    getDatePicker().setEnabled(true);
  }

  @Override protected void onStop() {
    //super.onStop();
  }
}
