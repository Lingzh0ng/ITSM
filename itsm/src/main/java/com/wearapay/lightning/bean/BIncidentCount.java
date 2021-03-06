package com.wearapay.lightning.bean;

/**
 * Created by lyz on 2017/7/13.
 */
public class BIncidentCount {
  private int activityUser;
  private int activityAll;
  private int ackUser;
  private int ackAll;
  private int resolvedUser;
  private int resolvedAll;

  private int userChangeMgmt;
  private int allChangeMgmt;

  private int userZSCChangeMgmt;
  private int allZSCChangeMgmt;

  public int getUserZSCChangeMgmt() {
    return userZSCChangeMgmt;
  }

  public void setUserZSCChangeMgmt(int userZSCChangeMgmt) {
    this.userZSCChangeMgmt = userZSCChangeMgmt;
  }

  public int getAllZSCChangeMgmt() {
    return allZSCChangeMgmt;
  }

  public void setAllZSCChangeMgmt(int allZSCChangeMgmt) {
    this.allZSCChangeMgmt = allZSCChangeMgmt;
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

  @Override public String toString() {
    return "BIncidentCount{" +
        "activityUser=" + activityUser +
        ", activityAll=" + activityAll +
        ", ackUser=" + ackUser +
        ", ackAll=" + ackAll +
        ", resolvedUser=" + resolvedUser +
        ", resolvedAll=" + resolvedAll +
        '}';
  }

  public int getActivityUser() {
    return activityUser;
  }

  public void setActivityUser(int activityUser) {
    this.activityUser = activityUser;
  }

  public int getActivityAll() {
    return activityAll;
  }

  public void setActivityAll(int activityAll) {
    this.activityAll = activityAll;
  }

  public int getAckUser() {
    return ackUser;
  }

  public void setAckUser(int ackUser) {
    this.ackUser = ackUser;
  }

  public int getAckAll() {
    return ackAll;
  }

  public void setAckAll(int ackAll) {
    this.ackAll = ackAll;
  }

  public int getResolvedUser() {
    return resolvedUser;
  }

  public void setResolvedUser(int resolvedUser) {
    this.resolvedUser = resolvedUser;
  }

  public int getResolvedAll() {
    return resolvedAll;
  }

  public void setResolvedAll(int resolvedAll) {
    this.resolvedAll = resolvedAll;
  }
}
