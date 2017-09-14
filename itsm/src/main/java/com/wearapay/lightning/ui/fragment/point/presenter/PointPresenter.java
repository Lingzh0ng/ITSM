package com.wearapay.lightning.ui.fragment.point.presenter;

import android.content.Context;
import com.wearapay.lightning.base.mvp.BaseFragmentPresenter;
import com.wearapay.lightning.bean.BChartInfo;
import com.wearapay.lightning.net.ApiHelper;
import com.wearapay.lightning.net.BaseObserver;
import com.wearapay.lightning.ui.fragment.point.view.IPointView;
import io.reactivex.annotations.NonNull;
import java.util.List;

/**
 * Created by lyz on 2017/9/13.
 */

public class PointPresenter extends BaseFragmentPresenter<IPointView> {
  public PointPresenter(Context mContext) {
    super(mContext);
  }

  public void getPointDate() {
    view.showProgress();
    wrap(ApiHelper.getInstance().getIncidentCharts()).subscribe(
        new BaseObserver<List<BChartInfo>>(view) {
          @Override public void onNext(@NonNull List<BChartInfo> bChartInfos) {
            super.onNext(bChartInfos);
            //TODO for test
            //for (int i = 0; i < bChartInfos.size(); i++) {
            //  if (i % 2 == 0) {
            //    bChartInfos.get(i).setApplicationNameC("wlf");
            //  } else {
            //    bChartInfos.get(i).setApplicationNameC("wzf");
            //  }
            //}
            view.displayPoint(bChartInfos);
          }

          @Override public void onError(@NonNull Throwable e) {
            super.onError(e);
            view.displayFail();
          }
        });
  }
}
