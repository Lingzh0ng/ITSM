package com.wearapay.lightning.ui.fragment.event.view;

import com.wearapay.lightning.base.mvp.IBaseView;
import com.wearapay.lightning.bean.IncidentDto;
import java.util.List;

/**
 * Created by lyz on 2017/9/11.
 */

public interface IItemView extends IBaseView {
  void onItemSuccess(List<IncidentDto> incidentDtos);

  void onItemFail();

}
