package com.wearapay.lightning.ui.fragment.event.presenter;

import android.content.Context;
import com.wearapay.lightning.LConsts;
import com.wearapay.lightning.R;
import com.wearapay.lightning.base.mvp.BaseFragmentPresenter;
import com.wearapay.lightning.bean.BIncidentRemark;
import com.wearapay.lightning.bean.DealStatus;
import com.wearapay.lightning.bean.IncidentDto;
import com.wearapay.lightning.bean.UserConfDto;
import com.wearapay.lightning.net.ApiHelper;
import com.wearapay.lightning.net.BaseObserver;
import com.wearapay.lightning.ui.fragment.event.view.IItemMemberView;
import io.reactivex.annotations.NonNull;
import java.util.List;

/**
 * Created by lyz on 2017/9/11.
 */

public class ItemMemberPresenter extends BaseFragmentPresenter<IItemMemberView> {
  public ItemMemberPresenter(Context mContext) {
    super(mContext);
  }

  public void eventCompile(String id, DealStatus dealStatus, IncidentDto incidentDto) {
    view.showProgress();
    int status = LConsts.INCIDENT_OCCUR;
    switch (dealStatus) {
      case DEAL_WAIT:
        status = LConsts.INCIDENT_OCCUR;
        break;
      case DEAL_DOING:
        status = LConsts.INCIDENT_HANDLE;
        break;
    }
    wrap(ApiHelper.getInstance()
        .eventCompile(incidentDto.getId(), status, id, new BIncidentRemark())).subscribe(
        new BaseObserver<IncidentDto>(view) {
          @Override public void onNext(@NonNull IncidentDto incidentDto) {
            super.onNext(incidentDto);
            view.hideProgress();
            view.onEventCompileSuccess(incidentDto);
          }

          @Override protected void handlerError(Throwable e) {
            super.handlerError(e);
            view.showMessage(R.string.e_general);
            view.hideProgress();
          }
        });
  }

  public void getAllMember() {
    wrap(ApiHelper.getInstance().getAllUser()).subscribe(new BaseObserver<List<UserConfDto>>(view) {
      @Override public void onNext(@NonNull List<UserConfDto> userConfDtos) {
        super.onNext(userConfDtos);
        view.onItemMemberSuccess(userConfDtos);
      }

      @Override public void onError(@NonNull Throwable e) {
        super.onError(e);
        view.onEventCompileFail();
      }
    });
  }
}
