package com.wearapay.lightning.ui.fragment.release.view;

import com.wearapay.lightning.base.mvp.IBaseView;
import com.wearapay.lightning.bean.BAppAutoDeploy;
import java.util.List;

/**
 * Created by lyz on 2017/9/11.
 */

public interface IReleaseListView extends IBaseView {
  void displayReleaseList(List<BAppAutoDeploy> bAppAutoDeploys);

  void getReleaseListFail();

}
