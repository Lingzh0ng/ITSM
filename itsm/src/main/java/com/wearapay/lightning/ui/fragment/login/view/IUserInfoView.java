package com.wearapay.lightning.ui.fragment.login.view;

import com.wearapay.lightning.base.mvp.IBaseView;

/**
 * Created by lyz on 2017/9/11.
 */

public interface IUserInfoView extends IBaseView {
  void logoutSuccess();

  void logoutFail();

}
