package com.wearapay.lightning.bean;

import com.wearapay.lightning.LConsts;

/**
 * Created by lyz on 2017/7/25.
 */
public class BIncidentTime {
  private long startTime;
  private long endTime;

  public BIncidentTime() {
  }

  public BIncidentTime(long endTime) {
    this.endTime = endTime;
    this.startTime = endTime - LConsts.MONTH_TIME;
  }

  public BIncidentTime(long startTime, long endTime) {

    this.startTime = startTime;
    this.endTime = endTime;
  }

  @Override public String toString() {
    return "BIncidentTime{" +
        "startTime=" + startTime +
        ", endTime=" + endTime +
        '}';
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }
}
