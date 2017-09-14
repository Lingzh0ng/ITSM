package com.wearapay.lightning.net;

import android.content.Context;
import android.content.Intent;
import com.wearapay.lightning.LConsts;
import com.wearapay.lightning.R;
import com.wearapay.lightning.base.mvp.IBaseView;
import com.wearapay.lightning.exception.NotLoginException;
import com.wearapay.lightning.net.callback.PPCodedException;
import com.wearapay.lightning.net.model.PPCodeMessage;
import com.wearapay.lightning.ui.LoginActivity;
import com.wearapay.lightning.uitls.ResourceUtil;
import com.wearapay.lightning.uitls.ToastUtils;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import java.net.SocketTimeoutException;
import java.util.Collection;
import retrofit2.HttpException;

/**
 * Created by lyz54 on 2017/6/28.
 */

public abstract class BaseObserver<T> implements Observer<T> {
  private Context context;
  private IBaseView view;

  public BaseObserver(IBaseView baseView) {
    this.view = baseView;
    this.context = view.getUseContext();
  }

  @Override public void onSubscribe(@NonNull Disposable d) {

  }

  @Override public void onNext(@NonNull T t) {
    if (view != null) {
      view.hideProgress();
    }
  }

  @Override public void onError(@NonNull Throwable e) {
    if (view != null) {
      view.hideProgress();
    }
    e.printStackTrace();
    if (e instanceof NotLoginException) {
      context.startActivity(new Intent(context, LoginActivity.class));
      return;
    } else if (e instanceof java.net.ConnectException
        || e instanceof java.net.SocketTimeoutException) {
      int id = ResourceUtil.getStringId(context, LConsts.ERROR_NETWORK);
      if (id > 0) {
        ToastUtils.showShortSafe(id);
      }
    } else if (e instanceof PPCodedException) {
      PPCodedException codedException = (PPCodedException) e;
      Collection<PPCodeMessage> messages = codedException.getMessages();
      if (messages != null && messages.size() > 0) {
        for (PPCodeMessage m : messages) {
          int id = ResourceUtil.getStringId(context, m.getCode());
          if (id > 0) {
            ToastUtils.showShortSafe(id);
          } else {
            ToastUtils.showShortSafe(R.string.e_general);
          }
          if (LConsts.TOKEN_EXPIRED.equals(m.getCode()) || LConsts.UNKONWN_USER.equals(
              m.getCode())) {
            ApiHelper.getInstance().storeUserId("");
            context.startActivity(new Intent(context, LoginActivity.class));
          }
        }
      }
    } else if (e instanceof HttpException || e instanceof SocketTimeoutException) {
      ToastUtils.showShort(R.string.error_network);
    } else {
      System.out.println("sever error");
      handlerError(e);
    }
  }

  protected void handlerError(Throwable e) {

  }

  @Override public void onComplete() {

  }
}
