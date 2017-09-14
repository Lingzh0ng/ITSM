package com.wearapay.lightning.ui.fragment.login.presenter;

import android.content.Context;
import com.google.gson.Gson;
import com.wearapay.lightning.base.mvp.BaseFragmentPresenter;
import com.wearapay.lightning.net.ApiHelper;
import com.wearapay.lightning.net.BaseObserver;
import com.wearapay.lightning.net.callback.PPCodedException;
import com.wearapay.lightning.net.model.PPResultBean;
import com.wearapay.lightning.ui.fragment.login.view.IUserInfoView;
import io.reactivex.annotations.NonNull;
import java.io.IOException;
import okhttp3.ResponseBody;

/**
 * Created by lyz on 2017/9/11.
 */

public class UserLogoutPresenter extends BaseFragmentPresenter<IUserInfoView> {
  public UserLogoutPresenter(Context mContext) {
    super(mContext);
  }

  public void logout() {
    view.showProgress();
    wrap(ApiHelper.getInstance().logout()).subscribe(new BaseObserver<ResponseBody>(view) {
      @Override public void onNext(@NonNull ResponseBody aBoolean) {
        super.onNext(aBoolean);
        Gson gson = new Gson();
        try {
          PPResultBean result = gson.fromJson(aBoolean.string(), PPResultBean.class);
          if (result.getStatus().equalsIgnoreCase(PPResultBean.SUCCESS)) {
            ApiHelper.getInstance().localLogout();
            view.logoutSuccess();
          } else {
            throw new PPCodedException(result.getErrors());
          }
        } catch (IOException e) {
          e.printStackTrace();
          ApiHelper.getInstance().localLogout();
          view.logoutSuccess();
        }
      }

      @Override protected void handlerError(Throwable e) {
        super.handlerError(e);
        view.logoutFail();
      }
    });
  }
}
