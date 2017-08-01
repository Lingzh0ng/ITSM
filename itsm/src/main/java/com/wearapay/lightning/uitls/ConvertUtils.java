package com.wearapay.lightning.uitls;

import com.wearapay.lightning.App;
import com.wearapay.lightning.R;

/**
 * Created by lyz on 2017/8/1.
 */
public class ConvertUtils {
  public static String NumberConvertRome(int number) {
    String romeNumber = "";
    switch (number) {
      case 1:
        romeNumber = App.app.getString(R.string.text_number_1);
        break;
      case 2:
        romeNumber = App.app.getString(R.string.text_number_2);
        break;
      case 3:
        romeNumber = App.app.getString(R.string.text_number_3);
        break;
      case 4:
        romeNumber = App.app.getString(R.string.text_number_4);
        break;
    }

    return romeNumber;
  }
}
