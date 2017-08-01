package com.wearapay.lightning.bean;

/**
 * Created by lyz on 2017/8/1.
 */
public class BChangeCount {
  private int userChangeMgmt;
  private int allChangeMgmt;

  @Override public String toString() {
    return "BChangeCount{" +
        "userChangeMgmt=" + userChangeMgmt +
        ", allChangeMgmt=" + allChangeMgmt +
        '}';
  }

  public int getUserChangeMgmt() {
    return userChangeMgmt;
  }

  public void setUserChangeMgmt(int userChangeMgmt) {
    this.userChangeMgmt = userChangeMgmt;
  }

  public int getAllChangeMgmt() {
    return allChangeMgmt;
  }

  public void setAllChangeMgmt(int allChangeMgmt) {
    this.allChangeMgmt = allChangeMgmt;
  }
}
