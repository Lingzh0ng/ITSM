package com.wearapay.lightning.bean;

import java.util.List;

/**
 * Created by lyz on 2017/7/25.
 */
public class BCountIncidentByTime {
  private int level;//事件级别
  private List<BCountIncident> countInfo;//统计信息

  @Override public String toString() {
    return "BCountIncidentByTime{" +
        "level=" + level +
        ", countInfo=" + countInfo +
        '}';
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public List<BCountIncident> getCountInfo() {
    return countInfo;
  }

  public void setCountInfo(List<BCountIncident> countInfo) {
    this.countInfo = countInfo;
  }
}
