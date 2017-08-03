package com.wearapay.lightning.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lyz on 2017/8/1.
 */
public class BAppAutoDeploy implements Serializable{
  //private String STATUS_WAITING;
  //private String STATUS_PROCESS;
  //private String STATUS_SUCCESS;
  //private String STATUS_FAILED;
  private String changeNo;
  private String businessName;
  private String proposer;
  private long startTime;
  private long endTime;
  private String purpose;
  private List<BAutoDeployInfo> displayInfo;
  private List<BAutoDeploy> detaillog;
  private String deployLog;
  private BReleaseStatus status;

  public BReleaseStatus getStatus() {
    return status;
  }

  public void setStatus(BReleaseStatus status) {
    this.status = status;
  }

  @Override public String toString() {
    return "BAppAutoDeploy{" +
        "changeNo='" + changeNo + '\'' +
        ", businessName='" + businessName + '\'' +
        ", proposer='" + proposer + '\'' +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", purpose='" + purpose + '\'' +
        ", displayInfo=" + displayInfo +
        ", detaillog=" + detaillog +
        ", deployLog='" + deployLog + '\'' +
        '}';
  }

  public String getChangeNo() {
    return changeNo;
  }

  public void setChangeNo(String changeNo) {
    this.changeNo = changeNo;
  }

  public String getBusinessName() {
    return businessName;
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public String getProposer() {
    return proposer;
  }

  public void setProposer(String proposer) {
    this.proposer = proposer;
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

  public String getPurpose() {
    return purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }


  public String getDeployLog() {
    return deployLog;
  }

  public void setDeployLog(String deployLog) {
    this.deployLog = deployLog;
  }

  public List<BAutoDeployInfo> getDisplayInfo() {
    return displayInfo;
  }

  public void setDisplayInfo(List<BAutoDeployInfo> displayInfo) {
    this.displayInfo = displayInfo;
  }

  public List<BAutoDeploy> getDetaillog() {
    return detaillog;
  }

  public void setDetaillog(List<BAutoDeploy> detaillog) {
    this.detaillog = detaillog;
  }
}
