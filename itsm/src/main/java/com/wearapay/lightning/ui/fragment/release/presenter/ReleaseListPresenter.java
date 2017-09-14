package com.wearapay.lightning.ui.fragment.release.presenter;

import android.content.Context;
import com.wearapay.lightning.LConsts;
import com.wearapay.lightning.base.mvp.BaseFragmentPresenter;
import com.wearapay.lightning.bean.BAppAutoDeploy;
import com.wearapay.lightning.net.ApiHelper;
import com.wearapay.lightning.net.BaseObserver;
import com.wearapay.lightning.ui.fragment.release.view.IReleaseListView;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import java.util.List;

/**
 * Created by lyz on 2017/9/11.
 */

public class ReleaseListPresenter extends BaseFragmentPresenter<IReleaseListView> {
  public ReleaseListPresenter(Context mContext) {
    super(mContext);
  }

  public void getReleaseLists(LConsts.ReleaseEnvironment environment, int position) {
    Observable<List<BAppAutoDeploy>> observable;
    if (position == 0) {
      observable = ApiHelper.getInstance().getDeployUser(environment.getEnv());
    } else {
      observable = ApiHelper.getInstance().getDeployAll(environment.getEnv());
    }
    //if (environment == LConsts.ReleaseEnvironment.SC) {
    //
    //} else {
    //  if (position == 0) {
    //    observable = ApiHelper.getInstance().getZSCDeployUser();
    //  } else {
    //    observable = ApiHelper.getInstance().getZSCDeployAll();
    //  }
    //}
    wrap(observable).subscribe(new BaseObserver<List<BAppAutoDeploy>>(view) {
      @Override public void onNext(@NonNull List<BAppAutoDeploy> bAppAutoDeploys) {
        super.onNext(bAppAutoDeploys);
        view.displayReleaseList(bAppAutoDeploys);
      }

      @Override public void onError(@NonNull Throwable e) {
        super.onError(e);
        view.getReleaseListFail();
      }
    });
  }
}
