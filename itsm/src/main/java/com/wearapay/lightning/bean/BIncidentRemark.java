package com.wearapay.lightning.bean;

/**
 * Created by lyz on 2017/7/17.
 */
public class BIncidentRemark {
  private String remark;

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  @Override public String toString() {
    return "BIncidentRemark{" +
        "remark='" + remark + '\'' +
        '}';
  }
}
