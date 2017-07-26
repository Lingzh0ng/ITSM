package com.wearapay.lightning.bean;

/**
 * Created by lyz on 2017/7/25.
 */
public class BCountIncident {
  private String alarmContent;
  private int incidentCount;
  private String businessName;
  private String entityIp;
  private String entityName;
  private String longestTime;
  private String averageTime;
  private String lastDay;

  @Override public String toString() {
    return "BCountIncident{" +
        "alarmContent='" + alarmContent + '\'' +
        ", incidentCount=" + incidentCount +
        ", businessName='" + businessName + '\'' +
        ", entityIp='" + entityIp + '\'' +
        ", entityName='" + entityName + '\'' +
        ", longestTime='" + longestTime + '\'' +
        ", averageTime='" + averageTime + '\'' +
        ", lastDay='" + lastDay + '\'' +
        '}';
  }

  public String getAlarmContent() {
    return alarmContent;
  }

  public void setAlarmContent(String alarmContent) {
    this.alarmContent = alarmContent;
  }

  public int getIncidentCount() {
    return incidentCount;
  }

  public void setIncidentCount(int incidentCount) {
    this.incidentCount = incidentCount;
  }

  public String getBusinessName() {
    return businessName;
  }

  public void setBusinessName(String businessName) {
    this.businessName = businessName;
  }

  public String getEntityIp() {
    return entityIp;
  }

  public void setEntityIp(String entityIp) {
    this.entityIp = entityIp;
  }

  public String getEntityName() {
    return entityName;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public String getLongestTime() {
    return longestTime;
  }

  public void setLongestTime(String longestTime) {
    this.longestTime = longestTime;
  }

  public String getAverageTime() {
    return averageTime;
  }

  public void setAverageTime(String averageTime) {
    this.averageTime = averageTime;
  }

  public String getLastDay() {
    return lastDay;
  }

  public void setLastDay(String lastDay) {
    this.lastDay = lastDay;
  }
}
