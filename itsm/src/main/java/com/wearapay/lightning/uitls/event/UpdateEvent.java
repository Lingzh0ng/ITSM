package com.wearapay.lightning.uitls.event;

/**
 * Created by lyz on 2017/7/13.
 */

public class UpdateEvent {
  private boolean isUpdate;

  public boolean isLogin() {
    return isLogin;
  }

  public void setLogin(boolean login) {
    isLogin = login;
  }

  private boolean isLogin;

  @Override public String toString() {
    return "UpdateEvent{" +
        "isUpdate=" + isUpdate +
        '}';
  }

  public UpdateEvent(boolean isUpdate) {
    this.isUpdate = isUpdate;
  }

  public UpdateEvent(boolean isUpdate, boolean isLogin) {
    this.isUpdate = isUpdate;
  }

  public boolean isUpdate() {

    return isUpdate;
  }

  public void setUpdate(boolean update) {
    isUpdate = update;
  }
}
