package com.wearapay.lightning.ui.fragment.statistical.presenter;

import android.content.Context;
import com.wearapay.lightning.base.mvp.BaseFragmentPresenter;
import com.wearapay.lightning.bean.BCountIncidentByTime;
import com.wearapay.lightning.bean.BIncidentTime;
import com.wearapay.lightning.net.ApiHelper;
import com.wearapay.lightning.net.BaseObserver;
import com.wearapay.lightning.ui.fragment.statistical.view.IStatisticalView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.List;

/**
 * Created by lyz on 2017/9/11.
 */

public class StatisticalPresenter extends BaseFragmentPresenter<IStatisticalView> {
  public StatisticalPresenter(Context mContext) {
    super(mContext);
  }

  public void getStatisticalInfo(BIncidentTime bIncidentTime) {
    view.showProgress();
    wrap(ApiHelper.getInstance().getIncidentByTime(bIncidentTime)).flatMap(
        new Function<List<BCountIncidentByTime>, ObservableSource<Boolean>>() {
          @Override public ObservableSource<Boolean> apply(
              @NonNull List<BCountIncidentByTime> bCountIncidentByTimes) throws Exception {
            view.displayStatistical(bCountIncidentByTimes);
            if (bCountIncidentByTimes.size() < 2) {
              return Observable.just(false);
            }
            return Observable.just(true);
          }
        }).subscribe(new BaseObserver<Boolean>(view) {
      @Override public void onNext(@NonNull Boolean aBoolean) {
        super.onNext(aBoolean);
        if (aBoolean) {
          view.refreshUI();
        } else {
          view.dispalyFail();
        }
      }

      @Override public void onError(@NonNull Throwable e) {
        super.onError(e);
        view.dispalyFail();
      }
    });
  }
}
