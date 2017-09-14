package com.wearapay.lightning.ui.fragment.statistical.view;

import com.wearapay.lightning.base.mvp.IBaseView;
import com.wearapay.lightning.bean.BCountIncidentByTime;
import java.util.List;

/**
 * Created by lyz on 2017/9/11.
 */

public interface IStatisticalView extends IBaseView {
  void displayStatistical(List<BCountIncidentByTime> bCountIncidentByTimes);

  void refreshUI();

  void dispalyFail();

}
