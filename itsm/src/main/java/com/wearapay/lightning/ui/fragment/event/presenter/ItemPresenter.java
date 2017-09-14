package com.wearapay.lightning.ui.fragment.event.presenter;

import android.content.Context;
import com.wearapay.lightning.base.mvp.BaseFragmentPresenter;
import com.wearapay.lightning.bean.DealStatus;
import com.wearapay.lightning.bean.IncidentDto;
import com.wearapay.lightning.net.ApiHelper;
import com.wearapay.lightning.net.BaseObserver;
import com.wearapay.lightning.ui.fragment.event.view.IItemView;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import java.util.List;

/**
 * Created by lyz on 2017/9/11.
 */

public class ItemPresenter extends BaseFragmentPresenter<IItemView> {
  public ItemPresenter(Context mContext) {
    super(mContext);
  }

  public void getEventLists(DealStatus dealStatus, int position) {
    Observable<List<IncidentDto>> eventObservable = null;
    switch (dealStatus) {
      case DEAL_WAIT:
        if (position == 0) {
          eventObservable = ApiHelper.getInstance().getWaitUserEvent();
        } else {
          eventObservable = ApiHelper.getInstance().getWaitAllEvent();
        }
        break;
      case DEAL_DOING:
        if (position == 0) {
          eventObservable = ApiHelper.getInstance().getActUserEvent();
        } else {
          eventObservable = ApiHelper.getInstance().getActAllEvent();
        }
        break;
      case DEAL_COMPLETE:
        if (position == 0) {
          eventObservable = ApiHelper.getInstance().getResolveUserEvent();
        } else {
          eventObservable = ApiHelper.getInstance().getResolvedAllEvent();
        }
        break;
      default:
        break;
    }
    if (eventObservable != null) {
      wrap(eventObservable).subscribe(new BaseObserver<List<IncidentDto>>(view) {
        @Override public void onNext(@NonNull List<IncidentDto> incidentDtos) {
          view.onItemSuccess(incidentDtos);
        }

        @Override public void onError(@NonNull Throwable e) {
          super.onError(e);
          view.onItemFail();
        }
      });
    }
  }
}
