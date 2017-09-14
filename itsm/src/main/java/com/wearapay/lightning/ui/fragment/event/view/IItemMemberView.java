package com.wearapay.lightning.ui.fragment.event.view;

import com.wearapay.lightning.base.mvp.IBaseView;
import com.wearapay.lightning.bean.IncidentDto;
import com.wearapay.lightning.bean.UserConfDto;
import java.util.List;

/**
 * Created by lyz on 2017/9/11.
 */

public interface IItemMemberView extends IBaseView {
  void onItemMemberSuccess(List<UserConfDto> userConfDtos);

  void onItemMemberFail();

  void onEventCompileSuccess(IncidentDto incidentDto);

  void onEventCompileFail();

}
