package com.wearapay.lightning.ui.fragment.point.view;

import com.wearapay.lightning.base.mvp.IBaseView;
import com.wearapay.lightning.bean.BChartInfo;
import java.util.List;

/**
 * Created by lyz on 2017/9/13.
 */

public interface IPointView extends IBaseView {
  void displayPoint(List<BChartInfo> bChartInfos);

  void displayFail();
}
